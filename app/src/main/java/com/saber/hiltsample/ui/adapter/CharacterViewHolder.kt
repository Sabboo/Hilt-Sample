package com.saber.hiltsample.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.saber.hiltsample.data.MarvelCharacter
import com.bumptech.glide.Glide
import com.saber.hiltsample.databinding.CharacterItemBinding


class CharacterViewHolder(private val characterItemBinding: CharacterItemBinding) :
    RecyclerView.ViewHolder(characterItemBinding.root) {

    fun bind(character: MarvelCharacter?) {
        characterItemBinding.heroName.text = character?.name
        Glide.with(characterItemBinding.heroImageView.context)
            .load(character?.thumbnail?.getFullPath()).into(characterItemBinding.heroImageView)
    }

}