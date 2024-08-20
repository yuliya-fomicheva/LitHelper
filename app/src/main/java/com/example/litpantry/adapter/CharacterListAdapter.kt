package com.example.litpantry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.litpantry.database.entity.BookCharacter
import com.example.litpantry.databinding.ItemCharacterLayoutBinding

class CharacterListAdapter (//private var characters: List<BookCharacter>
    //private val characters: LiveData<BookCharacter>
): BaseAdapter(), View.OnClickListener{

    private var characters= emptyList<BookCharacter>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<BookCharacter>) {
        characters = list
        notifyDataSetChanged() //обновление списка
    }

    fun getList(): List<BookCharacter> {
        return characters
    }
    override fun getCount(): Int {
        return characters.size
    }




    override fun getItem(position: Int): BookCharacter {
        return characters[position]
    }

    override fun getItemId(position: Int): Long {
        return characters[position].characterId.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = convertView?.tag as ItemCharacterLayoutBinding? ?: createBinding(parent.context)

        val character = getItem(position)

        binding.itemCharName.text =character.characterName
        binding.moreImageViewButton.visibility = View.INVISIBLE
        binding.view3.visibility = View.INVISIBLE

        return binding.root

    }

    private fun createBinding(context: Context): ItemCharacterLayoutBinding {
        val binding = ItemCharacterLayoutBinding.inflate(LayoutInflater.from(context))
        binding.root.tag = binding
        return binding
    }

    override fun onClick(v: View?) {
        val character = v?.tag as BookCharacter
    }


    /* override fun onClick(v: View?) {
         TODO("Not yet implemented")
     }*/


}