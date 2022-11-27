package com.tecfit.gym_android.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tecfit.gym_android.R
import com.tecfit.gym_android.databinding.BottomSheetDialogDetailProductBinding
import com.tecfit.gym_android.models.Product

class ProductAdapter(private val productList: List<Product>, val bottomSheetDialogDetailProduct : BottomSheetDialog) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false), bottomSheetDialogDetailProduct)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val item = productList[position]
        holder.render(item)

    }

    override fun getItemCount(): Int = productList.size
}