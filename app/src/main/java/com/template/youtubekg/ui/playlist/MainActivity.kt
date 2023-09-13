package com.template.youtubekg.ui.playlist

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.template.youtubekg.core.base.BaseActivity
import com.template.youtubekg.core.network.Resource
import com.template.youtubekg.data.model.PlayListModel
import com.template.youtubekg.databinding.ActivityMainBinding
import com.template.youtubekg.ui.detail.DetailActivity
import com.template.youtubekg.utils.ConnectionLiveData
import com.template.youtubekg.utils.Constants.PLAYLIST_DESCRIPTION
import com.template.youtubekg.utils.Constants.PLAYLIST_ID
import com.template.youtubekg.utils.Constants.PLAYLIST_TITLE
import com.template.youtubekg.utils.Constants.VIDEO_ITEM_COUNT
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun inflateViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainViewModel by viewModel()

    private val adapter = MainAdapter(this::onClick)

    override fun initLiveData() {
        super.initLiveData()
        viewModel.getPlayList().observe(this) { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(this, "Success Status", Toast.LENGTH_SHORT).show()
                    adapter.setList(response.data?.items)
                    viewModel.loading.value = false
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, "Error Status", Toast.LENGTH_SHORT).show()
                    viewModel.loading.value = false

                }

                Resource.Status.LOADING -> {
                    Toast.makeText(this, "Loading Status", Toast.LENGTH_SHORT).show()
                    viewModel.loading.value = true
                }
            }
        }
        viewModel.loading.observe(this) { status ->
            if (status) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }
    }

    override fun checkInternetConnection() {
        super.checkInternetConnection()
        ConnectionLiveData(application).observe(this) { isConnection ->
            if (!isConnection) {
                binding.noConnection.visibility = View.VISIBLE
                binding.mainContainer.visibility = View.GONE
            }
            binding.noInternetConnectionInclude.btnTryAgain.setOnClickListener {
                if (!isConnection) {
                    binding.mainContainer.visibility = View.GONE
                    binding.noConnection.visibility = View.VISIBLE
                } else {
                    binding.mainContainer.visibility = View.VISIBLE
                    binding.noConnection.visibility = View.GONE
                }
            }
        }
    }

    private fun onClick(item: PlayListModel.Item) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(PLAYLIST_ID, item.id)
        intent.putExtra(PLAYLIST_TITLE, item.snippet.title)
        intent.putExtra(PLAYLIST_DESCRIPTION, item.snippet.description)
        intent.putExtra(VIDEO_ITEM_COUNT, item.contentDetails.itemCount.toString())
        startActivity(intent)
    }

    override fun initView() {
        super.initView()
        binding.recyclerView.adapter = adapter
    }
}/*
Этот код представляет собой активити (`MainActivity`) в приложении Android, отвечающую за отображение списка плейлистов. Разберем его по частям:

1. `package com.template.youtubekg.ui.playlist`: Эта строка указывает на пакет, к которому принадлежит этот файл. В данном случае, это активити для отображения плейлистов.

2. `import`: Эти строки используются для подключения различных классов и модулей, необходимых для работы этой активити.

3. `class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>()`: Это объявление класса `MainActivity`. Он наследуется от `BaseActivity` и параметризован типами `ActivityMainBinding` и `MainViewModel`.

4. `override fun inflateViewBinding(): ActivityMainBinding`: Эта функция создает и возвращает объект привязки для разметки активити.

5. `override val viewModel: MainViewModel by viewModel()`: Здесь создается объект `viewModel` с использованием библиотеки Koin. `MainViewModel` представляет модель представления, связанную с этой активити.

6. `private val adapter = MainAdapter(this::onClick)`: Создается адаптер (`MainAdapter`) для управления отображением списка плейлистов. Также указана функция `onClick`, которая будет вызываться при нажатии на элемент списка.

7. `override fun initLiveData() { ... }`: Эта функция инициализирует наблюдателей для живых данных (LiveData) и обрабатывает результаты запросов к данным, изменяя интерфейс пользователя в зависимости от статуса загрузки данных.

8. `override fun checkInternetConnection() { ... }`: Эта функция проверяет доступ к интернету с помощью `ConnectionLiveData`.

9. `private fun onClick(item: PlayListModel.Item) { ... }`: Эта функция вызывается при нажатии на элемент списка плейлистов. Она создает намерение (`Intent`) для перехода к активити с детальной информацией о плейлисте и передает необходимые данные через интент.

10. `override fun initView() { ... }`: Здесь инициализируются элементы пользовательского интерфейса, такие как адаптер для списка.

Эта активити отвечает за отображение списка плейлистов. При нажатии на плейлист, происходит переход к активити с детальной информацией о выбранном плейлисте. Кроме того, осуществляется проверка подключения к интернету.
 */