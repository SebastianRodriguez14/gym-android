package com.tecfit.gym_android.models.custom

import kotlin.properties.Delegates

class ByRandom {
    companion object {

        var byBodyPart by Delegates.notNull<Boolean>()

    }
}