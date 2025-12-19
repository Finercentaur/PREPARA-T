package com.example.prepara_t

import android.content.Context
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Inicialización del DataStore a nivel de extensión de Context
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preparat_prefs")

object LocalStore {
    // 🔑 Claves de DataStore
    private val KEY_POSTAL = stringPreferencesKey("postal_code")

    // Guarda la seleccion en el mismo dispositivo para cada CP registrado
    private fun selectionKey(section: String, postalCode: String): Preferences.Key<String> {
        return stringPreferencesKey("selections_${postalCode}_$section")
    }
    private fun wordsKey(section: String, postalCode: String): Preferences.Key<String> {
        return stringPreferencesKey("sopa_words_${postalCode}_$section")
    }
    private fun answerKey(section: String, postalCode: String): Preferences.Key<String> {
        return stringPreferencesKey("sopa_answer_${postalCode}_$section")
    }
    private fun identificacionKey(fenomeno: String, postalCode: String): Preferences.Key<String> {
        return stringPreferencesKey("identificacion_${postalCode}_$fenomeno")
    }

    //  Constantes para las secciones válidas
    object Sections {
        const val GEOLOGICOS = "geologicos"
        const val HIDROMET = "hidromet"
        const val QUIMICOTEC = "quimicotec"
        const val SANITARIOECO = "sanitarioeco"
        const val SOCIOORG = "socioorg"

        val ALL = listOf(GEOLOGICOS, HIDROMET, QUIMICOTEC, SANITARIOECO, SOCIOORG)
    }

    // Constantes para los fenómenos
    object Fenomenos {
        const val ERUPCION = "erupcion"
        const val SISMO = "sismo"
        const val TSUNAMI = "tsunami"
        const val GRIETAS = "grietas"
        const val DESLIZAMIENTO = "deslizamiento"
        const val HUNDIMIENTOS = "hundimientos"

        val ALL = listOf(ERUPCION, SISMO, TSUNAMI, GRIETAS, DESLIZAMIENTO, HUNDIMIENTOS)
    }

    // ----------------------
    // 📌 Postal Code
    // ----------------------
    suspend fun setPostalCode(context: Context, postal: String) {
        try {
            context.dataStore.edit { it[KEY_POSTAL] = postal.trim() }
        } catch (e: Exception) {
            throw LocalStoreException("Error saving postal code: ${e.message}")
        }
    }

    suspend fun getPostalCode(context: Context): String? {
        return try {
            context.dataStore.data.first()[KEY_POSTAL]
        } catch (e: Exception) {
            null
        }
    }

    // ----------------------
    // 📌 Selections (checkbox / opciones)
    // ----------------------
    suspend fun updateSelection(context: Context, postalCode: String, section: String, option: String, checked: Boolean) {
        if (section.isBlank() || option.isBlank()) return

        try {
            val key = selectionKey(section, postalCode)
            val current = (context.dataStore.data.first()[key] ?: "")
                .split(',')
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .toMutableSet()

            if (checked) {
                current.add(option.trim())
            } else {
                current.remove(option.trim())
            }

            context.dataStore.edit { it[key] = current.joinToString(",") }
        } catch (e: Exception) {
            throw LocalStoreException("Error updating selection for section $section: ${e.message}")
        }
    }

    suspend fun getSelections(context: Context, postalCode: String, section: String): Set<String> {
        if (section.isBlank() || postalCode.isBlank()) return emptySet()

        return try {
            val key = selectionKey(section, postalCode)
            val raw = context.dataStore.data.first()[key] ?: ""
            raw.split(',')
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .toSet()
        } catch (e: Exception) {
            emptySet()
        }
    }

    // Función para borrar todas las selecciones de una sección
    suspend fun clearSelections(context: Context, postalCode: String, section: String) {
        try {
            val key = selectionKey(section, postalCode)
            context.dataStore.edit { it.remove(key) }
        } catch (e: Exception) {
            throw LocalStoreException("Error clearing selections for section $section: ${e.message}")
        }
    }

