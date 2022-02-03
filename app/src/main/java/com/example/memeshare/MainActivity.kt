package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ProgressBar.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import android.view.View.VISIBLE as VISIBLE1

//import kotlin.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    private fun loadMeme(){

    // Instantiate the RequestQueue.

//        progressBar
        ProgressBar.VISIBLE

        val url = "https://meme-api.herokuapp.com/gimme"

    // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageUrl = response.getString("url")

                val meme: ImageView = findViewById(R.id.memeImageView)
                print(url)
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>  {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        ProgressBar.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        ProgressBar.VISIBLE
                        return false
                    }
                }).into(meme)

            },
            {
                Toast.makeText(this,"Something went wrong!", Toast.LENGTH_LONG).show()


            })

    // Add the request to the RequestQueue.
     MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey! Checkout this reddit meme, I found on MemeShare $currentImageUrl")
        val chooser = Intent.createChooser(intent, "Share this meme using MemeShare")
        startActivity(chooser)

    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}