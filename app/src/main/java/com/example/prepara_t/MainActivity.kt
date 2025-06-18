package com.example.prepara_t

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image // Importar Image para el placeholder de imagen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size // Importar size para el placeholder de imagen
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField // Importar OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults // Importar OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

// Enum para manejar las diferentes pantallas de la aplicación
enum class AppScreen {
    HOME, // Pantalla principal (menú)
    POSTAL_CODE_INPUT, // Pantalla para ingresar el código postal
    CREDITS, // Nueva pantalla para mostrar los créditos
    FENOMENOS, // Nueva pantalla para el botón 'Inicio'
    GEOLOGICOS, // Nueva pantalla para fenómenos geológicos
    ERUPCION_VOLCANICA, // Nueva pantalla para erupción volcánica
    SISMO, // Nueva pantalla para Sismo
    TSUNAMI, // Nueva pantalla para Tsunami
    GRIETAS, // Nueva pantalla para Grietas
    DESLIZAMIENTO_LADERAS, // Nueva pantalla para Deslizamiento de laderas
    HUNDIMIENTOS_SOCAVONES, // Nueva pantalla para Hundimientos y socavones
    HIDROMETEOROLOGICOS, // Nueva pantalla para Riesgos Hidrometeorológicos
    CICLONES_TROPICALES,
    INUNDACIONES,
    HELADAS,
    NIEBLA,
    TORMENTAS_ELECTRICAS,
    GRANIZO,
    FRENTE_FRIO,
    SEQUIAS
}

