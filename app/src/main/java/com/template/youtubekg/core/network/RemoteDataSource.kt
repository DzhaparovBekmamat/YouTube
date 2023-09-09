package com.template.youtubekg.core.network

import com.template.youtubekg.core.base.BaseDataSource
import com.template.youtubekg.data.remote.MainApiService
import com.template.youtubekg.utils.Constants
import com.template.youtubekg.BuildConfig

class RemoteDataSource(private val apiService: MainApiService) : BaseDataSource() {

    suspend fun getPlaylist() = getResult {
        apiService.getPlaylists(
            key = BuildConfig.API_KEY,
            part = Constants.PART,
            channelId = Constants.CHANNEL_ID,
            maxResults = 10
        )
    }

    suspend fun getDetail(playlistId: String) = getResult {
        apiService.getPlaylistItems(
            key = BuildConfig.API_KEY,
            part = Constants.PART,
            playlistId = playlistId,
            maxResults = 20
        )
    }
}