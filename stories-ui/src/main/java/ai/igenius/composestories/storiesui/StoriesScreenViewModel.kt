package ai.igenius.composestories.storiesui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StoriesScreenViewModel : ViewModel() {

    private val _lastStoryId: MutableStateFlow<Int?> = MutableStateFlow(null)
    val lastNodeId: StateFlow<Int?> = _lastStoryId.asStateFlow()

    fun saveLastStoryId(value: Int?) {
        _lastStoryId.value = value
    }
}