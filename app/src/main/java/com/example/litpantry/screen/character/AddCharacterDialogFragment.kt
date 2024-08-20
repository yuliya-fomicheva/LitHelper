package com.example.litpantry.screen.character

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.litpantry.R
import com.example.litpantry.databinding.FragmentAddCharactrDialogBinding


class AddCharacterDialogFragment : DialogFragment() {

       override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val dialogBinding = FragmentAddCharactrDialogBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(requireContext())
                .setCancelable(true)
                .setTitle("Добавить персонажа")
                .setView(dialogBinding.root)
                .setPositiveButton("Сохранить", null)
                .setNegativeButton("Отмена", null)
                .create()

            dialog.setOnShowListener {
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                    val enteredText = dialogBinding.addNameChar.text.toString()
                    if (enteredText.isBlank()) {
                        dialogBinding.addNameChar.error = getString(R.string.fildEmpty)
                        return@setOnClickListener
                    }
                    parentFragmentManager.setFragmentResult(
                        REQUEST_KEY, bundleOf(
                            KEY_VOLUME_RESPONSE to enteredText))
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
            return inflater.inflate(R.layout.fragment_add_charactr_dialog, container, false)
        }

        companion object {
            @JvmStatic private val TAG = AddCharacterDialogFragment::class.java.simpleName
            @JvmStatic private val KEY_VOLUME_RESPONSE = "KEY_VOLUME_RESPONSE"
            @JvmStatic private val REQUEST_KEY = "REQUEST_KEY"


            fun show(manager: FragmentManager) {
                val dialogFragment = AddCharacterDialogFragment()
                dialogFragment.show(manager, TAG)
            }
            fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (String) -> Unit) {
                manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _, result ->
                    listener.invoke(result.getString(KEY_VOLUME_RESPONSE)!!)
                })
            }

        }
}