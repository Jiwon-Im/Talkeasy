package com.ssafy.talkeasy.core.data.remote.datasource.aac

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse
import com.ssafy.talkeasy.core.data.remote.service.AACApiService
import javax.inject.Inject

class AACRemoteDataSourceImpl @Inject constructor(
    private val aacApiService: AACApiService,
) : AACRemoteDataSource {

    override suspend fun generateSentence(body: AACWordRequest): DefaultResponse<String> =
        aacApiService.generateSentence(body)

    override suspend fun getWordList(categoryId: Int): PagingDefaultResponse<AACWordListResponse> =
        aacApiService.getWordList(categoryId)

    override suspend fun getRelativeVerbList(
        aacId: Int,
    ): PagingDefaultResponse<List<AACWordResponse>> = aacApiService.getRelativeVerbList(aacId)

    override suspend fun getTTSMp3Url(tts: TTSRequest): DefaultResponse<String> =
        aacApiService.getTTSMp3Url(tts)
}