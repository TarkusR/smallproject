package fr.isep.smallproject
import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val callButton = findViewById<Button>(R.id.callButton)
        callButton.setOnClickListener {
            makePhoneCall()
        }
    }

    private fun makePhoneCall() {
        val phoneIntent = Intent(Intent.ACTION_CALL)
        phoneIntent.data = Uri.parse("tel:0123456789")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL)
        } else {
            startActivity(phoneIntent)
        }
    }

    companion object {
        private const val REQUEST_CALL = 1
    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext, ReminderBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val timeToRepeat: Long = 1000 * 60 * 60 * 12 // 12 hours in milliseconds
        val startTime = System.currentTimeMillis() + timeToRepeat // Start 12 hours from now

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, timeToRepeat, pendingIntent)
    }

}
