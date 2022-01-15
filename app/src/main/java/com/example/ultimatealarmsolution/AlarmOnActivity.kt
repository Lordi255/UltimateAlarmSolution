package com.example.ultimatealarmsolution

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AlarmOnActivity : AppCompatActivity() {
    var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_on)

        //var mp: MediaPlayer = MediaPlayer.create(applicationContext,R.raw.alarm_tone)
        //mp.start()
        if (mMediaPlayer == null) {
            val filename = "android.resource://" + this.packageName + "/raw/alarm_tone"

            val mMediaPlayer = MediaPlayer().apply {
                setDataSource(application, Uri.parse(filename))
                setAudioAttributes(AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
                )
                prepare()
            }
            //mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.alarm_tone)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        }

        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            //mp.stop()
            mMediaPlayer!!.stop()

        }
    }
}