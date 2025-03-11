package com.example.learncompose.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/*The following classes are interfaces. AppColors, for example, represents a
* palette of 5 colors. A lighter palette vs a darker palette would have their respective 5 colors*/

data class AppColors (
    val background: Color,
    val onBackground: Color,
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
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

/*Without these, we would have to pass our (color scheme, typography, etc) to each
* component that requires it. Here's just a global way of doing that. */

val LocalColorScheme = staticCompositionLocalOf {
    AppColors(
        background =  Color.Unspecified,
        onBackground = Color.Unspecified,
        primary=  Color.Unspecified,
        secondary=  Color.Unspecified,
        tertiary =  Color.Unspecified
    )
}

val LocalTypography = staticCompositionLocalOf {
    AppTypography(
        titleLarge = TextStyle.Default,
        titleNormal = TextStyle.Default,
        body = TextStyle.Default,
        labelLarge = TextStyle.Default,
        labelNormal = TextStyle.Default,
        labelSmall = TextStyle.Default
    )
}

val LocalShape = staticCompositionLocalOf {
    AppShape (
        container = RectangleShape,
        button = RectangleShape
    )
}

val LocalSize = staticCompositionLocalOf {
    AppSize (
        large = Dp.Unspecified,
        medium = Dp.Unspecified,
        normal = Dp.Unspecified,
        small = Dp.Unspecified
    )
}