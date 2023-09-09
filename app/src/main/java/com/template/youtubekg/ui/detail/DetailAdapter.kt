package com.template.youtubekg.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.template.youtubekg.data.model.PlayListItemModel
import com.template.youtubekg.databinding.ItemDetailBinding

class DetailAdapter(private val onClick: (PlayListItemModel.Item) -> Unit) :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    private var list = mutableListOf<PlayListItemModel.Item>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(model: List<PlayListItemModel.Item>?) {
        list = model as MutableList<PlayListItemModel.Item>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder =
        DetailViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class DetailViewHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(playlistsModelItem: PlayListItemModel.Item) {
            with(binding) {
                tvTitle.text = playlistsModelItem.snippet?.title
                tvDuration.text = playlistsModelItem.snippet?.publishedAt
                Glide.with(ivDetail).load(playlistsModelItem.snippet?.thumbnails?.default?.url)
                    .into(ivDetail)
                itemView.setOnClickListener {
                    onClick.invoke(playlistsModelItem)
                }
            }
        }
    }
}