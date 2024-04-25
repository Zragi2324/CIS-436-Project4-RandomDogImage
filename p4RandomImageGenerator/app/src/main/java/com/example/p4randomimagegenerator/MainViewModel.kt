package com.example.p4randomimagegenerator

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import org.json.JSONException
import org.json.JSONObject

class MainViewModel : ViewModel() {


    // LiveData to hold the image URL
    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String>
        get() = _imageUrl
    fun fetchRandomImage(context: Context) {
        val imageUrl = "https://dog.ceo/api/breeds/image/random"
        val request = StringRequest(
            imageUrl,
            Response.Listener { response ->
                try {
                    // Parse the JSON response
                    val jsonResponse = JSONObject(response)
                    // Extract the image URL from the "message" field
                    val imageUrl = jsonResponse.getString("message")
                    // Set the fetched image URL to LiveData
                    _imageUrl.value = imageUrl
                } catch (e: JSONException) {
                    // Handle JSON parsing error
                    Log.e("MainViewModel", "Error parsing JSON: $e")
                }
            },
            Response.ErrorListener { error: VolleyError? ->
                Log.e("MainViewModel", "Error fetching image: $error")
            })

        // Create a request queue
        val requestQueue = Volley.newRequestQueue(context.applicationContext)
        // Add the request to the queue
        requestQueue.add(request)
    }

    fun displayImage(imageUrl: String, imageView: ImageView){
        Glide.with(imageView)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.img) // Placeholder image while loading
                    .error(R.drawable.img_1) // Image to display if loading fails
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Caches both original & resized image
            )
            .into(imageView)
    }

    }


