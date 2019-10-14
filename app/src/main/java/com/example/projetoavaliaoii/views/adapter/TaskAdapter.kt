package com.example.projetoavaliaoii.views.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoavaliaoii.R
import com.example.projetoavaliaoii.views.Task
import com.google.android.material.card.MaterialCardView

class TaskAdapter(
    val taskList: ArrayList<Task>,
    val clickListener: (Int) -> Unit,
    val longClickListener: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]

        holder.bind(position, clickListener, longClickListener) {
            notifyDataSetChanged()
        }
        holder.idTextView.text = task.id.toString()
        holder.titleTextView.text = task.title
        holder.contentTextView.text = task.description
        holder.checkBox.isChecked = task.completed

        if (task.completed){
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
            holder.titleTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById<TextView>(R.id.txtId)
        val titleTextView: TextView = itemView.findViewById<TextView>(R.id.txtTitulo)
        val contentTextView: TextView = itemView.findViewById<TextView>(R.id.txtDescricao)
        val checkBox: CheckBox = itemView.findViewById<CheckBox>(R.id.checked)

        fun bind(
            position: Int,
            clickListener: (Int) -> Unit,
            longClickListener: (Int) -> Unit,
            afterCallback: () -> Unit
        ) = with(itemView){
            val taskView = itemView.findViewById<MaterialCardView>(R.id.card_view)
            taskView.setOnClickListener {
                clickListener(position)
                afterCallback()
            }

            taskView.setOnLongClickListener {
                longClickListener(position)
                afterCallback()

                true
            }
        }
    }
}
