package com.example.prepara_t

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.LaunchedEffect
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
import android.widget.VideoView
import android.widget.MediaController
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectDragGestures
import kotlin.math.abs
import kotlin.math.sign
import android.widget.Toast
import kotlinx.coroutines.delay

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
    SEQUIAS,
    SOCIO_ORGANIZATIVOS, // Nueva pantalla para Socio-Organizativos
    QUIMICO_TECNOLOGICOS, // Nueva pantalla para Químico-Tecnológicos
    ALMACENAMIENTO_COMBUSTIBLES, // Nueva pantalla para Almacenamiento de combustibles
    FUGAS_GAS, // Nueva pantalla para Fugas de gas
    RESIDUOS_PELIGROSOS, // Nueva pantalla para Residuos peligrosos
    EXPLOSIONES_QT, // Nueva pantalla para Explosiones
    INCENDIOS_FORESTALES_QT, // Nueva pantalla para Incendios forestales
    INCENDIOS_URBANOS_QT, // Nueva pantalla para Incendios urbanos
    SANITARIO_ECOLOGICOS, // Nueva pantalla para Riesgos sanitario-ecológicos
    CONTAMINACION_AIRE, // Nueva pantalla para Contaminación del aire
    CONTAMINACION_AGUA, // Nueva pantalla para Contaminación del agua
    CONTAMINACION_SUELO, // Nueva pantalla para Contaminación del suelo
    PLAGAS, // Nueva pantalla para Plagas
    EPIDEMIAS,
    SOCIO_ORGANIZATIVOS_LISTA, // Nueva pantalla para lista de temas Socio-Organizativos
    ACCIDENTES_CARRETEROS, // Nueva pantalla para Accidentes carreteros, ferroviarios y aéreos
    CONCENTRACION_PERSONAS, // Nueva pantalla para Concentración masiva de personas
    TERRORISMO_SABOTAJE, // Nueva pantalla para Terrorismo y sabotaje
    SOPA_LETRAS_GEOLOGICOS, // Pantalla de sopa de letras para geológicos
    SOPA_LETRAS_HIDROMETEOROLOGICOS, // Pantalla de sopa de letras para hidrometeorológicos
    SOPA_LETRAS_SOCIO_ORGANIZATIVOS, // Pantalla de sopa de letras para socio-organizativos
    SOPA_LETRAS_SANITARIO_ECOLOGICO, // Pantalla de sopa de letras para sanitario-ecológico
    SOPA_LETRAS_QUIMICO_TECNOLOGICO // Pantalla de sopa de letras para químico-tecnológicos
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
                    // Pila de pantallas para navegación hacia atrás
                    val screenStack = remember { mutableStateListOf<AppScreen>() }
                    // Estado para doble toque en HOME
                    var backPressedTime by remember { mutableStateOf(0L) }
                    val context = LocalContext.current

                    // BackHandler global
                    BackHandler {
                        if (currentScreen == AppScreen.HOME) {
                            val now = System.currentTimeMillis()
                            if (now - backPressedTime < 2000) {
                                // Salir de la app
                                finish()
                            } else {
                                Toast.makeText(context, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show()
                                backPressedTime = now
                            }
                        } else {
                            // Navegar hacia atrás en la pila
                            if (screenStack.isNotEmpty()) {
                                currentScreen = screenStack.removeAt(screenStack.size - 1)
                            } else {
                                currentScreen = AppScreen.HOME
                            }
                        }
                    }

                    // Función para navegar y guardar historial
                    fun navigateTo(screen: AppScreen) {
                        if (screen != currentScreen) {
                            screenStack.add(currentScreen)
                            currentScreen = screen
                        }
                    }

                    // Lógica para mostrar la pantalla correcta según el estado
                    when (currentScreen) {
                        AppScreen.HOME -> PantallaInicio(
                            onNavigateToPostalCode = { navigateTo(AppScreen.POSTAL_CODE_INPUT) },
                            onNavigateToCredits = { navigateTo(AppScreen.CREDITS) },
                            onNavigateToFenomenos = { navigateTo(AppScreen.FENOMENOS) }
                        )
                        AppScreen.POSTAL_CODE_INPUT -> PantallaCodigoPostal(
                            onBackToMenu = { navigateTo(AppScreen.HOME) }
                        )
                        AppScreen.CREDITS -> PantallaCreditos(
                            onBackToMenu = { navigateTo(AppScreen.HOME) }
                        )
                        AppScreen.FENOMENOS -> PantallaInicioFenomenos(
                            onBackToMenu = { navigateTo(AppScreen.HOME) },
                            onNavigateToGeologicos = { navigateTo(AppScreen.GEOLOGICOS) },
                            onNavigateToHidrometeorologicos = { navigateTo(AppScreen.HIDROMETEOROLOGICOS) },
                            onNavigateToQuimicoTecnologicos = { navigateTo(AppScreen.QUIMICO_TECNOLOGICOS) },
                            onNavigateToSanitarioEcologicos = { navigateTo(AppScreen.SANITARIO_ECOLOGICOS) },
                            onNavigateToSocioOrganizativos = { navigateTo(AppScreen.SOCIO_ORGANIZATIVOS_LISTA) }
                        )
                        AppScreen.GEOLOGICOS -> PantallaGeologicos(
                            onBackToFenomenos = { navigateTo(AppScreen.FENOMENOS) },
                            onActividad = { navigateTo(AppScreen.SOPA_LETRAS_GEOLOGICOS) },
                            onBackToMenu = { navigateTo(AppScreen.HOME) },
                            onNavigateToErupcion = { navigateTo(AppScreen.ERUPCION_VOLCANICA) },
                            onNavigateToSismo = { navigateTo(AppScreen.SISMO) },
                            onNavigateToTsunami = { navigateTo(AppScreen.TSUNAMI) },
                            onNavigateToGrietas = { navigateTo(AppScreen.GRIETAS) },
                            onNavigateToDeslizamiento = { navigateTo(AppScreen.DESLIZAMIENTO_LADERAS) },
                            onNavigateToHundimientos = { navigateTo(AppScreen.HUNDIMIENTOS_SOCAVONES) }
                        )
                        AppScreen.ERUPCION_VOLCANICA -> PantallaErupcionVolcanica(
                            onBack = { navigateTo(AppScreen.GEOLOGICOS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.SISMO) }
                        )
                        AppScreen.SISMO -> PantallaSismo(
                            onBack = { navigateTo(AppScreen.ERUPCION_VOLCANICA) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.TSUNAMI) }
                        )
                        AppScreen.TSUNAMI -> PantallaTsunami(
                            onBack = { navigateTo(AppScreen.SISMO) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.GRIETAS) }
                        )
                        AppScreen.GRIETAS -> PantallaGrietas(
                            onBack = { navigateTo(AppScreen.TSUNAMI) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.DESLIZAMIENTO_LADERAS) }
                        )
                        AppScreen.DESLIZAMIENTO_LADERAS -> PantallaDeslizamientoLaderas(
                            onBack = { navigateTo(AppScreen.GRIETAS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.HUNDIMIENTOS_SOCAVONES) }
                        )
                        AppScreen.HUNDIMIENTOS_SOCAVONES -> PantallaHundimientosSocavones(
                            onBack = { navigateTo(AppScreen.DESLIZAMIENTO_LADERAS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.GEOLOGICOS) }
                        )
                        AppScreen.HIDROMETEOROLOGICOS -> PantallaRiesgosHidrometeorologicos(
                            onBack = { navigateTo(AppScreen.FENOMENOS) },
                            onActividad = { navigateTo(AppScreen.SOPA_LETRAS_HIDROMETEOROLOGICOS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNavigate = { pantalla -> navigateTo(pantalla) }
                        )
                        AppScreen.CICLONES_TROPICALES -> PantallaCiclonesTropicales(
                            onBack = { navigateTo(AppScreen.HIDROMETEOROLOGICOS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.INUNDACIONES) }
                        )
                        AppScreen.INUNDACIONES -> PantallaInundaciones(
                            onBack = { navigateTo(AppScreen.CICLONES_TROPICALES) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.HELADAS) }
                        )
                        AppScreen.HELADAS -> PantallaHeladas(
                            onBack = { navigateTo(AppScreen.INUNDACIONES) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.NIEBLA) }
                        )
                        AppScreen.NIEBLA -> PantallaNiebla(
                            onBack = { navigateTo(AppScreen.HELADAS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.TORMENTAS_ELECTRICAS) }
                        )
                        AppScreen.TORMENTAS_ELECTRICAS -> PantallaTormentasElectricas(
                            onBack = { navigateTo(AppScreen.NIEBLA) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.GRANIZO) }
                        )
                        AppScreen.GRANIZO -> PantallaGranizo(
                            onBack = { navigateTo(AppScreen.TORMENTAS_ELECTRICAS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.FRENTE_FRIO) }
                        )
                        AppScreen.FRENTE_FRIO -> PantallaFrenteFrio(
                            onBack = { navigateTo(AppScreen.GRANIZO) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.SEQUIAS) }
                        )
                        AppScreen.SEQUIAS -> PantallaSequias(
                            onBack = { navigateTo(AppScreen.FRENTE_FRIO) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.HIDROMETEOROLOGICOS) }
                        )
                        AppScreen.SOCIO_ORGANIZATIVOS -> PantallaSocioOrganizativos(
                            onBack = { navigateTo(AppScreen.FENOMENOS) },
                            onMenu = { navigateTo(AppScreen.HOME) }
                        )
                        AppScreen.QUIMICO_TECNOLOGICOS -> PantallaRiesgosQuimicoTecnologicos(
                            onBack = { navigateTo(AppScreen.FENOMENOS) },
                            onActividad = { navigateTo(AppScreen.SOPA_LETRAS_QUIMICO_TECNOLOGICO) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNavigate = { navigateTo(it) }
                        )
                        AppScreen.ALMACENAMIENTO_COMBUSTIBLES -> PantallaAlmacenamientoCombustibles(
                            onBack = { navigateTo(AppScreen.QUIMICO_TECNOLOGICOS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.FUGAS_GAS) }
                        )
                        AppScreen.FUGAS_GAS -> PantallaFugasGas(
                            onBack = { navigateTo(AppScreen.ALMACENAMIENTO_COMBUSTIBLES) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.RESIDUOS_PELIGROSOS) }
                        )
                        AppScreen.RESIDUOS_PELIGROSOS -> PantallaResiduosPeligrosos(
                            onBack = { navigateTo(AppScreen.FUGAS_GAS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.EXPLOSIONES_QT) }
                        )
                        AppScreen.EXPLOSIONES_QT -> PantallaExplosionesQT(
                            onBack = { navigateTo(AppScreen.RESIDUOS_PELIGROSOS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.INCENDIOS_FORESTALES_QT) }
                        )
                        AppScreen.INCENDIOS_FORESTALES_QT -> PantallaIncendiosForestalesQT(
                            onBack = { navigateTo(AppScreen.EXPLOSIONES_QT) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.INCENDIOS_URBANOS_QT) }
                        )
                        AppScreen.INCENDIOS_URBANOS_QT -> PantallaIncendiosUrbanosQT(
                            onBack = { navigateTo(AppScreen.INCENDIOS_FORESTALES_QT) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.QUIMICO_TECNOLOGICOS) }
                        )
                        AppScreen.SANITARIO_ECOLOGICOS -> PantallaRiesgosSanitarioEcologicos(
                            onBack = { navigateTo(AppScreen.FENOMENOS) },
                            onActividad = { navigateTo(AppScreen.SOPA_LETRAS_SANITARIO_ECOLOGICO) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNavigate = {
                                when (it) {
                                    "Contaminación del aire" -> navigateTo(AppScreen.CONTAMINACION_AIRE)
                                    "Contaminación del agua" -> navigateTo(AppScreen.CONTAMINACION_AGUA)
                                    "Contaminación del suelo" -> navigateTo(AppScreen.CONTAMINACION_SUELO)
                                    "Plagas" -> navigateTo(AppScreen.PLAGAS)
                                    "Epidemias" -> navigateTo(AppScreen.EPIDEMIAS)
                                }
                            }
                        )
                        AppScreen.CONTAMINACION_AIRE -> PantallaContaminacionAire(
                            onBack = { navigateTo(AppScreen.SANITARIO_ECOLOGICOS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.CONTAMINACION_AGUA) }
                        )
                        AppScreen.CONTAMINACION_AGUA -> PantallaContaminacionAgua(
                            onBack = { navigateTo(AppScreen.CONTAMINACION_AIRE) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.CONTAMINACION_SUELO) }
                        )
                        AppScreen.CONTAMINACION_SUELO -> PantallaContaminacionSuelo(
                            onBack = { navigateTo(AppScreen.CONTAMINACION_AGUA) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.PLAGAS) }
                        )
                        AppScreen.PLAGAS -> PantallaPlagas(
                            onBack = { navigateTo(AppScreen.CONTAMINACION_SUELO) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.EPIDEMIAS) }
                        )
                        AppScreen.EPIDEMIAS -> PantallaEpidemias(
                            onBack = { navigateTo(AppScreen.PLAGAS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.SANITARIO_ECOLOGICOS) }
                        )
                        AppScreen.SOCIO_ORGANIZATIVOS_LISTA -> PantallaRiesgosSocioOrganizativos(
                            onBack = { navigateTo(AppScreen.FENOMENOS) },
                            onActividad = { navigateTo(AppScreen.SOPA_LETRAS_SOCIO_ORGANIZATIVOS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNavigate = {
                                when (it) {
                                    "Accidentes carreteros, ferroviarios y aéreos" -> navigateTo(AppScreen.ACCIDENTES_CARRETEROS)
                                    "Concentración masiva de personas" -> navigateTo(AppScreen.CONCENTRACION_PERSONAS)
                                    "Terrorismo y sabotaje" -> navigateTo(AppScreen.TERRORISMO_SABOTAJE)
                                }
                            }
                        )
                        AppScreen.ACCIDENTES_CARRETEROS -> PantallaAccidentesCarreteros(
                            onBack = { navigateTo(AppScreen.SOCIO_ORGANIZATIVOS_LISTA) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.CONCENTRACION_PERSONAS) }
                        )
                        AppScreen.CONCENTRACION_PERSONAS -> PantallaConcentracionPersonas(
                            onBack = { navigateTo(AppScreen.ACCIDENTES_CARRETEROS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.TERRORISMO_SABOTAJE) }
                        )
                        AppScreen.TERRORISMO_SABOTAJE -> PantallaTerrorismoSabotaje(
                            onBack = { navigateTo(AppScreen.CONCENTRACION_PERSONAS) },
                            onMenu = { navigateTo(AppScreen.HOME) },
                            onNext = { navigateTo(AppScreen.SOCIO_ORGANIZATIVOS_LISTA) }
                        )
                        AppScreen.SOPA_LETRAS_GEOLOGICOS -> PantallaSopaLetrasGeologicos(
                            onBack = { navigateTo(AppScreen.GEOLOGICOS) }
                        )
                        AppScreen.SOPA_LETRAS_HIDROMETEOROLOGICOS -> PantallaSopaLetrasHidrometeorologicos(
                            onBack = { navigateTo(AppScreen.HIDROMETEOROLOGICOS) }
                        )
                        AppScreen.SOPA_LETRAS_SOCIO_ORGANIZATIVOS -> PantallaSopaLetrasSocioOrganizativos(
                            onBack = { navigateTo(AppScreen.SOCIO_ORGANIZATIVOS_LISTA) }
                        )
                        AppScreen.SOPA_LETRAS_SANITARIO_ECOLOGICO -> PantallaSopaLetrasSanitarioEcologico(
                            onBack = { navigateTo(AppScreen.SANITARIO_ECOLOGICOS) }
                        )
                        AppScreen.SOPA_LETRAS_QUIMICO_TECNOLOGICO -> PantallaSopaLetrasQuimicoTec(
                            onBack = { navigateTo(AppScreen.QUIMICO_TECNOLOGICOS) }
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
            .verticalScroll(rememberScrollState())
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
                .verticalScroll(rememberScrollState())
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
            .verticalScroll(rememberScrollState())
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
    onNavigateToHidrometeorologicos: () -> Unit = {},
    onNavigateToQuimicoTecnologicos: () -> Unit = {},
    onNavigateToSanitarioEcologicos: () -> Unit = {},
    onNavigateToSocioOrganizativos: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                    iconResId = R.drawable.erupcion, // Ícono de volcán
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                BotonMenuConLogoGrande(
                    texto = "Hidrometeorológico",
                    onClick = onNavigateToHidrometeorologicos,
                    iconResId = R.drawable.ciclones, // Ícono de ciclón
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
                    onClick = onNavigateToQuimicoTecnologicos,
                    iconResId = R.drawable.explosiones, // Ícono de explosión
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                BotonMenuConLogoGrande(
                    texto = "Sanitario-Ecológicos",
                    onClick = onNavigateToSanitarioEcologicos,
                    iconResId = R.drawable.contaminaciondelaire, // Ícono de contaminación del aire
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                BotonMenuConLogoGrande(
                    texto = "Socio-Organizativos",
                    onClick = onNavigateToSocioOrganizativos,
                    iconResId = R.drawable.accidentes, // Ícono de accidentes
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                Spacer(modifier = Modifier.height(64.dp)) // Más espacio para que el botón no quede tapado
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fenómenos Geológicos",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Los fenómenos geológicos son los que causan mayores afectaciones naturales y se clasifican en internos, externos y por intervención humana.",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Video de fenómenos geológicos
        VideoFenomenoGeologico()
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
                    containerColor = Color(0xFF1976D2),
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
                    text = "Menú",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun VideoFenomenoGeologico() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        VideoPlayerExo(
            modifier = Modifier.matchParentSize(),
            videoResId = R.raw.fenomenogeovid
        )
    }
}

@Composable
fun VideoPlayerExo(
    modifier: Modifier = Modifier,
    videoResId: Int
) {
    val context = LocalContext.current
    val exoPlayer = remember(videoResId) {
        ExoPlayer.Builder(context).build().apply {
            val uri = "android.resource://${context.packageName}/raw/" +
                context.resources.getResourceEntryName(videoResId)
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = false
        }
    }
    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = true
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = modifier
    )
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
                    .size(40.dp),
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
            Spacer(modifier = Modifier.size(2.dp))
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
    iconResId: Int, // Nuevo parámetro para el recurso del ícono
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
                    .size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = texto,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(modifier = Modifier.widthIn(min = 8.dp)) // Reducido de 16dp a 8dp
            Text(text = texto, fontSize = 16.sp, maxLines = 1)
        }
    }
}

