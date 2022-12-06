package com.tecfit.gym_android.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.switchmaterial.SwitchMaterial
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.LoginActivity
import com.tecfit.gym_android.activities.RegisterActivity
import com.tecfit.gym_android.activities.utilities.*
import com.tecfit.gym_android.databinding.FragmentProfileUserBinding
import com.tecfit.gym_android.models.File
import com.tecfit.gym_android.models.User
import com.tecfit.gym_android.models.custom.UserCustom
import com.tecfit.gym_android.models.custom.UserInAppCustom
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileOutputStream
import java.util.Date
import java.util.concurrent.TimeUnit

class ProfileUserFragment : Fragment() {

    private val REQUEST_UPDATE_GALERY = 1001
    private val REQUEST_POST_GALERY = 2001

    private lateinit var logOut: TextView
    private lateinit var btnSaveUser: TextView
    private lateinit var btnEditUser: TextView
    private lateinit var txtPerfil: TextView
    private lateinit var userInfoData: LinearLayout
    private lateinit var btnCamera: LinearLayout
    private lateinit var formEditUser: LinearLayout
    private lateinit var txtName: EditText
    private lateinit var txtLastname: EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtNameError: TextView
    private lateinit var txtLastnameError: TextView
    private lateinit var txtPhoneError: TextView



    private lateinit var photo: ImageView
    private lateinit var name: TextView
    private lateinit var membership: LinearLayout
    private lateinit var phone: EditText
    private lateinit var root:View
    private lateinit var switch:SwitchMaterial
    private lateinit var inputMembership: EditText

