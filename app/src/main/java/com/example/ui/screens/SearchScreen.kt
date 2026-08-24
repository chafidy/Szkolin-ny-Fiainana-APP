package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
    onSelectPlanche: (Int, Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val allPlanches = remember { BookData.allPlanches }

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
                        placeholder = { Text("Rechercher dans toute la collection...", fontSize = 14.sp, color = MinimalTextSecondary) },
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
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.TravelExplore,
                            contentDescription = null,
                            tint = MinimalPrimary,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
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
                    val planchesInCahier = BookData.getPlanchesForCahier(planche.cahierId)
                    val indexInCahier = planchesInCahier.indexOfFirst { it.id == planche.id }.coerceAtLeast(0)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectPlanche(planche.cahierId, indexInCahier) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                        border = BorderStroke(1.dp, MinimalOutline)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BadgePill(
                                    text = "Cahier ${cahier?.number ?: "01"} · ${planche.sectionNumber}",
                                    backgroundColor = MinimalSecondaryContainer,
                                    textColor = MinimalOnPrimaryContainer
                                )
                                Text(
                                    text = "Page ${planche.pageNumber}",
                                    style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)
                                )
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
