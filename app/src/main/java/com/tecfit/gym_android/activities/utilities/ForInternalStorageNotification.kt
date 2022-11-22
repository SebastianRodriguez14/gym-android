package com.tecfit.gym_android.activities.utilities

import android.content.Context
import com.tecfit.gym_android.models.Membership
import com.tecfit.gym_android.models.custom.NotificationMembership
import com.tecfit.gym_android.models.custom.UserInAppCustom
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class ForInternalStorageNotification {

    companion object {

        private const val FILE_NOTIFICATION = "notification"

        private lateinit var fileOutputStream: FileOutputStream
        private lateinit var fileInputStream: FileInputStream

        /*
        * El archivo tendrá el siguiente formato
        * boolean(notificationDisplayedThreeDaysBefore)|boolean(notificationDisplayedSameDay)
        * Si no hay nada escrito en el archivo, significa que no se ha activado la notificación de la membresía
        * Esto independientemente de si el usuario tiene o no una membresía*/


        // Este método se ejecutaría cada que le de clic al switch
        fun activeNotification(context: Context?){

            val membership = UserInAppCustom.membership

            if (membership != null) {

                val three_days_date = Date(membership.expiration_date.time)
                three_days_date.date = three_days_date.date - 3

                val same_day_date = Date(membership.expiration_date.time)

                // Si es true significa que ya debió de aparecer la notificación
                val showNotificationThreeDaysBefore =  Date().time >= three_days_date.time

                // Si es true significa que ya debió de aparecer la notificación
                val showNotificationSameDay = Date().time >= same_day_date.time

                val notification = NotificationMembership(showNotificationThreeDaysBefore, showNotificationSameDay)

                try {
                    val text = "${notification.notificationDisplayedThreeDaysBefore}|${notification.notificationDisplayedSameDay}"

                    fileOutputStream = context!!.openFileOutput(FILE_NOTIFICATION, 0)
                    fileOutputStream.write(text.toByteArray())
                    fileOutputStream.close()
                } catch (e:Exception){
                    e.printStackTrace()
                }

            }

        }

        fun disableNotification(context: Context?){
            try {
                val text = ""
                fileOutputStream = context!!.openFileOutput(FILE_NOTIFICATION, 0)
                fileOutputStream.write(text.toByteArray())
                fileOutputStream.close()
            } catch (e:Exception){
                e.printStackTrace()
            }
        }

        fun loadNotification(context: Context?): NotificationMembership?{

            try{

                fileInputStream = context!!.openFileInput(FILE_NOTIFICATION)
                val bytes = ByteArray(fileInputStream.available())
                fileInputStream.read(bytes)
                fileInputStream.close()
                val message = String(bytes)
                return formatTextToNotification(message)

            } catch (e:Exception){
                e.printStackTrace()
                return null
            }


        }

        fun formatTextToNotification(text: String): NotificationMembership?{
            if (text == "") {
                return null
            } else {
                val notification_detail = text.split("|")
                return NotificationMembership(
                    notification_detail.get(0).toBoolean(),
                    notification_detail.get(1).toBoolean()
                )
            }
        }


    }
}