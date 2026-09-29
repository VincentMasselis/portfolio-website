package com.masselis.portfolio.ui.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver

@OptIn(ExperimentalMaterial3Api::class)
internal val TopBarStatesSaver: Saver<MutableMap<String, TopAppBarState>, Any> = mapSaver(
    save = { states -> states.mapValues { (_, state) -> with(TopAppBarStateSaver) { save(state) } } },
    restore = { saved ->
        saved.mapValuesTo(mutableMapOf()) { (_, value) -> TopAppBarStateSaver.restore(value!!)!! }
    },
)

// Material declares its saver with a star projected saveable type, which forbids calling `restore`
@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNCHECKED_CAST")
private val TopAppBarStateSaver = TopAppBarState.Saver as Saver<TopAppBarState, Any>
