package com.developer.mayank.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.developer.domain.models.CharacterEntityItem
import com.developer.mayank.databinding.CharacterListItemBinding
import com.developer.mayank.ui.base.BaseAdapter
import javax.inject.Inject

class CharacterAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseAdapter<CharacterEntityItem>() {

    private val diffCallback = object : DiffUtil.ItemCallback<CharacterEntityItem>() {
        override fun areItemsTheSame(oldItem: CharacterEntityItem, newItem: CharacterEntityItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterEntityItem, newItem: CharacterEntityItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    inner class CharacterViewHolder(private val binding: CharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<CharacterEntityItem> {
        @SuppressLint("SetTextI18n")
        override fun bind(item: CharacterEntityItem) {
            binding.apply {
                textViewCharacterName.text = item.name
                textViewEpisode.text = "First Episode: ${item.firstEpisode}"
                textViewVoicedBy.text = item.voicedBy

                glide.load(item.image).into(imageViewCharacter)
                root.setOnClickListener {
                    onItemClickListener?.let { itemClick ->
                        itemClick(item)
                    }
                }
            }
        }
    }
}