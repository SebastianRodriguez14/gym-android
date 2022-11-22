package com.tecfit.gym_android.activities.utilities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tecfit.gym_android.R

class ForNotifications {
    companion object {

        private lateinit var pendingIntent: PendingIntent
        private const val CHANNEL_ID = "canal"

        fun checkSendNotification(context:Context?, activity: Class<*>){

            val notificationMembership = ForInternalStorageNotification.loadNotification(context)
            println("Notificación -> $notificationMembership")
            if (notificationMembership != null){

                if (notificationMembership.notificationDisplayedSameDay){
                    sendNotification(context!!, "Tu membresía expira hoy mismo 😨\nRenuévala en nuestro gimnasio y sigue entrenándote 💪🏻", activity)
                } else if (notificationMembership.notificationDisplayedThreeDaysBefore){
                    sendNotification(context!!, "Tu membresía expira en 3 días \nRenuévala en nuestro gimnasio y sigue entrenándote 💪🏻", activity)

                }


            }

        }

        fun sendNotification(context: Context?, message: String, activity: Class<*>){
            println("Entramos al método de enviado")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                println("Al if")
                showNotification(context!!, message, activity)
            } else {
                println("Al else")
//                showNewNotification(context!!, message, activity)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun showNotification(context: Context?, message: String, activity: Class<*>){
            val notificationChannel = NotificationChannel(CHANNEL_ID, "NEW", NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
            println("Creamos el notification manager")
            showNewNotification(context, message, activity)
        }

        private fun showNewNotification(context: Context?, message: String,activity: Class<*>){
            setPendingIntent(activity, context!!)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.start_page_logo)
                .setContentTitle("Membresía por expirar 😥")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
            val managerCompat = NotificationManagerCompat.from(context)
            managerCompat.notify(1, builder.build())


        }

        private fun setPendingIntent(activity: Class<*>, context:Context?) {

            val intent = Intent(context,activity)
            println("Creamos el intent")
            val stackBuilder = TaskStackBuilder.create(context)
            println("Creamos el stackbuilder")
            stackBuilder.addParentStack(activity)
            stackBuilder.addNextIntent(intent)
            println("Añadimos el parent y el intent al stack builder")
            pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)

        }



    }
}