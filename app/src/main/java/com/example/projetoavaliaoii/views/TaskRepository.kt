package com.example.projetoavaliaoii.views

import android.content.Context
import android.database.Cursor
import com.example.projetoavaliaoii.views.adapter.TaskAdapter
import com.example.projetosqlite.repository.sqlite.*

class TaskRepository {
    companion object {
        private var databaseHelper: TarefaSqlHelper? = null
        val tasksList: ArrayList<Task> = ArrayList()
        var adapter: TaskAdapter? = null

        fun initialize(context: Context){
            this.databaseHelper = TarefaSqlHelper(context)
            refresh()
        }

        fun refresh(){
            this.tasksList.clear()
            eachTask { task -> tasksList.add(task) }
            adapter?.notifyDataSetChanged()
        }

        private fun eachTask(callback: (Task) -> Unit) {
            val connection = databaseHelper!!.readableDatabase
            val cursor: Cursor = connection.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID ASC", null)

            while(cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                val completed = cursor.getInt(cursor.getColumnIndex(COLUMN_COMPLETED))
                callback(Task(id, title, description, completed == 1))
            }
            cursor.close()
            connection.close()
        }

        fun addTask(Task: Task) {
            val connection = databaseHelper!!.writableDatabase
            connection.execSQL("""
                INSERT INTO $TABLE_NAME ($COLUMN_TITLE, $COLUMN_DESCRIPTION, $COLUMN_COMPLETED)
                VALUES ("${Task.title}", "${Task.description}", 0);
            """.trimIndent())

            connection.close()
            refresh()
        }

        fun removeTask(Task: Task) {
            val connection = databaseHelper!!.writableDatabase
            connection.execSQL("""
                DELETE FROM "$TABLE_NAME"
                WHERE $COLUMN_ID = ${Task.id};
            """.trimIndent())

            connection.close()
            refresh()
        }

        fun completeTask(Task: Task) {
            val connection = databaseHelper!!.writableDatabase
            connection.execSQL("""
                UPDATE $TABLE_NAME
                SET $COLUMN_COMPLETED = 1
                WHERE $COLUMN_ID = ${Task.id};
            """.trimIndent())

            connection.close()
            refresh()
        }

        fun completeAllTasks() {
            val connection = databaseHelper!!.writableDatabase
            connection.execSQL("""
                UPDATE $TABLE_NAME
                SET $COLUMN_COMPLETED = 1
            """.trimIndent())

            connection.close()
            refresh()
        }
    }
}