    // ----------------------
    // 📌 Palabras encontradas en la sopa
    // ----------------------
    suspend fun setFoundWords(context: Context, postalCode: String, section: String, words: List<String>) {
        if (section.isBlank() ) return

        try {
            val key = wordsKey(section,postalCode)
            val cleanWords = words.map { it.trim() }.filter { it.isNotEmpty() }
            context.dataStore.edit { it[key] = cleanWords.joinToString(",") }
        } catch (e: Exception) {
            throw LocalStoreException("Error saving found words for section $section: ${e.message}")
        }
    }

    suspend fun getFoundWords(context: Context, postalCode: String, section: String): Set<String> {
        if (section.isBlank() ) return emptySet()

        return try {
            val key = wordsKey(section,postalCode)
            val raw = context.dataStore.data.first()[key] ?: ""
            raw.split(',')
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .toSet()
        } catch (e: Exception) {
            emptySet()
        }
    }

    // ----------------------
    // 📌 Respuestas en sopa
    // ----------------------
    suspend fun setAnswer(context: Context, postalCode: String, section: String, answer: String) {
        if (section.isBlank() ) return

        try {
            val key = answerKey(section,postalCode)
            context.dataStore.edit { it[key] = answer.trim() }
        } catch (e: Exception) {
            throw LocalStoreException("Error saving answer for section $section: ${e.message}")
        }
    }

    suspend fun getAnswer(context: Context, postalCode: String,  section: String): String? {
        if (section.isBlank() ) return null

        return try {
            val key = answerKey(section,postalCode)
            context.dataStore.data.first()[key]
        } catch (e: Exception) {
            null
        }
    }

    // ----------------------
    // 📌 Identificación de fenómenos (NUEVO)
    // ----------------------
    suspend fun saveIdentificacion(context: Context, postalCode: String, fenomeno: String, respuesta: String) {
        if (fenomeno.isBlank() || postalCode.isBlank()) return

        try {
            val key = identificacionKey(fenomeno,postalCode)
            context.dataStore.edit { preferences ->
                preferences[key] = respuesta.trim()
            }
        } catch (e: Exception) {
            throw LocalStoreException("Error saving identificacion for fenomeno $fenomeno: ${e.message}")
        }
    }

