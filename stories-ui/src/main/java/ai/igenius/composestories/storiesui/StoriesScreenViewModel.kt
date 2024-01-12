package ai.igenius.composestories.storiesui

import ai.igenius.composestories.storiesui.settings.SettingsManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StoriesScreenViewModel(
    context: Context
) : ViewModel() {

    private val settingsManager = SettingsManager(context.applicationContext)

    val settings: StateFlow<SettingsState> = settingsManager.lastStoryId
        .mapLatest { SettingsState.Data(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SettingsState.Loading
        )

    fun saveLastStoryId(value: Int?) {
        viewModelScope.launch {
            settingsManager.setLastStoryId(value)
        }
    }
}

sealed class SettingsState {
    data object Loading : SettingsState()

    data class Data(val lastNodeId: Int?): SettingsState()
}