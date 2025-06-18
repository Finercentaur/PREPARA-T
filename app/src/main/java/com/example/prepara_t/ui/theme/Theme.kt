package com.example.prepara_t.ui.theme // ¡Asegúrate de que este paquete coincida con la ruta de tu archivo!

import android.app.Activity
import android.os.Build // Necesario si descomentas dynamicColor en el futuro
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
// import androidx.compose.material3.dynamicDarkColorScheme // Comentada ya que dynamicColor está inactivo
// import androidx.compose.material3.dynamicLightColorScheme // Comentada ya que dynamicColor está inactivo
import androidx.compose.material3.lightColorScheme
// No se importa Typography aquí porque se espera que esté en Type.kt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext // Necesario si descomentas dynamicColor en el futuro
import androidx.compose.ui.platform.LocalView
// Las siguientes importaciones no son estrictamente necesarias en Theme.kt si Typography está en Type.kt
// y no hay otros usos directos en este archivo. Las dejo comentadas para limpiar,
// pero puedes descomentarlas si las necesitas en el futuro.
// import androidx.compose.ui.text.TextStyle
// import androidx.compose.ui.text.font.FontFamily
// import androidx.compose.ui.text.font.FontWeight
// import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color // Asegúrate de que Color esté importado

// Define una paleta de colores para el tema oscuro
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC), // Un púrpura oscuro
    secondary = Color(0xFF03DAC5), // Un verde azulado
    tertiary = Color(0xFF3700B3), // Un azul oscuro
    background = Color(0xFF121212), // Fondo oscuro
    surface = Color(0xFF121212), // Superficie oscura
    onPrimary = Color.White, // Texto sobre primary
    onSecondary = Color.Black, // Texto sobre secondary
    onTertiary = Color.White, // Texto sobre tertiary
    onBackground = Color.White, // Texto sobre background
    onSurface = Color.White // Texto sobre surface
)

// Define una paleta de colores para el tema claro
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE), // Un púrpura
    secondary = Color(0xFF03DAC5), // Un verde azulado
    tertiary = Color(0xFF3700B3), // Un azul oscuro
    background = Color.White, // Fondo claro
    surface = Color.White, // Superficie clara
    onPrimary = Color.White, // Texto sobre primary
    onSecondary = Color.Black, // Texto sobre secondary
    onTertiary = Color.White, // Texto sobre tertiary
    onBackground = Color.Black, // Texto sobre background
    onSurface = Color.Black // Texto sobre surface
)

/**
 * ¡IMPORTANTE! La definición de 'Typography' debe estar en tu archivo 'Type.kt'
 * (en el mismo paquete: com.example.prepara_t.ui.theme).
 * Este bloque fue removido de aquí para evitar la ambigüedad de resolución.
 * Asegúrate de que tu 'Type.kt' contenga algo como:
 *
 * val Typography = Typography(
 * bodyLarge = TextStyle(
 * fontFamily = FontFamily.Default,
 * fontWeight = FontWeight.Normal,
 * fontSize = 16.sp,
 * lineHeight = 24.sp,
 * letterSpacing = 0.5.sp
 * )
 * )
 */
// La definición de Typography ha sido eliminada de este archivo.

/**
 * Composable que define el tema de la aplicación "PREPARA_T".
 * @param darkTheme Indica si el tema oscuro debe estar activo (por defecto, sigue la configuración del sistema).
 * @param dynamicColor Indica si se deben usar colores dinámicos (solo Android 12+).
 * @param content Contenido Composable al que se aplicará este tema.
 */
@Composable
fun PREPARATTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Detecta si el sistema está en modo oscuro
    // dynamicColor: Boolean = true, // Descomentar para activar colores dinámicos en Android 12+
    content: @Composable () -> Unit // El contenido de la UI que usará este tema
) {
    // Determina el esquema de color a usar
    val colorScheme = when {
        // dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> { // Si se usa dynamicColor
        //     val context = LocalContext.current
        //     if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        // }
        darkTheme -> DarkColorScheme // Si el tema es oscuro
        else -> LightColorScheme // Si el tema es claro
    }

    // Configura el color de la barra de estado y la barra de navegación para un diseño de borde a borde
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb() // Color de la barra de estado
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    // Aplica el MaterialTheme a la jerarquía de la UI
    MaterialTheme(
        colorScheme = colorScheme, // Esquema de colores
        typography = Typography, // Se refiere a la Typography definida en Type.kt
        content = content // Contenido de la UI
    )
}