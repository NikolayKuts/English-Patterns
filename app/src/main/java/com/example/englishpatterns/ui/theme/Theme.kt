package com.example.englishpatterns.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val DarkColorPalette = darkColorScheme(
    primary = Purple200,
    secondary = Teal200
)

private val LightColorPalette = lightColorScheme(
    primary = Purple500,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun EnglishPatternsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalMainColors provides colorScheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object EnglishPatternsTheme {

    val colors: ColorScheme
        @Composable
        get() = LocalMainColors.current

//    val typographies: Typography
//        @Composable
//        get() = LocalCustomTypographies.current
//
//    val shapes: Shapes
//        @Composable
//        get() = LocalCustomShapes.current

}

val LocalMainColors = staticCompositionLocalOf<ColorScheme> {
    LightColorPalette
}

private val LocalCustomShapes = staticCompositionLocalOf<Shapes> {
    error("No shapes provided")
}

val LocalCustomTypographies = staticCompositionLocalOf<Typography> {
    error("No typographies provided")
}