package com.example.litpantry.screen.details

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.litpantry.adapter.APP
import com.example.litpantry.screen.character.AddCharacterDialogFragment
import com.example.litpantry.R
import com.example.litpantry.adapter.ActionListener
import com.example.litpantry.adapter.ActionListenerChar
import com.example.litpantry.adapter.CharacterAdapter
import com.example.litpantry.adapter.OtherInfAdapter
import com.example.litpantry.database.entity.*
import com.example.litpantry.databinding.FragmentDetailBinding

import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

@Suppress("DEPRECATION")
class DetailFragment : Fragment() {

    private lateinit var recyclerViewOther: RecyclerView
    private lateinit var recyclerViewChar: RecyclerView
    private lateinit var otherInfAdapter: OtherInfAdapter
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private lateinit var currentBook: Book
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        currentBook = arguments?.getSerializable("book") as Book

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)


        toolbar.setupWithNavController(navController, appBarConfig)


        init()
    }

  /*  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
    }*/


    private fun init()  {
        viewModel= ViewModelProvider(this)[DetailViewModel::class.java]

        recyclerViewOther = binding.rvOtherInf
        otherInfAdapter = OtherInfAdapter(object : ActionListener {
            override fun onOtherInfDelete(otherInf: OtherInfBook) {
                viewModel.deleteOtherInfBook(otherInf)
            }

            override fun onOtherInfDetails(otherInf: OtherInfBook) {
                Toast.makeText(context,otherInf.information, Toast.LENGTH_LONG).show()
            }


        })
        recyclerViewOther.adapter = otherInfAdapter


        val list: LiveData<BookAndInf> = viewModel.getAllOtherInf(currentBook.bookId)
        list.observe(viewLifecycleOwner) { listOtherInf ->
            otherInfAdapter.setList(listOtherInf.otherInforms.asReversed() ) //все новые элементы сверху
        }
      /*  binding.aboutTitle.text = currentBook.bookName
        binding.aboutAuthor.text = currentBook.bookAuthor
        binding.aboutYear.text = currentBook.year*/
        binding.toolbarBookName.text = currentBook.bookName
        binding.toolbarBookAuthor.text = currentBook.bookAuthor + ", " + currentBook.year
       // binding.toolbarBookYear.text = currentBook.year

        binding.btnAddInf.setOnClickListener {
            if(binding.radioOther.isChecked) {
                showAddOtherInfDialogFragment()
                setupCustomInputDialogFragmentListeners()
            }
            if(binding.radioChar.isChecked) {
                showAddCharDialogFragment()
                setupAddCharDialogFragmentListeners()
            }

        }

        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.detail_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.item_char -> {
                        //Toast.makeText(APP, "перс", Toast.LENGTH_LONG).show()
                        val bundle = Bundle()

                       bundle.putSerializable("book", currentBook)

                        APP.navController.navigate(R.id.action_detailFragment_to_characterGraphFragment, bundle)
                        true
                    }
                    R.id.item_delete -> {
                        confirmDialog()
                        true
                    }
                    R.id.item_doc -> {

                    /*    val targetDoc = viewModel.createWordDoc()

                        viewModel.addBookToDoc(targetDoc, currentBook)
                        saveOurDoc(targetDoc)

                        Toast.makeText(context,"OK", Toast.LENGTH_LONG).show()*/
                        val bundle = Bundle()

                        bundle.putSerializable("book", currentBook)

                        APP.navController.navigate(R.id.action_detailFragment_to_readAllAboutBookFragment, bundle)

                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        binding.radioGroup.setOnCheckedChangeListener { group, checkId ->
            if(checkId == R.id.radioOther) {
                binding.radioChar.isChecked = false
                otherInfAdapter = OtherInfAdapter(object : ActionListener {
                    override fun onOtherInfDelete(otherInf: OtherInfBook) {
                        viewModel.deleteOtherInfBook(otherInf)
                    }

                    override fun onOtherInfDetails(otherInf: OtherInfBook) {
                        Toast.makeText(context,otherInf.information, Toast.LENGTH_LONG).show()
                    }


                })
                recyclerViewOther.adapter = otherInfAdapter
                val list: LiveData<BookAndInf> = viewModel.getAllOtherInf(currentBook.bookId)
                list.observe(viewLifecycleOwner) { listOtherInf ->
                    otherInfAdapter.setList(listOtherInf.otherInforms.asReversed() ) //все новые элементы сверху
                }
            }
            if(checkId == R.id.radioChar) {
                binding.radioOther.isChecked = false
                recyclerViewChar = binding.rvOtherInf
                characterAdapter = CharacterAdapter(object : ActionListenerChar {
                    override fun onCharDelete(character: BookCharacter) {
                        viewModel.deleteBookCharacter(character)
                    }

                    override fun onCharDetails(character: BookCharacter) {
                        TODO("Not yet implemented")
                    }


                })
                recyclerViewChar.adapter = characterAdapter
                val list2: LiveData<BookAndCharacter> = viewModel.getCharacter(currentBook.bookId)
                list2.observe(viewLifecycleOwner) { listCharacter ->
                    characterAdapter.setList(listCharacter.characterList.asReversed() ) //все новые элементы сверху
                }
            }
        }

    }

    private fun showAddOtherInfDialogFragment() {
        AddOtherInfDialogFragment.show(childFragmentManager)
    }

    private fun setupCustomInputDialogFragmentListeners() {
        AddOtherInfDialogFragment.setupListener(childFragmentManager, this) {
            val text = it
            viewModel.insertOtherInf(OtherInfBook(information = text, parentBookIdOther = currentBook.bookId))
          //  updateUi()
        }

    }

    private fun showAddCharDialogFragment() {
        AddCharacterDialogFragment.show(childFragmentManager)
    }

    private fun setupAddCharDialogFragmentListeners() {
        AddCharacterDialogFragment.setupListener(childFragmentManager, this) {
            val text = it
            viewModel.insertCharacter(BookCharacter(characterName = text, parentBookIdChar = currentBook.bookId))
            //  updateUi()
        }

    }

  //  мб понадобится для private fun updateUi() {

   //}


    //предупрждение об удаление записи
    fun confirmDialog() {
        val builder = AlertDialog.Builder(APP)
        builder.setTitle("Удаление книги")
        builder.setMessage("Вы действительно хотите удалить книгу?")

        builder.setPositiveButton("Да") { _: DialogInterface, _: Int ->
            viewModel.deleteBook(currentBook)
            APP.navController.navigate(R.id.action_detailFragment_to_startFragment)
        }
        builder.setNegativeButton("Отмена") { _: DialogInterface, _: Int -> }
        builder.show()
    }

    private fun saveOurDoc(targetDoc: XWPFDocument){
        val ourAppFileDirectory = requireContext().filesDir
        //Check whether it exists or not, and create one if it does not exist.
        if (ourAppFileDirectory != null && !ourAppFileDirectory.exists()) {
            ourAppFileDirectory.mkdirs()
        }

        //Create a word file called test.docx and save it to the file system
        val wordFile = File(ourAppFileDirectory, "myDoc.docx")
        try {
            val fileOut = FileOutputStream(wordFile)
            targetDoc.write(fileOut)
            fileOut.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    companion object {
        fun clickChar(bookCharacter: BookCharacter) {
            val bundle = Bundle()
            bundle.putSerializable("bookChar", bookCharacter)
            APP.navController.navigate(R.id.action_detailFragment_to_characterFragment, bundle)
        }

    }



}

