package com.example.project1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_activity)
        // Charger les animations
        val topAnim = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnim = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        // Récupérer les vues
        val image = findViewById<ImageView>(R.id.img)
        val cardView = findViewById<CardView>(R.id.card)

        // Appliquer les animations
        image.startAnimation(topAnim)
        cardView.startAnimation(bottomAnim)

        //overridePendingTransition(R.anim.top_animation, R.anim.bottom_animation)


    }
}