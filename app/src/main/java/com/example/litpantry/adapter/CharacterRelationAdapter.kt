package com.example.litpantry.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Relation
import com.example.litpantry.R
import com.example.litpantry.database.entity.Book
import com.example.litpantry.database.entity.BookCharacterCrossRef
import com.example.litpantry.databinding.ItemBookLayoutBinding
import com.example.litpantry.databinding.ItemCharacterRelationLayoutBinding
import com.example.litpantry.screen.start.StartFragment

interface ActionListenerRelationChar {

    fun onRelationDelete(relation: BookCharacterCrossRef)


    fun onRelationDetails(relation: BookCharacterCrossRef)


}

class CharacterRelationAdapter(private var actionListener: ActionListenerRelationChar): RecyclerView.Adapter<CharacterRelationAdapter.CharacterRelationAdapterViewHolder>() {
    private var listCharacterCrossRef = emptyList<BookCharacterCrossRef>()
    class CharacterRelationAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemCharacterRelationLayoutBinding.bind(view)
        @SuppressLint("SetTextI18n")
        fun bind(characterCrossRef: BookCharacterCrossRef) {
            binding.apply {
                itemCharName.text = characterCrossRef.characterName2 + " : "
                itemRelation.text = characterCrossRef.descriptionRelationship
                itemView.tag = characterCrossRef
                moreImageViewButton.tag = characterCrossRef
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterRelationAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character_relation_layout, parent, false)
        return CharacterRelationAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterRelationAdapterViewHolder, position: Int) {
        holder.bind(listCharacterCrossRef[position])
        holder.itemView.setOnClickListener {

        }
        holder.binding.moreImageViewButton.setOnClickListener { v ->
            showPopupMenu(v)
        }

    }

    override fun getItemCount(): Int {
        return listCharacterCrossRef.size
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setList (list : List <BookCharacterCrossRef>) {
        listCharacterCrossRef = list
        notifyDataSetChanged() //обновление списка
    }

  /*  override fun onViewAttachedToWindow(holder: BookViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            StartFragment.clickBook(listBook[holder.adapterPosition])
        }

    }

    override fun onViewDetachedFromWindow(holder: BookViewHolder) {
        holder.itemView.setOnClickListener(null)
    }*/

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val relation = view.tag as BookCharacterCrossRef

        popupMenu.menu.add(0, ID_UPDATE, Menu.NONE, context.getString(R.string.update))
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_UPDATE -> {
                    actionListener.onRelationDetails(relation)
                }
                ID_REMOVE -> {
                    actionListener.onRelationDelete(relation)
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