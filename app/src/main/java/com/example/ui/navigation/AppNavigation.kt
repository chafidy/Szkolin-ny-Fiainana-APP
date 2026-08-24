package com.example.ui.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.*

enum class AppDestination(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME("Bibliothèque", Icons.Default.MenuBook, Icons.Outlined.MenuBook),
    READER("Lecture", Icons.Default.AutoStories, Icons.Outlined.AutoStories),
    PROGRESS("Mon Suivi", Icons.Default.Leaderboard, Icons.Outlined.Leaderboard),
    ATELIERS("Ateliers", Icons.Default.EditNote, Icons.Outlined.EditNote),
    QUIZ("Quiz", Icons.Default.Quiz, Icons.Outlined.Quiz),
    LEXIQUE("Lexique", Icons.Default.FormatQuote, Icons.Outlined.FormatQuote),
    SEARCH("Recherche", Icons.Default.Search, Icons.Outlined.Search)
}

@Composable
fun MainAppContainer(
    readerViewModel: ReaderViewModel = viewModel(),
    progressViewModel: ProgressViewModel = viewModel(),
    atelierViewModel: AtelierViewModel = viewModel(),
    quizViewModel: QuizViewModel = viewModel()
) {
    val readerState by readerViewModel.uiState.collectAsState()
    val progressState by progressViewModel.uiState.collectAsState()
    val atelierState by atelierViewModel.uiState.collectAsState()
    val quizState by quizViewModel.uiState.collectAsState()

    var currentDestination by remember { mutableStateOf(AppDestination.HOME) }
    var selectedLexiqueCahierId by remember { mutableStateOf(1) }

    val bottomNavItems = listOf(
        AppDestination.HOME,
        AppDestination.READER,
        AppDestination.PROGRESS,
        AppDestination.ATELIERS,
        AppDestination.QUIZ
    )

    Scaffold(
        bottomBar = {
            // Show bottom bar for primary tabs, hide when searching or in reader if preferred
            if (currentDestination != AppDestination.SEARCH) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MinimalSurfaceContainer,
                    border = BorderStroke(1.dp, MinimalOutline.copy(alpha = 0.3f))
                ) {
                    NavigationBar(
                        containerColor = MinimalSurfaceContainer,
                        tonalElevation = 0.dp,
                        windowInsets = WindowInsets.navigationBars
                    ) {
                        bottomNavItems.forEach { destination ->
                            val isSelected = currentDestination == destination
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { currentDestination = destination },
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                                        contentDescription = destination.title
                                    )
                                },
                                label = {
                                    Text(
                                        text = destination.title,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            fontSize = 11.sp
                                        )
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Grenat,
                                    selectedTextColor = Grenat,
                                    indicatorColor = GrenatDoux,
                                    unselectedIconColor = MinimalTextSecondary,
                                    unselectedTextColor = MinimalTextSecondary
                                ),
                                modifier = Modifier.testTag("nav_item_${destination.name.lowercase()}")
                            )
                        }
                    }
                }
            }
        },
        containerColor = MinimalBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentDestination) {
                AppDestination.HOME -> {
                    HomeScreen(
                        progressState = progressState,
                        readerState = readerState,
                        onSelectCahier = { cahierId ->
                            readerViewModel.selectCahier(cahierId)
                        },
                        onOpenPlanche = { cahierId, plancheIdx ->
                            readerViewModel.selectPlanche(cahierId, plancheIdx)
                            currentDestination = AppDestination.READER
                        },
                        onNavigateToProgress = { currentDestination = AppDestination.PROGRESS },
                        onNavigateToAteliers = { currentDestination = AppDestination.ATELIERS },
                        onNavigateToQuiz = { cahierId ->
                            quizViewModel.selectCahier(cahierId)
                            currentDestination = AppDestination.QUIZ
                        },
                        onNavigateToLexique = { cahierId ->
                            selectedLexiqueCahierId = cahierId
                            currentDestination = AppDestination.LEXIQUE
                        },
                        onSearchClick = { currentDestination = AppDestination.SEARCH }
                    )
                }

                AppDestination.READER -> {
                    ReaderScreen(
                        readerState = readerState,
                        onPreviousPlanche = { readerViewModel.previousPlanche() },
                        onNextPlanche = { readerViewModel.nextPlanche() },
                        onToggleRead = { id, cId -> readerViewModel.togglePlancheRead(id, cId) },
                        onToggleBookmark = { id, cId -> readerViewModel.toggleBookmark(id, cId) },
                        onSaveNote = { id, cId, note -> readerViewModel.saveNote(id, cId, note) },
                        onBackToLibrary = { currentDestination = AppDestination.HOME },
                        onNavigateToQuiz = { cahierId ->
                            quizViewModel.selectCahier(cahierId)
                            currentDestination = AppDestination.QUIZ
                        },
                        onNavigateToLexique = { cahierId ->
                            selectedLexiqueCahierId = cahierId
                            currentDestination = AppDestination.LEXIQUE
                        }
                    )
                }

                AppDestination.PROGRESS -> {
                    ProgressScreen(
                        progressState = progressState,
                        onToggleHabitDay = { day -> progressViewModel.toggleHabit(day) },
                        onUpdateGoal = { target, habit, time, trigger ->
                            progressViewModel.updateGoal(target, habit, time, trigger)
                        },
                        onSelectCahier = { cahierId ->
                            readerViewModel.selectCahier(cahierId)
                            currentDestination = AppDestination.READER
                        },
                        onNavigateToQuiz = { cahierId ->
                            quizViewModel.selectCahier(cahierId)
                            currentDestination = AppDestination.QUIZ
                        },
                        onBack = { currentDestination = AppDestination.HOME }
                    )
                }

                AppDestination.ATELIERS -> {
                    AteliersScreen(
                        atelierState = atelierState,
                        onSelectAtelier = { id -> atelierViewModel.selectAtelier(id) },
                        onUpdateField = { id, value -> atelierViewModel.updateField(id, value) },
                        onSaveAtelier = { atelierViewModel.saveCurrentAtelier() },
                        onResetAtelier = { atelierViewModel.resetForm() },
                        onBack = { currentDestination = AppDestination.HOME }
                    )
                }

                AppDestination.QUIZ -> {
                    QuizScreen(
                        quizState = quizState,
                        onSelectCahier = { cahierId -> quizViewModel.selectCahier(cahierId) },
                        onSelectOption = { idx, opt -> quizViewModel.selectOption(idx, opt) },
                        onNextQuestion = { quizViewModel.nextQuestion() },
                        onPreviousQuestion = { quizViewModel.previousQuestion() },
                        onSubmitQuiz = { quizViewModel.submitQuiz() },
                        onResetQuiz = { quizViewModel.resetQuiz() },
                        onBack = { currentDestination = AppDestination.HOME }
                    )
                }

                AppDestination.LEXIQUE -> {
                    LexiqueScreen(
                        initialCahierId = selectedLexiqueCahierId,
                        onBack = { currentDestination = AppDestination.HOME }
                    )
                }

                AppDestination.SEARCH -> {
                    SearchScreen(
                        onSelectPlanche = { cahierId, plancheIdx ->
                            readerViewModel.selectPlanche(cahierId, plancheIdx)
                            currentDestination = AppDestination.READER
                        },
                        onBack = { currentDestination = AppDestination.HOME }
                    )
                }
            }
        }
    }
}