@Composable
fun PREPARATTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = Color(0xFF2196F3),
            secondary = Color(0xFF03A9F4),
            background = Color.White
        ),
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PREPARATTheme { // Aplica el tema definido en tu proyecto
                Surface(
                    modifier = Modifier.fillMaxSize(), // La superficie ocupa toda la pantalla
                    color = MaterialTheme.colorScheme.background // Usa el color de fondo del tema
                ) {
                    // Estado mutable para controlar qué pantalla se muestra actualmente
                    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }

                    // Lógica para mostrar la pantalla correcta según el estado
                    when (currentScreen) {
                        AppScreen.HOME -> PantallaInicio(
                            onNavigateToPostalCode = { currentScreen = AppScreen.POSTAL_CODE_INPUT },
                            onNavigateToCredits = { currentScreen = AppScreen.CREDITS },
                            onNavigateToFenomenos = { currentScreen = AppScreen.FENOMENOS }
                        )
                        AppScreen.POSTAL_CODE_INPUT -> PantallaCodigoPostal(
                            onBackToMenu = { currentScreen = AppScreen.HOME }
                        )
                        AppScreen.CREDITS -> PantallaCreditos(
                            onBackToMenu = { currentScreen = AppScreen.HOME }
                        )
                        AppScreen.FENOMENOS -> PantallaInicioFenomenos(
                            onBackToMenu = { currentScreen = AppScreen.HOME },
                            onNavigateToGeologicos = { currentScreen = AppScreen.GEOLOGICOS },
                            onNavigateToHidrometeorologicos = { currentScreen = AppScreen.HIDROMETEOROLOGICOS }
                        )
                        AppScreen.GEOLOGICOS -> PantallaGeologicos(
                            onBackToFenomenos = { currentScreen = AppScreen.FENOMENOS },
                            onActividad = { /* TODO: Implementar navegación a actividad */ },
                            onBackToMenu = { currentScreen = AppScreen.HOME },
                            onNavigateToErupcion = { currentScreen = AppScreen.ERUPCION_VOLCANICA },
                            onNavigateToSismo = { currentScreen = AppScreen.SISMO },
                            onNavigateToTsunami = { currentScreen = AppScreen.TSUNAMI },
                            onNavigateToGrietas = { currentScreen = AppScreen.GRIETAS },
                            onNavigateToDeslizamiento = { currentScreen = AppScreen.DESLIZAMIENTO_LADERAS },
                            onNavigateToHundimientos = { currentScreen = AppScreen.HUNDIMIENTOS_SOCAVONES }
                        )
                        AppScreen.ERUPCION_VOLCANICA -> PantallaErupcionVolcanica(
                            onBack = { currentScreen = AppScreen.GEOLOGICOS },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.SISMO }
                        )
                        AppScreen.SISMO -> PantallaSismo(
                            onBack = { currentScreen = AppScreen.ERUPCION_VOLCANICA },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.TSUNAMI }
                        )
                        AppScreen.TSUNAMI -> PantallaTsunami(
                            onBack = { currentScreen = AppScreen.SISMO },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.GRIETAS }
                        )
                        AppScreen.GRIETAS -> PantallaGrietas(
                            onBack = { currentScreen = AppScreen.TSUNAMI },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.DESLIZAMIENTO_LADERAS }
                        )
                        AppScreen.DESLIZAMIENTO_LADERAS -> PantallaDeslizamientoLaderas(
                            onBack = { currentScreen = AppScreen.GRIETAS },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.HUNDIMIENTOS_SOCAVONES }
                        )
                        AppScreen.HUNDIMIENTOS_SOCAVONES -> PantallaHundimientosSocavones(
                            onBack = { currentScreen = AppScreen.DESLIZAMIENTO_LADERAS },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onRiesgos = { currentScreen = AppScreen.GEOLOGICOS }
                        )
                        AppScreen.HIDROMETEOROLOGICOS -> PantallaRiesgosHidrometeorologicos(
                            onBack = { currentScreen = AppScreen.FENOMENOS },
                            onActividad = { /* TODO: Implementar navegación a actividad */ },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNavigate = { pantalla -> currentScreen = pantalla }
                        )
                        AppScreen.CICLONES_TROPICALES -> PantallaCiclonesTropicales(
                            onBack = { currentScreen = AppScreen.HIDROMETEOROLOGICOS },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.INUNDACIONES }
                        )
                        AppScreen.INUNDACIONES -> PantallaInundaciones(
                            onBack = { currentScreen = AppScreen.CICLONES_TROPICALES },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.HELADAS }
                        )
                        AppScreen.HELADAS -> PantallaHeladas(
                            onBack = { currentScreen = AppScreen.INUNDACIONES },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.NIEBLA }
                        )
                        AppScreen.NIEBLA -> PantallaNiebla(
                            onBack = { currentScreen = AppScreen.HELADAS },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.TORMENTAS_ELECTRICAS }
                        )
                        AppScreen.TORMENTAS_ELECTRICAS -> PantallaTormentasElectricas(
                            onBack = { currentScreen = AppScreen.NIEBLA },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.GRANIZO }
                        )
                        AppScreen.GRANIZO -> PantallaGranizo(
                            onBack = { currentScreen = AppScreen.TORMENTAS_ELECTRICAS },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.FRENTE_FRIO }
                        )
                        AppScreen.FRENTE_FRIO -> PantallaFrenteFrio(
                            onBack = { currentScreen = AppScreen.GRANIZO },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.SEQUIAS }
                        )
                        AppScreen.SEQUIAS -> PantallaSequias(
                            onBack = { currentScreen = AppScreen.FRENTE_FRIO },
                            onMenu = { currentScreen = AppScreen.HOME },
                            onNext = { currentScreen = AppScreen.HIDROMETEOROLOGICOS }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable para la pantalla de inicio (menú principal) de la aplicación.
 * @param modifier Modificador para personalizar el diseño del Column.
 * @param onNavigateToPostalCode Callback que se invoca para navegar a la pantalla de código postal.
 * @param onNavigateToCredits Callback que se invoca para navegar a la pantalla de créditos.
 * @param onNavigateToFenomenos Callback que se invoca para navegar a la pantalla de fenómenos.
 */
@Composable
fun PantallaInicio(
    modifier: Modifier = Modifier,
    onNavigateToPostalCode: () -> Unit,
    onNavigateToCredits: () -> Unit,
    onNavigateToFenomenos: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Fenómenos naturales y antrópicos",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Espacio entre el título y el primer botón
        Spacer(modifier = Modifier.height(72.dp))

        // Botón "Inicio"
        BotonMenu(
            texto = "Inicio",
            onClick = onNavigateToFenomenos,
            fontSize = 32.sp, // Tamaño de fuente grande para este botón
            buttonModifier = Modifier
                .widthIn(min = 280.dp) // Ancho mínimo del botón
                .height(80.dp), // Altura del botón
            backgroundColor = Color(0xFFF0F0F0) // Color de fondo gris claro
        )

        // Espacio entre el botón "Inicio" y "Código postal"
        Spacer(modifier = Modifier.height(24.dp))

        // Botón "Ingresa tu código postal" que ahora navega a la nueva pantalla
        BotonMenu(
            texto = "Ingresa tu código postal",
            onClick = onNavigateToPostalCode, // Invoca el callback de navegación
            fontSize = 18.sp,
            buttonModifier = Modifier
                .widthIn(min = 280.dp)
                .height(60.dp),
            backgroundColor = Color(0xFFF0F0F0)
        )

        // Espacio entre el botón "Código postal" y "Créditos"
        Spacer(modifier = Modifier.height(24.dp))

        // Botón "Créditos"
        BotonMenu(
            texto = "Créditos",
            onClick = onNavigateToCredits,
            fontSize = 18.sp,
            buttonModifier = Modifier
                .widthIn(min = 280.dp)
                .height(60.dp),
            backgroundColor = Color(0xFFF0F0F0)
        )
    }
}

