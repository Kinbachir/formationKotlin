package com.orange.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.orange.pokemon.databinding.ActivityMainBinding
import com.orange.pokemon.model.Pokemon
import com.orange.pokemon.networking.ApiService
import com.orange.pokemon.networking.NetworkClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val service = NetworkClient().getRetrofit().create(ApiService::class.java)
        service.getAllPokemons().enqueue(object : Callback<List<Pokemon>> {
            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                if (response.isSuccessful) {
                    Log.e(TAG, "onResponse: ${response.body()?.get(0)}")
                    Glide.with(this@MainActivity)
                        .load(response.body()?.get(0)!!.imageurl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_android_black_24dp)
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(binding.imageView)
                }
            }

            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
            }

        })
    }
}