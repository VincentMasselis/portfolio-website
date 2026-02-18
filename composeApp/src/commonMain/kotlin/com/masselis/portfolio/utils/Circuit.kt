package com.masselis.portfolio.utils

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.presenter.Presenter

public interface NoOpPresenter : Presenter<NoOpState> {
    @Composable
    override fun present(): NoOpState = NoOpState
}

/**
 * Placeholder for subclasses of [com.slack.circuit.runtime.screen.Screen] which only have a single [com.slack.circuit.runtime.CircuitUiState] without any data
 * inside.
 */
public data object NoOpState : CircuitUiState