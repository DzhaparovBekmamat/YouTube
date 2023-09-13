package com.template.youtubekg.ui.detail

import androidx.lifecycle.LiveData
import com.template.youtubekg.core.base.BaseViewModel
import com.template.youtubekg.core.network.Resource
import com.template.youtubekg.data.model.PlayListItemModel
import com.template.youtubekg.data.repository.Repository

class DetailViewModel(private val repository: Repository) : BaseViewModel() {
    //`class DetailViewModel(private val repository: Repository) : BaseViewModel()`: Это объявление класса `DetailViewModel`.
//Он наследуется от `BaseViewModel` и принимает `repository` в качестве зависимости через конструктор. `BaseViewModel` представляет базовый класс для моделей представления.
    fun getPlayListItem(playlistId: String): LiveData<Resource<PlayListItemModel>> {
        return repository.getDetail(playlistId)
    }/*
    `fun getPlayListItem(playlistId: String): LiveData<Resource<PlayListItemModel>>`: Эта функция предоставляет
    метод для получения списка элементов плейлиста по его идентификатору. Она использует репозиторий (`Repository`) для этого.
    - `return repository.getDetail(playlistId)`: Здесь вызывается функция `getDetail` репозитория, которая возвращает `LiveData` с результатами запроса.
     */
}/*
В целом, этот `DetailViewModel` предоставляет интерфейс для получения списка элементов плейлиста и использует репозиторий для обработки запросов к данным. Он также наследует функциональность базовой модели представления (`BaseViewModel`), что может включать в себя обработку жизненного цикла и управление состоянием загрузки данных.
 */