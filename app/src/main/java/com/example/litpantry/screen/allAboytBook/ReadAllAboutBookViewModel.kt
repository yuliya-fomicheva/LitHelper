package com.example.litpantry.screen.allAboytBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.litpantry.adapter.REPOSITORY
import com.example.litpantry.database.entity.Book
import com.example.litpantry.database.entity.BookAndCharacter
import com.example.litpantry.database.entity.BookAndInf
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument

class ReadAllAboutBookViewModel : ViewModel() {

    fun getAllOtherInf(bookId: Int) : LiveData<BookAndInf> {
        return REPOSITORY.getBookOtherInformation(bookId)

    }

    fun getCharacter(bookId: Int) : LiveData<BookAndCharacter>  {
        return REPOSITORY.getCharacters(bookId)

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


    }

}