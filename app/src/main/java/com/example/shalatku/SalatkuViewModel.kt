package com.example.shalatku

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SalatKuViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SalatKuUiState())
    val uiState: StateFlow<SalatKuUiState> = _uiState.asStateFlow()

    fun onEvent(event: SalatKuEvent) {
        when (event) {
            is SalatKuEvent.PrayerToggled -> togglePrayer(event.name)
            SalatKuEvent.PrayersCleared -> clearPrayers()
            is SalatKuEvent.DaySelected -> selectDay(event.index)
            SalatKuEvent.TasbihIncremented -> incrementTasbih()
            SalatKuEvent.TasbihReset -> resetTasbih()
            is SalatKuEvent.RoundSizeSelected -> selectRoundSize(event.size)
            SalatKuEvent.PrayerTimesRequested -> loadPrayerTimes()

        }
    }
    // --- Tasbih ---
    private fun incrementTasbih() {
        _uiState.update { state ->
            val next = state.tasbihCount + 1
            if (next == state.roundSize) {
                state.copy(tasbihCount = 0, completedRounds = state.completedRounds + 1)
            } else {
                state.copy(tasbihCount = next)
            }
        }
    }

    private fun selectRoundSize(size: Int) {
        // hitungan direset, completedRounds tidak disentuh
        _uiState.update { it.copy(roundSize = size, tasbihCount = 0) }
    }

    private fun resetTasbih() {
        _uiState.update { it.copy(tasbihCount = 0, completedRounds = 0) }
    }

    // --- Tracker ---
    private fun togglePrayer(name: String) {
        _uiState.update { state ->
            val done = if (name in state.donePrayers) {
                state.donePrayers - name
            } else {
                state.donePrayers + name
            }
            state.copy(donePrayers = done)
        }
    }

    private fun clearPrayers() {
        _uiState.update { it.copy(donePrayers = emptyList()) }
    }

    // --- Home ---
    private fun selectDay(index: Int) {
        _uiState.update { it.copy(selectedDay = index) }
    }

    private var loadCount = 0

    private fun loadPrayerTimes() {
        if (_uiState.value.prayerTimes is PrayerTimesUiState.Loading) return

        loadCount++
        val shouldFail = loadCount % 2 == 0

        _uiState.update { it.copy(prayerTimes = PrayerTimesUiState.Loading) }

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    prayerTimes = if (shouldFail) {
                        PrayerTimesUiState.Error("Gagal memuat jadwal. Coba lagi.")
                    } else {
                        PrayerTimesUiState.Success(samplePrayers)
                    }
                )
            }
        }
    }
}