package com.example.wareline_photos_app.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.wareline_photos_app.Adapters.rcv_Adapter
import com.example.wareline_photos_app.R
import com.example.wareline_photos_app.dataClass.photo_data
import com.example.wareline_photos_app.fetchApiData.Singleton_request_queue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var txt : EditText
    private lateinit var btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn1)
        txt = findViewById(R.id.edittext)
        rv = findViewById(R.id.rcv_main)

        fetchPhotos("Random", 500)

        btn.setOnClickListener {
            fetchPhotos(txt.text.toString(), 500)
            txt.text.clear()
        }

        rv.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchPhotos(query: String, perPage: Int) {
        val url = "https://api.pexels.com/v1/search?query=$query&per_page=$perPage"
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val gson = Gson()
                val photosArray = response.getJSONArray("photos")
                val photoListType = object : TypeToken<List<photo_data>>() {}.type
                val photos: List<photo_data> = gson.fromJson(photosArray.toString(), photoListType)
                Log.i("Photos_URL",photos[0].src.original)
                val RcvAdapter = rcv_Adapter(this,photos.removeDuplicatesById())
                rv.adapter= RcvAdapter

            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "aOTs3U5gYxAndZVMu4Q1CFrCuoDyjfDXNPioiWHbQ5w0BS8eENfdo0Pa"
                return headers
            }
        }

        Singleton_request_queue.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun List<photo_data>.removeDuplicatesById(): List<photo_data> {
        return this.distinctBy { it.id }
    }
}