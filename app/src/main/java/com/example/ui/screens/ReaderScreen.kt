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
import androidx.compose.ui.text.style.TextOverflow
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
    onChangeTextScale: (Float) -> Unit = {},
    onSelectPlanche: (Int, Int) -> Unit = { _, _ -> },
    onBackToLibrary: () -> Unit,
    onNavigateToQuiz: (Int) -> Unit,
    onNavigateToLexique: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentCahier = BookData.cahiers.find { it.id == readerState.currentCahierId } ?: BookData.cahiers.first()
    val planches = BookData.getPlanchesForCahier(currentCahier.id)
    val currentPlanche = planches.getOrNull(readerState.currentPlancheIndex) ?: planches.firstOrNull()

    var showNoteDialog by remember { mutableStateOf(false) }
    var showTextScaleDialog by remember { mutableStateOf(false) }
    var showSommaireSignetsSheet by remember { mutableStateOf(false) }
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
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { showSommaireSignetsSheet = true }
                            .padding(vertical = 4.dp, horizontal = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Cahier ${currentCahier.number} · ${currentCahier.title}",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalTextPrimary
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Ouvrir le sommaire",
                                tint = MinimalTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = "Planche ${readerState.currentPlancheIndex + 1} / ${planches.size} · Toucher pour le sommaire",
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
                        onClick = { showSommaireSignetsSheet = true },
                        modifier = Modifier.testTag("reader_sommaire_signets_button")
                    ) {
                        BadgedBox(
                            badge = {
                                if (readerState.bookmarkedIds.isNotEmpty()) {
                                    Badge(
                                        containerColor = Grenat,
                                        contentColor = Blanc
                                    ) {
                                        Text("${readerState.bookmarkedIds.size}")
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmarks,
                                contentDescription = "Sommaire & Signets favoris",
                                tint = if (readerState.bookmarkedIds.isNotEmpty()) Grenat else MinimalTextSecondary
                            )
                        }
                    }
                    IconButton(
                        onClick = { showTextScaleDialog = true },
                        modifier = Modifier.testTag("reader_text_size_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatSize,
                            contentDescription = "Régler la taille du texte",
                            tint = if (readerState.textScale != 1.0f) Grenat else MinimalTextSecondary
                        )
                    }
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
                            contentDescription = if (isBookmarked) "Retirer des favoris" else "Épingler en favori",
                            tint = if (isBookmarked) Grenat else MinimalTextSecondary
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
                    KeyCardItem(card = card, textScale = readerState.textScale)
                }
            }

            // Detailed Encadrés Blancs / Boxes
            if (currentPlanche.detailedBoxes.isNotEmpty()) {
                items(currentPlanche.detailedBoxes) { box ->
                    DetailedBoxCard(box = box, textScale = readerState.textScale)
                }
            }

            // Bottom Signature Bandeau Bordeaux
            if (currentPlanche.bottomAdvice != null) {
                item {
                    BandeauBordeaux(advice = currentPlanche.bottomAdvice, textScale = readerState.textScale)
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

    // Text Size / Zoom Dialog
    if (showTextScaleDialog) {
        val scaleOptions = listOf(
            0.85f to "Petit (85%)",
            1.0f to "Normal (100%)",
            1.15f to "Grand (115%)",
            1.30f to "Très grand (130%)"
        )

        AlertDialog(
            onDismissRequest = { showTextScaleDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FormatSize,
                        contentDescription = null,
                        tint = Grenat,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Taille du texte & Zoom",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MinimalTextPrimary
                        )
                    )
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Ajuste la taille de la police pour un confort de lecture optimal.",
                        style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextSecondary),
                        textAlign = TextAlign.Center
                    )

                    // Quick A- / A+ Controls
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSecondaryContainer),
                        border = BorderStroke(1.dp, MinimalOutline)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = {
                                    val newScale = (readerState.textScale - 0.10f).coerceIn(0.85f, 1.35f)
                                    onChangeTextScale(newScale)
                                },
                                shape = RoundedCornerShape(10.dp),
                                enabled = readerState.textScale > 0.85f,
                                modifier = Modifier.testTag("decrease_text_size_btn")
                            ) {
                                Text("A-", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${(readerState.textScale * 100).toInt()}%",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Grenat
                                    )
                                )
                                Text(
                                    text = when {
                                        readerState.textScale <= 0.90f -> "Petit"
                                        readerState.textScale <= 1.05f -> "Normal"
                                        readerState.textScale <= 1.20f -> "Grand"
                                        else -> "Très grand"
                                    },
                                    style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)
                                )
                            }

                            Button(
                                onClick = {
                                    val newScale = (readerState.textScale + 0.10f).coerceIn(0.85f, 1.35f)
                                    onChangeTextScale(newScale)
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Grenat, contentColor = Blanc),
                                enabled = readerState.textScale < 1.35f,
                                modifier = Modifier.testTag("increase_text_size_btn")
                            ) {
                                Text("A+", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Blanc)
                            }
                        }
                    }

                    // Presets Selection Grid
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        scaleOptions.forEach { (scale, label) ->
                            val isSelected = kotlin.math.abs(readerState.textScale - scale) < 0.05f
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) Grenat else MinimalSurfaceContainer)
                                    .border(1.dp, if (isSelected) Grenat else MinimalOutline, RoundedCornerShape(10.dp))
                                    .clickable { onChangeTextScale(scale) }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) Blanc else MinimalTextPrimary,
                                            fontSize = (14 * scale).sp
                                        )
                                    )
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Blanc,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Interactive Live Preview
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Creme),
                        border = BorderStroke(1.dp, BordureLegere),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Aperçu en direct :",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GrenatProfond
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "La discipline quotidienne façonne notre avenir avec clarté.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = (14 * readerState.textScale).sp,
                                    lineHeight = (20 * readerState.textScale).sp,
                                    color = Encre
                                )
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showTextScaleDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Grenat,
                        contentColor = Blanc
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Terminé", color = Blanc, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onChangeTextScale(1.0f)
                    }
                ) {
                    Text("Réinitialiser (100%)", color = MinimalTextSecondary)
                }
            },
            containerColor = MinimalSurface,
            shape = RoundedCornerShape(20.dp)
        )
    }

    // Sommaire & Signets Épinglés BottomSheet
    if (showSommaireSignetsSheet) {
        var selectedTab by remember { mutableIntStateOf(if (readerState.bookmarkedIds.isNotEmpty()) 0 else 1) }
        val allBookmarkedPlanches = remember(readerState.bookmarkedIds) {
            readerState.bookmarkedIds.mapNotNull { BookData.getPlancheById(it) }
        }

        ModalBottomSheet(
            onDismissRequest = { showSommaireSignetsSheet = false },
            containerColor = MinimalSurface,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            modifier = Modifier.testTag("sommaire_signets_bottom_sheet")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Navigation Rapide",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MinimalTextPrimary
                            )
                        )
                        Text(
                            text = "Accédez à vos signets et aux planches du livre en 1 clic",
                            style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextSecondary)
                        )
                    }
                    IconButton(
                        onClick = { showSommaireSignetsSheet = false }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fermer",
                            tint = MinimalTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Tab Row
                PrimaryTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MinimalSurface,
                    contentColor = Grenat,
                    indicator = {
                        TabRowDefaults.PrimaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(selectedTab),
                            color = Grenat
                        )
                    }
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = null,
                                    tint = if (selectedTab == 0) Grenat else MinimalTextSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Mes Signets (${allBookmarkedPlanches.size})",
                                    fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = if (selectedTab == 1) Grenat else MinimalTextSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Cahier ${currentCahier.number}",
                                    fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Tab Content
                if (selectedTab == 0) {
                    // Signets & Favoris tab
                    if (allBookmarkedPlanches.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(MinimalSecondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.BookmarkBorder,
                                    contentDescription = null,
                                    tint = Grenat,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Aucun signet épinglé",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalTextPrimary
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Touche l'icône de signet 🔖 en haut à droite d'une planche pour l'épingler et la retrouver ici.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MinimalTextSecondary,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.padding(horizontal = 24.dp)
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 420.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(allBookmarkedPlanches) { planche ->
                                val cahier = BookData.cahiers.find { it.id == planche.cahierId } ?: BookData.cahiers.first()
                                val plancheIdx = BookData.getPlancheIndexInCahier(planche)
                                val isCurrent = planche.id == currentPlanche.id

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onSelectPlanche(planche.cahierId, plancheIdx)
                                            showSommaireSignetsSheet = false
                                        }
                                        .testTag("bookmark_item_${planche.id}"),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isCurrent) Grenat.copy(alpha = 0.08f) else MinimalSurfaceContainer
                                    ),
                                    border = BorderStroke(
                                        1.dp,
                                        if (isCurrent) Grenat else MinimalOutline
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(CircleShape)
                                                .background(if (isCurrent) Grenat else MinimalSecondaryContainer),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = cahier.number,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = if (isCurrent) Blanc else MinimalOnPrimaryContainer,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(10.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = "Cahier ${cahier.number} · ${cahier.title}",
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        color = Grenat,
                                                        fontWeight = FontWeight.SemiBold
                                                    )
                                                )
                                                if (isCurrent) {
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    BadgePill(
                                                        text = "En cours",
                                                        backgroundColor = Grenat,
                                                        textColor = Blanc
                                                    )
                                                }
                                            }
                                            Text(
                                                text = planche.title,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = MinimalTextPrimary
                                                ),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = planche.sectionNumber,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = MinimalTextSecondary
                                                )
                                            )
                                        }

                                        IconButton(
                                            onClick = {
                                                onToggleBookmark(planche.id, planche.cahierId)
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Bookmark,
                                                contentDescription = "Détacher des favoris",
                                                tint = Grenat,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Sommaire du cahier
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 420.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(planches.size) { idx ->
                            val planche = planches[idx]
                            val isPlancheRead = readerState.readPlanchesIds.contains(planche.id)
                            val isPlancheBookmarked = readerState.bookmarkedIds.contains(planche.id)
                            val isCurrent = idx == readerState.currentPlancheIndex

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSelectPlanche(currentCahier.id, idx)
                                        showSommaireSignetsSheet = false
                                    }
                                    .testTag("sommaire_item_$idx"),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isCurrent) Grenat.copy(alpha = 0.08f) else MinimalSurfaceContainer
                                ),
                                border = BorderStroke(
                                    1.dp,
                                    if (isCurrent) Grenat else MinimalOutline.copy(alpha = 0.6f)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Number indicator
                                    Box(
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(CircleShape)
                                            .background(
                                                when {
                                                    isCurrent -> Grenat
                                                    isPlancheRead -> VertSucces
                                                    else -> MinimalOutline.copy(alpha = 0.5f)
                                                }
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isPlancheRead && !isCurrent) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Lue",
                                                tint = Blanc,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        } else {
                                            Text(
                                                text = "${idx + 1}",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = Blanc,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp
                                                )
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = planche.title,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                                color = if (isCurrent) Grenat else MinimalTextPrimary
                                            ),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = planche.sectionNumber,
                                            style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)
                                        )
                                    }

                                    if (isPlancheBookmarked) {
                                        Icon(
                                            imageVector = Icons.Default.Bookmark,
                                            contentDescription = "Épinglée",
                                            tint = Grenat,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
