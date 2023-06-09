package com.ssafy.talkeasy.core.data.remote.service

import com.ssafy.talkeasy.core.data.remote.datasource.aac.AACWordListResponse
import com.ssafy.talkeasy.core.data.remote.datasource.aac.AACWordRequest
import com.ssafy.talkeasy.core.data.remote.datasource.aac.AACWordResponse
import com.ssafy.talkeasy.core.data.remote.datasource.aac.TTSRequest
import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AACApiService {

    @POST("/api/aac/generate")
    suspend fun generateSentence(
        @Body
        body: AACWordRequest,
    ): DefaultResponse<String>

    @GET("/api/aac/categories/{categoryId}")
    suspend fun getWordList(
        @Path("categoryId")
        categoryId: Int,
    ): PagingDefaultResponse<AACWordListResponse>

    @GET("/api/aac/relative-verb/{aacId}")
    suspend fun getRelativeVerbList(
        @Path("aacId")
        aacId: Int,
    ): PagingDefaultResponse<List<AACWordResponse>>

    @POST("/api/aac/tts")
    suspend fun getTTSMp3Url(
        @Body
        body: TTSRequest,
    ): DefaultResponse<String>
}