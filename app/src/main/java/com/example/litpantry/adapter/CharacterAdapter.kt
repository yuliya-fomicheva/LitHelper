package com.example.litpantry.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.litpantry.R
import com.example.litpantry.database.entity.BookCharacter
import com.example.litpantry.database.entity.BookCharacterCrossRef
import com.example.litpantry.databinding.ItemCharacterLayoutBinding
import com.example.litpantry.screen.character.CharacterFragment
import com.example.litpantry.screen.details.DetailFragment
import com.example.litpantry.screen.start.StartFragment

interface ActionListenerChar {

    fun onCharDelete(character: BookCharacter)


    fun onCharDetails(character: BookCharacter)


}

class CharacterAdapter(private var actionListener: ActionListenerChar): RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    private var listCharacter= emptyList<BookCharacter>()

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCharacterLayoutBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(character: BookCharacter) {
            binding.apply {
                itemCharName.text = character.characterName
                itemView.tag = character
                moreImageViewButton.tag = character
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character_layout, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(listCharacter[position])
        holder.itemView.setOnClickListener {

        }
        holder.binding.moreImageViewButton.setOnClickListener { v ->
            showPopupMenu(v)
        }
    }

    override fun getItemCount(): Int {
        return listCharacter.size
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<BookCharacter>) {
        listCharacter = list
        notifyDataSetChanged() //обновление списка
    }

    override fun onViewAttachedToWindow(holder: CharacterViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            DetailFragment.clickChar(listCharacter[holder.adapterPosition])
        }

    }

    override fun onViewDetachedFromWindow(holder: CharacterViewHolder) {
        holder.itemView.setOnClickListener(null)
    }


    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val character = view.tag as BookCharacter

        popupMenu.menu.add(0, ID_UPDATE, Menu.NONE, context.getString(R.string.update))
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_UPDATE -> {
                    actionListener.onCharDetails(character)
                }
                ID_REMOVE -> {
                    actionListener.onCharDelete(character)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    companion object {
        private const val ID_UPDATE = 1
        private const val ID_REMOVE = 2
    }
}