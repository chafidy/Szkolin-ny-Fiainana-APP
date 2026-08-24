package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Planche
import com.example.data.repository.BookData
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.ReaderUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    readerState: ReaderUiState,
    onPreviousPlanche: () -> Unit,
    onNextPlanche: () -> Unit,
    onToggleRead: (String, Int) -> Unit,
    onToggleBookmark: (String, Int) -> Unit,
    onSaveNote: (String, Int, String) -> Unit,
    onBackToLibrary: () -> Unit,
    onNavigateToQuiz: (Int) -> Unit,
    onNavigateToLexique: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentCahier = BookData.cahiers.find { it.id == readerState.currentCahierId } ?: BookData.cahiers.first()
    val planches = BookData.getPlanchesForCahier(currentCahier.id)
    val currentPlanche = planches.getOrNull(readerState.currentPlancheIndex) ?: planches.firstOrNull()

    var showNoteDialog by remember { mutableStateOf(false) }
    var currentNoteText by remember { mutableStateOf("") }

    LaunchedEffect(currentPlanche?.id) {
        currentNoteText = readerState.userNotes[currentPlanche?.id] ?: ""
    }

    if (currentPlanche == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Planche introuvable", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }

    val isRead = readerState.readPlanchesIds.contains(currentPlanche.id)
    val isBookmarked = readerState.bookmarkedIds.contains(currentPlanche.id)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Cahier ${currentCahier.number} · ${currentCahier.title}",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MinimalTextPrimary
                            ),
                            maxLines = 1
                        )
                        Text(
                            text = "Planche ${readerState.currentPlancheIndex + 1} / ${planches.size}",
                            style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackToLibrary,
                        modifier = Modifier.testTag("reader_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour à la bibliothèque",
                            tint = MinimalTextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showNoteDialog = true },
                        modifier = Modifier.testTag("reader_note_button")
                    ) {
                        Icon(
                            imageVector = if (currentNoteText.isNotEmpty()) Icons.Default.NoteAlt else Icons.Outlined.EditNote,
                            contentDescription = "Ajouter une note personnelle",
                            tint = if (currentNoteText.isNotEmpty()) MinimalPrimary else MinimalTextSecondary
                        )
                    }
                    IconButton(
                        onClick = { onToggleBookmark(currentPlanche.id, currentCahier.id) },
                        modifier = Modifier.testTag("reader_bookmark_button")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Marquer en favori",
                            tint = if (isBookmarked) MinimalPrimary else MinimalTextSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MinimalBackground
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars),
                color = MinimalSurfaceContainer,
                border = BorderStroke(1.dp, MinimalOutline.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onPreviousPlanche,
                        enabled = readerState.currentPlancheIndex > 0 || readerState.currentCahierId > 1,
                        modifier = Modifier.testTag("reader_prev_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "Planche précédente",
                            tint = if (readerState.currentPlancheIndex > 0 || readerState.currentCahierId > 1) MinimalTextPrimary else MinimalOutline
                        )
                    }

                    // Centered Action: Mark As Read Toggle
                    Button(
                        onClick = { onToggleRead(currentPlanche.id, currentCahier.id) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isRead) VertDoux else Grenat,
                            contentColor = if (isRead) VertSucces else Blanc
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("reader_toggle_read_button")
                    ) {
                        Icon(
                            imageVector = if (isRead) Icons.Default.CheckCircle else Icons.Outlined.CheckCircleOutline,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = if (isRead) VertSucces else Blanc
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isRead) "Lue" else "Marquer lu",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isRead) VertSucces else Blanc
                            ),
                            maxLines = 1,
                            softWrap = false
                        )
                    }

                    IconButton(
                        onClick = onNextPlanche,
                        enabled = readerState.currentPlancheIndex < planches.size - 1 || readerState.currentCahierId < 8,
                        modifier = Modifier.testTag("reader_next_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Planche suivante",
                            tint = if (readerState.currentPlancheIndex < planches.size - 1 || readerState.currentCahierId < 8) MinimalTextPrimary else MinimalOutline
                        )
                    }
                }
            }
        },
        containerColor = MinimalBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp)
        ) {
            // Planche Meta Section Badge
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BadgePill(
                        text = currentPlanche.sectionNumber,
                        backgroundColor = MinimalSecondaryContainer,
                        textColor = MinimalOnPrimaryContainer
                    )

                    BadgePill(
                        text = "Page ${currentPlanche.pageNumber}",
                        backgroundColor = MinimalSurfaceContainer,
                        textColor = MinimalTextSecondary
                    )
                }
            }

            // Title & Accroche Header
            item {
                Column {
                    Text(
                        text = currentPlanche.title,
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontSize = (24 * readerState.textScale).sp,
                            lineHeight = (30 * readerState.textScale).sp,
                            fontWeight = FontWeight.Bold,
                            color = MinimalTextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = currentPlanche.accroche,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = (15 * readerState.textScale).sp,
                            lineHeight = (22 * readerState.textScale).sp,
                            color = MinimalTextSecondary
                        )
                    )
                }
            }

            // Visual Diagram / Schema if present
            if (currentPlanche.schema != null) {
                item {
                    VisualSchemaView(schema = currentPlanche.schema)
                }
            }

            // Key Cards (Numbered or labeled concept cards)
            if (currentPlanche.keyCards.isNotEmpty()) {
                items(currentPlanche.keyCards) { card ->
                    KeyCardItem(card = card)
                }
            }

            // Detailed Encadrés Blancs / Boxes
            if (currentPlanche.detailedBoxes.isNotEmpty()) {
                items(currentPlanche.detailedBoxes) { box ->
                    DetailedBoxCard(box = box)
                }
            }

            // Bottom Signature Bandeau Bordeaux
            if (currentPlanche.bottomAdvice != null) {
                item {
                    BandeauBordeaux(advice = currentPlanche.bottomAdvice)
                }
            }

            // User Note Card if saved
            if (currentNoteText.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSurfaceContainer),
                        border = BorderStroke(1.dp, MinimalOutline)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.EditNote,
                                    contentDescription = null,
                                    tint = MinimalPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "MA NOTE PERSONNELLE",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MinimalTextPrimary,
                                        letterSpacing = 0.5.sp
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = currentNoteText,
                                style = MaterialTheme.typography.bodyMedium.copy(color = MinimalTextPrimary)
                            )
                        }
                    }
                }
            }

            // Quick Access to Lexique and Quiz for this Cahier
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { onNavigateToLexique(currentCahier.id) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("reader_goto_lexique"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MinimalTextPrimary),
                        border = BorderStroke(1.dp, MinimalOutline),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp), tint = MinimalPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Lexique",
                            maxLines = 1,
                            softWrap = false
                        )
                    }

                    Button(
                        onClick = { onNavigateToQuiz(currentCahier.id) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("reader_goto_quiz"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Grenat,
                            contentColor = Blanc
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp), tint = Blanc)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Quiz Cahier",
                            color = Blanc,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                }
            }
        }
    }

    // Note Input Dialog
    if (showNoteDialog) {
        var tempNote by remember { mutableStateOf(currentNoteText) }
        AlertDialog(
            onDismissRequest = { showNoteDialog = false },
            title = {
                Text(
                    text = "Note personnelle",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GrenatProfond
                    )
                )
            },
            text = {
                Column {
                    Text(
                        text = "Écris tes réflexions, idées ou exemples personnels sur cette planche :",
                        style = MaterialTheme.typography.bodySmall.copy(color = EncreMoyenne)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = tempNote,
                        onValueChange = { tempNote = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .testTag("note_input_field"),
                        placeholder = { Text("Mes notes...") },
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 6
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        currentNoteText = tempNote
                        onSaveNote(currentPlanche.id, currentCahier.id, tempNote)
                        showNoteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Grenat,
                        contentColor = Blanc
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Enregistrer", color = Blanc, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showNoteDialog = false }) {
                    Text("Annuler", color = MinimalTextSecondary)
                }
            },
            containerColor = MinimalSurface,
            shape = RoundedCornerShape(20.dp)
        )
    }
}