/**
 * Nuevo Composable para la pantalla de ingreso del código postal.
 * @param onBackToMenu Callback que se invoca para regresar a la pantalla de inicio.
 */
@Composable
fun PantallaCodigoPostal(onBackToMenu: () -> Unit) {
    var codigoPostalText by remember { mutableStateOf("") }
    var mostrarError by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Fenómenos naturales y antrópicos",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Transparent, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🏠",
                    fontSize = 48.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = codigoPostalText,
                onValueChange = { 
                    codigoPostalText = it
                    mostrarError = false
                },
                label = { Text("Ingresa tu código postal") },
                modifier = Modifier
                    .widthIn(min = 280.dp)
                    .height(60.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedContainerColor = Color(0xFFF0F0F0),
                    disabledContainerColor = Color(0xFFF0F0F0),
                    focusedBorderColor = if (mostrarError) Color.Red else Color.Transparent,
                    unfocusedBorderColor = if (mostrarError) Color.Red else Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(8.dp)
            )

            if (mostrarError) {
                Text(
                    text = mensajeError,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            BotonMenu(
                texto = "Enviar",
                onClick = {
                    when {
                        codigoPostalText.isEmpty() -> {
                            mostrarError = true
                            mensajeError = "Por favor ingresa un código postal"
                        }
                        !codigoPostalText.matches(Regex("^[0-9]{5}$")) -> {
                            mostrarError = true
                            mensajeError = "El código postal debe tener 5 dígitos"
                        }
                        else -> {
                            // TODO: Implementar la lógica para guardar en la base de datos
                            // Por ahora solo mostramos un mensaje de éxito
                            mostrarError = false
                            mensajeError = "Código postal guardado correctamente"
                        }
                    }
                },
                fontSize = 18.sp,
                buttonModifier = Modifier
                    .widthIn(min = 280.dp)
                    .height(60.dp),
                backgroundColor = Color(0xFFF0F0F0)
            )
        }

        Button(
            onClick = onBackToMenu,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF0F0F0),
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text("Regresar al menú")
        }
    }
}

@Composable
fun PantallaCreditos(onBackToMenu: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder para el logo de la izquierda
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Logo IPN", fontSize = 12.sp, color = Color.DarkGray, textAlign = TextAlign.Center)
            }
            // Placeholder para el logo de la derecha
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Logo ESIA", fontSize = 12.sp, color = Color.DarkGray, textAlign = TextAlign.Center)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sección de Estudios de Posgrado e\nInvestigación",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Proyecto SIP 20230221",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Directora de proyecto:\nBertha Nelly Cabrera Sánchez",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Participantes:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Elis Antonio Aguilar Rios | BEIFI\n" +
                    "Martín Cruz Muñoz | BEIFI\n" +
                    "Ernesto García Mendoza | Servicio Social\n" +
                    "Guillermo Ángeles Herrera | Servicio Social\n" +
                    "Kevin Heras Romero | Servicio Social\n" +
                    "José Carlos Saldívar Valdéz | Servicio Social\n" +
                    "Diana Vázquez López | BEIFI\n" +
                    "Roberto Del Olmo Mendoza | Prácticas Profesionales",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onBackToMenu,
            modifier = Modifier
                .widthIn(min = 200.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF0F0F0),
                contentColor = Color.Black
            )
        ) {
            Text("Regresar al menú")
        }
    }
}

