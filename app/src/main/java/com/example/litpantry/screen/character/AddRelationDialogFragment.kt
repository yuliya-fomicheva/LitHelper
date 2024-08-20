package com.example.litpantry.screen.character

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.litpantry.R
import com.example.litpantry.database.entity.BookCharacter
import com.example.litpantry.databinding.FragmentAddRelationDialogBinding
import com.example.litpantry.adapter.CharacterListAdapter


class AddRelationDialogFragment : DialogFragment() {
    private val volume: List<BookCharacter>
        get() = requireArguments().getBundle(ARG_VOLUME) as List<BookCharacter>

    private val requestKey: String
        get() = requireArguments().getString(ARG_REQUEST_KEY)!!

    private lateinit var adapter: CharacterListAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = FragmentAddRelationDialogBinding.inflate(layoutInflater)
        adapter= CharacterListAdapter()
        adapter.setList(volume)
        dialogBinding.spListChar.adapter = adapter

        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle("Добавить  отношения")
            .setView(dialogBinding.root)
            .setPositiveButton("Сохранить", null)
            .setNegativeButton("Отмена", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val enteredText = dialogBinding.editTextRelation.text.toString()
               /* if (enteredText.isBlank()) {
                    dialogBinding.addNameChar.error = getString(R.string.fildEmpty)
                    return@setOnClickListener
                }*/
                lateinit var character: BookCharacter

                dialogBinding.spListChar.onItemClickListener = object  : AdapterView.OnItemClickListener {
                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        character = volume[position]
                    }

                }

                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_VOLUME_RESPONSE to character))
                dismiss()
            }
        }

        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d(TAG, "Dialog dismissed")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_relation_dialog, container, false)
    }

    companion object {
        @JvmStatic private val TAG = AddCharacterDialogFragment::class.java.simpleName
        @JvmStatic private val KEY_VOLUME_RESPONSE = "KEY_VOLUME_RESPONSE"
        @JvmStatic private val REQUEST_KEY = "REQUEST_KEY"
        @JvmStatic private val ARG_VOLUME = "ARG_VOLUME"
        @JvmStatic private val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"


        fun show(manager: FragmentManager, volume: List<BookCharacter>, requestKey: String) {
            val dialogFragment = AddCharacterDialogFragment()
            dialogFragment.arguments = bundleOf(
                ARG_VOLUME to volume,
                ARG_REQUEST_KEY to requestKey
            )
            dialogFragment.show(manager, TAG)
        }
        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (String) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _, result ->
                listener.invoke(result.getBundle(KEY_VOLUME_RESPONSE)!!.toString())
            })
        }

    }
}