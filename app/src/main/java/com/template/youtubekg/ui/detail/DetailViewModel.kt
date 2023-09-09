package com.template.youtubekg.ui.detail

import androidx.lifecycle.LiveData
import com.template.youtubekg.core.base.BaseViewModel
import com.template.youtubekg.core.network.Resource
import com.template.youtubekg.data.model.PlayListItemModel
import com.template.youtubekg.data.repository.Repository

class DetailViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlayListItemModel>> {
        return repository.getPlaylistItems(playlistId)
    }
}