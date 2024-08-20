package com.example.litpantry.screen.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.litpantry.adapter.REPOSITORY
import com.example.litpantry.database.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument

class DetailViewModel  (application: Application): AndroidViewModel(application)  {

    fun deleteBook(book: Book)  = viewModelScope.launch(Dispatchers.IO) {
        REPOSITORY.deleteBook(book)
    }


    fun insertOtherInf(otherInfBook: OtherInfBook)  = viewModelScope.launch(Dispatchers.IO) {
        REPOSITORY.insertOtherInf(otherInfBook)
    }

   fun getAllOtherInf(bookId: Int) : LiveData<BookAndInf>  {
        return REPOSITORY.getBookOtherInformation(bookId)

    }
    fun insertCharacter(character: BookCharacter)  = viewModelScope.launch(Dispatchers.IO) {
        REPOSITORY.insertCharacter(character)
    }

    fun getCharacter(bookId: Int) : LiveData<BookAndCharacter>  {
        return REPOSITORY.getCharacters(bookId)

    }
    fun deleteOtherInfBook(otherInfBook: OtherInfBook)  = viewModelScope.launch(Dispatchers.IO) {
        REPOSITORY.deleteOtherInfBook(otherInfBook)
    }

    fun deleteBookCharacter(bookCharacter: BookCharacter)  = viewModelScope.launch(Dispatchers.IO) {
        REPOSITORY.deleteBookCharacter(bookCharacter)
    }

    fun createWordDoc(): XWPFDocument {
        return XWPFDocument()
    }

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


       sentenceRun3.fontSize = 11
       sentenceRun3.fontFamily = "Roboto"
       sentenceRun3.setText(book.year)
       //add a sentence break
       sentenceRun3.addBreak()

       val sentenceRun4 = paragraph1.createRun()

       sentenceRun4.fontSize = 11
       sentenceRun4.fontFamily = "Roboto"




       sentenceRun4.setText(book.year)
       //add a sentence break
       sentenceRun4.addBreak()


    }


    }

    //saving the word document


    //retrieving the document from the file system