    private lateinit var bottomSheetDialogUpdate: BottomSheetDialog
    private lateinit var bottomSheetViewUpdate: View
    private var uriImageUpdate: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_profile_user, container, false)

        createUpdateDialog()
        btnSaveUser = root.findViewById(R.id.btn_save_user)
        btnEditUser = root.findViewById(R.id.btn_edit_user)

        //layout
        txtPerfil = root.findViewById(R.id.txt_perfil)
        userInfoData = root.findViewById(R.id.user_info_data)
        btnCamera = root.findViewById(R.id.btn_camera)
        formEditUser = root.findViewById(R.id.form_edit_user)
        txtName = root.findViewById(R.id.txt_name)
        txtLastname = root.findViewById(R.id.txt_lastname)
        txtPhone = root.findViewById(R.id.txt_phone)
        txtNameError = root.findViewById(R.id.lbl_name_error)
        txtLastnameError = root.findViewById(R.id.lbl_lastname_error)
        txtPhoneError = root.findViewById(R.id.lbl_phone_error)


        name = root.findViewById(R.id.profile_user_name)
        membership = root.findViewById(R.id.profile_user_active_membership)
        membership.isVisible = false
        phone = root.findViewById(R.id.profile_user_input_phone)
        photo = root.findViewById(R.id.photo_profile)
        switch = root.findViewById(R.id.profile_user_switch_notification)
        inputMembership = root.findViewById(R.id.profile_user_input_membership)
        logOut = root.findViewById(R.id.profile_user_logout_link)
        logOut.setOnClickListener{
            println("asd")
            context?.logout()
            UserInAppCustom.user = null
            ForInternalStorageUser.saveUser(null, context)
            ForInternalStorageNotification.disableNotification(context)
            ForInternalStorageNotification.changeStateNotification(context, false)
            startActivity(Intent(context, LoginActivity::class.java))

        }



        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                ForInternalStorageNotification.activeNotification(context)
                ForNotifications.sendNotification(context)
            } else {
                ForInternalStorageNotification.disableNotification(context)
            }
            ForInternalStorageNotification.changeStateNotification(context, isChecked)

        }

        btnSaveUser.setOnClickListener{
            if(validateUpdate()){
                bottomSheetDialogUpdate.show()
            }
        }

        btnEditUser.setOnClickListener{
            txtPerfil.visibility = View.INVISIBLE
            name.visibility = View.INVISIBLE
            userInfoData.visibility = View.INVISIBLE
            btnCamera.visibility = View.VISIBLE
            formEditUser.visibility = View.VISIBLE
            btnSaveUser.visibility = View.VISIBLE
            btnEditUser.visibility = View.INVISIBLE
            txtName.setText(UserInAppCustom.user?.name)
            txtLastname.setText(UserInAppCustom.user?.lastname)
            txtPhone.setText(UserInAppCustom.user?.phone)
        }

        btnCamera.setOnClickListener{
            checkPermissionsForGalery(1)
        }



        checkUser()
        return root

    }

    fun createUpdateDialog(){
        bottomSheetDialogUpdate = BottomSheetDialog(
            requireActivity(), R.style.BottonSheetDialog
        )

        bottomSheetViewUpdate =
            layoutInflater.inflate(R.layout.bottom_sheet_dialog_edit_user, null)

        bottomSheetDialogUpdate.setContentView(bottomSheetViewUpdate)

        bottomSheetViewUpdate.findViewById<TextView>(R.id.edit_user_cancel).setOnClickListener{
            bottomSheetDialogUpdate.dismiss()
        }

        bottomSheetViewUpdate.findViewById<TextView>(R.id.edit_user_update).setOnClickListener(){
            putUserData(UserInAppCustom.user?.email!!)
            if (uriImageUpdate == null) {
                putUserData(UserInAppCustom.user?.email!!)
            } else {
                putUserDataImage(UserInAppCustom.user?.email!!)
            }
            bottomSheetDialogUpdate.findViewById<View>(R.id.edit_user_update)!!.background.alpha = 60
            bottomSheetDialogUpdate.findViewById<View>(R.id.edit_user_update)!!.isEnabled = false
            bottomSheetDialogUpdate.findViewById<View>(R.id.edit_user_cancel)!!.isEnabled = false
            txtPerfil.visibility = View.VISIBLE
            name.visibility = View.VISIBLE
            userInfoData.visibility = View.VISIBLE
            formEditUser.visibility = View.INVISIBLE
            btnCamera.visibility = View.INVISIBLE
            btnSaveUser.visibility = View.INVISIBLE
            btnEditUser.visibility = View.VISIBLE

        }
    }

    fun setInformationUser(){

        name.text = UserInAppCustom.user!!.name + ' ' + UserInAppCustom.user!!.lastname
        phone.setText(UserInAppCustom.user!!.phone)
        membership.isVisible = UserInAppCustom.user!!.membership
        println(UserInAppCustom.user!!.image?.url)
        if (UserInAppCustom.user!!.image?.url != null) {
            Glide.with(root.context).load(UserInAppCustom.user!!.image?.url).into(photo)
        } else {
            photo.setBackgroundResource(R.drawable.profile_user_image_default)
        }
    }

    fun setInformationMembership(){

        val existMembership:Boolean
        if (UserInAppCustom.membership!!.id_membership == 0){
            inputMembership.setText("Sin membresía")
            existMembership = false
        } else {
            val currentDate = Date()

            val time_elapsed:Long = UserInAppCustom.membership!!.expiration_date.time - currentDate.time

            val unit = TimeUnit.DAYS

            val days = unit.convert(time_elapsed, TimeUnit.MILLISECONDS) //Días restantes


            val remainingDays = formatRemainingDays(days)

            inputMembership.setText(remainingDays)
            switch.isEnabled = true
            val activeNotification = ForInternalStorageNotification.loadStateNotification(context)
            if (activeNotification!= null){
                switch.isChecked = activeNotification
            }
            existMembership = true
        }

        UserInAppCustom.user!!.membership = existMembership

    }

    fun formatRemainingDays(days:Long):String {
        var remainingDays = ""
//        println( "Cantidad de días -> $days")
//        println(days/30)
        if (days>30L){
            remainingDays += "${days/30} mes y ${days%30} días"
        } else if (days == 30L ) {
            remainingDays += "${days/30} mes"
        } else {
            remainingDays += "${days%30} días"
        }

        return remainingDays

    }

    fun checkUser(){

        val timerForCheckUser = GlobalScope.launch(Dispatchers.Main) {

            do {
                if (UserInAppCustom.user != null) {
                    setInformationUser()

                    cancel()
                }
                delay(3000)
            } while (true)
        }

        val timerForCheckMembership = GlobalScope.launch(Dispatchers.Main){

            do {

                if (UserInAppCustom.membership != null) {

                    setInformationMembership()

                    cancel()
                }

                delay(3000)

            } while (true)

        }

    }

    private fun checkPermissionsForGalery(type:Int) {
        //Verificación de la versión de android
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)  ==
                PackageManager.PERMISSION_DENIED){
                // Si no tiene permisos, lo pedimos
                val filePermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requireActivity().requestPermissions(filePermission, REQUEST_UPDATE_GALERY)
            } else {
                // Sí tiene permisos
                openGalery(type)
            }
        } else {
            // Versión de lollipop hacia abajo tienen permisos por defecto
            openGalery(type)
        }
    }
    // El type es para saber si se abrirá para actualizar o registrar
    private fun openGalery(type:Int){
        val intentGalery = Intent(Intent.ACTION_PICK)
        intentGalery.type = "image/*"
        if (type == 1) {
            startActivityForResult(intentGalery, REQUEST_UPDATE_GALERY)
        } else if (type == 2) {
            startActivityForResult(intentGalery, REQUEST_POST_GALERY)
        }
        Toast.makeText(context, "Open galery...", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_UPDATE_GALERY){

            uriImageUpdate = data?.data!!
            photo.setImageURI(uriImageUpdate)
        }
    }

    private fun processImage(uri: Uri?): MultipartBody.Part {
        val filesDir = context?.filesDir
        val file = java.io.File(filesDir, "image.png")
        val inputStream = context?.contentResolver?.openInputStream(uri!!)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("multipartFile", file.name, requestBody)
    }

    private fun validateUpdate():Boolean{
        val checks = arrayOf(
            ForValidations.valInput(txtName,txtNameError, ForValidations::valOnlyText),
            ForValidations.valInput(txtLastname,txtLastnameError, ForValidations::valOnlyText),
            ForValidations.valInput(txtPhone, txtPhoneError,ForValidations::valOnlyPhone)
        )
        return !checks.contains(true)
    }


    private fun putUserData(email: String){
        val apiService: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)
        val resultUser: Call<User> = apiService.putUser(UserCustom(txtName.text.toString(), txtLastname.text.toString(),
            txtPhone.text.toString(), UserInAppCustom.user?.image!!, UserInAppCustom.user?.membership!!), email)
        resultUser.enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                UserInAppCustom.user = response.body()
                checkUser()
                bottomSheetDialogUpdate.findViewById<View>(R.id.edit_user_update)!!.background.alpha = 255
                bottomSheetDialogUpdate.findViewById<View>(R.id.edit_user_update)!!.isEnabled = true
                bottomSheetDialogUpdate.findViewById<View>(R.id.edit_user_cancel)!!.isEnabled = true
                bottomSheetDialogUpdate.dismiss()
                ForInternalStorageUser.saveUser(UserInAppCustom.user, context)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context,  "No se pudo actualizar los datos", Toast.LENGTH_SHORT)
            }

        })
    }


    private fun putUserDataImage(email: String){
        val apiService: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)
        val requestIdFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), UserInAppCustom.user?.image?.id_file.toString())
        val resultFile: Call<File> = apiService.updateFile(processImage(uriImageUpdate), requestIdFile)

        resultFile.enqueue(object : Callback<File> {
            override fun onResponse(call: Call<File>, response: Response<File>){
                if (response.isSuccessful){
                    UserInAppCustom.user?.image = response.body()!!
                    putUserData(email)
                    uriImageUpdate = null
                }
            }
            override fun onFailure(call:Call<File>, t:Throwable){
                println("Error: updateFile() failure")
                println(t.message)
            }
        })
    }
}