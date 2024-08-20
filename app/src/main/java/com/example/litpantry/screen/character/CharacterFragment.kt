package com.example.litpantry.screen.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.litpantry.screen.details.AddOtherInfDialogFragment
import com.example.litpantry.R
import com.example.litpantry.adapter.*
import com.example.litpantry.database.entity.*
import com.example.litpantry.databinding.FragmentCharacterBinding


class CharacterFragment : Fragment() {

    companion object {
        fun newInstance() = CharacterFragment()
    }

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewOther: RecyclerView
    private lateinit var binding: FragmentCharacterBinding
    private lateinit var viewModel: CharacterViewModel
    private lateinit var currentCharacter: BookCharacter
    private lateinit var characterAdapter: CharacterListAdapter
    private lateinit var characterRelationAdapter: CharacterRelationAdapter
    private lateinit var otherInfCharAdapter: OtherInfCharAdapter
    private lateinit var quoteAdapter: QuoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(layoutInflater, container, false)
        currentCharacter = arguments?.getSerializable("bookChar") as BookCharacter


        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar3)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar3)

        toolbar.setupWithNavController(navController, appBarConfig)
        init()

    }

    private fun init() {
        if(!currentCharacter.biography.isNullOrEmpty())
            binding.showToRead.text = currentCharacter.biography

        binding.toolbarCharName.text = currentCharacter.characterName

        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
      //  binding.nameChar.text = currentCharacter.characterName

        recyclerView = binding.rvCharactersRelation
        characterRelationAdapter = CharacterRelationAdapter(object : ActionListenerRelationChar {
            override fun onRelationDelete(relation: BookCharacterCrossRef) {
                viewModel.deleteRelation(relation)
            }

            override fun onRelationDetails(relation: BookCharacterCrossRef) {
                TODO("Not yet implemented")
            }


        })
        recyclerView.adapter = characterRelationAdapter

        val listRelation: LiveData<CharacterWithOther> =
            viewModel.getCharacterRelations(currentCharacter.characterId)

        listRelation.observe(viewLifecycleOwner) { listCharacterCrossRef ->
            characterRelationAdapter.setList(listCharacterCrossRef.characters.asReversed()) //все новые элементы сверху
        }

        recyclerViewOther = binding.rvText
        otherInfCharAdapter = OtherInfCharAdapter(object : ActionListenerCharOther {
            override fun onOtherInfDelete(otherInf: OtherInfCharacter) {
                viewModel.deleteOtherInfChar(otherInf)
            }

            override fun onOtherInfDetails(otherInf: OtherInfCharacter) {
                TODO("Not yet implemented")
            }

        })
        recyclerViewOther.adapter = otherInfCharAdapter

        val listOtherInf: LiveData<CharacterAndOtherInf> = viewModel.getCharacterOtherInf(currentCharacter.characterId)
        listOtherInf.observe(viewLifecycleOwner) { listOtherInfCharacter ->
            otherInfCharAdapter.setList(listOtherInfCharacter.bookCharacterList.asReversed()) //все новые элементы сверху
        }




        characterAdapter = CharacterListAdapter()

        val list2: LiveData<BookAndCharacter> =
            viewModel.getCharacter(currentCharacter.parentBookIdChar)

        list2.observe(viewLifecycleOwner) { characters ->
            characterAdapter.setList(characters.characterList) //все новые элементы сверху
        }


        binding.spListChar.adapter = characterAdapter

        var savePosition: Int = 0
        binding.spListChar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Toast.makeText(activity, position.toString(), Toast.LENGTH_LONG).show()

                savePosition = position

                // viewModel.insertCharacterCrossRef(BookCharacterCrossRef(characterId1 = currentCharacter.characterId, characterId2 = adapter.getList()[position].characterId, descriptionRelationship = binding.editTextRelation.text.toString()))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkId ->

            //проверка нажатия
            if (checkId == R.id.radioBio) {
                visibleSaveAndInsert(currentCharacter.biography)
            }

            if (checkId == R.id.radioPortrait) {
                visibleSaveAndInsert(currentCharacter.portrait)
            }


            if(checkId == R.id.radioRelation) {
                visibleRelation()
            }

            if(checkId == R.id.radioQuotes) {
                visibleQuoteAndOther()

                recyclerViewOther = binding.rvText
                quoteAdapter = QuoteAdapter(object : ActionListenerQuote {
                    override fun onOtherInfDelete(quote: Quote) {
                        viewModel.deleteOtherInfQuote(quote)
                    }

                    override fun onOtherInfDetails(quote: Quote) {
                        TODO("Not yet implemented")
                    }


                })
                recyclerViewOther.adapter = quoteAdapter

                val listQuote: LiveData<CharacterAndQuote> = viewModel.getCharacterQuotes(currentCharacter.characterId)
                listQuote.observe(viewLifecycleOwner) { listQuote ->
                    quoteAdapter.setList(listQuote.quotes.asReversed()) //все новые элементы сверху
                }

            }

            if(checkId == R.id.radioOtherChar) {
                visibleQuoteAndOther()

                recyclerViewOther = binding.rvText
                otherInfCharAdapter = OtherInfCharAdapter(object : ActionListenerCharOther {
                    override fun onOtherInfDelete(otherInf: OtherInfCharacter) {
                        viewModel.deleteOtherInfChar(otherInf)
                    }

                    override fun onOtherInfDetails(otherInf: OtherInfCharacter) {
                        TODO("Not yet implemented")
                    }


                })
                recyclerViewOther.adapter = otherInfCharAdapter

                val listOtherInf: LiveData<CharacterAndOtherInf> = viewModel.getCharacterOtherInf(currentCharacter.characterId)
                listOtherInf.observe(viewLifecycleOwner) { listOtherInfCharacter ->
                    otherInfCharAdapter.setList(listOtherInfCharacter.bookCharacterList.asReversed()) //все новые элементы сверху
                }

            }


            binding.btnAddInf.setOnClickListener {
                if(checkId == R.id.radioRelation) {
                    viewModel.insertCharacterCrossRef(
                        BookCharacterCrossRef(
                            characterId1 = currentCharacter.characterId,
                            characterName1 = currentCharacter.characterName,
                            characterId2 = characterAdapter.getList()[savePosition].characterId,
                            characterName2 = characterAdapter.getList()[savePosition].characterName,
                            descriptionRelationship = binding.editTextRelation.text.toString()
                        )
                    )
                    binding.editTextRelation.setText("")
                }

                if(checkId == R.id.radioQuotes) {
                    showAddOtherInfDialogFragment()
                    setupCustomInputQuoteDialogFragmentListeners()
                }

                if(checkId == R.id.radioOtherChar) {
                    showAddOtherInfDialogFragment()
                    setupCustomInputOtherDialogFragmentListeners()
                }

            }



            //кнопка сохранения
            binding.btnSaveInfChar.setOnClickListener {
                if (checkId == R.id.radioBio) {
                    currentCharacter.biography = binding.showTextToWrite.text.toString()
                    clickSaveButton(currentCharacter.biography)
                }
                if (checkId == R.id.radioPortrait) {
                    currentCharacter.portrait = binding.showTextToWrite.text.toString()
                    clickSaveButton(currentCharacter.portrait)
                }
            }

            //кнопка редактирования
            binding.btnInsertInfChar.setOnClickListener {

                if(checkId == R.id.radioBio) {
                    clickInsertButton(currentCharacter.biography)
                }
                if(checkId == R.id.radioPortrait) {
                    clickInsertButton(currentCharacter.portrait)
                }
            }
        }

    }

    private fun visibleRelation() {
        binding.cardView2.visibility = View.VISIBLE
        binding.btnAddInf.visibility = View.VISIBLE
        binding.btnInsertInfChar.visibility = View.INVISIBLE
        binding.btnSaveInfChar.visibility = View.INVISIBLE
        binding.showToRead.visibility = View.INVISIBLE
        binding.showTextToWrite.visibility = View.INVISIBLE
        binding.linearLayout2.visibility = View.VISIBLE
        binding.rvCharactersRelation.visibility = View.VISIBLE
        binding.rvText.visibility = View.INVISIBLE
    }

    //меняет видимость кнопок
    private fun visibleSaveAndInsert (text: String?) {
        binding.cardView2.visibility = View.INVISIBLE
        binding.linearLayout2.visibility = View.INVISIBLE
        binding.rvCharactersRelation.visibility = View.INVISIBLE
        binding.btnAddInf.visibility = View.INVISIBLE
        binding.btnSaveInfChar.visibility = View.INVISIBLE
        binding.btnInsertInfChar.visibility = View.VISIBLE
        binding.showToRead.visibility = View.VISIBLE
        binding.showToRead.text = text
        binding.rvText.visibility = View.INVISIBLE


        if (binding.btnSaveInfChar.isVisible) {
            binding.showTextToWrite.visibility = View.VISIBLE
            binding.showToRead.visibility = View.INVISIBLE
            binding.showTextToWrite.setText(text)
        }

        if (binding.btnInsertInfChar.isVisible) {
            binding.showTextToWrite.visibility = View.INVISIBLE
            binding.showToRead.visibility = View.VISIBLE
            binding.showToRead.text = text
        }
    }

    private fun visibleQuoteAndOther() {
        binding.cardView2.visibility = View.INVISIBLE
        binding.btnAddInf.visibility = View.VISIBLE
        binding.btnInsertInfChar.visibility = View.INVISIBLE
        binding.btnSaveInfChar.visibility = View.INVISIBLE
        binding.showToRead.visibility = View.INVISIBLE
        binding.showTextToWrite.visibility = View.INVISIBLE
        binding.linearLayout2.visibility = View.INVISIBLE
        binding.rvCharactersRelation.visibility = View.INVISIBLE

        binding.rvText.visibility = View.VISIBLE


    }

    private fun clickInsertButton(text: String?) {
        binding.showTextToWrite.setText(text)
        binding.showTextToWrite.visibility = View.VISIBLE
        binding.btnSaveInfChar.visibility = View.VISIBLE

        binding.showToRead.visibility = View.INVISIBLE
        binding.btnInsertInfChar.visibility = View.INVISIBLE

    }

    private fun clickSaveButton(text: String?) {
        viewModel.updateCharacter(currentCharacter)
        binding.showToRead.text = text

        binding.btnInsertInfChar.visibility = View.VISIBLE
        binding.showToRead.visibility = View.VISIBLE

        binding.showTextToWrite.visibility = View.INVISIBLE
        binding.btnSaveInfChar.visibility = View.INVISIBLE
    }

    private fun showAddOtherInfDialogFragment() {
        AddOtherInfDialogFragment.show(childFragmentManager)
    }

    private fun setupCustomInputQuoteDialogFragmentListeners() {
        AddOtherInfDialogFragment.setupListener(childFragmentManager, this) {
            val text = it
            viewModel.insertCharacterQuote(Quote(text = text, parentCharacter = currentCharacter.characterId))
            //  updateUi()
        }

    }

    private fun setupCustomInputOtherDialogFragmentListeners() {
        AddOtherInfDialogFragment.setupListener(childFragmentManager, this) {
            val text = it
            viewModel.insertCharacterOtherInf(OtherInfCharacter(text = text, parentCharacter = currentCharacter.characterId))
            //  updateUi()
        }

    }
    }


/*     binding.btnAddInf.setOnClickListener {
    /*   shoOtherInfDialogFragment()
       setupCustomInputDialogFragmentListeners()
   }*/
}

/*  private fun showAddOtherInfDialogFragment() {
AddRelationDialogFragment.show(childFragmentManager,)
}

private fun setupCustomInputDialogFragmentListeners() {
AddRelationDialogFragment.setupListener(childFragmentManager, this) {
   val text = it
   binding.testText.text = text
   //  updateUi()
} */

} */



