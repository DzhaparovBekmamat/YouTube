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
import com.template.youtubekg.utils.Constants.PLAYLIST_ID
import com.template.youtubekg.utils.Constants.PLAYLIST_TITLE
import com.template.youtubekg.utils.Constants.PLAYLIST_DESCRIPTION
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
                    Toast.makeText(this, "success status", Toast.LENGTH_SHORT).show()
                    adapter.setList(response.data?.items)
                    viewModel.loading.value = false
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, "error status", Toast.LENGTH_SHORT).show()
                    viewModel.loading.value = false

                }

                Resource.Status.LOADING -> {
                    Toast.makeText(this, "loading status", Toast.LENGTH_SHORT).show()
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
}