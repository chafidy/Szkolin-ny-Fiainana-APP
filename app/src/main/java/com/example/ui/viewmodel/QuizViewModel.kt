package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.QuizQuestion
import com.example.data.repository.BookData
import com.example.data.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val selectedCahierId: Int = 1,
    val currentQuestionIndex: Int = 0,
    val selectedAnswers: Map<Int, String> = emptyMap(), // questionIndex to "A" or "B"
    val isSubmitted: Boolean = false,
    val finalScore: Int = 0,
    val isPassed: Boolean = false,
    val savedBestScore: Int? = null
)

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProgressRepository

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ProgressRepository(database.progressDao())
        loadBestScore(1)
    }

    fun selectCahier(cahierId: Int) {
        _uiState.update {
            it.copy(
                selectedCahierId = cahierId,
                currentQuestionIndex = 0,
                selectedAnswers = emptyMap(),
                isSubmitted = false,
                finalScore = 0
            )
        }
        loadBestScore(cahierId)
    }

    private fun loadBestScore(cahierId: Int) {
        viewModelScope.launch {
            repository.getQuizScore(cahierId).collect { scoreEntity ->
                _uiState.update { it.copy(savedBestScore = scoreEntity?.score) }
            }
        }
    }

    fun selectOption(questionIndex: Int, option: String) {
        if (_uiState.value.isSubmitted) return
        val current = _uiState.value.selectedAnswers.toMutableMap()
        current[questionIndex] = option
        _uiState.update { it.copy(selectedAnswers = current) }
    }

    fun nextQuestion() {
        val questions = getQuestionsForCahier()
        if (_uiState.value.currentQuestionIndex < questions.size - 1) {
            _uiState.update { it.copy(currentQuestionIndex = it.currentQuestionIndex + 1) }
        }
    }

    fun previousQuestion() {
        if (_uiState.value.currentQuestionIndex > 0) {
            _uiState.update { it.copy(currentQuestionIndex = it.currentQuestionIndex - 1) }
        }
    }

    fun submitQuiz() {
        val questions = getQuestionsForCahier()
        var correctCount = 0
        questions.forEachIndexed { idx, q ->
            val userAns = _uiState.value.selectedAnswers[idx]
            if (userAns == q.correctOption) {
                correctCount++
            }
        }

        val passed = correctCount >= 8
        _uiState.update {
            it.copy(
                isSubmitted = true,
                finalScore = correctCount,
                isPassed = passed
            )
        }

        viewModelScope.launch {
            repository.saveQuizResult(_uiState.value.selectedCahierId, correctCount, questions.size)
        }
    }

    fun resetQuiz() {
        _uiState.update {
            it.copy(
                currentQuestionIndex = 0,
                selectedAnswers = emptyMap(),
                isSubmitted = false,
                finalScore = 0
            )
        }
    }

    fun getQuestionsForCahier(): List<QuizQuestion> {
        val cahierId = _uiState.value.selectedCahierId
        val qList = BookData.quizzes.filter { it.cahierId == cahierId }
        return if (qList.isNotEmpty()) qList else BookData.quizzes.take(10)
    }
}
