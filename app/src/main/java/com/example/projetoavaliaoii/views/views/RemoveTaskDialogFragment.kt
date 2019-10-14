package com.example.projetoavaliaoii.views.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.projetoavaliaoii.R
import java.lang.IllegalStateException

class RemoveTaskDialogFragment(val callback: () -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setTitle("Deletar tarefa")
                .setPositiveButton("Deletar", DialogInterface.OnClickListener { dialog, id ->
                    callback()
                })
                .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, id ->
                    dismiss()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
