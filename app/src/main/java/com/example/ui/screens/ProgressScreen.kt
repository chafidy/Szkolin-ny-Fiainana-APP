package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
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
    onSetReminder: (Boolean, Int, Int) -> Unit = { _, _, _ -> },
    onSendTestNotification: () -> Unit = {},
    onSelectCahier: (Int) -> Unit,
    onNavigateToQuiz: (Int) -> Unit,
    onNavigateToAbout: (Int) -> Unit = {},
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showGoalDialog by remember { mutableStateOf(false) }
    var showReminderDialog by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onSetReminder(true, progressState.userSettings.reminderHour, progressState.userSettings.reminderMinute)
            Toast.makeText(context, "Rappels quotidiens activés !", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Autorisation de notification requise pour recevoir les rappels", Toast.LENGTH_LONG).show()
        }
    }

    val toggleReminder = { enabled: Boolean ->
        if (enabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                onSetReminder(true, progressState.userSettings.reminderHour, progressState.userSettings.reminderMinute)
                Toast.makeText(context, "Rappel programmé à %02dh%02d".format(progressState.userSettings.reminderHour, progressState.userSettings.reminderMinute), Toast.LENGTH_SHORT).show()
            } else {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            onSetReminder(enabled, progressState.userSettings.reminderHour, progressState.userSettings.reminderMinute)
            if (enabled) {
                Toast.makeText(context, "Rappel programmé à %02dh%02d".format(progressState.userSettings.reminderHour, progressState.userSettings.reminderMinute), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Rappels désactivés", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
                    IconButton(
                        onClick = { onNavigateToAbout(0) },
                        modifier = Modifier.testTag("progress_about_button")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "À Propos & Mentions",
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
                            height = 10.dp,
                            barColor = Moutarde,
                            trackColor = Blanc.copy(alpha = 0.25f)
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

            // Daily Reminder & Streak Card
            item {
                val reminderHour = progressState.userSettings.reminderHour
                val reminderMinute = progressState.userSettings.reminderMinute
                val reminderEnabled = progressState.userSettings.reminderEnabled
                val timeFormatted = "%02d:%02d".format(reminderHour, reminderMinute)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("daily_reminder_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                    border = BorderStroke(1.dp, if (reminderEnabled) Moutarde.copy(alpha = 0.6f) else MinimalOutline)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(if (reminderEnabled) Moutarde.copy(alpha = 0.2f) else MinimalSecondaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (reminderEnabled) Icons.Default.NotificationsActive else Icons.Default.NotificationsNone,
                                        contentDescription = null,
                                        tint = if (reminderEnabled) Grenat else MinimalTextSecondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "RAPPEL QUOTIDIEN DE LECTURE",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp,
                                            color = MinimalTextPrimary
                                        )
                                    )
                                    Text(
                                        text = if (reminderEnabled) "Programmé à $timeFormatted chaque jour" else "Désactivé • Pour protéger votre série",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = if (reminderEnabled) Grenat else MinimalTextSecondary,
                                            fontWeight = if (reminderEnabled) FontWeight.SemiBold else FontWeight.Normal
                                        )
                                    )
                                }
                            }

                            Switch(
                                checked = reminderEnabled,
                                onCheckedChange = { toggleReminder(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Blanc,
                                    checkedTrackColor = Grenat,
                                    uncheckedThumbColor = MinimalTextSecondary,
                                    uncheckedTrackColor = MinimalOutline
                                ),
                                modifier = Modifier.testTag("reminder_switch")
                            )
                        }

                        if (reminderEnabled) {
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = MinimalOutline.copy(alpha = 0.5f))
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Heure de notification",
                                        style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = timeFormatted,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MinimalTextPrimary
                                        )
                                    )
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedButton(
                                        onClick = {
                                            onSendTestNotification()
                                            Toast.makeText(context, "Notification de test envoyée !", Toast.LENGTH_SHORT).show()
                                        },
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                                        shape = RoundedCornerShape(10.dp),
                                        border = BorderStroke(1.dp, MinimalOutline),
                                        modifier = Modifier.testTag("test_reminder_btn")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Send,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp),
                                            tint = MinimalTextPrimary
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Tester",
                                            style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextPrimary)
                                        )
                                    }

                                    Button(
                                        onClick = { showReminderDialog = true },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Grenat,
                                            contentColor = Blanc
                                        ),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.testTag("change_reminder_time_btn")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Schedule,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp),
                                            tint = Blanc
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Changer",
                                            style = MaterialTheme.typography.labelSmall.copy(color = Blanc, fontWeight = FontWeight.Bold)
                                        )
                                    }
                                }
                            }
                        }
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

            // Quick access: Mentions & Politique de confidentialité
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToAbout(1) }
                        .testTag("progress_privacy_banner"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                    border = BorderStroke(1.dp, MinimalOutline.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PrivacyTip,
                                contentDescription = null,
                                tint = VertSucces,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Données 100% locales & Vie privée",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MinimalTextPrimary
                                    )
                                )
                                Text(
                                    text = "Consulter la politique de confidentialité (Google Play)",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MinimalTextSecondary,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = MinimalTextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }

    // Reminder Time Configuration Dialog
    if (showReminderDialog) {
        var selectedHour by remember { mutableStateOf(progressState.userSettings.reminderHour) }
        var selectedMinute by remember { mutableStateOf(progressState.userSettings.reminderMinute) }

        val presets = listOf(
            Triple(7, 0, "07:00 Matin"),
            Triple(12, 30, "12:30 Midi"),
            Triple(19, 0, "19:00 Soir"),
            Triple(20, 30, "20:30 Idéal"),
            Triple(21, 30, "21:30 Nuit")
        )

        AlertDialog(
            onDismissRequest = { showReminderDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = Grenat,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Heure du rappel quotidien",
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
                        text = "Choisis le moment propice pour lire chaque jour et préserver ta série active.",
                        style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextSecondary),
                        textAlign = TextAlign.Center
                    )

                    // Big interactive time display with +/- buttons
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSecondaryContainer),
                        border = BorderStroke(1.dp, MinimalOutline)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Hour controls
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                IconButton(
                                    onClick = { selectedHour = (selectedHour + 1) % 24 },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Heure +", tint = Grenat)
                                }
                                Text(
                                    text = "%02d".format(selectedHour),
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MinimalTextPrimary
                                    )
                                )
                                IconButton(
                                    onClick = { selectedHour = if (selectedHour - 1 < 0) 23 else selectedHour - 1 },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Heure -", tint = Grenat)
                                }
                                Text("Heures", style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary, fontSize = 10.sp))
                            }

                            Text(
                                text = ":",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Grenat
                                )
                            )

                            // Minute controls
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                IconButton(
                                    onClick = { selectedMinute = (selectedMinute + 5) % 60 },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Minutes +", tint = Grenat)
                                }
                                Text(
                                    text = "%02d".format(selectedMinute),
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MinimalTextPrimary
                                    )
                                )
                                IconButton(
                                    onClick = { selectedMinute = if (selectedMinute - 5 < 0) 55 else selectedMinute - 5 },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Minutes -", tint = Grenat)
                                }
                                Text("Minutes", style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary, fontSize = 10.sp))
                            }
                        }
                    }

                    // Presets
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Raccourcis suggérés :",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MinimalTextSecondary
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            presets.take(3).forEach { (h, m, label) ->
                                val isSelected = selectedHour == h && selectedMinute == m
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) Grenat else MinimalSurfaceContainer)
                                        .border(1.dp, if (isSelected) Grenat else MinimalOutline, RoundedCornerShape(8.dp))
                                        .clickable {
                                            selectedHour = h
                                            selectedMinute = m
                                        }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "%02d:%02d".format(h, m),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Blanc else MinimalTextPrimary,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            presets.drop(3).forEach { (h, m, label) ->
                                val isSelected = selectedHour == h && selectedMinute == m
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) Grenat else MinimalSurfaceContainer)
                                        .border(1.dp, if (isSelected) Grenat else MinimalOutline, RoundedCornerShape(8.dp))
                                        .clickable {
                                            selectedHour = h
                                            selectedMinute = m
                                        }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "%02d:%02d".format(h, m),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Blanc else MinimalTextPrimary,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        toggleReminder(true)
                        onSetReminder(true, selectedHour, selectedMinute)
                        showReminderDialog = false
                        Toast.makeText(context, "Rappel quotidien fixé à %02dh%02d".format(selectedHour, selectedMinute), Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Grenat,
                        contentColor = Blanc
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Confirmer l'heure", color = Blanc, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showReminderDialog = false }) {
                    Text("Annuler", color = MinimalTextSecondary)
                }
            },
            containerColor = MinimalSurface,
            shape = RoundedCornerShape(20.dp)
        )
    }
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
            tint = Blanc,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge.copy(
                color = Blanc,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Blanc.copy(alpha = 0.8f),
                fontSize = 10.sp
            )
        )
    }
}
