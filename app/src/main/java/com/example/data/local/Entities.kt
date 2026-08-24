package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planche_progress")
data class PlancheProgressEntity(
    @PrimaryKey val plancheId: String,
    val cahierId: Int,
    val isRead: Boolean = false,
    val isBookmarked: Boolean = false,
    val readTimestamp: Long = 0L,
    val userNote: String = ""
)

@Entity(tableName = "habit_checks")
data class HabitCheckEntity(
    @PrimaryKey val dayIndex: Int, // 1..30
    val isChecked: Boolean = false,
    val checkedDate: String = "",
    val note: String = ""
)

@Entity(tableName = "quiz_scores")
data class QuizScoreEntity(
    @PrimaryKey val cahierId: Int,
    val score: Int,
    val totalQuestions: Int = 10,
    val completedAt: Long = System.currentTimeMillis(),
    val isPassed: Boolean = false // true if score >= 8
)

@Entity(tableName = "atelier_entries")
data class AtelierEntryEntity(
    @PrimaryKey val atelierId: String,
    val cahierId: Int,
    val keyValuesJson: String, // JSON or serialized string of fieldId: value
    val lastUpdated: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val userName: String = "Apprenant",
    val dailyTargetPlanches: Int = 1,
    val selectedHabitName: String = "10 minutes de lecture quotidienne",
    val habitTime: String = "20h30",
    val habitTrigger: String = "Juste avant de dormir",
    val currentStreak: Int = 1,
    val longestStreak: Int = 1,
    val lastActiveDate: String = "",
    val totalReadingMinutes: Int = 0,
    val reminderEnabled: Boolean = false,
    val reminderHour: Int = 20,
    val reminderMinute: Int = 30
)
