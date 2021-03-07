package com.orange.pokemon

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.orange.pokemon.adapter.PokemoneAdapter
import com.orange.pokemon.data.PokemonDatabase
import com.orange.pokemon.data.PokemonEntity
import com.orange.pokemon.databinding.ActivityMainBinding
import com.orange.pokemon.model.Pokemon
import com.orange.pokemon.networking.ApiService
import com.orange.pokemon.networking.NetworkClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"
var adt=PokemoneAdapter()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val isConnected: Boolean=(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected == true
        val db = PokemonDatabase.getInstance(this@MainActivity)
        val userDao = db.getPokemon()
        if(isConnected) {
            lifecycleScope.launch {
                userDao.deleteAll()
            }
            val service = NetworkClient().getRetrofit().create(ApiService::class.java)
            service.getAllPokemons().enqueue(object : Callback<List<Pokemon>> {
                override fun onResponse(
                    call: Call<List<Pokemon>>,
                    response: Response<List<Pokemon>>
                ) {
                    if (response.isSuccessful) {
                        Log.e(TAG, "onResponse: ${response.body()?.get(0)}")
                        val listIterator = response.body()?.listIterator()
                        lifecycleScope.launch {
                            while (listIterator!!.hasNext()) {
                                userDao.insertOne(PokemonEntity.ModelMapper.from(listIterator!!.next()))
                            }
                            binding.listRecyclerView.apply {
                                layoutManager = LinearLayoutManager(this@MainActivity)
                                adapter = adt
                            }
                            adt.submitList(userDao.getAll())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ", t)
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
                }
            })
        }
        else {
            lifecycleScope.launch {

                binding.listRecyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = adt
                }
                adt.submitList(userDao.getAll())
            }
        }
    }
}