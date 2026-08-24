package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.*
import com.example.data.repository.BookData
import com.example.data.repository.ProgressRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class CahierProgressSummary(
    val cahierId: Int,
    val cahierNumber: String,
    val title: String,
    val theme: String,
    val planchesReadCount: Int,
    val totalPlanches: Int,
    val quizScore: Int?,
    val isQuizPassed: Boolean,
    val ateliersCompletedCount: Int,
    val totalAteliers: Int
) {
    val completionPercentage: Int
        get() = if (totalPlanches > 0) ((planchesReadCount.toFloat() / totalPlanches) * 100).toInt() else 0
}

data class ProgressUiState(
    val totalPlanchesRead: Int = 0,
    val totalPlanchesInApp: Int = BookData.allPlanches.size,
    val globalPercentage: Int = 0,
    val cahiersProgress: List<CahierProgressSummary> = emptyList(),
    val habitChecks: List<HabitCheckEntity> = (1..30).map { HabitCheckEntity(it) },
    val habitCheckedCount: Int = 0,
    val currentStreak: Int = 1,
    val longestStreak: Int = 1,
    val quizPassedCount: Int = 0,
    val userSettings: UserSettingsEntity = UserSettingsEntity(),
    val isGoalDialogVisible: Boolean = false
)

class ProgressViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProgressRepository

    private val _uiState = MutableStateFlow(ProgressUiState())
    val uiState: StateFlow<ProgressUiState> = _uiState.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ProgressRepository(database.progressDao())

        viewModelScope.launch {
            combine(
                repository.allProgress,
                repository.allHabitChecks,
                repository.allQuizScores,
                repository.allAtelierEntries,
                repository.userSettings
            ) { progressList, habits, quizScores, atelierEntries, settings ->
                val readIds = progressList.filter { it.isRead }.map { it.plancheId }.toSet()
                val totalRead = readIds.size
                val totalPlanches = BookData.allPlanches.size
                val globalPercent = if (totalPlanches > 0) ((totalRead.toFloat() / totalPlanches) * 100).toInt() else 0

                val habitMap = habits.associateBy { it.dayIndex }
                val fullHabits = (1..30).map { day ->
                    habitMap[day] ?: HabitCheckEntity(dayIndex = day)
                }
                val checkedCount = fullHabits.count { it.isChecked }

                // Calculate streaks in habits
                var maxStreak = 0
                var currentRun = 0
                for (h in fullHabits) {
                    if (h.isChecked) {
                        currentRun++
                        if (currentRun > maxStreak) maxStreak = currentRun
                    } else {
                        currentRun = 0
                    }
                }

                val quizMap = quizScores.associateBy { it.cahierId }
                val quizPassed = quizScores.count { it.isPassed }

                val atelierSet = atelierEntries.map { it.atelierId }.toSet()

                val cahierSummaries = BookData.cahiers.map { cahier ->
                    val cahierPlanches = BookData.getPlanchesForCahier(cahier.id)
                    val readInCahier = cahierPlanches.count { readIds.contains(it.id) }
                    val quiz = quizMap[cahier.id]
                    val cahierAteliers = BookData.workshops.filter { it.cahierId == cahier.id }
                    val completedAteliers = cahierAteliers.count { atelierSet.contains(it.id) }

                    CahierProgressSummary(
                        cahierId = cahier.id,
                        cahierNumber = cahier.number,
                        title = cahier.title,
                        theme = cahier.theme,
                        planchesReadCount = readInCahier,
                        totalPlanches = cahierPlanches.size.coerceAtLeast(1),
                        quizScore = quiz?.score,
                        isQuizPassed = quiz?.isPassed ?: false,
                        ateliersCompletedCount = completedAteliers,
                        totalAteliers = cahierAteliers.size
                    )
                }

                ProgressUiState(
                    totalPlanchesRead = totalRead,
                    totalPlanchesInApp = totalPlanches,
                    globalPercentage = globalPercent,
                    cahiersProgress = cahierSummaries,
                    habitChecks = fullHabits,
                    habitCheckedCount = checkedCount,
                    currentStreak = currentRun.coerceAtLeast(1),
                    longestStreak = maxStreak.coerceAtLeast(1),
                    quizPassedCount = quizPassed,
                    userSettings = settings ?: UserSettingsEntity()
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun toggleHabit(dayIndex: Int) {
        viewModelScope.launch {
            val currentChecked = _uiState.value.habitChecks.find { it.dayIndex == dayIndex }?.isChecked ?: false
            repository.toggleHabitDay(dayIndex, currentChecked)
        }
    }

    fun updateGoal(targetPlanches: Int, habitName: String, habitTime: String, habitTrigger: String) {
        viewModelScope.launch {
            repository.updateGoalSettings(targetPlanches, habitName, habitTime, habitTrigger)
        }
    }
}
