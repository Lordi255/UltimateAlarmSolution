package com.example.ultimatealarmsolution

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.ConfirmationAlreadyPresentingException
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.LocalDateTime
import java.util.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    private lateinit var picker : MaterialTimePicker
    private lateinit var calendar : Calendar
    private lateinit var timestoppicker : MaterialTimePicker
    private lateinit var timerstop : Calendar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val edithourstart = findViewById<Button>(R.id.button3)
        val edithourstop = findViewById<Button>(R.id.button4)
        val intervalnumber = findViewById<TextView>(R.id.editTextNumber)

        edithourstart.setOnClickListener {
            picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(LocalDateTime.now().hour)
                .setMinute(LocalDateTime.now().minute)
                .setTitleText("Select Alarm Time")
                .build()

            picker.show(supportFragmentManager, "ultimatealarm")

            picker.addOnPositiveButtonClickListener {


                calendar = Calendar.getInstance()
                calendar[Calendar.HOUR_OF_DAY] = picker.hour
                calendar[Calendar.MINUTE] = picker.minute
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0
            }
        }

        edithourstop.setOnClickListener {
            timestoppicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(LocalDateTime.now().hour)
                .setMinute(LocalDateTime.now().minute)
                .setTitleText("Select Alarm Time")
                .build()

            timestoppicker.show(supportFragmentManager, "ultimatealarm")

            timestoppicker.addOnPositiveButtonClickListener {


                timerstop = Calendar.getInstance()
                timerstop[Calendar.HOUR_OF_DAY] = timestoppicker.hour
                timerstop[Calendar.MINUTE] = timestoppicker.minute
                timerstop[Calendar.SECOND] = 0
                timerstop[Calendar.MILLISECOND] = 0
            }
        }

        button.setOnClickListener {
            var i = Intent(applicationContext, MyBroadcastReceiver::class.java)
            var am : AlarmManager= getSystemService(Context.ALARM_SERVICE) as AlarmManager
            var inter = 0

            while(timerstop.timeInMillis > (inter + calendar.timeInMillis))
            {

                var pi: PendingIntent? = PendingIntent.getBroadcast(applicationContext, inter/1000 , i, 0)

                am.setExact(AlarmManager.RTC_WAKEUP, inter + calendar.timeInMillis, pi)

                inter = inter + (intervalnumber.text.toString().toInt() * 1000)
                Logger.getLogger(MainActivity::class.java.name).warning(intervalnumber.text.toString())

            }

            Toast.makeText(applicationContext, "Alarm is set for " + calendar[Calendar.HOUR_OF_DAY] + " Hours and "+ calendar[Calendar.MINUTE] + " Minutes" + timerstop[Calendar.HOUR_OF_DAY]+timerstop[Calendar.MINUTE], Toast.LENGTH_LONG).show()
        }

    }
}