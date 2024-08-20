package com.example.litpantry.screen.start

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.litpantry.adapter.APP
import com.example.litpantry.R
import com.example.litpantry.database.entity.Book
import com.example.litpantry.databinding.FragmentStartBinding
import com.example.litpantry.adapter.ActionListenerBook
import com.example.litpantry.adapter.BookAdapter
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class StartFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentStartBinding
    lateinit var recyclerView: RecyclerView
    lateinit var bookAdapter: BookAdapter
    lateinit var viewModel: StartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar1)


        return binding.root
    }


        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.main_menu, menu)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar1)

        toolbar.setupWithNavController(navController, appBarConfig)
        init()
    }


     private fun init() {
        viewModel = ViewModelProvider(this).get(StartViewModel::class.java)
         recyclerView = binding.rvBooks
         bookAdapter = BookAdapter(object : ActionListenerBook {
             override fun onBookDelete(book: Book) {
                 val builder = AlertDialog.Builder(APP)
                 builder.setTitle("Удаление книги")
                 builder.setMessage("Вы действительно хотите удалить книгу?")

                 builder.setPositiveButton("Да") { _: DialogInterface, _: Int ->
                     viewModel.deleteBook(book)
                 }
                 builder.setNegativeButton("Отмена") { _: DialogInterface, _: Int -> }
                 builder.show()
             }

             override fun onBookDetails(book: Book) {
                 TODO("Not yet implemented")
             }


         })
         recyclerView.adapter = bookAdapter
         viewModel.getAllBooks().observe(viewLifecycleOwner) { listBook ->
             bookAdapter.setList(listBook.asReversed() ) //все новые элементы сверху
         }
         binding.btnAddBook.setOnClickListener {
             APP.navController.navigate(R.id.action_startFragment_to_addFragment)

         }

         (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider,
             SearchView.OnQueryTextListener {
             override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                 menuInflater.inflate(R.menu.main_menu, menu)

                 val search = menu?.findItem(R.id.menu_search)
                 val searchView = search?.actionView as? SearchView

                 searchView?.isSubmitButtonEnabled = true
                 searchView?.setOnQueryTextListener(this)
                 searchView?.queryHint = "Поиск"



             }

             override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                 return when (menuItem.itemId) {
                     R.id.item_calendar -> {
                         APP.navController.navigate(R.id.action_startFragment_to_calendarFragment)
                         true
                     }
                     else -> false
                 }
             }

             override fun onQueryTextSubmit(query: String?): Boolean {
                 return true
             }

             override fun onQueryTextChange(query: String?): Boolean {
                 if(query != null){
                     searchDatabase(query)
                 }
                 return true
             }
         }, viewLifecycleOwner)

     }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchBook(searchQuery).observe(viewLifecycleOwner) { listBook ->
            bookAdapter.setList(listBook.asReversed()) //все новые элементы сверху
        }

    }



  companion object {
      fun clickBook(book: Book) {
          val bundle = Bundle()
          bundle.putSerializable("book", book)
          APP.navController.navigate(R.id.action_startFragment_to_detailFragment, bundle)
      }

    }


}
