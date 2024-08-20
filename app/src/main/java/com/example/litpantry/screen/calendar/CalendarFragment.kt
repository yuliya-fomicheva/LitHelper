package com.example.litpantry.screen.calendar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.example.litpantry.R
import com.example.litpantry.adapter.ActionListenerReadingBook
import com.example.litpantry.adapter.BookIsReadingAdapter
import com.example.litpantry.adapter.BookReadingAdapter
import com.example.litpantry.database.entity.ReadingBook
import com.example.litpantry.databinding.FragmentCalendarBinding
import getDot
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment(), OnDayClickListener, OnSelectDateListener {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var viewModel: CalendarViewModel
    private lateinit var recyclerView: RecyclerView

    private lateinit var bookAdapter : BookReadingAdapter
    private lateinit var isBookIsReadingAdapter: BookIsReadingAdapter

    private val notes = mutableMapOf<EventDay, String>()
    val events: MutableList<EventDay> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar6)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar6)

        toolbar.setupWithNavController(navController, appBarConfig)
        init()

    }

    fun init() {

        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]
        binding.calendarView.setOnDayClickListener(this)

        viewModel.getAllBooks().observe(viewLifecycleOwner) { listBook ->
            for(i in listBook.indices) {
                if(!listBook[i].calendarDate?.isEmpty()!!)  {
                    events.add(EventDay(convertStringToCalendar(listBook[i].calendarDate)!!, activity?.applicationContext!!.getDot()))
                }
                binding.calendarView.setEvents(events)
            }
        }

        recyclerView = binding.rvBooksReading
        bookAdapter = BookReadingAdapter(object : ActionListenerReadingBook {
            override fun onCheckRead(readingBook: ReadingBook) {
                readingBook.isRead = true
                viewModel.updateReadingBook(readingBook)
            }

            override fun onBookDetails(readingBook: ReadingBook) {
                TODO("Not yet implemented")
            }

            override fun onBookDelete(readingBook: ReadingBook) {
                viewModel.deleteReadingBook(readingBook)
            }
        })
        recyclerView.adapter = bookAdapter
        viewModel.getIsReadingBook(false).observe(viewLifecycleOwner) { listBook ->
            bookAdapter.setList(listBook.asReversed() ) //все новые элементы сверху
        }

        recyclerView = binding.rvBooksIsReading
        isBookIsReadingAdapter = BookIsReadingAdapter(object : ActionListenerReadingBook {
            override fun onCheckRead(readingBook: ReadingBook) {
                readingBook.isRead = false
                viewModel.updateReadingBook(readingBook)
            }

            override fun onBookDetails(readingBook: ReadingBook) {
                TODO("Not yet implemented")
            }

            override fun onBookDelete(readingBook: ReadingBook) {
                viewModel.deleteReadingBook(readingBook)
            }
        })
        recyclerView.adapter = isBookIsReadingAdapter
        viewModel.getIsReadingBook(true).observe(viewLifecycleOwner) { listBook ->
            isBookIsReadingAdapter.setList(listBook.asReversed() ) //все новые элементы сверху
        }


    }

    private fun convertStringToCalendar(
        time: String?
    ): Calendar? {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        var date: Date? = null
        return try {
            date = dateFormat.parse(time)
            calendar.time = date
            calendar
        } catch (e: ParseException) {
            null
        }
    }

    private fun openDatePicker() {
        DatePickerBuilder(requireContext(), this)
            .build()
            .show()
    }

    //клик по дню
    override fun onDayClick(eventDay: EventDay) {
      /*  val intent = Intent(this, NotePreviewActivity::class.java)
        intent.putExtra(CALENDAR_EXTRA, eventDay.calendar)
        intent.putExtra(NOTE_EXTRA, notes[eventDay])
        startActivity(intent)
        for (i in events.indices) {
            if(eventDay.calendar.equals(events[i].calendar)) {
                binding.textView2.text = viewModel.getBook(2).bookName.toString()
            }
        } */

    }



    override fun onSelect(calendar: List<Calendar>) {
        //Toast.makeText(context, calendar.first().toString(), Toast.LENGTH_LONG).show()
       /* val intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra(CALENDAR_EXTRA, calendar.first())
        startActivityForResult(intent, RESULT_CODE)*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == RESULT_CODE) {
            val note = data?.getStringExtra(NOTE_EXTRA) ?: return
            val calendar = data.getSerializableExtra(CALENDAR_EXTRA) as Calendar
            val eventDay = EventDay(calendar, activity?.applicationContext!!.getDot())
            notes[eventDay] = note
            binding.calendarView.setEvents(notes.keys.toList())
        }
    }

    companion object {
        const val CALENDAR_EXTRA = "calendar"
        const val NOTE_EXTRA = "note"
        const val RESULT_CODE = 8
    }
}