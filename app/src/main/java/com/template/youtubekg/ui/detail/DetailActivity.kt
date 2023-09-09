package com.template.youtubekg.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.appbar.AppBarLayout
import com.template.youtubekg.R
import com.template.youtubekg.core.base.BaseActivity
import com.template.youtubekg.core.network.Resource
import com.template.youtubekg.data.model.PlayListItemModel
import com.template.youtubekg.ui.play.VideoActivity
import com.template.youtubekg.utils.ConnectionLiveData
import com.template.youtubekg.utils.Constants
import com.template.youtubekg.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("UNCHECKED_CAST")
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override fun inflateViewBinding(): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)

    override val viewModel: DetailViewModel by viewModel()

    private val adapter = DetailAdapter(this::onClick)

    private var isFabVisible = true

    private fun hideFab() {
        val fab = findViewById<AppCompatImageButton>(R.id.appCompatImageButton)
        fab.animate().scaleX(0f).scaleY(0f).setDuration(150).start()
    }

    private fun showFab() {
        val fab = findViewById<AppCompatImageButton>(R.id.appCompatImageButton)
        fab.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appBarLayout = findViewById<AppBarLayout>(R.id.appbar)
        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset < 0) {
                if (isFabVisible) {
                    isFabVisible = false
                    hideFab()
                }
            } else {
                if (!isFabVisible) {
                    isFabVisible = true
                    showFab()
                }
            }
        }
    }

    private fun onClick(item: PlayListItemModel.Item) {
        val intent = Intent(this, VideoActivity::class.java).apply {
            putExtra(Constants.VIDEO_ITEM_ID, item.id)
            putExtra(Constants.VIDEO_ID, item.contentDetails?.videoId)
            putExtra(Constants.VIDEO_TITLE, item.snippet?.title)
            putExtra(Constants.VIDEO_DESCRIPTION, item.snippet?.description)
        }
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    override fun initLiveData() {
        super.initLiveData()

        val getIntentId = intent.getStringExtra(Constants.PLAYLIST_ID)
        val getIntentDesc = intent.getStringExtra(Constants.PLAYLIST_DESCRIPTION)
        val getIntentTitle = intent.getStringExtra(Constants.PLAYLIST_TITLE)
        val getIntentItemCount = intent.getStringExtra(Constants.VIDEO_ITEM_COUNT)

        viewModel.getPlaylistItems(getIntentId!!).observe(this) { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(this, "Success Status", Toast.LENGTH_SHORT).show()
                    adapter.setList(response.data?.items as List<PlayListItemModel.Item>?)
                    viewModel.loading.value = false
                    binding.tvTitle.text = getIntentTitle
                    binding.tvDesc.text = getIntentDesc
                    binding.tvNumberOfSeries.text = "$getIntentItemCount video series"
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

    override fun initView() {
        super.initView()
        binding.includeRecyclerView.recyclerView.adapter = adapter
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun initListener() {
        super.initListener()
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    override fun checkInternetConnection() {
        super.checkInternetConnection()
        ConnectionLiveData(application).observe(this) { isConnection ->
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
    }
}
