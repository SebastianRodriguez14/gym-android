package com.tecfit.gym_android.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.switchmaterial.SwitchMaterial
import com.tecfit.gym_android.R
import com.tecfit.gym_android.databinding.BottomSheetDialogDetailProductBinding
import com.tecfit.gym_android.fragments.adapter.ProductAdapter
import com.tecfit.gym_android.models.Product
import com.tecfit.gym_android.models.custom.ArraysForClass
import com.tecfit.gym_android.models.custom.SelectedClasses
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterProducts {
    companion object {
        var nameProduct:String = ""
        var availableProduct:Boolean = false
        fun applyFilters(products: List<Product>): List<Product> {
            val filteredProducts = products.filter { product ->
                val checkName = if(nameProduct == "") true else product.name.lowercase().startsWith(nameProduct.lowercase())
                val checkAvailable = if (availableProduct) product.status else true
                checkName && checkAvailable
            }
            return filteredProducts
        }
    }
}

class ProductFragment : Fragment() {

    private lateinit var root:View
    private lateinit var recyclerView: RecyclerView
    private lateinit var switch: SwitchMaterial
    private lateinit var inputNameProduct : EditText
    private lateinit var listProductsLinearLayout: LinearLayout
    private lateinit var listProductsVoidLinearLayout: LinearLayout

    private lateinit var bottomSheetDialogDetailProduct: BottomSheetDialog
    private lateinit var bottomSheetViewDetail:View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_products, container, false)
        recyclerView = root.findViewById(R.id.recyclerview_products)
        createDetailDialog()
        val gridLayoutManager = GridLayoutManager(root.context, 2)
        gridLayoutManager.widthMode
        recyclerView.layoutManager = gridLayoutManager

        listProductsLinearLayout = root.findViewById(R.id.products_list_linear)
        listProductsVoidLinearLayout = root.findViewById(R.id.products_list_void_linear)

        if (ArraysForClass.arrayProducts == null){
            apiGetProducts()
        } else {
            setArrayForRecycler()
        }

        switch = root.findViewById(R.id.products_switch)
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (ArraysForClass.arrayProducts != null) {
                FilterProducts.availableProduct = isChecked
                setArrayForRecycler(true)
          }
            //            else{
//                initRecyclerView(R.id.recyclerview_products)
//            }

        }

        inputNameProduct = root.findViewById(R.id.product_input_name)
        inputNameProduct.addTextChangedListener(object : TextWatcher  {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (ArraysForClass.arrayProducts != null) {
                    FilterProducts.nameProduct = s.toString()
                    setArrayForRecycler(true)
                }
            }
        })



        return root
    }

//    private fun initRecyclerView(id: Int) {
//        val recyclerView=root.findViewById<RecyclerView>(id)
//        recyclerView.layoutManager=LinearLayoutManager(root.context)
//        recyclerView.adapter= ArraysForClass.arrayProducts?.let { ProductAdapter(it, bottomSheetDialogDetailProductBinding) }
//    }

    private fun createDetailDialog() {
        bottomSheetDialogDetailProduct=BottomSheetDialog(requireActivity(),R.style.BottonSheetDialog)
        bottomSheetViewDetail= layoutInflater.inflate(R.layout.bottom_sheet_dialog_detail_product,null)
        bottomSheetDialogDetailProduct.setContentView(bottomSheetViewDetail)
        bottomSheetDialogDetailProduct.setOnShowListener {
            if(SelectedClasses.productSelected.discount > 0){
                bottomSheetViewDetail.findViewById<TextView>(R.id.detail_product_discount).isVisible = true
                bottomSheetViewDetail.findViewById<TextView>(R.id.detail_product_discount).text = "Sale ${SelectedClasses.productSelected.discount.toInt()}%"
            }else{
                bottomSheetViewDetail.findViewById<TextView>(R.id.detail_product_discount).isVisible = false
            }
            if(!SelectedClasses.productSelected.status){
                bottomSheetViewDetail.findViewById<ImageView>(R.id.detail_image_product).setColorFilter(Color.parseColor("#34E10A0A"))
            }else{
                bottomSheetViewDetail.findViewById<ImageView>(R.id.detail_image_product).setColorFilter(Color.TRANSPARENT)
            }
            Glide.with(this).load(SelectedClasses.productSelected.image.url).into(bottomSheetViewDetail.findViewById(R.id.detail_image_product))
            bottomSheetViewDetail.findViewById<TextView>(R.id.detail_product_description).text = SelectedClasses.productSelected.name
            bottomSheetViewDetail.findViewById<TextView>(R.id.detail_product_price).text = "S/. %.2f".format(SelectedClasses.productSelected.price)
        }

    }


    private fun setArrayForRecycler(filter:Boolean = false) {
        var products = if (!filter) ArraysForClass.arrayProducts!! else FilterProducts.applyFilters(
            ArraysForClass.arrayProducts!!
        )

        recyclerView.adapter = ProductAdapter(products,bottomSheetDialogDetailProduct)

        listProductsLinearLayout.isVisible = products.isNotEmpty()
        listProductsVoidLinearLayout.isVisible = products.isEmpty()


    }

    private fun apiGetProducts() {

        val apiService:ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val resultProducts:Call<List<Product>> = apiService.getProducts()

        resultProducts.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val listProducts = response.body()
                if (listProducts != null) {
                    ArraysForClass.arrayProducts = listProducts
                    setArrayForRecycler()
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                println("Error: getProducts() failure")
                apiGetProducts()
            }
        })

    }

}

