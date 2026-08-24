package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.WorkshopDefinition
import com.example.data.repository.BookData
import com.example.data.repository.ProgressRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONObject

data class AtelierUiState(
    val selectedAtelierId: String = "atelier_budget",
    val formValues: Map<String, String> = emptyMap(),
    val isSavedSuccess: Boolean = false,
    // Calculated live values
    val budgetResultTotalRecu: Long = 0L,
    val budgetResultEpargne20: Long = 0L,
    val budgetResultBesoins: Long = 0L,
    val budgetResultEnvies: Long = 0L,
    val budgetBilan: Long = 0L,
    val rateCalculatedHourly: Long = 0L,
    val rateCalculatedFixed: Long = 0L
)

class AtelierViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProgressRepository

    private val _uiState = MutableStateFlow(AtelierUiState())
    val uiState: StateFlow<AtelierUiState> = _uiState.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ProgressRepository(database.progressDao())

        // Load active atelier data when switching
        viewModelScope.launch {
            _uiState.map { it.selectedAtelierId }.distinctUntilChanged().collect { atelierId ->
                loadAtelier(atelierId)
            }
        }
    }

    fun selectAtelier(atelierId: String) {
        _uiState.update { it.copy(selectedAtelierId = atelierId, isSavedSuccess = false) }
    }

    private fun loadAtelier(atelierId: String) {
        viewModelScope.launch {
            repository.getAtelierEntry(atelierId).firstOrNull()?.let { entry ->
                try {
                    val json = JSONObject(entry.keyValuesJson)
                    val map = mutableMapOf<String, String>()
                    val keys = json.keys()
                    while (keys.hasNext()) {
                        val k = keys.next()
                        map[k] = json.getString(k)
                    }
                    _uiState.update { it.copy(formValues = map) }
                    recalculate()
                } catch (e: Exception) {
                    // ignore format errors
                }
            } ?: run {
                _uiState.update { it.copy(formValues = emptyMap()) }
                recalculate()
            }
        }
    }

    fun updateField(fieldId: String, value: String) {
        val newMap = _uiState.value.formValues.toMutableMap()
        newMap[fieldId] = value
        _uiState.update { it.copy(formValues = newMap, isSavedSuccess = false) }
        recalculate()
    }

    private fun recalculate() {
        val values = _uiState.value.formValues

        // 1. Budget calculation: A - B - C - D
        val recuPoche = values["recu_poche"]?.toLongOrNull() ?: 0L
        val recuJobs = values["recu_jobs"]?.toLongOrNull() ?: 0L
        val recuAutres = values["recu_autres"]?.toLongOrNull() ?: 0L
        val totalA = recuPoche + recuJobs + recuAutres

        val epargneB = (totalA * 0.20).toLong()

        val besoinTransport = values["besoin_transport"]?.toLongOrNull() ?: 0L
        val besoinRepas = values["besoin_repas"]?.toLongOrNull() ?: 0L
        val besoinTel = values["besoin_tel"]?.toLongOrNull() ?: 0L
        val totalC = besoinTransport + besoinRepas + besoinTel

        val envieSorties = values["envie_sorties"]?.toLongOrNull() ?: 0L
        val envieSnacks = values["envie_snacks"]?.toLongOrNull() ?: 0L
        val envieAutres = values["envie_autres"]?.toLongOrNull() ?: 0L
        val totalD = envieSorties + envieSnacks + envieAutres

        val bilan = totalA - epargneB - totalC - totalD

        // 2. Rate calculation: (A + B) / C * D
        val gainA = values["gains_voulus"]?.toLongOrNull() ?: 0L
        val fraisB = values["frais_mensuels"]?.toLongOrNull() ?: 0L
        val heuresC = values["heures_mois"]?.toLongOrNull() ?: 1L
        val heuresD = values["heures_offre"]?.toLongOrNull() ?: 1L
        val hourly = if (heuresC > 0) (gainA + fraisB) / heuresC else 0L
        val fixed = hourly * heuresD

        _uiState.update {
            it.copy(
                budgetResultTotalRecu = totalA,
                budgetResultEpargne20 = epargneB,
                budgetResultBesoins = totalC,
                budgetResultEnvies = totalD,
                budgetBilan = bilan,
                rateCalculatedHourly = hourly,
                rateCalculatedFixed = fixed
            )
        }
    }

    fun saveCurrentAtelier() {
        viewModelScope.launch {
            val currentId = _uiState.value.selectedAtelierId
            val atelierDef = BookData.workshops.find { it.id == currentId } ?: return@launch
            val json = JSONObject(_uiState.value.formValues).toString()
            repository.saveAtelierData(currentId, atelierDef.cahierId, json)
            _uiState.update { it.copy(isSavedSuccess = true) }
        }
    }

    fun resetForm() {
        _uiState.update { it.copy(formValues = emptyMap(), isSavedSuccess = false) }
        recalculate()
    }
}
