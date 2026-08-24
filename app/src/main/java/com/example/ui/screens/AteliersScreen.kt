package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WorkshopDefinition
import com.example.data.repository.BookData
import com.example.ui.components.BadgePill
import com.example.ui.theme.*
import com.example.ui.viewmodel.AtelierUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AteliersScreen(
    atelierState: AtelierUiState,
    onSelectAtelier: (String) -> Unit,
    onUpdateField: (String, String) -> Unit,
    onSaveAtelier: () -> Unit,
    onResetAtelier: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentWorkshop = BookData.workshops.find { it.id == atelierState.selectedAtelierId } ?: BookData.workshops.first()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Ateliers Pratiques",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MinimalTextPrimary
                            )
                        )
                        Text(
                            text = "Outils personnels à remplir au fur et à mesure",
                            style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("atelier_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            tint = MinimalTextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onResetAtelier,
                        modifier = Modifier.testTag("atelier_reset_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Réinitialiser",
                            tint = MinimalTextSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MinimalBackground)
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
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedVisibility(
                        visible = atelierState.isSavedSuccess,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = VertSucces,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Enregistré avec succès !",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = VertSucces,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = onSaveAtelier,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Grenat,
                            contentColor = Blanc
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("save_atelier_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Blanc
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sauvegarder ma fiche",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, color = Blanc)
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
            contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
        ) {
            // Horizontal Carousel of Workshops
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(BookData.workshops) { ws ->
                        val isSelected = ws.id == atelierState.selectedAtelierId
                        Surface(
                            modifier = Modifier
                                .clickable { onSelectAtelier(ws.id) }
                                .testTag("atelier_chip_${ws.id}"),
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) MinimalPrimary else MinimalSurface,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) MinimalPrimary else MinimalOutline
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Cahier 0${ws.cahierId}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) MinimalSurface else MinimalPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = ws.title,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = if (isSelected) MinimalSurface else MinimalTextPrimary,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Active Workshop Header Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                    border = BorderStroke(1.dp, MinimalOutline)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            BadgePill(
                                text = "ATELIER · CAHIER 0${currentWorkshop.cahierId}",
                                backgroundColor = MinimalSecondaryContainer,
                                textColor = MinimalOnPrimaryContainer
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = currentWorkshop.title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MinimalTextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = currentWorkshop.subtitle,
                            style = MaterialTheme.typography.bodyMedium.copy(color = MinimalTextSecondary)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "💡 ${currentWorkshop.quote}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MinimalTextSecondary,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        )
                    }
                }
            }

            // Live Calculation Output if Budget
            if (currentWorkshop.calculationType == "BUDGET_CALC") {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("budget_live_calc_card"),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                        border = BorderStroke(1.5.dp, if (atelierState.budgetBilan >= 0) VertSucces else RougeAlerte)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "BILAN AUTOMATIQUE EN TEMPS RÉEL",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalTextPrimary,
                                    letterSpacing = 1.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total Reçu (A) :", style = MaterialTheme.typography.bodyMedium.copy(color = MinimalTextSecondary))
                                Text("${atelierState.budgetResultTotalRecu} Ar", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = VertSucces))
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Épargne 20 % (B) :", style = MaterialTheme.typography.bodyMedium.copy(color = MinimalTextSecondary))
                                Text("${atelierState.budgetResultEpargne20} Ar", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MinimalPrimary))
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total Besoins (C) :", style = MaterialTheme.typography.bodyMedium.copy(color = MinimalTextSecondary))
                                Text("${atelierState.budgetResultBesoins} Ar", style = MaterialTheme.typography.bodyMedium.copy(color = MinimalTextPrimary))
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total Envies (D) :", style = MaterialTheme.typography.bodyMedium.copy(color = MinimalTextSecondary))
                                Text("${atelierState.budgetResultEnvies} Ar", style = MaterialTheme.typography.bodyMedium.copy(color = MinimalTextPrimary))
                            }

                            Divider(modifier = Modifier.padding(vertical = 12.dp), color = MinimalOutline)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Reste disponible :",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MinimalTextPrimary)
                                )
                                Text(
                                    text = "${atelierState.budgetBilan} Ar",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = if (atelierState.budgetBilan >= 0) VertSucces else RougeAlerte
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Live Calculation Output if Rate Calculation
            if (currentWorkshop.calculationType == "RATE_CALC") {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSecondaryContainer),
                        border = BorderStroke(1.dp, MinimalOutline)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "CALCUL AUTOMATIQUE DE TON PRIX",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalOnPrimaryContainer
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Taux horaire minimum : ≈ ${atelierState.rateCalculatedHourly} Ar / h",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = MinimalTextPrimary)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Prix forfait conseillé pour cette offre : ≈ ${atelierState.rateCalculatedFixed} Ar",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MinimalPrimary
                                )
                            )
                        }
                    }
                }
            }

            // Form Fields
            items(currentWorkshop.fields) { field ->
                val currentValue = atelierState.formValues[field.id] ?: ""
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                    border = BorderStroke(1.dp, MinimalOutline)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = field.label,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MinimalTextPrimary
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = currentValue,
                            onValueChange = { onUpdateField(field.id, it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("field_${field.id}"),
                            placeholder = { Text(field.placeholder, fontSize = 13.sp, color = MinimalTextSecondary) },
                            trailingIcon = if (field.suffix.isNotEmpty()) {
                                { Text(field.suffix, modifier = Modifier.padding(end = 12.dp), style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)) }
                            } else null,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MinimalPrimary,
                                unfocusedBorderColor = MinimalOutline
                            )
                        )
                    }
                }
            }
        }
    }
}
