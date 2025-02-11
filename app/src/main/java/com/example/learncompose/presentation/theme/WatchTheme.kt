package com.example.learncompose.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp


private val LightColorScheme = AppColors (
    background = OffWhite,
    onBackground = SuperiorBlue,
    primary = YiminBlue,
    secondary = SkyBlue,
    tertiary = BattleGrey,
)

private val DarkColorScheme = AppColors (
    background = YiminBlue,
    onBackground = OffWhite,
    primary = SuperiorBlue,
    secondary = SkyBlue,
    tertiary = BattleGrey,
)

private val typography = AppTypography(
    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleNormal = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    body = TextStyle(
        fontFamily = Gantari,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Gantari,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    labelNormal = TextStyle(
        fontFamily = Gantari,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Gantari,
        fontWeight = FontWeight.Thin,
        fontSize = 24.sp
    ),

)

private val shape = AppShape (
    container = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(50.dp)
)

private val size = AppSize (
    large = 24.dp,
    medium = 16.dp,
    normal = 12.dp,
    small = 8.dp
)

@Composable
fun WatchTheme (
    isDarkTheme : Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =  DarkColorScheme
    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        LocalTypography provides typography,
        LocalShape provides shape,
       LocalSize provides size,

    ) {
        content()
    }
}

object WatchTheme {
    val colorScheme : AppColors
        @Composable get() = LocalColorScheme.current

    val typography : AppTypography
        @Composable get() = LocalTypography.current

    val shapes : AppShape
        @Composable get() = LocalShape.current

    val size : AppSize
        @Composable get() = LocalSize.current
}