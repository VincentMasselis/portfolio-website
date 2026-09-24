package com.masselis.portfolio.data

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


// Tonal palettes generated with material-color-utilities (HCT): primary from brand green #4CAF50,
// neutrals from brand navy #1C2E3F, tertiary from the navy hue at higher chroma. Dark scheme tones.
private val PortfolioColorScheme: ColorScheme = darkColorScheme(
    primary = Color(0xFF78DC77),
    onPrimary = Color(0xFF00390A),
    primaryContainer = Color(0xFF005313),
    onPrimaryContainer = Color(0xFF94F990),
    inversePrimary = Color(0xFF006E1C),
    secondary = Color(0xFFBACCB3),
    onSecondary = Color(0xFF253423),
    secondaryContainer = Color(0xFF3B4B38),
    onSecondaryContainer = Color(0xFFD5E8CF),
    tertiary = Color(0xFF97CBFF),
    onTertiary = Color(0xFF003354),
    tertiaryContainer = Color(0xFF004A77),
    onTertiaryContainer = Color(0xFFCEE5FF),
    background = Color(0xFF08141F),
    onBackground = Color(0xFFD7E4F4),
    surface = Color(0xFF08141F),
    onSurface = Color(0xFFD7E4F4),
    surfaceVariant = Color(0xFF35495C),
    onSurfaceVariant = Color(0xFFB4C9E0),
    surfaceTint = Color(0xFF78DC77),
    inverseSurface = Color(0xFFD7E4F4),
    inverseOnSurface = Color(0xFF26313E),
    outline = Color(0xFF7F93A9),
    outlineVariant = Color(0xFF35495C),
    surfaceBright = Color(0xFF2F3A47),
    surfaceDim = Color(0xFF08141F),
    surfaceContainerLowest = Color(0xFF040F1A),
    surfaceContainerLow = Color(0xFF111D28),
    surfaceContainer = Color(0xFF15212C),
    surfaceContainerHigh = Color(0xFF1F2B37),
    surfaceContainerHighest = Color(0xFF2A3642),
)

private val PortfolioTypography: Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        letterSpacing = 2.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = 1.5.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 1.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        letterSpacing = 1.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
    ),
)

@Composable
public fun PortfolioTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PortfolioColorScheme,
        typography = PortfolioTypography,
        content = content,
    )
}