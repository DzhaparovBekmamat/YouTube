package com.template.youtubekg.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.template.youtubekg.core.base.BaseActivity
import com.template.youtubekg.core.network.Resource
import com.template.youtubekg.data.model.PlayListItemModel
import com.template.youtubekg.databinding.ActivityDetailBinding
import com.template.youtubekg.ui.play.VideoActivity
import com.template.youtubekg.utils.ConnectionLiveData
import com.template.youtubekg.utils.Constants.PLAYLIST_DESCRIPTION
import com.template.youtubekg.utils.Constants.PLAYLIST_ID
import com.template.youtubekg.utils.Constants.PLAYLIST_TITLE
import com.template.youtubekg.utils.Constants.VIDEO_DESCRIPTION
import com.template.youtubekg.utils.Constants.VIDEO_ID
import com.template.youtubekg.utils.Constants.VIDEO_ITEM_COUNT
import com.template.youtubekg.utils.Constants.VIDEO_TITLE
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("UNCHECKED_CAST")
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override fun inflateViewBinding(): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)

    //`override fun inflateViewBinding(): ActivityDetailBinding`: Эта функция создает и возвращает объект привязки для разметки активити.
    override val viewModel: DetailViewModel by viewModel()

    //`override val viewModel: DetailViewModel by viewModel()`: Здесь создается объект `viewModel` с
    //использованием библиотеки Koin. `DetailViewModel` представляет модель представления, связанную с этой активити.
    private val adapter = DetailAdapter(this::onClick)

    //`private val adapter = DetailAdapter(this::onClick)`: Создается адаптер (`DetailAdapter`) для
    //управления отображением списка элементов. Также указана функция `onClick`, которая будет вызываться при нажатии на элемент списка.
    private fun onClick(item: PlayListItemModel.Item) {
        val intent = Intent(this, VideoActivity::class.java)
        intent.putExtra(VIDEO_ID, item.contentDetails?.videoId)
        intent.putExtra(VIDEO_TITLE, item.snippet?.title)
        intent.putExtra(VIDEO_DESCRIPTION, item.snippet?.description)
        startActivity(intent)
    }

    /*
    `private fun onClick(item: PlayListItemModel.Item)`: Эта функция вызывается при нажатии на элемент списка.
    Она создает намерение (`Intent`) для перехода к другой активити (`VideoActivity`) и передает данные о видео (идентификатор, заголовок, описание).
     */
    @SuppressLint("SetTextI18n")
    override fun initLiveData() {
        super.initLiveData()
        val getIntentId = intent.getStringExtra(PLAYLIST_ID)
        val getIntentDesc = intent.getStringExtra(PLAYLIST_DESCRIPTION)
        val getIntentTitle = intent.getStringExtra(PLAYLIST_TITLE)
        val getIntentItemCount = intent.getStringExtra(VIDEO_ITEM_COUNT)/*
        `override fun initLiveData() { ... }`: Эта функция инициализирует наблюдателей для живых данных (LiveData).
        Она также обрабатывает результаты запросов к данным, изменяя интерфейс пользователя в зависимости от статуса загрузки данных.
         */
        viewModel.getPlayListItem(getIntentId!!).observe(this@DetailActivity) { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(this@DetailActivity, "Success Status", Toast.LENGTH_SHORT).show()
                    adapter.setList(response.data?.items as List<PlayListItemModel.Item>?)
                    viewModel.loading.value = false
                    binding.tvTitle.text = getIntentTitle
                    binding.tvDesc.text = getIntentDesc
                    binding.tvNumberOfSeries.text = "$getIntentItemCount video series"
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this@DetailActivity, "Error Status", Toast.LENGTH_SHORT).show()
                    viewModel.loading.value = false
                }

                Resource.Status.LOADING -> {
                    Toast.makeText(this@DetailActivity, "Loading Status", Toast.LENGTH_SHORT).show()
                    viewModel.loading.value = true
                }
            }
            viewModel.loading.observe(this@DetailActivity) { status ->
                if (status) binding.progressBar.visibility = View.VISIBLE
                else binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun initView() {
        super.initView()
        binding.includeRecyclerView.recyclerView.adapter = adapter
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    //`override fun initView() { ... }`: Здесь инициализируются элементы пользовательского интерфейса, такие как адаптер для списка и панель инструментов.
    override fun initListener() {
        super.initListener()
        binding.tvBack.setOnClickListener {
            finish()
        }
    }

    //`override fun initListener() { ... }`: В этой функции устанавливаются слушатели событий, такие как нажатие на кнопку "назад".
    override fun checkInternetConnection() {
        super.checkInternetConnection()
        ConnectionLiveData(application).observe(this@DetailActivity) { isConnection ->
            if (!isConnection) {
                binding.mainContainer.visibility = View.GONE
                binding.noConnection.visibility = View.VISIBLE
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
    }/*
    `override fun checkInternetConnection() { ... }`: Эта функция проверяет доступ к интернету с помощью `ConnectionLiveData`.
    В зависимости от наличия интернет-соединения, она скрывает или показывает соответствующие элементы пользовательского интерфейса.
     */
}/*
Обратите внимание, что код также включает в себя некоторую логику работы с данными и обработку состояний (успех, ошибка, загрузка), но я не могу анализировать этот аспект без знания других частей вашего проекта.
 */