package com.example.projetoavaliaoii.views.views

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoavaliaoii.R
import com.example.projetoavaliaoii.views.TaskRepository
import com.example.projetoavaliaoii.views.UserSession
import com.example.projetoavaliaoii.views.adapter.TaskAdapter

class ListActivity : AppCompatActivity() {
    val broadcastReceiver = TaskBroadcastReceiver()
    val filter = IntentFilter(TaskBroadcastReceiver.FILTER_NAME).apply{
        addAction(TaskBroadcastReceiver.ACTION_NAME)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        TaskRepository.initialize(applicationContext)
        applicationContext.registerReceiver(broadcastReceiver, filter)

        val taskListView = findViewById<RecyclerView>(R.id.rvTarefas)
        taskListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val clickCallback: (Int) -> Unit = { position ->
            val task = TaskRepository.tasksList[position]
            TaskRepository.completeTask(task)
        }

        val longClickCallback: (Int) -> Unit = { position ->
            val task = TaskRepository.tasksList[position]
            if(task.completed){
                val dialog = RemoveTaskDialogFragment {
                    TaskRepository.removeTask(task)
                }
                dialog.show(supportFragmentManager, "RemoveTaskDialog")
            }else{
                Intent().also {intent ->
                    intent.setAction(TaskBroadcastReceiver.ACTION_NAME)
                    intent.putExtra("taskTitle", task.title)
                    sendBroadcast(intent)
                }
            }
        }

        val adapter = TaskAdapter(TaskRepository.tasksList, clickCallback, longClickCallback)

        taskListView.adapter = adapter
        TaskRepository.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_sair -> {
                UserSession.reset()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_cadastrar -> {
                val intent = Intent(this, CadastraTarefaActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_concluida -> {
                TaskRepository.completeAllTasks()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