    suspend fun getIdentificacion(context: Context, postalCode: String, fenomeno: String): String {
        if (fenomeno.isBlank() || postalCode.isBlank()) return ""

        return try {
            val key = identificacionKey(fenomeno, postalCode)
            return context.dataStore.data.first()[key] ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    //  Limpiar identificación de un fenómeno
    suspend fun clearIdentificacion(context: Context, postalCode: String, fenomeno: String) {
        try {
            val key = identificacionKey(fenomeno,postalCode)
            context.dataStore.edit { it.remove(key) }
        } catch (e: Exception) {
            throw LocalStoreException("Error clearing identificacion for fenomeno $fenomeno: ${e.message}")
        }
    }

    // ----------------------
    // 📌 Funciones de utilidad
    // ----------------------
    suspend fun hasAnySelections(context: Context, postalCode: String, section: String): Boolean {
        return getSelections(context,postalCode, section).isNotEmpty()
    }

    suspend fun getSelectionCount(context: Context, postalCode: String, section: String): Int {
        return getSelections(context,postalCode, section).size
    }

    //  Verificar si los datos están completos para una sección
    suspend fun isSectionComplete(context: Context, postalCode: String, section: String): Boolean {
        val hasSelections = hasAnySelections(context,postalCode, section)
        val hasAnswer = !getAnswer(context,postalCode, section).isNullOrBlank()
        val hasFoundWords = getFoundWords(context,postalCode, section).isNotEmpty()

        return hasSelections && (hasAnswer || hasFoundWords)
    }

    //  Contar identificaciones completadas
    suspend fun getIdentificacionesCount(context: Context, postalCode: String): Int {
        return try {
            Fenomenos.ALL.count { fenomeno ->
                getIdentificacion(context,postalCode, fenomeno).isNotEmpty()
            }
        } catch (e: Exception) {
            0
        }
    }

    // ----------------------
    // 📌 Exportar todo a JSON (mejorado)
    // ----------------------
    suspend fun exportAll(context: Context, postalCode: String): File {
        return try {
            val root = JSONObject()

            // Postal Code
            root.put("postal_code", getPostalCode(context) ?: "")
            root.put("export_timestamp", System.currentTimeMillis())
            root.put("export_date", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()))

            // Selections
            val selections = JSONObject()
            val sopa = JSONObject()
            val identificaciones = JSONObject()
            var totalSelections = 0

            for (section in Sections.ALL) {
                val sectionSelections = getSelections(context,postalCode, section)
                selections.put(section, sectionSelections.joinToString(","))
                totalSelections += sectionSelections.size

                val prog = JSONObject()
                prog.put("found", getFoundWords(context,postalCode, section).joinToString(","))
                prog.put("answer", getAnswer(context,postalCode, section) ?: "")
                prog.put("is_complete", isSectionComplete(context,postalCode, section))

                sopa.put(section, prog)
            }

            // Identificaciones de fenómenos
            for (fenomeno in Fenomenos.ALL) {
                identificaciones.put(fenomeno, getIdentificacion(context,postalCode, fenomeno))
            }

            root.put("selections", selections)
            root.put("sopa_progress", sopa)
            root.put("identificaciones", identificaciones)
            root.put("total_selections", totalSelections)
            root.put("total_identificaciones", getIdentificacionesCount(context,postalCode))

            // Guardar en archivo con mejor nomenclatura
            val dir = context.getExternalFilesDir(null) ?: context.filesDir
            val ts = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val file = File(dir, "preparat_export_$ts.json")

            file.writeText(root.toString(2)) // Formato JSON con indentación

            file
        } catch (e: Exception) {
            throw LocalStoreException("Error exporting data: ${e.message}")
        }
    }

    //  Función para importar datos (mejorada)
    suspend fun importFromFile(context: Context, postalCode: String, file: File) {
        try {
            val jsonString = file.readText()
            val root = JSONObject(jsonString)

            // Importar postal code
            if (root.has("postal_code")) {
                setPostalCode(context, root.getString("postal_code"))
            }

            // Importar selections
            if (root.has("selections")) {
                val selections = root.getJSONObject("selections")
                for (section in Sections.ALL) {
                    if (selections.has(section)) {
                        val sectionData = selections.getString(section)
                        // Limpiar sección actual
                        clearSelections(context,postalCode, section)
                        // Importar nuevas selecciones
                        sectionData.split(",").forEach { option ->
                            if (option.trim().isNotEmpty()) {
                                updateSelection(context,postalCode, section, option.trim(), true)
                            }
                        }
                    }
                }
            }

            // Importar identificaciones
            if (root.has("identificaciones")) {
                val identificaciones = root.getJSONObject("identificaciones")
                for (fenomeno in Fenomenos.ALL) {
                    if (identificaciones.has(fenomeno)) {
                        val respuesta = identificaciones.getString(fenomeno)
                        if (respuesta.isNotEmpty()) {
                            saveIdentificacion(context,postalCode, fenomeno, respuesta)
                        }
                    }
                }
            }

        } catch (e: Exception) {
            throw LocalStoreException("Error importing data: ${e.message}")
        }
    }

    // Función para limpiar todos los datos (útil para testing o reset)
    suspend fun clearAllData(context: Context) {
        try {
            context.dataStore.edit { preferences ->
                preferences.clear()
            }
        } catch (e: Exception) {
            throw LocalStoreException("Error clearing all data: ${e.message}")
        }
    }
}

// Excepción personalizada para mejor manejo de errores
class LocalStoreException(message: String) : Exception(message)