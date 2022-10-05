package com.tecfit.gym_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.fragments.adapter.ProductAdapter
import com.tecfit.gym_android.models.Product
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class ProductFragment : Fragment() {


    private lateinit var productsList:List<Product>
    private lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_products, container, false)


        apiGetProducts()
        return root
    }

    private fun initRecyclerView() {
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview_products)
        val gridLayoutManager = GridLayoutManager(root.context, 2)
        gridLayoutManager.widthMode

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = ProductAdapter(productsList)
    }

    private fun apiGetProducts() {

        val apiService:ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val resultProducts:Call<List<Product>> = apiService.getProducts()

        resultProducts.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val listProducts = response.body()

                if (listProducts != null) {

                    productsList = listProducts
                    initRecyclerView()


                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                println("Error: getProducts() failure")
            }
        })

    }

}

