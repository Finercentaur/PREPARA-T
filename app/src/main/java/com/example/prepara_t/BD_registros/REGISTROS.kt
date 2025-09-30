package com.example.prepara_t.BD_registros

data class REGISTROS(
    val CP: String,
    val GEOLOGICOS: String,
    val HIDROMETEOROLOGICOS: String,
    val QUIMICO_TECNOLOGICO: String,
    val SANITARIO_ECOLOGICOS: String,
    val SOCIO_ORGANIZATIVOS: String
)
data class db_REGISTROSData(
    val spreadsheet_id: String,
    val sheet: String,
    val rows: List<List<String>>
)
