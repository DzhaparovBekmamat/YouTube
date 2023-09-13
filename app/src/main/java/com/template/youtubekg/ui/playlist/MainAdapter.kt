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
                LayoutInflater.from(parent.context), parent, false
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
}/*
Этот код представляет собой адаптер (`MainAdapter`) для RecyclerView в Android, который используется для отображения списка плейлистов в `MainActivity`. Разберем его по частям:

1. `package com.template.youtubekg.ui.playlist`: Эта строка указывает на пакет, к которому принадлежит этот файл. В данном случае, это адаптер для списка плейлистов.

2. `import`: Эти строки используются для подключения различных классов и модулей, необходимых для работы этого адаптера.

3. `class MainAdapter(private val onClick: (PlayListModel.Item) -> Unit) : Adapter<MainAdapter.PlaylistViewHolder>()`: Это объявление класса адаптера. Он наследуется от `RecyclerView.Adapter` и параметризован типом `PlaylistViewHolder`. Также в конструктор передается лямбда `onClick`, которая будет вызываться при нажатии на элемент списка.

4. `private var list = mutableListOf<PlayListModel.Item>()`: Эта переменная для хранения списка элементов, представляющих плейлисты.

5. `fun setList(model: List<PlayListModel.Item>?)`: Эта функция устанавливает новый список элементов в адаптер.

6. `override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder`: Эта функция создает новый `PlaylistViewHolder`, который будет использоваться для отображения элементов списка.

7. `override fun getItemCount(): Int`: Эта функция возвращает количество элементов в списке.

8. `override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int)`: Эта функция связывает данные с элементами пользовательского интерфейса внутри `PlaylistViewHolder`.

9. `inner class PlaylistViewHolder(private val binding: ItemPlaylistBinding) : ViewHolder(binding.root)`: Это объявление класса `PlaylistViewHolder`, который представляет собой элемент пользовательского интерфейса в RecyclerView.

10. `fun onBind(playlistsModelItem: PlayListModel.Item)`: Эта функция заполняет элемент пользовательского интерфейса данными из `PlayListModel.Item`.

11. `with(binding) { ... }`: Здесь происходит привязка данных к элементам пользовательского интерфейса с использованием привязки данных (`binding`).

12. `itemView.setOnClickListener { onClick.invoke(playlistsModelItem) }`: Здесь устанавливается слушатель клика на элемент списка. При клике, вызывается лямбда `onClick`, передавая в нее объект `PlayListModel.Item`.

Этот адаптер предназначен для работы с RecyclerView в `MainActivity`. Он связывает данные о плейлистах с элементами пользовательского интерфейса и реагирует на клики на элементах списка.
 */