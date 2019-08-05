package com.imamfrf.dicoding.submission5made.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.imamfrf.dicoding.submission5made.MainActivity
import com.imamfrf.dicoding.submission5made.R

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(s: String?) {
        super.onNewToken(s)
        Log.d("TES123", "Refreshed token: " + s!!)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage!!.notification != null) {
            sendNotification(remoteMessage.notification!!.body, remoteMessage.data)
        }
    }

    private fun sendNotification(messageBody: String?, data: Map<String, String>?) {
        val channelId = getString(R.string.default_notification_channel_id)
        val channelName = getString(R.string.default_notification_channel_name)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("type", data?.get("type"))
        //Log.d("TES123", data?.get("key"))
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

            notificationBuilder.setChannelId(channelId)

            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = notificationBuilder.build()

        mNotificationManager.notify(0, notification)
    }
}