package com.example.litpantry.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.litpantry.R
import com.example.litpantry.database.entity.Book
import com.example.litpantry.database.entity.OtherInfBook
import com.example.litpantry.databinding.ItemBookLayoutBinding
import com.example.litpantry.screen.details.DetailFragment
import com.example.litpantry.screen.start.StartFragment


interface ActionListenerBook {

    fun onBookDelete(book: Book)


    fun onBookDetails(book: Book)


}

class BookAdapter(private val actionListener: ActionListenerBook): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private var listBook = emptyList<Book>()
    class BookViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemBookLayoutBinding.bind(view)
        @SuppressLint("SetTextI18n")
        fun bind(book : Book) {
            binding.apply {
                itemBookName.text = book.bookName
                itemBookAuthor.text = book.bookAuthor
             //   itemBookYearTxt.text = book.year
                itemView.tag = book
                moreImageViewButton.tag = book

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_layout, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(listBook[position])
        holder.itemView.setOnClickListener {

        }

        holder.binding.moreImageViewButton.setOnClickListener{ v ->
            showPopupMenu(v)
        }
    }

    override fun getItemCount(): Int {
        return listBook.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList (list : List <Book>) {
        listBook = list
        notifyDataSetChanged() //обновление списка
    }

    override fun onViewAttachedToWindow(holder: BookViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            StartFragment.clickBook(listBook[holder.adapterPosition])
        }

    }

    override fun onViewDetachedFromWindow(holder: BookViewHolder) {
        holder.itemView.setOnClickListener(null)
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val book = view.tag as Book

        popupMenu.menu.add(0, ID_UPDATE, Menu.NONE, context.getString(R.string.update))
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_UPDATE -> {
                    actionListener.onBookDetails(book)
                }
                ID_REMOVE -> {
                    actionListener.onBookDelete(book)
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