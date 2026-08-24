package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.PlancheProgressEntity
import com.example.data.model.Cahier
import com.example.data.model.Planche
import com.example.data.repository.BookData
import com.example.data.repository.ProgressRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ReaderUiState(
    val currentCahierId: Int = 1,
    val currentPlancheIndex: Int = 0,
    val searchQuery: String = "",
    val textScale: Float = 1.0f,
    val isFocusMode: Boolean = false,
    val readPlanchesIds: Set<String> = emptySet(),
    val bookmarkedIds: Set<String> = emptySet(),
    val userNotes: Map<String, String> = emptyMap()
)

class ReaderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProgressRepository

    private val _uiState = MutableStateFlow(ReaderUiState())
    val uiState: StateFlow<ReaderUiState> = _uiState.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ProgressRepository(database.progressDao())

        viewModelScope.launch {
            repository.allProgress.collect { progressList ->
                val readSet = progressList.filter { it.isRead }.map { it.plancheId }.toSet()
                val bookmarkSet = progressList.filter { it.isBookmarked }.map { it.plancheId }.toSet()
                val notes = progressList.filter { it.userNote.isNotEmpty() }.associate { it.plancheId to it.userNote }
                _uiState.update {
                    it.copy(
                        readPlanchesIds = readSet,
                        bookmarkedIds = bookmarkSet,
                        userNotes = notes
                    )
                }
            }
        }
    }

    fun selectCahier(cahierId: Int) {
        _uiState.update {
            it.copy(currentCahierId = cahierId, currentPlancheIndex = 0)
        }
    }

    fun selectPlanche(cahierId: Int, index: Int) {
        _uiState.update {
            it.copy(currentCahierId = cahierId, currentPlancheIndex = index.coerceAtLeast(0))
        }
    }

    fun nextPlanche() {
        val cahierPlanches = BookData.getPlanchesForCahier(_uiState.value.currentCahierId)
        val nextIdx = _uiState.value.currentPlancheIndex + 1
        if (nextIdx < cahierPlanches.size) {
            _uiState.update { it.copy(currentPlancheIndex = nextIdx) }
        } else if (_uiState.value.currentCahierId < 8) {
            _uiState.update {
                it.copy(currentCahierId = it.currentCahierId + 1, currentPlancheIndex = 0)
            }
        }
    }

    fun previousPlanche() {
        val prevIdx = _uiState.value.currentPlancheIndex - 1
        if (prevIdx >= 0) {
            _uiState.update { it.copy(currentPlancheIndex = prevIdx) }
        } else if (_uiState.value.currentCahierId > 1) {
            val prevCahierPlanches = BookData.getPlanchesForCahier(_uiState.value.currentCahierId - 1)
            _uiState.update {
                it.copy(
                    currentCahierId = it.currentCahierId - 1,
                    currentPlancheIndex = (prevCahierPlanches.size - 1).coerceAtLeast(0)
                )
            }
        }
    }

    fun togglePlancheRead(plancheId: String, cahierId: Int) {
        viewModelScope.launch {
            val isRead = _uiState.value.readPlanchesIds.contains(plancheId)
            repository.togglePlancheRead(plancheId, cahierId, isRead)
        }
    }

    fun toggleBookmark(plancheId: String, cahierId: Int) {
        viewModelScope.launch {
            val isBookmarked = _uiState.value.bookmarkedIds.contains(plancheId)
            repository.toggleBookmark(plancheId, cahierId, !isBookmarked)
        }
    }

    fun saveNote(plancheId: String, cahierId: Int, note: String) {
        viewModelScope.launch {
            repository.saveNote(plancheId, cahierId, note)
        }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setTextScale(scale: Float) {
        _uiState.update { it.copy(textScale = scale.coerceIn(0.85f, 1.35f)) }
    }

    fun toggleFocusMode() {
        _uiState.update { it.copy(isFocusMode = !it.isFocusMode) }
    }
}
