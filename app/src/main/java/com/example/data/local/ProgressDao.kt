package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    // Planche progress
    @Query("SELECT * FROM planche_progress")
    fun getAllPlancheProgress(): Flow<List<PlancheProgressEntity>>

    @Query("SELECT * FROM planche_progress WHERE cahierId = :cahierId")
    fun getProgressForCahier(cahierId: Int): Flow<List<PlancheProgressEntity>>

    @Query("SELECT * FROM planche_progress WHERE plancheId = :plancheId")
    fun getPlancheProgress(plancheId: String): Flow<PlancheProgressEntity?>

    @Query("SELECT * FROM planche_progress WHERE isBookmarked = 1")
    fun getBookmarks(): Flow<List<PlancheProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlancheProgress(progress: PlancheProgressEntity)

    @Query("UPDATE planche_progress SET isRead = :isRead, readTimestamp = :timestamp WHERE plancheId = :plancheId")
    suspend fun setPlancheRead(plancheId: String, isRead: Boolean, timestamp: Long)

    @Query("UPDATE planche_progress SET isBookmarked = :isBookmarked WHERE plancheId = :plancheId")
    suspend fun setPlancheBookmarked(plancheId: String, isBookmarked: Boolean)

    @Query("UPDATE planche_progress SET userNote = :note WHERE plancheId = :plancheId")
    suspend fun setPlancheNote(plancheId: String, note: String)

    // Habit checks (30 jours de discipline)
    @Query("SELECT * FROM habit_checks ORDER BY dayIndex ASC")
    fun getHabitChecks(): Flow<List<HabitCheckEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHabitCheck(habit: HabitCheckEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllHabitChecks(habits: List<HabitCheckEntity>)

    // Quiz scores
    @Query("SELECT * FROM quiz_scores")
    fun getAllQuizScores(): Flow<List<QuizScoreEntity>>

    @Query("SELECT * FROM quiz_scores WHERE cahierId = :cahierId")
    fun getQuizScore(cahierId: Int): Flow<QuizScoreEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuizScore(score: QuizScoreEntity)

    // Ateliers
    @Query("SELECT * FROM atelier_entries WHERE atelierId = :atelierId")
    fun getAtelierEntry(atelierId: String): Flow<AtelierEntryEntity?>

    @Query("SELECT * FROM atelier_entries")
    fun getAllAtelierEntries(): Flow<List<AtelierEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAtelierEntry(entry: AtelierEntryEntity)

    // User settings / profile
    @Query("SELECT * FROM user_settings WHERE id = 1")
    fun getUserSettings(): Flow<UserSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserSettings(settings: UserSettingsEntity)
}
