package com.template.youtubekg.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.template.youtubekg.core.network.RemoteDataSource
import com.template.youtubekg.core.network.Resource
import com.template.youtubekg.data.model.PlayListItemModel
import com.template.youtubekg.data.model.PlayListModel
import kotlinx.coroutines.Dispatchers


class Repository(private val remoteDataSource: RemoteDataSource) {
    /*
    `class Repository(private val remoteDataSource: RemoteDataSource)`: Это объявление класса `Repository`. В конструкторе этого класса принимается `remoteDataSource`,
     который представляет собой источник данных из удаленного источника (например, сетевого API).
     */
    fun getPlaylist(): LiveData<Resource<PlayListModel>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            emit(remoteDataSource.getPlaylist())
        }
    }
    /*
    `fun getPlaylist(): LiveData<Resource<PlayListModel>>`: Это функция, предназначенная для получения плейлиста.
     Она возвращает `LiveData`, что позволяет наблюдать за изменениями данных в режиме реального времени. `Resource<PlayListModel>`
     указывает на обертку для данных, где `PlayListModel` представляет собой модель данных о плейлисте.
   - `return liveData(Dispatchers.IO) { ... }`: Этот блок создает и возвращает `LiveData` с использованием `liveData`,
     что позволяет выполнять асинхронные операции в фоновом потоке (`Dispatchers.IO`).
   - `emit(Resource.loading())`: Эта строка отправляет статус загрузки через `LiveData`.
   - `emit(remoteDataSource.getPlaylist())`: Эта строка отправляет результат выполнения запроса к удаленному источнику через `LiveData`.
     */

    fun getDetail(playlistId: String): LiveData<Resource<PlayListItemModel>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            emit(remoteDataSource.getDetail(playlistId))
        }
    }
    /*
    `fun getPlaylistItems(playlistId: String): LiveData<Resource<PlayListItemModel>>`: Это функция, предназначенная
     для получения элементов плейлиста по его идентификатору. Она также возвращает `LiveData` с типом `Resource<PlayListItemModel>`,
     где `PlayListItemModel` представляет собой модель данных о элементах плейлиста.
   - Аналогично предыдущей функции, здесь также используется `liveData`, `Dispatchers.IO`, `Resource.loading()` и
    `remoteDataSource.getDetail(playlistId)` для асинхронного получения данных.
     */
}
