package com.example.litpantry.screen.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.litpantry.adapter.REPOSITORY
import com.example.litpantry.database.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {

    fun getCharacter(bookId: Int) : LiveData<BookAndCharacter> {
        return REPOSITORY.getCharacters(bookId)

    }

    fun insertCharacterCrossRef(characterCrossRef: BookCharacterCrossRef)   = viewModelScope.launch(
        Dispatchers.IO) {
        REPOSITORY.insertCharacterCrossRef(characterCrossRef)
    }

    fun getCharacterRelations  (characterId: Int): LiveData<CharacterWithOther> {
        return REPOSITORY.getCharacterRelations(characterId)
    }

    fun updateCharacter(bookCharacter: BookCharacter)   = viewModelScope.launch(Dispatchers.IO) {
        REPOSITORY.updateCharacter(bookCharacter)
    }

    fun getCharacterOtherInf(characterId: Int): LiveData<CharacterAndOtherInf> {
        return REPOSITORY.getCharacterOtherInf(characterId)
    }

    fun getCharacterQuotes(characterId: Int): LiveData<CharacterAndQuote> {
        return REPOSITORY.getCharacterQuotes(characterId)
    }

    fun insertCharacterQuote(quote: Quote)  = viewModelScope.launch(Dispatchers.IO)  {
       REPOSITORY.insertCharacterQuote(quote)
    }

    fun insertCharacterOtherInf(characterOtherInf: OtherInfCharacter)  = viewModelScope.launch(Dispatchers.IO)  {
        REPOSITORY.insertCharacterOtherInf(characterOtherInf)
    }

    fun deleteOtherInfChar(otherInfCharacter: OtherInfCharacter)  = viewModelScope.launch(Dispatchers.IO)  {
        REPOSITORY.deleteOtherInfChar(otherInfCharacter)
    }

    fun deleteOtherInfQuote(quote: Quote)  = viewModelScope.launch(Dispatchers.IO)  {
        REPOSITORY.deleteOtherInfQuote(quote)
    }

    fun deleteRelation(bookCharacterCrossRef: BookCharacterCrossRef) =  viewModelScope.launch(Dispatchers.IO)  {
        REPOSITORY.deleteRelation(bookCharacterCrossRef)
    }





}