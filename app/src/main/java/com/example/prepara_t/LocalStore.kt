package com.example.prepara_t

import android.content.Context
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ✅ Inicialización del DataStore a nivel de extensión de Context
private val Context.dataStore by preferencesDataStore(name = "preparat_prefs")

object LocalStore {
    // 🔑 Claves de DataStore
    private val KEY_POSTAL = stringPreferencesKey("postal_code")
    private fun selectionKey(section: String) = stringPreferencesKey("selections_$section")
    private fun wordsKey(section: String) = stringPreferencesKey("sopa_words_$section")
    private fun answerKey(section: String) = stringPreferencesKey("sopa_answer_$section")

    // ----------------------
    // 📌 Postal Code
    // ----------------------
    suspend fun setPostalCode(context: Context, postal: String) {
        context.dataStore.edit { it[KEY_POSTAL] = postal }
    }

    suspend fun getPostalCode(context: Context): String? {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_POSTAL]
    }

    // ----------------------
    // 📌 Selections (checkbox / opciones)
    // ----------------------
    suspend fun updateSelection(context: Context, section: String, option: String, checked: Boolean) {
        val key = selectionKey(section)
        val current = (context.dataStore.data.first()[key] ?: "")
            .split(',')
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .toMutableSet()

        if (checked) current.add(option) else current.remove(option)

        context.dataStore.edit { it[key] = current.joinToString(",") }
    }

    suspend fun getSelections(context: Context, section: String): Set<String> {
        val key = selectionKey(section)
        val raw = context.dataStore.data.first()[key] ?: ""
        return raw.split(',').map { it.trim() }.filter { it.isNotEmpty() }.toSet()
    }

    // ----------------------
    // 📌 Palabras encontradas en la sopa
    // ----------------------
    suspend fun setFoundWords(context: Context, section: String, words: List<String>) {
        val key = wordsKey(section)
        context.dataStore.edit { it[key] = words.joinToString(",") }
    }

    suspend fun getFoundWords(context: Context, section: String): Set<String> {
        val key = wordsKey(section)
        val raw = context.dataStore.data.first()[key] ?: ""
        return raw.split(',').map { it.trim() }.filter { it.isNotEmpty() }.toSet()
    }

    // ----------------------
    // 📌 Respuestas en sopa
    // ----------------------
    suspend fun setAnswer(context: Context, section: String, answer: String) {
        val key = answerKey(section)
        context.dataStore.edit { it[key] = answer }
    }

    suspend fun getAnswer(context: Context, section: String): String? {
        val key = answerKey(section)
        return context.dataStore.data.first()[key]
    }

    // ----------------------
    // 📌 Exportar todo a JSON
    // ----------------------
    suspend fun exportAll(context: Context): File {
        val sections = listOf("geologicos", "hidromet", "quimicotec", "sanitarioeco", "socioorg")
        val root = JSONObject()

        // Postal Code
        root.put("postal_code", getPostalCode(context) ?: "")

        // Selections
        val selections = JSONObject()
        val sopa = JSONObject()

        for (s in sections) {
            selections.put(s, getSelections(context, s).joinToString(","))

            val prog = JSONObject()
            prog.put("found", getFoundWords(context, s).joinToString(","))
            prog.put("answer", getAnswer(context, s) ?: "")

            sopa.put(s, prog)
        }

        root.put("selections", selections)
        root.put("sopa_progress", sopa)

        // Guardar en archivo
        val dir = context.getExternalFilesDir(null) ?: context.filesDir
        val ts = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val file = File(dir, "preparat_export_$ts.json")
        file.writeText(root.toString())

        return file
    }
}


