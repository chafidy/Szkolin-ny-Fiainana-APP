package com.example.data.repository

import com.example.data.local.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class ProgressRepository(private val progressDao: ProgressDao) {

    val allProgress: Flow<List<PlancheProgressEntity>> = progressDao.getAllPlancheProgress()
    val allHabitChecks: Flow<List<HabitCheckEntity>> = progressDao.getHabitChecks()
    val allQuizScores: Flow<List<QuizScoreEntity>> = progressDao.getAllQuizScores()
    val allAtelierEntries: Flow<List<AtelierEntryEntity>> = progressDao.getAllAtelierEntries()
    val userSettings: Flow<UserSettingsEntity?> = progressDao.getUserSettings()

    fun getProgressForCahier(cahierId: Int): Flow<List<PlancheProgressEntity>> =
        progressDao.getProgressForCahier(cahierId)

    fun getPlancheProgress(plancheId: String): Flow<PlancheProgressEntity?> =
        progressDao.getPlancheProgress(plancheId)

    fun getBookmarks(): Flow<List<PlancheProgressEntity>> =
        progressDao.getBookmarks()

    fun getQuizScore(cahierId: Int): Flow<QuizScoreEntity?> =
        progressDao.getQuizScore(cahierId)

    fun getAtelierEntry(atelierId: String): Flow<AtelierEntryEntity?> =
        progressDao.getAtelierEntry(atelierId)

    suspend fun togglePlancheRead(plancheId: String, cahierId: Int, currentStatus: Boolean) {
        val now = if (!currentStatus) System.currentTimeMillis() else 0L
        progressDao.savePlancheProgress(
            PlancheProgressEntity(
                plancheId = plancheId,
                cahierId = cahierId,
                isRead = !currentStatus,
                readTimestamp = now
            )
        )
    }

    suspend fun toggleBookmark(plancheId: String, cahierId: Int, isBookmarked: Boolean) {
        val existing = PlancheProgressEntity(
            plancheId = plancheId,
            cahierId = cahierId,
            isBookmarked = isBookmarked
        )
        progressDao.setPlancheBookmarked(plancheId, isBookmarked)
    }

    suspend fun saveNote(plancheId: String, cahierId: Int, note: String) {
        progressDao.savePlancheProgress(
            PlancheProgressEntity(
                plancheId = plancheId,
                cahierId = cahierId,
                userNote = note
            )
        )
    }

    suspend fun toggleHabitDay(dayIndex: Int, currentChecked: Boolean, note: String = "") {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateStr = if (!currentChecked) dateFormat.format(Date()) else ""
        progressDao.saveHabitCheck(
            HabitCheckEntity(
                dayIndex = dayIndex,
                isChecked = !currentChecked,
                checkedDate = dateStr,
                note = note
            )
        )
    }

    suspend fun saveQuizResult(cahierId: Int, score: Int, total: Int = 10) {
        progressDao.saveQuizScore(
            QuizScoreEntity(
                cahierId = cahierId,
                score = score,
                totalQuestions = total,
                completedAt = System.currentTimeMillis(),
                isPassed = score >= 8
            )
        )
    }

    suspend fun saveAtelierData(atelierId: String, cahierId: Int, jsonContent: String) {
        progressDao.saveAtelierEntry(
            AtelierEntryEntity(
                atelierId = atelierId,
                cahierId = cahierId,
                keyValuesJson = jsonContent,
                lastUpdated = System.currentTimeMillis()
            )
        )
    }

    suspend fun updateGoalSettings(
        targetPlanches: Int,
        habitName: String,
        habitTime: String,
        habitTrigger: String,
        reminderEnabled: Boolean,
        reminderHour: Int,
        reminderMinute: Int
    ) {
        progressDao.saveUserSettings(
            UserSettingsEntity(
                id = 1,
                dailyTargetPlanches = targetPlanches,
                selectedHabitName = habitName,
                habitTime = habitTime,
                habitTrigger = habitTrigger,
                reminderEnabled = reminderEnabled,
                reminderHour = reminderHour,
                reminderMinute = reminderMinute
            )
        )
    }

    suspend fun updateReminderSettings(
        enabled: Boolean,
        hour: Int,
        minute: Int
    ) {
        val current = progressDao.getUserSettings().map { it ?: UserSettingsEntity() }
        // Fetch or create
        progressDao.saveUserSettings(
            UserSettingsEntity(
                id = 1,
                dailyTargetPlanches = 1,
                habitTime = "%02dh%02d".format(hour, minute),
                reminderEnabled = enabled,
                reminderHour = hour,
                reminderMinute = minute
            )
        )
    }
}
