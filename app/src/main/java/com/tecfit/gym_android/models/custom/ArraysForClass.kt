package com.tecfit.gym_android.models.custom

import com.tecfit.gym_android.models.BodyPart
import com.tecfit.gym_android.models.Product
import com.tecfit.gym_android.models.Trainer

class ArraysForClass {

    companion object{
        var arrayTrainers: List<Trainer>? = null
        var arrayProducts: List<Product>? = null
        var arrayBodyParts = mutableListOf<BodyPart>()
    }

}