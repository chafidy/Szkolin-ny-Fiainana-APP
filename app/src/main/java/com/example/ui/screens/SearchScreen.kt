package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Planche
import com.example.data.repository.BookData
import com.example.ui.components.BadgePill
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    bookmarkedIds: Set<String> = emptySet(),
    onToggleBookmark: (String, Int) -> Unit = { _, _ -> },
    onSelectPlanche: (Int, Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val allPlanches = remember { BookData.allPlanches }
    val bookmarkedPlanches = remember(bookmarkedIds) {
        bookmarkedIds.mapNotNull { BookData.getPlancheById(it) }
    }

    val searchResults = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            emptyList()
        } else {
            allPlanches.filter { planche ->
                planche.title.contains(searchQuery, ignoreCase = true) ||
                planche.accroche.contains(searchQuery, ignoreCase = true) ||
                planche.sectionNumber.contains(searchQuery, ignoreCase = true) ||
                (planche.bottomAdvice?.content?.contains(searchQuery, ignoreCase = true) == true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("global_search_input"),
                        placeholder = { Text("Rechercher dans les 160 planches...", fontSize = 14.sp, color = MinimalTextSecondary) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = MinimalPrimary)
                        },
                        trailingIcon = if (searchQuery.isNotEmpty()) {
                            {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Effacer", tint = MinimalTextSecondary)
                                }
                            }
                        } else null,
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MinimalPrimary,
                            unfocusedBorderColor = MinimalOutline
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("search_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            tint = MinimalTextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MinimalBackground)
            )
        },
        containerColor = MinimalBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp)
        ) {
            if (searchQuery.isBlank()) {
                // Bookmarked quick section if any
                if (bookmarkedPlanches.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = null,
                                    tint = Grenat,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "VOS SIGNETS & FAVORIS",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp,
                                        color = GrenatProfond
                                    )
                                )
                            }
                            BadgePill(
                                text = "${bookmarkedPlanches.size} planche${if (bookmarkedPlanches.size > 1) "s" else ""}",
                                backgroundColor = Moutarde.copy(alpha = 0.2f),
                                textColor = GrenatProfond
                            )
                        }
                    }

                    items(bookmarkedPlanches) { planche ->
                        val cahier = BookData.cahiers.find { it.id == planche.cahierId }
                        val plancheIndex = BookData.getPlancheIndexInCahier(planche)

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectPlanche(planche.cahierId, plancheIndex) }
                                .testTag("search_bookmark_${planche.id}"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                            border = BorderStroke(1.dp, Moutarde.copy(alpha = 0.5f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Grenat),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = cahier?.number ?: "01",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Blanc,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Cahier ${cahier?.number ?: "01"} · ${planche.sectionNumber}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Grenat,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Text(
                                        text = planche.title,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MinimalTextPrimary
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = planche.accroche,
                                        style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextSecondary),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                IconButton(
                                    onClick = { onToggleBookmark(planche.id, planche.cahierId) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "Retirer des favoris",
                                        tint = Grenat
                                    )
                                }
                            }
                        }
                    }

                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MinimalOutline.copy(alpha = 0.5f)
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.TravelExplore,
                            contentDescription = null,
                            tint = MinimalPrimary,
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Rechercher parmi les 160 planches",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MinimalTextPrimary
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Exemples : Budget, Épargne, CV, Négociation, Habitude, Sommeil, Impôts...",
                            style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextSecondary)
                        )
                    }
                }
            } else if (searchResults.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Aucune planche trouvée pour « $searchQuery »",
                            style = MaterialTheme.typography.bodyMedium.copy(color = MinimalTextSecondary)
                        )
                    }
                }
            } else {
                item {
                    Text(
                        text = "${searchResults.size} résultat(s) trouvé(s)",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MinimalTextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                items(searchResults) { planche ->
                    val cahier = BookData.cahiers.find { it.id == planche.cahierId }
                    val indexInCahier = BookData.getPlancheIndexInCahier(planche)
                    val isBookmarked = bookmarkedIds.contains(planche.id)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectPlanche(planche.cahierId, indexInCahier) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                        border = BorderStroke(1.dp, if (isBookmarked) Moutarde else MinimalOutline)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    BadgePill(
                                        text = "Cahier ${cahier?.number ?: "01"} · ${planche.sectionNumber}",
                                        backgroundColor = MinimalSecondaryContainer,
                                        textColor = MinimalOnPrimaryContainer
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Page ${planche.pageNumber}",
                                        style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)
                                    )
                                }

                                IconButton(
                                    onClick = { onToggleBookmark(planche.id, planche.cahierId) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                                        contentDescription = if (isBookmarked) "Retirer des favoris" else "Épingler en favori",
                                        tint = if (isBookmarked) Grenat else MinimalTextSecondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = planche.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalTextPrimary
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = planche.accroche,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MinimalTextSecondary,
                                    lineHeight = 17.sp
                                ),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}
