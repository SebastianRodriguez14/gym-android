package com.tecfit.gym_android.models.custom

import com.tecfit.gym_android.models.File
import com.tecfit.gym_android.models.Membership

data class UserCustom(var name: String, var lastname: String, var phone: String, var image: File, var membership: Boolean)
