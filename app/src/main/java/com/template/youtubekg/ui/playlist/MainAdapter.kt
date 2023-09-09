package com.template.youtubekg.ui.playlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.template.youtubekg.data.model.PlayListModel
import com.template.youtubekg.databinding.ItemPlaylistBinding

class MainAdapter(private val onClick: (PlayListModel.Item) -> Unit) :
    Adapter<MainAdapter.PlaylistViewHolder>() {

    private var list = mutableListOf<PlayListModel.Item>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(model: List<PlayListModel.Item>?) {
        list = model as MutableList<PlayListModel.Item>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder =
        PlaylistViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class PlaylistViewHolder(private val binding: ItemPlaylistBinding) :
        ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(playlistsModelItem: PlayListModel.Item) {
            with(binding) {
                Glide.with(ivPlaylist).load(playlistsModelItem.snippet.thumbnails.default.url)
                    .into(ivPlaylist)
                tvTitle.text = playlistsModelItem.snippet.title
                tvNumber.text = "${playlistsModelItem.contentDetails.itemCount} video series"
                itemView.setOnClickListener {
                    onClick.invoke(playlistsModelItem)
                }
            }
        }
    }
}