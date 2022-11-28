package com.tecfit.gym_android.fragments.adapter

import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tecfit.gym_android.R
import com.tecfit.gym_android.databinding.BottomSheetDialogDetailProductBinding
import com.tecfit.gym_android.models.Product
import com.tecfit.gym_android.models.custom.SelectedClasses

class ProductViewHolder(val view:View, val bottomSheetDialog: BottomSheetDialog) :RecyclerView.ViewHolder(view){

    //Propiedades normales de los productos
    val pr_image = view.findViewById<ImageView>(R.id.item_product_image)
    val pr_description = view.findViewById<TextView>(R.id.item_product_description)
    val pr_price = view.findViewById<TextView>(R.id.item_product_price)
    val pr_linear=view.findViewById<ImageView>(R.id.item_product_image)


    //El fondo rojo para productos no disponibles -> cambiar background a #34E10A0A en caso de no estar disponible
    val pr_status_background = view.findViewById<FrameLayout>(R.id.item_product_status_background)

    //El círculo verde para productos disponibles -> cambiar visibility a false en caso de no estar disponible
    val pr_status = view.findViewById<LinearLayout>(R.id.item_product_status)

    //La parte donde se visualiza el descuento -> cambiar visibility a false en caso de no estar disponible
    val pr_discount = view.findViewById<TextView>(R.id.item_product_discount)

    fun render(product:Product) {

        //Acá se realizará la modificación cuando se use el cloudinary correctamente
        Glide.with(view.context).load(product.image.url).into(pr_image)
        pr_description.text = product.name
        pr_price.text = "S/. %.2f".format(product.price)

        //Verificación de descuento
        if (product.discount > 0){
            pr_discount.visibility = View.VISIBLE
            pr_discount.text = "Sale " + Math.round(product.discount) + "%"
        }else{
            pr_discount.visibility = View.INVISIBLE
        }

        if (!product.status){
            pr_status_background.setBackgroundColor(Color.parseColor("#34E10A0A"))
            pr_status.visibility = View.INVISIBLE
            pr_image.setColorFilter(Color.parseColor("#34E10A0A"))
        }else{
            pr_status_background.setBackgroundColor(Color.TRANSPARENT)
            pr_status.visibility = View.VISIBLE
            pr_image.setColorFilter(Color.TRANSPARENT)
        }

       pr_linear.setOnClickListener{
           println("AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIII DETAIL PRODUCT")
           SelectedClasses.productSelected = product
           bottomSheetDialog.show()
       }

    }

}