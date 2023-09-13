package com.template.youtubekg.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

class ConnectionLiveData(private val connectivityManager: ConnectivityManager) :
    LiveData<Boolean>() {

    constructor(application: Application) : this(
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    )

    @SuppressLint("ObsoleteSdkInt")
    private val networkCallback =
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP) object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                postValue(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                postValue(false)
            }
        }

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActive() {
        super.onActive()
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}/*
Этот код представляет собой класс `ConnectionLiveData`, который предоставляет LiveData для отслеживания состояния подключения к интернету. Разберем его по частям:

1. `import`: Здесь импортируются необходимые классы и модули.

2. `class ConnectionLiveData(private val connectivityManager: ConnectivityManager) : LiveData<Boolean>()`: Это объявление класса `ConnectionLiveData`. Он наследуется от `LiveData<Boolean>`. Класс принимает в конструкторе менеджер подключения к интернету.

3. `constructor(application: Application) : this(application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)`: Это второй конструктор класса, который принимает объект `Application` и использует его для получения `ConnectivityManager`.

4. `private val networkCallback = ...`: Это приватное свойство, представляющее колбэк для слежения за изменениями состояния сети. Внутри определены два метода: `onAvailable` вызывается, когда сеть становится доступной, и `onLost` - когда сеть теряется.

5. `override fun onActive() { ... }`: Этот метод вызывается, когда LiveData начинает быть активным (когда есть активные подписчики). Здесь регистрируется колбэк `networkCallback` для отслеживания изменений состояния сети.

6. `override fun onInactive() { ... }`: Этот метод вызывается, когда LiveData больше не активен (когда нет активных подписчиков). Здесь отменяется регистрация колбэка `networkCallback`.

Этот класс предоставляет удобный способ отслеживания состояния подключения к интернету в Android-приложении, используя LiveData и ConnectivityManager. Когда состояние сети меняется, LiveData автоматически оповещает подписчиков.
 */