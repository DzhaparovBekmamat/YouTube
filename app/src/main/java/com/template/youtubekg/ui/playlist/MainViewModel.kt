package com.template.youtubekg.ui.playlist

import androidx.lifecycle.LiveData
import com.template.youtubekg.core.base.BaseViewModel
import com.template.youtubekg.core.network.Resource
import com.template.youtubekg.data.model.PlayListModel
import com.template.youtubekg.data.repository.Repository

class MainViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPlayList(): LiveData<Resource<PlayListModel>> {
        return repository.getPlaylist()
    }
}