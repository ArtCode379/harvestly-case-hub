package harvestly.accessories.harvestlycasehub.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Forest,
    onPrimary = PureWhite,
    secondary = HarvestGold,
    onSecondary = CharcoalGreen,
    background = WarmIvory,
    onBackground = CharcoalGreen,
    surface = PureWhite,
    onSurface = CharcoalGreen,
    surfaceVariant = PaleSage,
    onSurfaceVariant = Sage,
    outline = SoftBorder,
    tertiary = ForestLight
)

private val DarkColors = darkColorScheme(
    primary = HarvestGold,
    onPrimary = CharcoalGreen,
    secondary = ForestLight,
    background = CharcoalGreen,
    onBackground = WarmIvory,
    surface = Forest,
    onSurface = WarmIvory,
    surfaceVariant = ForestLight,
    onSurfaceVariant = SoftBorder,
    outline = Sage
)

@Composable
fun ProductAppDOFIPTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content
    )
}
