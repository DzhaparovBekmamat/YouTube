package com.template.youtubekg.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.template.youtubekg.core.network.RemoteDataSource
import com.template.youtubekg.core.network.Resource
import com.template.youtubekg.data.model.PlayListItemModel
import com.template.youtubekg.data.model.PlayListModel
import kotlinx.coroutines.Dispatchers


class Repository(private val remoteDataSource: RemoteDataSource) {

    fun getPlaylist(): LiveData<Resource<PlayListModel>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            emit(remoteDataSource.getPlaylist())
        }
    }

    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlayListItemModel>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            emit(remoteDataSource.getDetail(playlistId))
        }
    }
}