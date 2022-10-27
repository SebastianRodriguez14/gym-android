package com.tecfit.gym_android.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.tecfit.gym_android.R
import com.tecfit.gym_android.fragments.adapter.ProductAdapter
import com.tecfit.gym_android.models.Product
import com.tecfit.gym_android.models.custom.ArraysForClass
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

        fun validateLayouts(){

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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_products, container, false)
        recyclerView = root.findViewById(R.id.recyclerview_products)
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
            FilterProducts.availableProduct = isChecked
            setArrayForRecycler(true)
        }

        inputNameProduct = root.findViewById(R.id.product_input_name)
        inputNameProduct.addTextChangedListener(object : TextWatcher  {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                println(s.toString())
                FilterProducts.nameProduct = s.toString()
                setArrayForRecycler(true)
            }
        })



        return root
    }


    private fun setArrayForRecycler(filter:Boolean = false) {
        val products = if (!filter) ArraysForClass.arrayProducts!! else FilterProducts.applyFilters(
            ArraysForClass.arrayProducts!!
        )
        recyclerView.adapter = ProductAdapter(products)

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
            }
        })

    }

}

