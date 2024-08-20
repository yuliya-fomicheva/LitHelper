package com.example.litpantry.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.litpantry.R
import com.example.litpantry.database.entity.OtherInfBook
import com.example.litpantry.database.entity.OtherInfCharacter
import com.example.litpantry.database.entity.Quote
import com.example.litpantry.databinding.ItemOtherInfBinding


interface ActionListener {

    fun onOtherInfDelete(otherInf: OtherInfBook)


    fun onOtherInfDetails(otherInf: OtherInfBook)


}
class OtherInfAdapter(private val actionListener: ActionListener)
    : RecyclerView.Adapter<OtherInfAdapter.OtherInfViewHolder>() {


    private var listOtherInf = emptyList<OtherInfBook>()
    class OtherInfViewHolder(view: View): RecyclerView.ViewHolder(view) {
         val binding = ItemOtherInfBinding.bind(view)
        fun bind(otherInfBook: OtherInfBook) {
            binding.apply {
                itemInfText.text = otherInfBook.information
                itemView.tag = otherInfBook
                moreImageViewButton.tag = otherInfBook

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherInfViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_other_inf, parent, false)
        /*val inflater = LayoutInflater.from(parent.context)
        val binding= ItemOtherInfBinding.inflate(inflater,parent, false )
        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener { showPopupMenu(view)}*/
        return OtherInfViewHolder(view)
    }

    override fun onBindViewHolder(holder: OtherInfViewHolder, position: Int) {
        holder.bind(listOtherInf[position])
       /* val otherInf: OtherInfBook = listOtherInf[position]
        with(holder.binding) {
            holder.itemView.tag = otherInf
            moreImageViewButton.tag = otherInf
        */
         val otherInf: OtherInfBook = listOtherInf[position]

        holder.itemView.setOnClickListener {
            actionListener.onOtherInfDetails(otherInf)
        }
        holder.binding.moreImageViewButton.setOnClickListener{v ->
            showPopupMenu(v)

        }
    }


    override fun getItemCount(): Int {
        return listOtherInf.size
    }




    @SuppressLint("NotifyDataSetChanged")
    fun setList (list : List <OtherInfBook>) {
        listOtherInf = list
        notifyDataSetChanged() //обновление списка
    }


    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val otherInf = view.tag as OtherInfBook

        popupMenu.menu.add(0, ID_UPDATE, Menu.NONE, context.getString(R.string.update))
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_UPDATE -> {
                    actionListener.onOtherInfDetails(otherInf)
                }
                ID_REMOVE -> {
                    actionListener.onOtherInfDelete(otherInf)
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

interface ActionListenerCharOther {


    fun onOtherInfDelete(otherInf: OtherInfCharacter)


    fun onOtherInfDetails(otherInf: OtherInfCharacter)

}

class OtherInfCharAdapter (private val actionListener: ActionListenerCharOther): RecyclerView.Adapter<OtherInfCharAdapter.OtherInfViewHolder>() {

    private var listOtherInfCharacter = emptyList<OtherInfCharacter>()
    class OtherInfViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemOtherInfBinding.bind(view)

        fun bind(otherInfCharacter: OtherInfCharacter) {
            binding.apply {
                itemInfText.text = otherInfCharacter.text
                itemView.tag = otherInfCharacter
                moreImageViewButton.tag = otherInfCharacter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherInfViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_other_inf, parent, false)
        return OtherInfViewHolder(view)
    }

    override fun onBindViewHolder(holder: OtherInfViewHolder, position: Int) {
        holder.bind(listOtherInfCharacter[position])
        holder.itemView.setOnClickListener {
        }
        holder.binding.moreImageViewButton.setOnClickListener{ v->
            showPopupMenu(v)
        }
    }

    override fun getItemCount(): Int {
        return listOtherInfCharacter.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList (list : List <OtherInfCharacter>) {
        listOtherInfCharacter = list
        notifyDataSetChanged() //обновление списка
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val otherInf = view.tag as OtherInfCharacter

        popupMenu.menu.add(0, ID_UPDATE, Menu.NONE, context.getString(R.string.update))
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_UPDATE -> {
                    actionListener.onOtherInfDetails(otherInf)
                }
                ID_REMOVE -> {
                    actionListener.onOtherInfDelete(otherInf)
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

interface ActionListenerQuote {

    fun onOtherInfDelete(quote: Quote)

    fun onOtherInfDetails(quote: Quote)

}

class QuoteAdapter(private val actionListener: ActionListenerQuote): RecyclerView.Adapter<QuoteAdapter.OtherInfViewHolder>() {

    private var listQuote = emptyList<Quote>()
    class OtherInfViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemOtherInfBinding.bind(view)
        fun bind(quote: Quote) {
            binding.apply {
                itemInfText.text = quote.text
                itemView.tag = quote
                moreImageViewButton.tag = quote
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherInfViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_other_inf, parent, false)
        return OtherInfViewHolder(view)
    }

    override fun onBindViewHolder(holder: OtherInfViewHolder, position: Int) {
        holder.bind(listQuote[position])
        holder.itemView.setOnClickListener {

        }

        holder.binding.moreImageViewButton.setOnClickListener{
            v -> showPopupMenu(v)
        }
    }

    override fun getItemCount(): Int {
        return listQuote.size
    }




    @SuppressLint("NotifyDataSetChanged")
    fun setList (list : List <Quote>) {
        listQuote = list
        notifyDataSetChanged() //обновление списка
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val quote = view.tag as Quote

        popupMenu.menu.add(0, ID_UPDATE, Menu.NONE, context.getString(R.string.update))
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_UPDATE -> {
                    actionListener.onOtherInfDetails(quote)
                }
                ID_REMOVE -> {
                    actionListener.onOtherInfDelete(quote)
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


