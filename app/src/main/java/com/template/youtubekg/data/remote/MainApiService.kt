package com.template.youtubekg.data.remote

import com.template.youtubekg.data.model.PlayListItemModel
import com.template.youtubekg.data.model.PlayListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApiService {
    @GET("playlists")
    suspend fun getPlaylists(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
    ): Response<PlayListModel>

    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int
    ): Response<PlayListItemModel>
}