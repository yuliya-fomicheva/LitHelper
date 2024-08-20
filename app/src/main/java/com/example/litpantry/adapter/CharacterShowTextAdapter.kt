package com.example.litpantry.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.litpantry.R
import com.example.litpantry.database.entity.BookCharacter
import com.example.litpantry.databinding.ItemCharacterAllInfLayoutBinding


class CharacterShowTextAdapter (): RecyclerView.Adapter<CharacterShowTextAdapter.CharacterShowTextViewHolder>() {
    private var listCharacter= emptyList<BookCharacter>()

    class CharacterShowTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCharacterAllInfLayoutBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(character: BookCharacter) {
            binding.apply {
                binding.TextViewCharacterName.text = character.characterName
                binding.TextViewBiography.text = character.biography
                binding.TextViewPortret.text = character.portrait

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterShowTextViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character_all_inf_layout, parent, false)
        return CharacterShowTextViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterShowTextViewHolder, position: Int) {
        holder.bind(listCharacter[position])
    }

    override fun getItemCount(): Int {
        return listCharacter.size
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<BookCharacter>) {
        listCharacter = list
        notifyDataSetChanged() //обновление списка
    }




}