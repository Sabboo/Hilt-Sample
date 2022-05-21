package com.saber.hiltsample.api

import com.saber.hiltsample.data.ListingResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest

const val PRIVATE_KEY = "00a456fa3c27eeb9ffa7ad32986130b9d845eb4a"
const val PUBLIC_KEY = "1116eefee0856af6da81cf3138899bd9"

interface MarvelAPI {

    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: String? = null,
        @Query("apikey") apikey: String = PUBLIC_KEY,
        @Query("ts") ts: String,
        @Query("hash") hash: String = md5(ts + PRIVATE_KEY + PUBLIC_KEY)
    ): ListingResponse

}

fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}