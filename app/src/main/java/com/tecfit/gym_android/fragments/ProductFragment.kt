package com.tecfit.gym_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
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
import kotlin.jvm.internal.PropertyReference0Impl

class FilterProducts {
    companion object {
        var nameProduct:String = ""
        var availableProduct:Boolean = false

        fun applyFilters(products: List<Product>): List<Product> {

            val filteredProducts = products.filter { product ->
                val checkName = product.name.lowercase().startsWith(nameProduct.lowercase())
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_products, container, false)
        recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview_products)
        val gridLayoutManager = GridLayoutManager(root.context, 2)
        gridLayoutManager.widthMode
        recyclerView.layoutManager = gridLayoutManager

        if (ArraysForClass.arrayProducts == null){
            apiGetProducts()
        } else {
            setArrayForRecycler(ArraysForClass.arrayProducts!!)
        }

        switch = root.findViewById(R.id.products_switch)
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            FilterProducts.availableProduct = isChecked
            setArrayForRecycler(FilterProducts.applyFilters(ArraysForClass.arrayProducts!!))
        }



        return root
    }


    private fun setArrayForRecycler(products:List<Product>) {
        recyclerView.adapter = ProductAdapter(products)
    }

    private fun apiGetProducts() {

        val apiService:ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val resultProducts:Call<List<Product>> = apiService.getProducts()

        resultProducts.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val listProducts = response.body()

                if (listProducts != null) {
                    ArraysForClass.arrayProducts = listProducts
                    setArrayForRecycler(listProducts)
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                println("Error: getProducts() failure")
            }
        })

    }

}

