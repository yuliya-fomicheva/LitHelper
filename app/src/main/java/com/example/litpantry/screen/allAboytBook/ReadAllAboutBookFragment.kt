package com.example.litpantry.screen.allAboytBook

import android.graphics.Color
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.litpantry.R
import com.example.litpantry.adapter.CharacterShowTextAdapter
import com.example.litpantry.database.entity.*
import com.example.litpantry.databinding.FragmentReadAllAboutBookBinding

import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ReadAllAboutBookFragment : Fragment() {

    private lateinit var viewModel: ReadAllAboutBookViewModel
    private lateinit var binding: FragmentReadAllAboutBookBinding
    private lateinit var currentBook: Book
    private lateinit var recyclerViewChar: RecyclerView
    private lateinit var characterAdapter: CharacterShowTextAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadAllAboutBookBinding.inflate(layoutInflater, container, false)
        currentBook = arguments?.getSerializable("book") as Book
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)


        toolbar.setupWithNavController(navController, appBarConfig)
        init()

    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun init() {

        binding.toolbarBookName.text = currentBook.bookName
        binding.toolbarBookAuthor.text = currentBook.bookAuthor + ", " + currentBook.year


        viewModel= ViewModelProvider(this)[ReadAllAboutBookViewModel::class.java]

        val list: LiveData<BookAndInf> = viewModel.getAllOtherInf(currentBook.bookId)
        list.observe(viewLifecycleOwner) { listOtherInf ->
            // otherInfAdapter.setList(listOtherInf.otherInforms.asReversed() ) //все новые элементы сверху

            val builder =  SpannableStringBuilder()

            listOtherInf.otherInforms.forEach { text ->
                builder.append(
                    " " + text.information + "\n\n", // Add some linebreaks
                    BulletSpan(0, Color.GRAY ,10), // Add the bullet point span
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            binding.textViewBulletOther.text = builder

        }

      /*  (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.read_all_about_book_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {

                    R.id.item_save -> {

                            val targetDoc = viewModel.createWordDoc()

                         addBookToDoc(targetDoc, currentBook)
                        saveOurDoc(targetDoc)

                            Toast.makeText(context,"Файл сохранен", Toast.LENGTH_LONG).show()
                     /*   val bundle = Bundle()

                        bundle.putSerializable("book", currentBook)

                        APP.navController.navigate(R.id.action_detailFragment_to_readAllAboutBookFragment, bundle)*/


                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)*/



        recyclerViewChar = binding.rvChar
        characterAdapter = CharacterShowTextAdapter()
        recyclerViewChar.adapter = characterAdapter
        val list2: LiveData<BookAndCharacter> = viewModel.getCharacter(currentBook.bookId)
        list2.observe(viewLifecycleOwner) { listCharacter ->
            characterAdapter.setList(listCharacter.characterList.asReversed() ) //все новые элементы сверху
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun addBookToDoc(targetDoc: XWPFDocument, book: Book){
        //creating a paragraph in our document and setting its alignment
        val paragraph1 = targetDoc.createParagraph()
        paragraph1.alignment = ParagraphAlignment.LEFT

        //creating a run for adding text
        val sentenceRun1 = paragraph1.createRun()

        //format the text
        sentenceRun1.fontSize = 11
        sentenceRun1.fontFamily = "Roboto"
        sentenceRun1.setText(book.bookAuthor)
        //add a sentence break
        sentenceRun1.addBreak()

        //add another run
        val sentenceRun2 = paragraph1.createRun()
        sentenceRun2.isBold = true
        sentenceRun2.fontSize = 14
        sentenceRun2.fontFamily = "Roboto"
        sentenceRun2.setText(book.bookName)
        sentenceRun2.addBreak()

        val sentenceRun3 = paragraph1.createRun()

        //format the text
        sentenceRun3.fontSize = 11
        sentenceRun3.fontFamily = "Roboto"
        sentenceRun3.setText(book.year)
        //add a sentence break
        sentenceRun3.addBreak()


        val sentenceRun4 = paragraph1.createRun()
        sentenceRun4.fontSize = 11
        sentenceRun4.fontFamily = "Roboto"

        val list: LiveData<BookAndInf> = viewModel.getAllOtherInf(currentBook.bookId)
        val builder = mutableListOf<String>('1'.toString(), 2.toString(), 3.toString())

        list.observe(viewLifecycleOwner) { listOtherInf ->
            // otherInfAdapter.setList(listOtherInf.otherInforms.asReversed() ) //все новые элементы сверху

            listOtherInf.otherInforms.forEach { text ->
                builder.add(text.information.toString())
            }


        }
        for (i in builder.indices) {
            sentenceRun4.addTab()
            sentenceRun4.setText(builder[i])
            sentenceRun4.addBreak()
        }


        //add a sentence break\
        sentenceRun4.addBreak()





    }


    private fun saveOurDoc(targetDoc: XWPFDocument){
        val ourAppFileDirectory = requireContext().filesDir
        //Check whether it exists or not, and create one if it does not exist.
        if (ourAppFileDirectory != null && !ourAppFileDirectory.exists()) {
            ourAppFileDirectory.mkdirs()
        }

        //Create a word file called test.docx and save it to the file system
        val wordFile = File(ourAppFileDirectory, currentBook.bookName + ".docx")
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


    }



