package fr.isep.smallproject

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat


class ReminderBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationHelper = NotificationHelper(context)
        val nb = notificationHelper.channelNotification
        notificationHelper.manager?.notify(1, nb.build())
    }
}

class NotificationHelper(private val context: Context) {
    private val CHANNEL_ID = "reminderChannel"
    var manager: NotificationManager? = null
        get() {
            if (field == null) {
                field = ContextCompat.getSystemService(context, NotificationManager::class.java)
            }
            return field
        }

    val channelNotification: NotificationCompat.Builder
        get() = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Reminder")
            .setContentText("Don't forget to use the app!")
}
