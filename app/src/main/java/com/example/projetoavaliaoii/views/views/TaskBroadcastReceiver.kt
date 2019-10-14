package com.example.projetoavaliaoii.views.views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TaskBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val FILTER_NAME = "somefilter"
        const val ACTION_NAME = "someaction"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val taskTitle = intent?.getStringExtra("taskTitle")
        if (taskTitle != null) {
            NotificationUtils.notificationSimple(context!!, "Task could not be removed", """
                The task "$taskTitle" could not be removed because it is not completed
            """.trimIndent())
        }
    }
}
