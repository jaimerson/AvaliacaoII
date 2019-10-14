package com.example.projetoavaliaoii.views.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetoavaliaoii.R
import com.example.projetoavaliaoii.views.Task
import com.example.projetoavaliaoii.views.TaskRepository
import kotlinx.android.synthetic.main.activity_cadastra_tarefa.*

class CadastraTarefaActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_tarefa)

        btnCadastrarTarefa.setOnClickListener {
            val task = Task(-1, edtTituloTarefa.text.toString(), edtDescricaoTarefa.text.toString())
            TaskRepository.addTask(task)
            NotificationUtils.notificationSimple(this, "Task created", task.title)
            startActivity(Intent(this, ListActivity::class.java))
        }
    }
}
