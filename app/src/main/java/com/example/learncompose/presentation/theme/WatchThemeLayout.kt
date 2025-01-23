package com.example.learncompose.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class AppColors (
    val background: Color,
    val onBackground: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
)

data class AppTypography (
    val titleLarge: TextStyle,
    val titleNormal: TextStyle,
    val body: TextStyle,
    val labelLarge: TextStyle,
    val labelNormal: TextStyle,
    val labelSmall: TextStyle
)

data class AppShape (
    val container: Shape,
    val button: Shape
)

data class AppSize (
    val large: Dp,
    val medium: Dp,
    val normal: Dp,
    val small: Dp
)

//Provide a way to pass properties of design system into composition

val DefColorScheme = staticCompositionLocalOf {
    AppColors(
        background =  Color.Unspecified,
        onBackground = Color.Unspecified,
        primary=  Color.Unspecified,
        onPrimary=  Color.Unspecified,
        secondary=  Color.Unspecified,
        onSecondary=  Color.Unspecified
    )
}

val DefTypography = staticCompositionLocalOf {
    AppTypography(
        titleLarge = TextStyle.Default,
        titleNormal = TextStyle.Default,
        body = TextStyle.Default,
        labelLarge = TextStyle.Default,
        labelNormal = TextStyle.Default,
        labelSmall = TextStyle.Default
    )
}

val DefShape = staticCompositionLocalOf {
    AppShape (
        container = RectangleShape,
        button = RectangleShape
    )
}

val DefSize = staticCompositionLocalOf {
    AppSize (
        large = Dp.Unspecified,
        medium = Dp.Unspecified,
        normal = Dp.Unspecified,
        small = Dp.Unspecified
    )
}