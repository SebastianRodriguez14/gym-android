package com.tecfit.gym_android.models.custom

import com.tecfit.gym_android.models.BodyPart
import com.tecfit.gym_android.models.Exercise
import com.tecfit.gym_android.models.Product
import com.tecfit.gym_android.models.Routine

// En esta clase guardan la selección del usuario para pasar datos entre los fragmentos
// Hay otra forma para hacer esto pero me dio flojera averiguar O_o
class SelectedClasses {

    companion object {

        lateinit var bodyPart:BodyPart  // Para pasar entre el fragment de rutinas y el listado el id de la parte del cuerpo y su nombre
        lateinit var routine:Routine
        lateinit var exercise: Exercise
        lateinit var productSelected:Product
    }


}