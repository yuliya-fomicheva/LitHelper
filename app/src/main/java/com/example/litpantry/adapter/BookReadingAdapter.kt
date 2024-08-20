package com.example.litpantry.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.compose.ui.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.litpantry.R
import com.example.litpantry.database.entity.Book
import com.example.litpantry.database.entity.ReadingBook
import com.example.litpantry.databinding.ItemBookLayoutBinding
import com.example.litpantry.databinding.ItemIsReadingBookLayoutBinding
import com.example.litpantry.databinding.ItemReadingBookLayoutBinding
import com.example.litpantry.screen.start.StartFragment
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


interface ActionListenerReadingBook {

    fun onCheckRead(readingBook: ReadingBook)

    fun onBookDetails(readingBook: ReadingBook)

    fun onBookDelete(readingBook: ReadingBook)

}
class BookReadingAdapter(private val actionListener: ActionListenerReadingBook): RecyclerView.Adapter<BookReadingAdapter.BookReadingViewHolder>() {
    private var listBook = emptyList<ReadingBook>()
    class BookReadingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemReadingBookLayoutBinding.bind(view)
        @SuppressLint("SetTextI18n")
        fun bind(readingBook: ReadingBook) {
            if(!readingBook.isRead) {
                binding.apply {
                    itemBookName.text = readingBook.bookName
                    var between: Int =  betweenDate(readingBook.calendarDate.toString())
                    if(between >= 0) {
                        itemBookAuthor.text =
                            "Осталось дней: " + betweenDate(readingBook.calendarDate.toString()).toString()
                    } else {
                        itemBookAuthor.text = "Срок истек"
                        itemBookAuthor.setTextColor(Color.RED)
                    }
                    itemView.tag = readingBook
                    moreImageViewButton.tag = readingBook

                }
            }
        }


        private fun betweenDate(time: String): Int {
            // Declaring two date strings
            var c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH) + 1
            val day = c.get(Calendar.DAY_OF_MONTH)

            val mDate2 = "$day-$month-$year"
            // Creating a date format
            val mDateFormat = SimpleDateFormat("dd-MM-yyyy")

            // Converting the dates
            // from string to date format
            val mDate11 = mDateFormat.parse(time)
            val mDate22 = mDateFormat.parse(mDate2)

            // Finding the absolute difference between
            // the two dates.time (in milli seconds)
            val mDifference = (mDate11.time - mDate22.time)

            // Converting milli seconds to dates
            return (mDifference / (24 * 60 * 60 * 1000)).toInt()

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookReadingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reading_book_layout, parent, false)
        return BookReadingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookReadingViewHolder, position: Int) {
        holder.bind(listBook[position])
        holder.itemView.setOnClickListener {

        }
        holder.binding.moreImageViewButton.setOnClickListener {
            actionListener.onCheckRead(holder.binding.moreImageViewButton.tag as ReadingBook)
        }

        holder.itemView.setOnLongClickListener {v  ->
            showPopupMenu(v)
        }
    }

    override fun getItemCount(): Int {
        return listBook.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList (list : List <ReadingBook>) {
        listBook = list
        notifyDataSetChanged() //обновление списка
    }

    private fun showPopupMenu(view: View): Boolean {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val book = view.tag as ReadingBook

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

        return true
    }

    companion object {
        private const val ID_UPDATE = 1
        private const val ID_REMOVE = 2
    }

}



class BookIsReadingAdapter(private val actionListener: ActionListenerReadingBook): RecyclerView.Adapter<BookIsReadingAdapter.BookIsReadingViewHolder>() {
    private var listBook = emptyList<ReadingBook>()
    class BookIsReadingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemIsReadingBookLayoutBinding.bind(view)
        @SuppressLint("SetTextI18n")
        fun bind(readingBook: ReadingBook) {
            if(readingBook.isRead) {
                binding.apply {
                    itemBookName.text = readingBook.bookName
                    itemView.tag = readingBook
                    moreImageViewButton.tag = readingBook
                }
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookIsReadingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_is_reading_book_layout, parent, false)
        return BookIsReadingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookIsReadingViewHolder, position: Int) {
        holder.bind(listBook[position])
        holder.itemView.setOnClickListener {

        }
        holder.binding.moreImageViewButton.setOnClickListener {
            actionListener.onCheckRead(holder.binding.moreImageViewButton.tag as ReadingBook)
        }
    }

    override fun getItemCount(): Int {
        return listBook.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList (list : List <ReadingBook>) {
        listBook = list
        notifyDataSetChanged() //обновление списка
    }

}