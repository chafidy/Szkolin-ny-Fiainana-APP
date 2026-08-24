package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LexiqueItem
import com.example.data.repository.BookData
import com.example.ui.components.BadgePill
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LexiqueScreen(
    initialCahierId: Int = 1,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCahierId by remember { mutableStateOf(initialCahierId) }
    var searchQuery by remember { mutableStateOf("") }

    val currentCahier = BookData.cahiers.find { it.id == selectedCahierId } ?: BookData.cahiers.first()
    val allItems = remember { BookData.lexique }

    val filteredItems = remember(selectedCahierId, searchQuery) {
        allItems.filter {
            (it.cahierId == selectedCahierId || searchQuery.isNotBlank()) &&
            (searchQuery.isBlank() || it.term.contains(searchQuery, ignoreCase = true) || it.definition.contains(searchQuery, ignoreCase = true))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Lexique & Notions Clés",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MinimalTextPrimary
                            )
                        )
                        Text(
                            text = "Le vocabulaire essentiel de la vraie vie",
                            style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("lexique_back_button")
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
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
        ) {
            // Search Input
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("lexique_search_input"),
                    placeholder = { Text("Rechercher un terme (ex. Actif, Passif, Deep Work...)", color = MinimalTextSecondary) },
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MinimalPrimary,
                        unfocusedBorderColor = MinimalOutline
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
            }

            // Cahier Selection Horizontal Carousel if not searching
            if (searchQuery.isEmpty()) {
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(BookData.cahiers) { cahier ->
                            val isSelected = cahier.id == selectedCahierId
                            Surface(
                                modifier = Modifier
                                    .clickable { selectedCahierId = cahier.id }
                                    .testTag("lexique_tab_${cahier.number}"),
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) Grenat else Blanc,
                                border = BorderStroke(1.dp, if (isSelected) Grenat else BordureLegere)
                            ) {
                                Text(
                                    text = "Cahier ${cahier.number}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Blanc else GrenatProfond
                                    ),
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                // 5 Quotes / Phrases à emporter
                item {
                    val phrases = BookData.rulesToRemember[currentCahier.id] ?: emptyList()
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSecondaryContainer),
                        border = BorderStroke(1.dp, MinimalOutline)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.FormatQuote,
                                    contentDescription = null,
                                    tint = MinimalOnPrimaryContainer,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "LES 5 PHRASES À EMPORTER · CAHIER ${currentCahier.number}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = MinimalOnPrimaryContainer,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            phrases.forEach { phrase ->
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = phrase,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MinimalTextPrimary,
                                            lineHeight = 18.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Lexique Items list
            items(filteredItems) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                            Text(
                                text = item.term,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalTextPrimary
                                )
                            )

                            BadgePill(
                                text = "Cahier 0${item.cahierId}",
                                backgroundColor = MinimalSecondaryContainer,
                                textColor = MinimalOnPrimaryContainer
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = item.definition,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MinimalTextSecondary,
                                lineHeight = 20.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
