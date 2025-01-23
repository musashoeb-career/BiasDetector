package com.example.learncompose.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


private val PrimaryColorScheme = AppColors (
    background = YiminBlue,
    onBackground = SuperiorBlue,
    primary = SkyBlue,
    onPrimary = OffWhite,
    secondary = BattleGrey,
    onSecondary = BattleGrey
)

private val SecondaryColorScheme = AppColors (
    background = SuperiorBlue,
    onBackground = YiminBlue,
    primary = OffWhite,
    onPrimary = SkyBlue,
    secondary = BattleGrey,
    onSecondary = BattleGrey
)

private val typography = AppTypography(
    titleLarge = TextStyle(
        fontFamily = Gantari,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleNormal = TextStyle(
        fontFamily = Gantari,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    body = TextStyle(
        fontFamily = Gantari,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Gantari,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    labelNormal = TextStyle(
        fontFamily = Gantari,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Gantari,
        fontWeight = FontWeight.Bold,
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
    val colorScheme = if (isDarkTheme) SecondaryColorScheme else PrimaryColorScheme
    CompositionLocalProvider(
        DefColorScheme provides colorScheme,
        DefTypography provides typography,
        DefShape provides shape,
        DefSize provides size,

    ) {
        content()
    }
}

object WatchTheme {
    val colorScheme : AppColors
        @Composable get() = DefColorScheme.current

    val typography : AppTypography
        @Composable get() = DefTypography.current

    val shapes : AppShape
        @Composable get() = DefShape.current

    val size : AppSize
        @Composable get() = DefSize.current
}