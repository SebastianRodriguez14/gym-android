package com.tecfit.gym_android.activities.utilities

import android.content.Context
import com.tecfit.gym_android.models.File
import com.tecfit.gym_android.models.Membership
import com.tecfit.gym_android.models.User
import com.tecfit.gym_android.models.custom.NotificationMembership
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class ForInternalStorageUser {

    companion object {
        private const val FILE_USER = "user"

        private lateinit var fileOutputStream: FileOutputStream
        private lateinit var fileInputStream: FileInputStream

        /**
         * Estructura del archivo de texto de user
         *  id_user|email|password|name|lastname|phone|membership|image_id-url
         */

        fun saveUser(user:User?, context:Context?){

            try{
                val text = if (user != null) formatUserToText(user) else ""
                fileOutputStream = context!!.openFileOutput(FILE_USER, 0)
                fileOutputStream.write(text.toByteArray())

                fileOutputStream.close()

            } catch (e:Exception){
                e.printStackTrace()
            }

        }

        fun formatUserToText(user:User):String = "${user.id_user}|${user.email}|${user.password}|${user.name}|${user.lastname}|" +
                    "${user.phone}|${user.membership}" + if (user.image == null) "null" else
                        "|${user.image.id_file}-${user.image.url}"



        fun loadUser(context: Context?):User?{
            try{

                fileInputStream = context!!.openFileInput(FILE_USER)
                val bytes = ByteArray(fileInputStream.available())
                fileInputStream.read(bytes)
                fileInputStream.close()
                val message = String(bytes)
                return formatTextToUser(message)

            } catch(e:Exception){
                e.printStackTrace()
                return null
            }
        }

        fun formatTextToUser(text:String):User?{

            if (text == "") {
                return null
            } else {
                val user_detail = text.split("|")
                var existFile = false
                var image_user_detail= listOf<String>()
                if (user_detail.size == 8){
                    image_user_detail = user_detail.get(7).split("-")
                    existFile = true
                }

                return User(
                    user_detail.get(0).toInt(), user_detail.get(1),
                    user_detail.get(2), user_detail.get(3), user_detail.get(4),
                    user_detail.get(5), user_detail.get(6).toBoolean(),
                    if(existFile) File(image_user_detail.get(1), image_user_detail.get(0).toInt()) else null
                )
            }

        }

        /**
         * Estructura del archivo de texto de notification
         *  fecha_tres_días_antes|fecha_del_mismo_día_de_expiración|fecha_activa (boolean) -> true = notificación activa, false = inactiva
         */




    }

}