@Composable
fun PantallaInicioFenomenos(
    onBackToMenu: () -> Unit,
    onNavigateToGeologicos: () -> Unit = {},
    onNavigateToHidrometeorologicos: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Fenómenos",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Placeholder para el video
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(140.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("[Aquí irá el video]", color = Color.DarkGray, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
            // Pregunta
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp))
                    .padding(vertical = 12.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "¿Qué tipo de fenómeno identificas en tu entorno?",
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            // --- Nueva distribución en una sola columna ---
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título Natural
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "De origen Natural",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                BotonMenuConLogoGrande(
                    texto = "Geológico",
                    onClick = onNavigateToGeologicos,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                BotonMenuConLogoGrande(
                    texto = "Hidrometeorológico",
                    onClick = onNavigateToHidrometeorologicos,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                // Título Antrópico
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "De origen Antrópico",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                BotonMenuConLogoGrande(
                    texto = "Químico-Tecnológicos",
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                BotonMenuConLogoGrande(
                    texto = "Sanitario-Ecológicos",
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                BotonMenuConLogoGrande(
                    texto = "Socio-Organizativos",
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        // Botón para regresar al menú principal
        Button(
            onClick = onBackToMenu,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF0F0F0),
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text("Regresar al menú")
        }
    }
}

@Composable
fun PantallaGeologicos(
    onBackToFenomenos: () -> Unit,
    onActividad: () -> Unit,
    onBackToMenu: () -> Unit,
    onNavigateToErupcion: () -> Unit,
    onNavigateToSismo: (() -> Unit)? = null,
    onNavigateToTsunami: (() -> Unit)? = null,
    onNavigateToGrietas: (() -> Unit)? = null,
    onNavigateToDeslizamiento: (() -> Unit)? = null,
    onNavigateToHundimientos: (() -> Unit)? = null
) {
    // Estado para los checkboxes
    val opciones = listOf(
        "Erupción volcánica",
        "Sismo",
        "Tsunami",
        "Grietas",
        "Deslizamiento de laderas",
        "Hundimientos y socavones"
    )
    val checkedList = remember { mutableStateListOf(false, false, false, false, false, false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Riesgos Geológicos",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Los riesgos geológicos son los que causan mayores afectaciones naturales y se clasifican en internos, externos y por intervención humana.",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder para el video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("[Aquí irá el video]", color = Color.DarkGray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Botones con logo, texto y checkbox
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            opciones.forEachIndexed { idx, texto ->
                OpcionGeologica(
                    texto = texto,
                    checked = checkedList[idx],
                    onCheckedChange = { checkedList[idx] = it },
                    onClick = when (texto) {
                        "Erupción volcánica" -> onNavigateToErupcion
                        "Sismo" -> onNavigateToSismo
                        "Tsunami" -> onNavigateToTsunami
                        "Grietas" -> onNavigateToGrietas
                        "Deslizamiento de laderas" -> onNavigateToDeslizamiento
                        "Hundimientos y socavones" -> onNavigateToHundimientos
                        else -> null
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onBackToFenomenos,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 120.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Regresar",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onActividad,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .widthIn(min = 120.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Actividad",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onBackToMenu,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 120.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Menú principal",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OpcionGeologica(
    texto: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder para el logo
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (texto == "Erupción volcánica") {
                    Image(
                        painter = painterResource(id = R.drawable.erupcion),
                        contentDescription = "Erupción volcánica",
                        modifier = Modifier.size(36.dp)
                    )
                } else if (texto == "Sismo") {
                    Image(
                        painter = painterResource(id = R.drawable.sismo),
                        contentDescription = "Sismo",
                        modifier = Modifier.size(36.dp)
                    )
                } else if (texto == "Tsunami") {
                    Image(
                        painter = painterResource(id = R.drawable.tsunami),
                        contentDescription = "Tsunami",
                        modifier = Modifier.size(36.dp)
                    )
                } else if (texto == "Grietas") {
                    Image(
                        painter = painterResource(id = R.drawable.grietas),
                        contentDescription = "Grietas",
                        modifier = Modifier.size(36.dp)
                    )
                } else if (texto == "Deslizamiento de laderas") {
                    Image(
                        painter = painterResource(id = R.drawable.movimiento_ladera),
                        contentDescription = "Deslizamiento de laderas",
                        modifier = Modifier.size(36.dp)
                    )
                } else if (texto == "Hundimientos y socavones") {
                    Image(
                        painter = painterResource(id = R.drawable.hundimiento),
                        contentDescription = "Hundimientos y socavones",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.widthIn(min = 12.dp))
            Text(
                text = texto,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun BotonMenu(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 18.sp,
    buttonModifier: Modifier = Modifier.widthIn(min = 200.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .then(buttonModifier), // Aplica el modificador personalizado
        shape = RoundedCornerShape(8.dp), // Esquinas redondeadas
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, // Color de fondo del botón
            contentColor = Color.Black // Color del texto del botón
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp) // Sombra
    ) {
        Text(text = texto, fontSize = fontSize, fontWeight = FontWeight.Normal)
    }
}

@Composable
fun BotonMenuConLogoGrande(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .widthIn(min = 220.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Logo cuadrado grande
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Aquí irá el logo
            }
            Spacer(modifier = Modifier.widthIn(min = 16.dp))
            Text(text = texto, fontSize = 18.sp, maxLines = 2)
        }
    }
}

@Composable
fun PantallaErupcionVolcanica(
    onBack: () -> Unit,
    onMenu: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fenómenos Geológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Erupción volcánica",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Las erupciones volcánicas expulsan roca fundida, gases y fragmentos de roca. Se dividen en efusivas y explosivas.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder para el video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("[Aquí irá el video]", color = Color.DarkGray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Regresar",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onMenu,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Menú",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onNext,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Siguiente",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PantallaSismo(
    onBack: () -> Unit,
    onMenu: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Riesgos Geológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Sismo",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Un sismo es un movimiento brusco de las rocas subterráneas que genera ondas sísmicas.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder para el video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("[Aquí irá el video]", color = Color.DarkGray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Regresar",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onMenu,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Menú",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onNext,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Siguiente",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PantallaTsunami(
    onBack: () -> Unit,
    onMenu: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Riesgos Geológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Tsunami",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Los tsunamis son olas gigantes generadas por sismos submarinos, causando pérdidas humanas e infraestructurales. Se clasifican en locales, regionales y lejanos según la distancia.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder para el video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("[Aquí irá el video]", color = Color.DarkGray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Regresar",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onMenu,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Menú",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onNext,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Siguiente",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PantallaGrietas(
    onBack: () -> Unit,
    onMenu: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Riesgos Geológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Grietas",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Las grietas son fenómenos que se manifiestan en una serie de desplazamientos verticales y horizontales y se observan en los elementos estructurales.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder para el video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("[Aquí irá el video]", color = Color.DarkGray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Regresar",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onMenu,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Menú",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onNext,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Siguiente",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PantallaDeslizamientoLaderas(
    onBack: () -> Unit,
    onMenu: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Riesgos Geológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Deslizamiento de laderas",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Los deslizamientos de laderas son movimientos de masa de tierra, rocas o escombros ladera abajo, causados por la gravedad y factores como lluvias intensas o sismos.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder para el video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("[Aquí irá el video]", color = Color.DarkGray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Regresar",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onMenu,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Menú",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onNext,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Siguiente",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PantallaHundimientosSocavones(
    onBack: () -> Unit,
    onMenu: () -> Unit,
    onRiesgos: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Riesgos Geológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Hundimientos y socavones",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Los hundimientos y socavones son el resultado de la combinación de agua y ciertos materiales del suelo.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder para el video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("[Aquí irá el video]", color = Color.DarkGray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Regresar",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onMenu,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Menú",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onRiesgos,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Riesgos",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PantallaRiesgosHidrometeorologicos(
    onBack: () -> Unit,
    onActividad: () -> Unit,
    onMenu: () -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    val opciones = listOf(
        "Ciclones tropicales",
        "Inundaciones",
        "Heladas",
        "Niebla",
        "Tormentas eléctricas",
        "Granizo",
        "Frente frío",
        "Sequías"
    )
    val checkedList = remember { mutableStateListOf(false, false, false, false, false, false, false, false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fenómenos Hidrometeorológicos",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Los riesgos hidrometeorológicos son agentes perturbadores originados por fenómenos atmosféricos naturales que pueden generar afectaciones en diversas regiones del territorio.",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder para el video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("[Aquí irá el video]", color = Color.DarkGray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Reemplazo: LazyColumn desplazable para las opciones
        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(opciones) { idx, texto ->
                OpcionHidrometeorologica(
                    texto = texto,
                    checked = checkedList[idx],
                    onCheckedChange = { checkedList[idx] = it },
                    onClick = {
                        onNavigate(
                            when (idx) {
                                0 -> AppScreen.CICLONES_TROPICALES
                                1 -> AppScreen.INUNDACIONES
                                2 -> AppScreen.HELADAS
                                3 -> AppScreen.NIEBLA
                                4 -> AppScreen.TORMENTAS_ELECTRICAS
                                5 -> AppScreen.GRANIZO
                                6 -> AppScreen.FRENTE_FRIO
                                7 -> AppScreen.SEQUIAS
                                else -> AppScreen.HIDROMETEOROLOGICOS
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Spacer(modifier = Modifier.weight(1f)) // Esto mantiene los botones abajo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 120.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Regresar",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onActividad,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .widthIn(min = 120.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Actividad",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onMenu,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 120.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Menú principal",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OpcionHidrometeorologica(
    texto: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder para el logo
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                when (texto) {
                    "Ciclones tropicales" -> Image(
                        painter = painterResource(id = R.drawable.ciclones),
                        contentDescription = "Ciclones tropicales",
                        modifier = Modifier.size(36.dp)
                    )
                    "Inundaciones" -> Image(
                        painter = painterResource(id = R.drawable.inundaciones),
                        contentDescription = "Inundaciones",
                        modifier = Modifier.size(36.dp)
                    )
                    "Heladas" -> Image(
                        painter = painterResource(id = R.drawable.helada),
                        contentDescription = "Heladas",
                        modifier = Modifier.size(36.dp)
                    )
                    "Niebla" -> Image(
                        painter = painterResource(id = R.drawable.niebla),
                        contentDescription = "Niebla",
                        modifier = Modifier.size(36.dp)
                    )
                    "Tormentas eléctricas" -> Image(
                        painter = painterResource(id = R.drawable.tormenta),
                        contentDescription = "Tormentas eléctricas",
                        modifier = Modifier.size(36.dp)
                    )
                    "Granizo" -> Image(
                        painter = painterResource(id = R.drawable.granizo),
                        contentDescription = "Granizo",
                        modifier = Modifier.size(36.dp)
                    )
                    "Frente frío" -> Image(
                        painter = painterResource(id = R.drawable.frente),
                        contentDescription = "Frente frío",
                        modifier = Modifier.size(36.dp)
                    )
                    "Sequías" -> Image(
                        painter = painterResource(id = R.drawable.sequias),
                        contentDescription = "Sequías",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.widthIn(min = 12.dp))
            Text(
                text = texto,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun PantallaCiclonesTropicales(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoHidromet(
        titulo = "Ciclones tropicales / Huracanes",
        subtitulo = "Riesgos Hidrometeorológicos",
        descripcion = "Los ciclones tropicales son fenómenos naturales poderosos que se forman sobre aguas cálidas. Se clasifican como depresiones tropicales, tormentas tropicales, huracanes y huracanes extremos.",
        videoPlaceholder = "[Aquí irá el video de ciclones]",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext
    )
}

@Composable
fun PantallaInundaciones(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoHidromet(
        titulo = "Inundaciones",
        subtitulo = "Riesgos Hidrometeorológicos",
        descripcion = "Una inundación es un evento que aumenta el nivel del agua en ríos o mares, afectando áreas habitualmente secas y causando daños en población, agricultura, ganadería e infraestructura.",
        videoPlaceholder = "[Aquí irá el video de inundaciones]",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext
    )
}

@Composable
fun PantallaHeladas(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoHidromet(
        titulo = "Heladas",
        subtitulo = "Riesgos Hidrometeorológicos",
        descripcion = "Las heladas ocurren cuando la temperatura del aire baja a 0°C o menos durante más de cuatro horas.",
        videoPlaceholder = "[Aquí irá el video de heladas]",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext
    )
}

@Composable
fun PantallaNiebla(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoHidromet(
        titulo = "Niebla",
        subtitulo = "Riesgos Hidrometeorológicos",
        descripcion = "La niebla consiste en gotas de agua suspendidas en el aire, reduciendo la visibilidad a menos de mil metros.",
        videoPlaceholder = "[Aquí irá el video de niebla]",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext
    )
}

@Composable
fun PantallaTormentasElectricas(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoHidromet(
        titulo = "Tormentas eléctricas",
        subtitulo = "Riesgos Hidrometeorológicos",
        descripcion = "Las tormentas eléctricas son descargas eléctricas bruscas de electricidad atmosférica que se manifiestan por un resplandor breve.",
        videoPlaceholder = "[Aquí irá el video de tormentas eléctricas]",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext
    )
}

@Composable
fun PantallaGranizo(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoHidromet(
        titulo = "Granizo",
        subtitulo = "Riesgos Hidrometeorológicos",
        descripcion = "El granizo es un tipo de precipitación en forma de piedras de hielo y se forma en las tormentas severas y pueden ser destructivas.",
        videoPlaceholder = "[Aquí irá el video de granizo]",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext
    )
}

@Composable
fun PantallaFrenteFrio(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoHidromet(
        titulo = "Frente frío",
        subtitulo = "Riesgos Hidrometeorológicos",
        descripcion = "Los frentes fríos son el choque de dos masas de aire, una fría y una cálida, impulsados por una masa de aire frío a una alta velocidad.",
        videoPlaceholder = "[Aquí irá el video de frente frío]",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext
    )
}

@Composable
fun PantallaSequias(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoHidromet(
        titulo = "Sequías",
        subtitulo = "Riesgos Hidrometeorológicos",
        descripcion = "La sequía es un fenómeno que ocurre cuando la precipitación en un lapso es menor que el promedio y puede afectar las actividades humanas.",
        videoPlaceholder = "[Aquí irá el video de sequías]",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext
    )
}

@Composable
fun PantallaInfoHidromet(
    titulo: String,
    subtitulo: String,
    descripcion: String,
    videoPlaceholder: String,
    onBack: () -> Unit,
    onMenu: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = subtitulo,
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = titulo,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = descripcion,
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder para el video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(videoPlaceholder, color = Color.DarkGray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Regresar",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onMenu,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Menú",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onNext,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Siguiente",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// Preview para la pantalla de inicio
@Preview(showBackground = true)
@Composable
fun PantallaInicioPreview() {
    PREPARATTheme {
        // Se pasa un lambda vacío para la navegación en el preview
        PantallaInicio(onNavigateToPostalCode = {}, onNavigateToCredits = {}, onNavigateToFenomenos = {})
    }
}

// Preview para la nueva pantalla de código postal
@Preview(showBackground = true)
@Composable
fun PantallaCodigoPostalPreview() {
    PREPARATTheme {
        // Se pasa un lambda vacío para el botón de regreso en el preview
        PantallaCodigoPostal(onBackToMenu = {})
    }
}