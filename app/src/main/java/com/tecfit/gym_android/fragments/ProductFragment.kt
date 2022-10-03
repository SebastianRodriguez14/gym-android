package com.tecfit.gym_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.fragments.adapter.ProductAdapter
import com.tecfit.gym_android.models.Product
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class ProductFragment : Fragment() {


    private val productsList = listOf<Product>(
        Product("Creabolic (creatina) de 500grs", true, 10.0, 1, 100.0, "https://res.cloudinary.com/dsh0qgcpn/image/upload/v1664642042/Creabolic_lgrzgv.jpg"),
        Product("Nox-Up pre-entreno de 500grs", false, 0.0, 2, 80.0, "https://res.cloudinary.com/dsh0qgcpn/image/upload/v1664642767/Nox-Uppreentreno_kbwtp2.jpg")

    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root:View = inflater.inflate(R.layout.fragment_products, container, false)

        initRecyclerView(root, R.id.recyclerview_products_01)
        apiGetProducts()
        return root
    }

    private fun initRecyclerView(view:View, id:Int) {
        val recyclerView = view.findViewById<RecyclerView>(id)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = ProductAdapter(productsList)
    }

    private fun apiGetProducts() {

        val apiService:ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val resultProducts:Call<List<Product>> = apiService.getProducts()

        resultProducts.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val listProducts = response.body()

                if (listProducts != null) {
                    for (pr in listProducts){
                        println("--- Id: " + pr.id_product)
                        println("Nombre "  + pr.name)
                        println("Status "  + pr.status)
                        println("Image "  + pr.image)
                        println("Price "  + pr.price)
                        println("Discount "  + pr.discount)
                    }
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                println("Error: getProducts() failure")
            }
        })

    }

}

