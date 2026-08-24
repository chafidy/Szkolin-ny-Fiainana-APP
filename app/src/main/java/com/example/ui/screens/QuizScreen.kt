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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuizQuestion
import com.example.data.repository.BookData
import com.example.ui.components.BadgePill
import com.example.ui.components.BrandProgressBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.QuizUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    quizState: QuizUiState,
    onSelectCahier: (Int) -> Unit,
    onSelectOption: (Int, String) -> Unit,
    onNextQuestion: () -> Unit,
    onPreviousQuestion: () -> Unit,
    onSubmitQuiz: () -> Unit,
    onResetQuiz: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentCahier = BookData.cahiers.find { it.id == quizState.selectedCahierId } ?: BookData.cahiers.first()
    val questions = remember(quizState.selectedCahierId) {
        val qList = BookData.quizzes.filter { it.cahierId == quizState.selectedCahierId }
        if (qList.isNotEmpty()) qList else BookData.quizzes.take(10)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Le Grand Quiz",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MinimalTextPrimary
                            )
                        )
                        Text(
                            text = "Cahier ${currentCahier.number} · 10 questions",
                            style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("quiz_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            tint = MinimalTextPrimary
                        )
                    }
                },
                actions = {
                    if (quizState.isSubmitted) {
                        IconButton(
                            onClick = onResetQuiz,
                            modifier = Modifier.testTag("quiz_restart_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Recommencer",
                                tint = MinimalTextSecondary
                            )
                        }
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
            // Cahier Selection Horizontal Tabs
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(BookData.cahiers) { cahier ->
                        val isSelected = cahier.id == quizState.selectedCahierId
                        Surface(
                            modifier = Modifier
                                .clickable { onSelectCahier(cahier.id) }
                                .testTag("quiz_cahier_chip_${cahier.number}"),
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) MinimalPrimary else MinimalSurface,
                            border = BorderStroke(1.dp, if (isSelected) MinimalPrimary else MinimalOutline)
                        ) {
                            Text(
                                text = "Cahier ${cahier.number}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) MinimalSurface else MinimalTextPrimary
                                ),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            // If quiz is already submitted, show result overview
            if (quizState.isSubmitted) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quiz_result_card"),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (quizState.isPassed) VertDoux else MinimalSecondaryContainer
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (quizState.isPassed) VertSucces.copy(alpha = 0.5f) else MinimalOutline
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(if (quizState.isPassed) VertSucces else MinimalPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (quizState.isPassed) Icons.Default.EmojiEvents else Icons.Default.Replay,
                                    contentDescription = null,
                                    tint = MinimalSurface,
                                    modifier = Modifier.size(36.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "SCORE : ${quizState.finalScore} / 10",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalTextPrimary
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = if (quizState.isPassed)
                                    "🎉 Félicitations ! Score de 8 ou plus : tu as bien assimilé les notions de ce cahier !"
                                else
                                    "Continue ton apprentissage ! Relis les planches et réessaie pour viser au moins 8 / 10.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MinimalTextSecondary,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 20.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = onResetQuiz,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Grenat,
                                    contentColor = Blanc
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp), tint = Blanc)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Recommencer le quiz", color = Blanc, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Corrigé & Explications détaillées",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MinimalTextPrimary
                        )
                    )
                }

                // Question by question review
                items(questions.indices.toList()) { idx ->
                    val q = questions[idx]
                    val userAns = quizState.selectedAnswers[idx]
                    val isCorrect = userAns == q.correctOption

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                        border = BorderStroke(1.dp, if (isCorrect) VertSucces.copy(alpha = 0.5f) else RougeAlerte.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Question ${idx + 1}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MinimalTextPrimary
                                    )
                                )
                                BadgePill(
                                    text = if (isCorrect) "Correct" else "À revoir",
                                    backgroundColor = if (isCorrect) VertDoux else MinimalSecondaryContainer,
                                    textColor = if (isCorrect) VertSucces else RougeAlerte
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = q.question,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MinimalTextPrimary
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "• Option A : ${q.optionA} ${if (q.correctOption == "A") "✓ (Bonne réponse)" else ""}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = if (q.correctOption == "A") VertSucces else MinimalTextSecondary,
                                    fontWeight = if (q.correctOption == "A") FontWeight.Bold else FontWeight.Normal
                                )
                            )
                            Text(
                                text = "• Option B : ${q.optionB} ${if (q.correctOption == "B") "✓ (Bonne réponse)" else ""}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = if (q.correctOption == "B") VertSucces else MinimalTextSecondary,
                                    fontWeight = if (q.correctOption == "B") FontWeight.Bold else FontWeight.Normal
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MinimalSurfaceContainer,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Explication : ${q.explanation}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MinimalTextPrimary,
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                    ),
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                // Interactive Question by Question Mode
                val activeIdx = quizState.currentQuestionIndex
                val activeQuestion = questions.getOrNull(activeIdx) ?: questions.first()
                val selectedOption = quizState.selectedAnswers[activeIdx]

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                        border = BorderStroke(1.dp, MinimalOutline)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "QUESTION ${activeIdx + 1} / ${questions.size}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MinimalTextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                )

                                Text(
                                    text = "${quizState.selectedAnswers.size} / ${questions.size} répondues",
                                    style = MaterialTheme.typography.labelSmall.copy(color = MinimalTextSecondary)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            BrandProgressBar(
                                progress = (activeIdx + 1).toFloat() / questions.size,
                                height = 6.dp,
                                barColor = MinimalPrimary
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = activeQuestion.question,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalTextPrimary,
                                    lineHeight = 26.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Option A
                            QuizOptionCard(
                                letter = "A",
                                text = activeQuestion.optionA,
                                isSelected = selectedOption == "A",
                                onSelect = { onSelectOption(activeIdx, "A") },
                                testTag = "quiz_option_A"
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Option B
                            QuizOptionCard(
                                letter = "B",
                                text = activeQuestion.optionB,
                                isSelected = selectedOption == "B",
                                onSelect = { onSelectOption(activeIdx, "B") },
                                testTag = "quiz_option_B"
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Navigation Buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedButton(
                                    onClick = onPreviousQuestion,
                                    enabled = activeIdx > 0,
                                    shape = RoundedCornerShape(20.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MinimalTextPrimary),
                                    border = BorderStroke(1.dp, MinimalOutline)
                                ) {
                                    Text("Précédente")
                                }

                                if (activeIdx < questions.size - 1) {
                                    Button(
                                        onClick = onNextQuestion,
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Grenat,
                                            contentColor = Blanc
                                        )
                                    ) {
                                        Text("Suivante", color = Blanc, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    Button(
                                        onClick = onSubmitQuiz,
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Grenat,
                                            contentColor = Blanc
                                        ),
                                        modifier = Modifier.testTag("quiz_submit_button")
                                    ) {
                                        Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp), tint = Blanc)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Valider le quiz", color = Blanc, fontWeight = FontWeight.Bold)
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

@Composable
fun QuizOptionCard(
    letter: String,
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .testTag(testTag),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) GrenatProfond else Blanc,
        border = BorderStroke(
            1.5.dp,
            if (isSelected) Grenat else BordureLegere
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Grenat else Creme),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letter,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Blanc else GrenatProfond
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Blanc else Encre
                )
            )
        }
    }
}
