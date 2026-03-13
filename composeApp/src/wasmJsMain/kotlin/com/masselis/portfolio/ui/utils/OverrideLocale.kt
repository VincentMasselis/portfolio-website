package com.masselis.portfolio.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun(
    """
    (localeCode) => {
        if (localeCode !== null) {
            localStorage.setItem('__customLocale', localeCode);
        } else {
            localStorage.removeItem('__customLocale');
        }
        window.dispatchEvent(new Event('languagechange'));
    }
"""
)
private external fun insertCustomLocale(localeCode: String?)

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("() => localStorage.getItem('__customLocale')")
private external fun retrieveCustomLocale(): String?

internal object OverrideLocale {
    private var _state: Locale? by mutableStateOf(retrieveCustomLocale()?.let(::Locale))
    var value: Locale?
        private set(value) {
            insertCustomLocale(value?.toLanguageTag())
            _state = value
        }
        get() = _state

    fun set(locale: Locale) {
        if (value != locale) {
            value = locale
        }
    }

    fun clear() {
        if (value != null) {
            value = null
        }
    }

    @Composable
    fun CompositionProvider(content: @Composable () -> Unit) {
        key(value) {
            content()
        }
    }
}

internal val Locale.Companion.displayedLocale: Locale by derivedStateOf { OverrideLocale.value ?: Locale.current }