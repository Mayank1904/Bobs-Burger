package com.developer.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.developer.presentation.databinding.CharacterListItemBinding
import com.developer.presentation.models.CharacterModel
import com.developer.presentation.ui.base.BaseAdapter
import javax.inject.Inject

class CharacterAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseAdapter<CharacterModel>() {

    private val diffCallback = object : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
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
        RecyclerView.ViewHolder(binding.root), Binder<CharacterModel> {
        @SuppressLint("SetTextI18n")
        override fun bind(item: CharacterModel) {
            binding.apply {
                with(item) {
                    textViewCharacterName.text = name
                    textViewEpisode.text = "First Episode: $firstEpisode"
                    textViewVoicedBy.text = voicedBy

                    glide.load(image).into(imageViewCharacter)
                    root.setOnClickListener {
                        onItemClickListener?.let { itemClick ->
                            itemClick(this)
                        }
                    }
                }

            }
        }
    }
}