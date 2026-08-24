package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.data.repository.BookData
import com.example.ui.components.BadgePill
import com.example.ui.components.BrandProgressBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.ProgressUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    progressState: ProgressUiState,
    onToggleHabitDay: (Int) -> Unit,
    onUpdateGoal: (Int, String, String, String) -> Unit,
    onSelectCahier: (Int) -> Unit,
    onNavigateToQuiz: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showGoalDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Suivi de Progression",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MinimalTextPrimary
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("progress_back_button")
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
                        onClick = { showGoalDialog = true },
                        modifier = Modifier.testTag("progress_settings_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Personnaliser mon rythme",
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
        ) {
            // Global Overview Card (Clean Minimalism Primary Container)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("global_progress_card"),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = MinimalPrimaryContainer),
                    border = BorderStroke(1.dp, MinimalOutline.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "PROGRESSION GLOBALE",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MinimalOnPrimaryContainer.copy(alpha = 0.7f),
                                        letterSpacing = 1.2.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Collection Complète",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = MinimalOnPrimaryContainer,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(MinimalSurface),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${progressState.globalPercentage}%",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = MinimalPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        BrandProgressBar(
                            progress = progressState.totalPlanchesRead.toFloat() / progressState.totalPlanchesInApp.coerceAtLeast(1),
                            height = 8.dp,
                            barColor = MinimalPrimary,
                            trackColor = MinimalOnPrimaryContainer.copy(alpha = 0.12f)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            ProgressMetricItem(
                                label = "Planches lues",
                                value = "${progressState.totalPlanchesRead} / ${progressState.totalPlanchesInApp}",
                                icon = Icons.Default.MenuBook
                            )
                            ProgressMetricItem(
                                label = "Discipline",
                                value = "${progressState.habitCheckedCount} / 30 j",
                                icon = Icons.Default.Checklist
                            )
                            ProgressMetricItem(
                                label = "Quiz validés",
                                value = "${progressState.quizPassedCount} / 8",
                                icon = Icons.Default.Verified
                            )
                        }
                    }
                }
            }

            // Habit Tracker: 30 Jours de Discipline
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("discipline_habit_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                    border = BorderStroke(1.dp, MinimalOutline)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(MinimalSecondaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CalendarToday,
                                        contentDescription = null,
                                        tint = MinimalPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "30 JOURS DE DISCIPLINE",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp,
                                            color = MinimalTextPrimary
                                        )
                                    )
                                    Text(
                                        text = progressState.userSettings.selectedHabitName,
                                        style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextSecondary)
                                    )
                                }
                            }

                            BadgePill(
                                text = "${progressState.habitCheckedCount}/30",
                                backgroundColor = if (progressState.habitCheckedCount >= 20) VertDoux else MinimalSecondaryContainer,
                                textColor = if (progressState.habitCheckedCount >= 20) VertSucces else MinimalOnPrimaryContainer
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Règle des 2 jours : Ne jamais laisser deux cases blanches à la suite. 20 cases sur 30 = réussite.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MinimalTextSecondary,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // 30 Days Grid (6 columns x 5 rows)
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            for (row in 0 until 5) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    for (col in 0 until 6) {
                                        val day = row * 6 + col + 1
                                        val isChecked = progressState.habitChecks.getOrNull(day - 1)?.isChecked ?: false
                                        val isMilestone = day == 7 || day == 14 || day == 21 || day == 28

                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .aspectRatio(1f)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(
                                                    if (isChecked) MinimalPrimary
                                                    else if (isMilestone) MinimalSecondaryContainer
                                                    else MinimalSurfaceContainer
                                                )
                                                .border(
                                                    1.dp,
                                                    if (isChecked) MinimalPrimary
                                                    else if (isMilestone) MinimalPrimary.copy(alpha = 0.4f)
                                                    else MinimalOutline,
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .clickable { onToggleHabitDay(day) }
                                                .testTag("habit_day_$day"),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text(
                                                    text = "J%02d".format(day),
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontSize = 10.sp,
                                                        fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Medium,
                                                        color = if (isChecked) MinimalSurface else MinimalTextPrimary
                                                    )
                                                )
                                                if (isChecked) {
                                                    Icon(
                                                        imageVector = Icons.Default.Check,
                                                        contentDescription = null,
                                                        tint = MinimalSurface,
                                                        modifier = Modifier.size(10.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "• J07, J14, J21, J28 : Bilans hebdomadaires",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = MinimalTextSecondary)
                            )
                            Text(
                                text = "Série max : ${progressState.longestStreak} j",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalPrimary
                                )
                            )
                        }
                    }
                }
            }

            // Personalized Goal Settings Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showGoalDialog = true }
                        .testTag("reading_goal_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MinimalSurfaceContainer),
                    border = BorderStroke(1.dp, MinimalOutline)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MinimalSecondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Flag,
                                contentDescription = null,
                                tint = MinimalPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "MON OBJECTIF DE LECTURE",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalTextSecondary,
                                    letterSpacing = 0.5.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${progressState.userSettings.dailyTargetPlanches} planche(s) par jour · Heure : ${progressState.userSettings.habitTime}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MinimalTextPrimary
                                )
                            )
                            Text(
                                text = "Déclencheur : ${progressState.userSettings.habitTrigger}",
                                style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextSecondary)
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Modifier",
                            tint = MinimalPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Detail per Cahier Progress Breakdown
            item {
                Text(
                    text = "DÉTAIL PAR CAHIER",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = MinimalTextSecondary
                    ),
                    modifier = Modifier.padding(start = 2.dp, top = 4.dp)
                )
            }

            items(progressState.cahiersProgress) { itemSummary ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectCahier(itemSummary.cahierId) },
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(MinimalSecondaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = itemSummary.cahierNumber,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = MinimalOnPrimaryContainer,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = itemSummary.title,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MinimalTextPrimary
                                        )
                                    )
                                    Text(
                                        text = itemSummary.theme,
                                        style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)
                                    )
                                }
                            }

                            BadgePill(
                                text = "${itemSummary.completionPercentage}%",
                                backgroundColor = if (itemSummary.completionPercentage == 100) VertDoux else MinimalSecondaryContainer,
                                textColor = if (itemSummary.completionPercentage == 100) VertSucces else MinimalOnPrimaryContainer
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        BrandProgressBar(
                            progress = itemSummary.planchesReadCount.toFloat() / itemSummary.totalPlanches.coerceAtLeast(1),
                            height = 6.dp,
                            barColor = if (itemSummary.completionPercentage == 100) VertSucces else MinimalPrimary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${itemSummary.planchesReadCount} / ${itemSummary.totalPlanches} planches lues",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = MinimalTextSecondary)
                            )

                            if (itemSummary.quizScore != null) {
                                BadgePill(
                                    text = "Quiz : ${itemSummary.quizScore}/10",
                                    backgroundColor = if (itemSummary.isQuizPassed) VertDoux else MinimalSecondaryContainer,
                                    textColor = if (itemSummary.isQuizPassed) VertSucces else MinimalOnPrimaryContainer
                                )
                            } else {
                                TextButton(
                                    onClick = { onNavigateToQuiz(itemSummary.cahierId) },
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text("Faire le quiz", style = MaterialTheme.typography.labelSmall.copy(color = MinimalPrimary, fontWeight = FontWeight.Bold))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Goal Configuration Dialog
    if (showGoalDialog) {
        var tempTarget by remember { mutableStateOf(progressState.userSettings.dailyTargetPlanches.toString()) }
        var tempHabit by remember { mutableStateOf(progressState.userSettings.selectedHabitName) }
        var tempTime by remember { mutableStateOf(progressState.userSettings.habitTime) }
        var tempTrigger by remember { mutableStateOf(progressState.userSettings.habitTrigger) }

        AlertDialog(
            onDismissRequest = { showGoalDialog = false },
            title = {
                Text(
                    text = "Personnaliser mon rythme",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MinimalTextPrimary
                    )
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Fixe un objectif réaliste et accessible pour installer une habitude durable.",
                        style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextSecondary)
                    )

                    OutlinedTextField(
                        value = tempTarget,
                        onValueChange = { tempTarget = it },
                        label = { Text("Planches par jour") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = tempHabit,
                        onValueChange = { tempHabit = it },
                        label = { Text("Habitude choisie") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = tempTime,
                        onValueChange = { tempTime = it },
                        label = { Text("Heure fixe") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = tempTrigger,
                        onValueChange = { tempTrigger = it },
                        label = { Text("Déclencheur (Juste après quoi ?)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val target = tempTarget.toIntOrNull() ?: 1
                        onUpdateGoal(target, tempHabit, tempTime, tempTrigger)
                        showGoalDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Grenat,
                        contentColor = Blanc
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Valider", color = Blanc, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showGoalDialog = false }) {
                    Text("Annuler", color = MinimalTextSecondary)
                }
            },
            containerColor = MinimalSurface,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
fun ProgressMetricItem(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MinimalPrimary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge.copy(
                color = MinimalOnPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = MinimalOnPrimaryContainer.copy(alpha = 0.75f),
                fontSize = 10.sp
            )
        )
    }
}
