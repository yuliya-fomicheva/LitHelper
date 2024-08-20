package com.example.litpantry.screen.add

import android.app.DatePickerDialog
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
import com.example.litpantry.adapter.APP
import com.example.litpantry.R
import com.example.litpantry.database.entity.Book
import com.example.litpantry.database.entity.ReadingBook
import com.example.litpantry.databinding.FragmentAddBinding
import java.util.*


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var viewModel: AddViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar5)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar5)

        toolbar.setupWithNavController(navController, appBarConfig)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[AddViewModel::class.java]

        binding.addDateToRead.setOnClickListener {
            showCalendar()
        }

        binding.btnSaveBook.setOnClickListener {
            val nameBook = binding.addTitleBook.text.toString()
            val author = binding.addAuthorBook.text.toString()
            val year = binding.addYearBook.text.toString()
            val calendarDate = binding.addDateToRead.text.toString()

            if(calendarDate.isNullOrEmpty()) {

                viewModel.insertBook(
                    Book(
                        bookName = nameBook,
                        bookAuthor = author,
                        year = year,
                        calendarDate = calendarDate
                    )
                )
            } else {
                viewModel.insertBook(
                    Book(
                        bookName = nameBook,
                        bookAuthor = author,
                        year = year,
                        calendarDate = calendarDate
                    )
                )
                viewModel.insertReadingBook(
                    ReadingBook(bookName = nameBook,
                        calendarDate = calendarDate, isRead = false)
                )
            }

            APP.navController.navigate(R.id.action_addFragment_to_startFragment)
        }

    }

    private fun showCalendar () {
      /*  val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.Events.TITLE, binding.addTitleBook.text.toString())
            .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)*/

      /*  startActivity(intent)*/
        // on below line we are getting
        // the instance of our calendar.
       val c = Calendar.getInstance()


        // on below line we are getting
        // our day, month and year.
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // on below line we are creating a
        // variable for date picker dialog.
        val datePickerDialog = DatePickerDialog(
            // on below line we are passing context.
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                // on below line we are setting
                // date to our edit text.
                val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                binding.addDateToRead.setText(dat)
            },
            // on below line we are passing year, month
            // and day for the selected date in our date picker.
            year,
            month,
            day
        )
        // at last we are calling show
        // to display our date picker dialog.
        datePickerDialog.show()

    }


}


