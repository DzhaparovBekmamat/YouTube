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
/*
`suspend fun getPlaylists(...)` и `suspend fun getPlaylistItems(...)`: Это объявления функций.
Они используются для отправки сетевых запросов. Ключевое слово `suspend` означает, что эти функции
могут быть приостановлены и возобновлены, что полезно для асинхронного программирования в Kotlin.
 */
    /*
    `@Query(...)`: Эти аннотации используются для указания параметров запроса для API. Например, `@Query("part") part: String`
     означает, что будет добавлен параметр запроса с именем "part" и значением типа String.
     */
    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int
    ): Response<PlayListItemModel>
    /*
    `Response<PlayListModel>` и `Response<PlayListItemModel>`: Эти указывают ожидаемый тип ответа от API.
    В данном случае ожидается ответ в виде `PlayListModel` или `PlayListItemModel`, которые, вероятно,
     представляют собой модели данных, представляющие плейлисты и элементы плейлистов.
     */
}