@Composable
fun PantallaErupcionVolcanica(
    onBack: () -> Unit,
    onMenu: () -> Unit,
    onNext: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Fenómenos Geológicos",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Erupción volcánica",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Las erupciones volcánicas expulsan roca fundida, gases y fragmentos de roca. Se dividen en efusivas y explosivas.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            VideoPlayerExo(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                videoResId = R.raw.fgerupcion
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
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
                    containerColor = Color(0xFF1976D2),
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Fenómenos Geológicos",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sismo",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Un sismo es un movimiento brusco de las rocas subterráneas que genera ondas sísmicas.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            VideoPlayerExo(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                videoResId = R.raw.fgsismo
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
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
                    containerColor = Color(0xFF1976D2),
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Fenómenos Geológicos",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tsunami",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Los tsunamis son olas gigantes generadas por sismos submarinos, causando pérdidas humanas e infraestructurales. Se clasifican en locales, regionales y lejanos según la distancia.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            VideoPlayerExo(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                videoResId = R.raw.fgtsunami
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
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
                    containerColor = Color(0xFF1976D2),
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Fenómenos Geológicos",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Grietas",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Las grietas son fenómenos que se manifiestan en una serie de desplazamientos verticales y horizontales y se observan en los elementos estructurales.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            VideoPlayerExo(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                videoResId = R.raw.fggrietas
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
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
                    containerColor = Color(0xFF1976D2),
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Fenómenos Geológicos",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Deslizamiento de laderas",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Los deslizamientos de laderas son fenómenos que se manifiestan como derrumbes, deslizamientos y flujos.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            VideoPlayerExo(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                videoResId = R.raw.fgdeslizamiento
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
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
                    containerColor = Color(0xFF1976D2),
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
    onNext: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Fenómenos Geológicos",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Hundimientos y socavones",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Los hundimientos y socavones son el resultado de la combinación de agua y ciertos materiales del suelo.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            VideoPlayerExo(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                videoResId = R.raw.fgundimientos
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
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
                    containerColor = Color(0xFF1976D2),
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp)) // Más espacio arriba
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
            text = "Los fenómenos hidrometeorológicos son agentes perturbadores originados por fenómenos atmosféricos naturales que pueden generar afectaciones en diversas regiones del territorio.",
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
        // Ahora usamos un Column normal para las opciones
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            opciones.forEachIndexed { idx, texto ->
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
                    containerColor = Color(0xFF1976D2),
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
                    text = "Menú",
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
                    .size(40.dp),
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
            Spacer(modifier = Modifier.size(2.dp))
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Fenómenos Hidrometeorológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ciclones tropicales / Huracanes",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Los ciclones tropicales son fenómenos naturales poderosos que se forman sobre aguas cálidas. Se clasifican como depresiones tropicales, tormentas tropicales, huracanes y huracanes extremos.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.fhciclon
        )
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaInundaciones(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Fenómenos Hidrometeorológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Inundaciones",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Una inundación es un evento que aumenta el nivel del agua en ríos o mares, afectando áreas habitualmente secas y causando daños en población, agricultura, ganadería e infraestructura.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.fhinundaciones
        )
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaHeladas(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoHidromet(
        titulo = "Heladas",
        subtitulo = "Fenómenos Hidrometeorológicos",
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
        subtitulo = "Fenómenos Hidrometeorológicos",
        descripcion = "La niebla consiste en gotas de agua suspendidas en el aire, reduciendo la visibilidad a menos de mil metros.",
        videoPlaceholder = "[Aquí irá el video de niebla]",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext
    )
}

@Composable
fun PantallaTormentasElectricas(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Fenómenos Hidrometeorológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tormentas eléctricas",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Las tormentas eléctricas son descargas eléctricas bruscas de electricidad atmosférica que se manifiestan por un resplandor breve.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.fhtormenta
        )
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaGranizo(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Fenómenos Hidrometeorológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Granizo",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "El granizo es un tipo de precipitación en forma de piedras de hielo y se forma en las tormentas severas y pueden ser destructivas.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.fhgranizo
        )
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaFrenteFrio(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Fenómenos Hidrometeorológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Frente frío",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Los frentes fríos son el choque de dos masas de aire, una fría y una cálida, impulsados por una masa de aire frío a una alta velocidad.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.fhfrentefrio
        )
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaSequias(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoHidromet(
        titulo = "Sequías",
        subtitulo = "Fenómenos Hidrometeorológicos",
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp)) // Más espacio arriba
        Text(
            text = subtitulo,
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = titulo,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp)) // Más espacio entre título y texto
        Text(
            text = descripcion,
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp)) // Más espacio antes del video
        // Placeholder para el video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaRiesgosQuimicoTecnologicos(
    onBack: () -> Unit,
    onActividad: () -> Unit,
    onMenu: () -> Unit,
    onNavigate: (AppScreen) -> Unit // Nuevo parámetro para navegación
) {
    val opciones = listOf(
        "Almacenamiento y transportación de combustibles",
        "Fugas de gas y derrames de sustancias",
        "Manejo de residuos peligrosos",
        "Explosiones",
        "Incendios forestales",
        "Incendios urbanos"
    )
    val checkedList = remember { mutableStateListOf(false, false, false, false, false, false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp)) // Más espacio arriba
        Text(
            text = "Fenómenos Químico-Tecnológicos",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Los fenómenos químicos – tecnológicos son aquellos provocados por actividades industriales, comerciales y de servicios.",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.fenomenoquimicotec
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Lista de opciones: todas en un solo Column (sin LazyColumn)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            opciones.forEachIndexed { idx, texto ->
                OpcionQuimicoTecnologico(
                    texto = texto,
                    checked = checkedList[idx],
                    onCheckedChange = { checkedList[idx] = it },
                    onClick = {
                        when (idx) {
                            0 -> onNavigate(AppScreen.ALMACENAMIENTO_COMBUSTIBLES)
                            1 -> onNavigate(AppScreen.FUGAS_GAS)
                            2 -> onNavigate(AppScreen.RESIDUOS_PELIGROSOS)
                            3 -> onNavigate(AppScreen.EXPLOSIONES_QT)
                            4 -> onNavigate(AppScreen.INCENDIOS_FORESTALES_QT)
                            5 -> onNavigate(AppScreen.INCENDIOS_URBANOS_QT)
                        }
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
                    containerColor = Color(0xFF1976D2),
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
                    text = "Menú",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OpcionQuimicoTecnologico(
    texto: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
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
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                when (texto) {
                    "Almacenamiento y transportación de combustibles" -> Image(
                        painter = painterResource(id = R.drawable.contaminaciondelsuelo),
                        contentDescription = "Contaminación del suelo",
                        modifier = Modifier.size(36.dp)
                    )
                    "Fugas de gas y derrames de sustancias" -> Image(
                        painter = painterResource(id = R.drawable.fugadegas),
                        contentDescription = "Fuga de gas",
                        modifier = Modifier.size(36.dp)
                    )
                    "Manejo de residuos peligrosos" -> Image(
                        painter = painterResource(id = R.drawable.residuos),
                        contentDescription = "Residuos peligrosos",
                        modifier = Modifier.size(36.dp)
                    )
                    "Explosiones" -> Image(
                        painter = painterResource(id = R.drawable.explosiones),
                        contentDescription = "Explosiones",
                        modifier = Modifier.size(36.dp)
                    )
                    "Incendios forestales" -> Image(
                        painter = painterResource(id = R.drawable.incendiosforestales),
                        contentDescription = "Incendios forestales",
                        modifier = Modifier.size(36.dp)
                    )
                    "Incendios urbanos" -> Image(
                        painter = painterResource(id = R.drawable.incendiosurbanos),
                        contentDescription = "Incendios urbanos",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = texto,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.weight(1f),
                maxLines = 3
            )
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun PantallaSocioOrganizativos(onBack: () -> Unit, onMenu: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Pantalla Socio-Organizativos (en construcción)", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Regresar") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onMenu) { Text("Menú") }
    }
}

@Composable
fun PantallaAlmacenamientoCombustibles(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Fenómenos químico – tecnológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Almacenamiento y transportación de combustibles",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "El almacenamiento y la transportación de combustible consiste en el conjunto de recintos y recipientes usados para contener productos químicos combustibles.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.qtalmacenamiento
        )
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaFugasGas(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Fenómenos químico – tecnológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Fugas de gas y derrames de sustancias",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "La fugas de gas y derrames de sustancias es la emisión de gas o sustancias fuera de un sistema por fracturas, rupturas o diferentes afectaciones al mismo.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.qtfugasdegas
        )
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaResiduosPeligrosos(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Fenómenos químico – tecnológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Manejo de residuos peligrosos",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "El manejo de residuos peligrosos es cuando se manejan sustancias que pueden representar un peligro como el Gas L.P., amoniaco, cloro, etc.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.qtmanejoderesiduos
        )
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaExplosionesQT(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Fenómenos químico – tecnológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Explosiones",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Las explosiones se originan a partir de una reacción química, por ignición o calentamiento de algunos materiales provocando una expansión violenta de los gases.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.qtexplociones
        )
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaIncendiosForestalesQT(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Fenómenos químico – tecnológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Incendios forestales",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Lo incendios forestales ocurren cuando el fuego se extiende de manera descontrolada y afecta a bosques, selvas o vegetación.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.qtincenciosforestales
        )
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaIncendiosUrbanosQT(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Fenómenos químico – tecnológicos",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Incendios urbanos",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Los incendios urbanos son reacciones químicas que se manifiesta en escenarios urbanos  y desprende luz, calor, humo y gases en grandes cantidades.",
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.qtincendiosurbanos
        )
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
                    containerColor = Color(0xFF1976D2),
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
fun PantallaRiesgosSanitarioEcologicos(
    onBack: () -> Unit,
    onActividad: () -> Unit,
    onMenu: () -> Unit,
    onNavigate: (String) -> Unit // Puedes cambiar a un enum si lo deseas
) {
    val opciones = listOf(
        "Contaminación del aire",
        "Contaminación del agua",
        "Contaminación del suelo",
        "Plagas",
        "Epidemias"
    )
    val checkedList = remember { mutableStateListOf(false, false, false, false, false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp)) // Más espacio arriba
        Text(
            text = "Fenómenos Sanitario-Ecológicos",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Los fenómenos sanitario – ecológicos son aquellos donde un agente perturbador afectan a los seres vivos y el medio ambiente.",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.fenomenosnitarioeco
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Lista de temas
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            opciones.forEachIndexed { idx, texto ->
                OpcionSanitarioEcologico(
                    texto = texto,
                    checked = checkedList[idx],
                    onCheckedChange = { checkedList[idx] = it },
                    onClick = { onNavigate(texto) }
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
                    containerColor = Color(0xFF1976D2),
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
                    text = "Menú",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OpcionSanitarioEcologico(
    texto: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
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
            // Logo asociado a cada tema
            Box(
                modifier = Modifier
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                when (texto) {
                    "Contaminación del aire" -> Image(
                        painter = painterResource(id = R.drawable.contaminaciondelaire),
                        contentDescription = "Contaminación del aire",
                        modifier = Modifier.size(36.dp)
                    )
                    "Contaminación del agua" -> Image(
                        painter = painterResource(id = R.drawable.contaminaciondelagua),
                        contentDescription = "Contaminación del agua",
                        modifier = Modifier.size(36.dp)
                    )
                    "Contaminación del suelo" -> Image(
                        painter = painterResource(id = R.drawable.contaminaciondelsuelo),
                        contentDescription = "Contaminación del suelo",
                        modifier = Modifier.size(36.dp)
                    )
                    "Plagas" -> Image(
                        painter = painterResource(id = R.drawable.plagas),
                        contentDescription = "Plagas",
                        modifier = Modifier.size(36.dp)
                    )
                    "Epidemias" -> Image(
                        painter = painterResource(id = R.drawable.epidemias),
                        contentDescription = "Epidemias",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = texto,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.weight(1f),
                maxLines = 3
            )
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun PantallaContaminacionAire(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoSanitarioEco(
        titulo = "Fenómenos sanitario – ecológicos\nContaminación del aire",
        descripcion = "La contaminación del aire es la presencia en la atmósfera de uno o más elementos físicos, químicos o biológicos suspendidos en grandes concentraciones.",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext,
        videoResId = R.raw.fsecontaminaciondeaire
    )
}

@Composable
fun PantallaContaminacionAgua(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoSanitarioEco(
        titulo = "Fenómenos sanitario – ecológicos\nContaminación del agua",
        descripcion = "La contaminación del agua se da cuando se le incorpora materias extrañas al agua, tal como microorganismos, químicos e industriales.",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext,
        videoResId = R.raw.fsecontaminaciondeagua
    )
}

@Composable
fun PantallaContaminacionSuelo(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoSanitarioEco(
        titulo = "Fenómenos sanitario – ecológicos\nContaminación del suelo",
        descripcion = "La contaminación del suelo sucede con la incorporación de material como basura, desechos tóxicos, productos químicos y desechos industriales.",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext,
        videoResId = R.raw.fsecontaminaciondesuelo
    )
}

@Composable
fun PantallaPlagas(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoSanitarioEco(
        titulo = "Fenómenos sanitario – ecológicos\nPlagas",
        descripcion = "Las plagas son fauna y flora dañina que afecta la salud de las personas, infraestructura urbana y el ambiente.",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext,
        videoResId = R.raw.fseplagas
    )
}

@Composable
fun PantallaEpidemias(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoSanitarioEco(
        titulo = "Fenómenos sanitario – ecológicos\nEpidemias",
        descripcion = "Las epidemias son el aumento inusual del número de casos de una enfermedad determinada en una población específica, en un periodo determinado.",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext,
        videoResId = R.raw.fseepidemias
    )
}

@Composable
fun PantallaInfoSanitarioEco(
    titulo: String,
    descripcion: String,
    onBack: () -> Unit,
    onMenu: () -> Unit,
    onNext: () -> Unit,
    videoResId: Int? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = titulo,
            fontSize = 24.sp,
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
        if (videoResId != null) {
            VideoPlayerExo(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                videoResId = videoResId
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("[Aquí irá el video]", color = Color.DarkGray, fontSize = 14.sp)
            }
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
                    containerColor = Color(0xFF1976D2),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = if (titulo.contains("Epidemias")) "Fenómenos" else "Siguiente",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PantallaRiesgosSocioOrganizativos(
    onBack: () -> Unit,
    onActividad: () -> Unit,
    onMenu: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val opciones = listOf(
        "Accidentes carreteros, ferroviarios y aéreos",
        "Concentración masiva de personas",
        "Terrorismo y sabotaje"
    )
    val checkedList = remember { mutableStateListOf(false, false, false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp)) // Más espacio arriba
        Text(
            text = "Fenómenos Socio – Organizativos",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Los fenómenos socio – organizativos se generan con motivo de errores humanos o por acciones premeditadas.",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        VideoPlayerExo(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoResId = R.raw.fenomenosocioorg
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Lista de temas
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            opciones.forEachIndexed { idx, texto ->
                OpcionSocioOrganizativo(
                    texto = texto,
                    checked = checkedList[idx],
                    onCheckedChange = { checkedList[idx] = it },
                    onClick = { onNavigate(texto) }
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
                    containerColor = Color(0xFF1976D2),
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
                    text = "Menú",
                    fontSize = 15.sp,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OpcionSocioOrganizativo(
    texto: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
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
            // Logo asociado a cada tema
            Box(
                modifier = Modifier
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                when (texto) {
                    "Accidentes carreteros, ferroviarios y aéreos" -> Image(
                        painter = painterResource(id = R.drawable.accidentes),
                        contentDescription = "Accidentes carreteros, ferroviarios y aéreos",
                        modifier = Modifier.size(36.dp)
                    )
                    "Concentración masiva de personas" -> Image(
                        painter = painterResource(id = R.drawable.concentracion),
                        contentDescription = "Concentración masiva de personas",
                        modifier = Modifier.size(36.dp)
                    )
                    "Terrorismo y sabotaje" -> Image(
                        painter = painterResource(id = R.drawable.terrorismo),
                        contentDescription = "Terrorismo y sabotaje",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = texto,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.weight(1f),
                maxLines = 3
            )
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun PantallaAccidentesCarreteros(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoSocioOrg(
        titulo = "Fenómenos socio – organizativos\nAccidentes carreteros, ferroviarios y aéreos",
        descripcion = "Los accidentes carreteros, ferroviarios y aéreos se producen por errores humanos, fallas en los equipos, sobrecargas o por distintas fallas.",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext,
        esUltimo = false
    )
}

@Composable
fun PantallaConcentracionPersonas(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoSocioOrg(
        titulo = "Fenómenos socio – organizativos\nConcentración masiva de personas",
        descripcion = "La concentración masiva de personas es la reunión de mucha gente en lugares grandes como estadios, plazas o auditorios.",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext,
        esUltimo = false,
        videoResId = R.raw.fsoconsentracionmasiva
    )
}

@Composable
fun PantallaTerrorismoSabotaje(onBack: () -> Unit, onMenu: () -> Unit, onNext: () -> Unit) {
    PantallaInfoSocioOrg(
        titulo = "Fenómenos socio – organizativos\nTerrorismo y sabotaje",
        descripcion = "El terrorismo, sabotaje o vandalismo es una forma de afectación de carácter público y privado en la que se infunde terror y se pone en riesgo a la población.",
        onBack = onBack,
        onMenu = onMenu,
        onNext = onNext,
        esUltimo = true,
        videoResId = R.raw.fsoterrorismo
    )
}

@Composable
fun PantallaInfoSocioOrg(
    titulo: String,
    descripcion: String,
    onBack: () -> Unit,
    onMenu: () -> Unit,
    onNext: () -> Unit,
    esUltimo: Boolean,
    videoResId: Int? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = titulo,
            fontSize = 24.sp,
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
        if (videoResId != null) {
            VideoPlayerExo(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                videoResId = videoResId
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("[Aquí irá el video]", color = Color.DarkGray, fontSize = 14.sp)
            }
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
                    containerColor = Color(0xFF1976D2),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .widthIn(min = 100.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = if (esUltimo) "Fenómenos" else "Siguiente",
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

// --- Pantalla de Sopa de Letras para Fenómenos Geológicos ---
@Composable
fun PantallaSopaLetrasGeologicos(
    onBack: () -> Unit
) {
    // Matriz de la sopa de letras (10x10, sin GEOLOGICOS)
    val matriz = listOf(
        listOf('U','M','Á','N','K','M','Y','A','O','T'),
        listOf('E','R','U','P','C','I','O','N','P','S'),
        listOf('V','O','L','C','A','N','I','C','A','A'),
        listOf('B','G','Ó','D','B','K','O','Ú','Í','S'),
        listOf('Á','Ü','L','A','D','E','R','A','S','S'),
        listOf('L','S','I','S','M','O','C','I','N','I'),
        listOf('G','R','I','E','T','A','S','M','G','I'),
        listOf('D','X','L','Ñ','Í','C','V','G','J','M'),
        listOf('Ñ','S','O','C','A','V','O','N','E','S'),
        listOf('T','S','U','N','A','M','I','O','S','S')
    )
    val palabras = listOf(
        "ERUPCION", "VOLCANICA", "GRIETAS", "LADERAS", "SISMO", "SOCAVONES", "TSUNAMI"
    )
    SopaLetrasScreen(
        matriz = matriz,
        palabras = palabras,
        onBack = onBack
    )
}

// Composable base para la sopa de letras (lógica e interfaz básica)
@Composable
fun SopaLetrasScreen(
    matriz: List<List<Char>>,
    palabras: List<String>,
    onBack: () -> Unit
) {
    // Aquí irá la lógica de selección, validación y tachado
    // Por ahora, solo muestro la matriz y la lista de palabras
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sopa de letras:",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Fenómenos Geológicos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            textAlign = TextAlign.Center
        )

        // Estados para selección y palabras encontradas
        val seleccion = remember { mutableStateListOf<Pair<Int, Int>>() }
        val encontradas = remember { mutableStateListOf<String>() }
        val posicionesEncontradas = remember { mutableStateListOf<List<Pair<Int, Int>>>() }
        var inicio by remember { mutableStateOf<Pair<Int, Int>?>(null) }
        var fin by remember { mutableStateOf<Pair<Int, Int>?>(null) }
        var seleccionando by remember { mutableStateOf(false) }

        // Función para obtener la palabra seleccionada y sus posiciones
        fun palabraSeleccionadaYPosiciones(): Pair<String, List<Pair<Int, Int>>> {
            if (inicio == null || fin == null) return "" to emptyList()
            val (i0, j0) = inicio!!
            val (i1, j1) = fin!!
            val di = (i1 - i0).coerceIn(-1, 1)
            val dj = (j1 - j0).coerceIn(-1, 1)
            val len = maxOf(kotlin.math.abs(i1 - i0), kotlin.math.abs(j1 - j0)) + 1
            val sb = StringBuilder()
            val posiciones = mutableListOf<Pair<Int, Int>>()
            for (k in 0 until len) {
                val ni = i0 + k * di
                val nj = j0 + k * dj
                if (ni in matriz.indices && nj in matriz[0].indices) {
                    sb.append(matriz[ni][nj])
                    posiciones.add(ni to nj)
                }
            }
            return sb.toString().uppercase() to posiciones
        }

        // Matriz de letras con interacción drag
        for ((i, fila) in matriz.withIndex()) {
            Row {
                for ((j, letra) in fila.withIndex()) {
                    val pos = i to j
                    val seleccionado = seleccion.contains(pos)
                    val subrayado = posicionesEncontradas.any { lista -> pos in lista }
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                when {
                                    seleccionado -> Color(0xFF90CAF9) // Azul claro si está seleccionando
                                    subrayado -> Color(0xFF1976D2) // Azul fuerte si la palabra fue encontrada
                                    else -> Color(0xFFE0E0E0)
                                },
                                RoundedCornerShape(4.dp)
                            )
                            .padding(2.dp)
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = {
                                        inicio = pos
                                        fin = pos
                                        seleccion.clear()
                                        seleccion.add(pos)
                                        seleccionando = true
                                    },
                                    onDrag = { change, _ ->
                                        if (inicio != null) {
                                            val localPos = change.position
                                            val cellSize = 62f // Ajustado para sensibilidad óptima
                                            val j1 = (localPos.x / cellSize).toInt().coerceIn(0, matriz[0].size - 1)
                                            val i1 = (localPos.y / cellSize).toInt().coerceIn(0, matriz.size - 1)
                                            val (i0, j0) = inicio!!
                                            // Priorizar horizontal, luego vertical
                                            val dx = j1 - j0
                                            val dy = i1 - i0
                                            val absDx = abs(dx)
                                            val absDy = abs(dy)
                                            val di: Int
                                            val dj: Int
                                            val len: Int
                                            if (absDx > 0) {
                                                di = 0
                                                dj = if (dx > 0) 1 else -1
                                                len = absDx + 1
                                            } else if (absDy > 0) {
                                                di = if (dy > 0) 1 else -1
                                                dj = 0
                                                len = absDy + 1
                                            } else {
                                                di = 0
                                                dj = 0
                                                len = 1
                                            }
                                            seleccion.clear()
                                            for (k in 0 until len) {
                                                val ni = i0 + k * di
                                                val nj = j0 + k * dj
                                                if (ni in matriz.indices && nj in matriz[0].indices) {
                                                    seleccion.add(Pair(ni, nj))
                                                }
                                            }
                                            fin = Pair(i0 + (len - 1) * di, j0 + (len - 1) * dj)
                                        }
                                    },
                                    onDragEnd = {
                                        seleccionando = false
                                        val (palabra, posiciones) = palabraSeleccionadaYPosiciones()
                                        if (palabra in palabras && palabra !in encontradas) {
                                            encontradas.add(palabra)
                                            posicionesEncontradas.add(posiciones)
                                        }
                                        seleccion.clear()
                                    },
                                    onDragCancel = {
                                        seleccionando = false
                                        seleccion.clear()
                                    }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = letra.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.size(2.dp))
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Lista de palabras:", fontWeight = FontWeight.SemiBold)
        Column(
            modifier = Modifier.padding(top = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            for (palabra in palabras) {
                val tachada = palabra in encontradas
                Text(
                    text = palabra,
                    fontSize = 16.sp,
                    textDecoration = if (tachada) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (tachada) Color.Gray else Color.Black
                )
            }
        }
        // Contador de palabras encontradas
        Text(
            text = "Palabras encontradas: ${encontradas.size} de ${palabras.size}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1976D2),
            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onBack) { Text("Regresar") }
    }
}

// --- Pantalla de Sopa de Letras para Fenómenos Hidrometeorológicos ---
@Composable
fun PantallaSopaLetrasHidrometeorologicos(
    onBack: () -> Unit
) {
    // Matriz de la sopa de letras (10x10) solo con las 7 palabras seleccionadas
    val matriz = listOf(
        listOf('H','E','L','A','D','A','S','Q','W','E'), // HELADAS
        listOf('S','E','Q','U','I','A','S','A','S','D'), // SEQUIAS
        listOf('H','U','R','A','C','A','N','E','S','F'), // HURACANES
        listOf('X','B','M','P','L','O','P','Q','G','H'),
        listOf('N','Y','V','E','H','C','D','N','Ñ','J'),
        listOf('I','G','R','A','N','I','Z','O','K','L'), // GRANIZO
        listOf('F','R','I','O','S','U','T','O','P','A'), // FRIOS
        listOf('U','T','N','I','E','B','L','A','B','C'), // NIEBLA
        listOf('A','B','C','D','E','F','G','H','I','J'),
        listOf('T','O','R','M','E','N','T','A','S','S')  // TORMENTAS
    )
    val palabras = listOf(
        "GRANIZO", "HELADAS", "HURACANES", "NIEBLA", "SEQUIAS", "TORMENTAS", "FRIOS"
    )
    SopaLetrasScreen(
        matriz = matriz,
        palabras = palabras,
        onBack = onBack,
        titulo = "Sopa de letras:",
        subtitulo = "Fenómenos Hidrometeorológicos"
    )
}

// Modificar SopaLetrasScreen para aceptar título y subtítulo opcionales
@Composable
fun SopaLetrasScreen(
    matriz: List<List<Char>>,
    palabras: List<String>,
    onBack: () -> Unit,
    titulo: String = "Sopa de letras:",
    subtitulo: String = "Fenómenos Geológicos"
) {
    // Aquí irá la lógica de selección, validación y tachado
    // Por ahora, solo muestro la matriz y la lista de palabras
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = titulo,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = subtitulo,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            textAlign = TextAlign.Center
        )

        // Estados para selección y palabras encontradas
        val seleccion = remember { mutableStateListOf<Pair<Int, Int>>() }
        val encontradas = remember { mutableStateListOf<String>() }
        val posicionesEncontradas = remember { mutableStateListOf<List<Pair<Int, Int>>>() }
        var inicio by remember { mutableStateOf<Pair<Int, Int>?>(null) }
        var fin by remember { mutableStateOf<Pair<Int, Int>?>(null) }
        var seleccionando by remember { mutableStateOf(false) }

        // Función para obtener la palabra seleccionada y sus posiciones
        fun palabraSeleccionadaYPosiciones(): Pair<String, List<Pair<Int, Int>>> {
            if (inicio == null || fin == null) return "" to emptyList()
            val (i0, j0) = inicio!!
            val (i1, j1) = fin!!
            val di = (i1 - i0).coerceIn(-1, 1)
            val dj = (j1 - j0).coerceIn(-1, 1)
            val len = maxOf(kotlin.math.abs(i1 - i0), kotlin.math.abs(j1 - j0)) + 1
            val sb = StringBuilder()
            val posiciones = mutableListOf<Pair<Int, Int>>()
            for (k in 0 until len) {
                val ni = i0 + k * di
                val nj = j0 + k * dj
                if (ni in matriz.indices && nj in matriz[0].indices) {
                    sb.append(matriz[ni][nj])
                    posiciones.add(ni to nj)
                }
            }
            return sb.toString().uppercase() to posiciones
        }

        // Matriz de letras con interacción drag
        for ((i, fila) in matriz.withIndex()) {
            Row {
                for ((j, letra) in fila.withIndex()) {
                    val pos = i to j
                    val seleccionado = seleccion.contains(pos)
                    val subrayado = posicionesEncontradas.any { lista -> pos in lista }
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                when {
                                    seleccionado -> Color(0xFF90CAF9) // Azul claro si está seleccionando
                                    subrayado -> Color(0xFF1976D2) // Azul fuerte si la palabra fue encontrada
                                    else -> Color(0xFFE0E0E0)
                                },
                                RoundedCornerShape(4.dp)
                            )
                            .padding(2.dp)
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = {
                                        inicio = pos
                                        fin = pos
                                        seleccion.clear()
                                        seleccion.add(pos)
                                        seleccionando = true
                                    },
                                    onDrag = { change, _ ->
                                        if (inicio != null) {
                                            val localPos = change.position
                                            val cellSize = 62f // Ajustado para sensibilidad óptima
                                            val j1 = (localPos.x / cellSize).toInt().coerceIn(0, matriz[0].size - 1)
                                            val i1 = (localPos.y / cellSize).toInt().coerceIn(0, matriz.size - 1)
                                            val (i0, j0) = inicio!!
                                            // Priorizar horizontal, luego vertical
                                            val dx = j1 - j0
                                            val dy = i1 - i0
                                            val absDx = abs(dx)
                                            val absDy = abs(dy)
                                            val di: Int
                                            val dj: Int
                                            val len: Int
                                            if (absDx > 0) {
                                                di = 0
                                                dj = if (dx > 0) 1 else -1
                                                len = absDx + 1
                                            } else if (absDy > 0) {
                                                di = if (dy > 0) 1 else -1
                                                dj = 0
                                                len = absDy + 1
                                            } else {
                                                di = 0
                                                dj = 0
                                                len = 1
                                            }
                                            seleccion.clear()
                                            for (k in 0 until len) {
                                                val ni = i0 + k * di
                                                val nj = j0 + k * dj
                                                if (ni in matriz.indices && nj in matriz[0].indices) {
                                                    seleccion.add(Pair(ni, nj))
                                                }
                                            }
                                            fin = Pair(i0 + (len - 1) * di, j0 + (len - 1) * dj)
                                        }
                                    },
                                    onDragEnd = {
                                        seleccionando = false
                                        val (palabra, posiciones) = palabraSeleccionadaYPosiciones()
                                        if (palabra in palabras && palabra !in encontradas) {
                                            encontradas.add(palabra)
                                            posicionesEncontradas.add(posiciones)
                                        }
                                        seleccion.clear()
                                    },
                                    onDragCancel = {
                                        seleccionando = false
                                        seleccion.clear()
                                    }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = letra.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.size(2.dp))
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Lista de palabras:", fontWeight = FontWeight.SemiBold)
        Column(
            modifier = Modifier.padding(top = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            for (palabra in palabras) {
                val tachada = palabra in encontradas
                Text(
                    text = palabra,
                    fontSize = 16.sp,
                    textDecoration = if (tachada) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (tachada) Color.Gray else Color.Black
                )
            }
        }
        // Contador de palabras encontradas
        Text(
            text = "Palabras encontradas: ${encontradas.size} de ${palabras.size}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1976D2),
            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onBack) { Text("Regresar") }
    }
}

// --- Pantalla de Sopa de Letras para Fenómenos Socio-Organizativos ---
@Composable
fun PantallaSopaLetrasSocioOrganizativos(
    onBack: () -> Unit
) {
    // Matriz de la sopa de letras (10x10) exactamente como en la imagen proporcionada, agregando MASIVO en la fila 6
    val matriz = listOf(
        listOf('M','C','A','R','R','E','T','E','R','O'),
        listOf('K','Á','J','Y','S','S','F','X','M','T'),
        listOf('P','E','R','S','O','N','A','S','S','Q'),
        listOf('S','A','B','O','T','A','J','E','S','N'),
        listOf('G','M','G','S','O','C','I','O','M','V'),
        listOf('J','J','Ñ','X','M','Ú','A','M','V','Q'),
        listOf('M','A','S','I','V','O','R','Ñ','Ó','Á'), // MASIVO en horizontal
        listOf('X','G','T','S','A','E','R','E','O','U'),
        listOf('T','E','R','R','O','R','I','S','M','O'),
        listOf('R','A','C','C','I','D','E','N','T','E')
    )
    val palabras = listOf(
        "ACCIDENTE", "AEREO", "CARRETERO", "MASIVO", "PERSONAS", "SABOTAJE", "SOCIO", "TERRORISMO"
    )
    SopaLetrasScreen(
        matriz = matriz,
        palabras = palabras,
        onBack = onBack,
        titulo = "Sopa de letras:",
        subtitulo = "Fenómenos Socio-Organizativos"
    )
}

// --- Pantalla de Sopa de Letras para Fenómenos Sanitario-Ecológicos ---
@Composable
fun PantallaSopaLetrasSanitarioEcologico(
    onBack: () -> Unit
) {
    // Matriz de la sopa de letras (10x10) con SUELO y ECOLOGICOS en horizontal y la última fila igual
    val matriz = listOf(
        listOf('S','U','E','L','O','V','S','Í','R','O'), // SUELO horizontal
        listOf('U','S','Z','L','D','H','U','S','É','C'),
        listOf('E','C','O','L','O','G','I','C','O','S'), // ECOLOGICOS horizontal
        listOf('Ú','P','L','A','G','A','S','Y','L','O'),
        listOf('N','N','Q','M','T','G','X','C','E','G'),
        listOf('S','A','N','I','T','A','R','I','O','G'),
        listOf('M','O','N','A','I','R','E','Ñ','Q','O'),
        listOf('E','P','I','D','E','M','I','A','S','S'),
        listOf('Ñ','F','M','X','Ú','A','G','U','A','S'),
        listOf('Y','Ü','P','Ñ','M','U','Í','S','K','L') // Última fila igual
    )
    val palabras = listOf(
        "AGUA", "AIRE", "ECOLOGICOS", "EPIDEMIAS", "PLAGAS", "SANITARIO", "SUELO"
    )
    SopaLetrasScreen(
        matriz = matriz,
        palabras = palabras,
        onBack = onBack,
        titulo = "Sopa de letras:",
        subtitulo = "Fenómenos Sanitario-Ecológicos"
    )
}

// --- Pantalla de Sopa de Letras para Químico-Tecnológicos ---
@Composable
fun PantallaSopaLetrasQuimicoTec(
    onBack: () -> Unit
) {
    // Matriz de la sopa de letras (10x10) ajustada: sin TRANSPORTE, URBANOS corregido, INCENDIOS añadido
    val matriz = listOf(
        listOf('F','R','E','S','I','D','U','O','S','N'), // RESIDUOS
        listOf('O','O','F','U','G','A','S','É','É','I'), // FUGAS
        listOf('U','R','B','A','N','O','S','T','E','O'), // URBANOS (U normal)
        listOf('F','N','N','E','Ú','C','D','H','Ñ','C'),
        listOf('E','X','P','L','O','S','I','O','N','S'), // EXPLOSION
        listOf('I','N','C','E','N','D','I','O','S','Q'), // INCENDIOS
        listOf('H','L','M','X','R','É','A','H','Ú','D'),
        listOf('D','C','H','Q','J','A','J','L','K','I'),
        listOf('D','E','R','R','A','M','E','S','E','O'), // DERRAMES
        listOf('P','E','L','I','G','R','O','S','O','S')  // PELIGROSOS
    )
    val palabras = listOf(
        "DERRAMES", "FORESTALES", "FUGAS", "INCENDIOS", "PELIGROSOS", "RESIDUOS", "URBANOS", "EXPLOSION"
    )
    SopaLetrasScreen(
        matriz = matriz,
        palabras = palabras,
        onBack = onBack,
        titulo = "Sopa de letras:",
        subtitulo = "Fenómenos Químico-Tecnológicos"
    )
}