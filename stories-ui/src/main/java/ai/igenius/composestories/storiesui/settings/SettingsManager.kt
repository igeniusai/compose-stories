package ai.igenius.composestories.storiesui.settings

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsManager(
    context: Context
) {
    private val dataStore = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("compose-stories")
    }

    val lastStoryId: Flow<Int?> = dataStore.data
        .map { preferences -> preferences[LAST_STORY_ID] }

    suspend fun setLastStoryId(value: Int?) {
        dataStore.edit { settings ->
            value?.let {
                settings[LAST_STORY_ID] = value
            } ?: run {
                if (settings.contains(LAST_STORY_ID)) {
                    settings.remove(LAST_STORY_ID)
                }
            }
        }
    }

    companion object {
        private val LAST_STORY_ID = intPreferencesKey("last_node_id")
    }
}