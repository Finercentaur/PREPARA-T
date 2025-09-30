package com.example.prepara_t.BD_registros

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WebService {
    //GET...
    @GET("exec?spreadsheetId=1vu74AISb9Ph0cGgys7UtXx9yB1o4-pZi4jONjOUnU8U&sheet=db_REGISTROS")
    suspend fun obtenerRegistros()
    : Response<GetResponse>

    //POST...
    @POST("exec")
    suspend fun agregarRegistro(
        @Body db_REGISTROS: db_REGISTROSData
    ): Response<PostResponse>
}