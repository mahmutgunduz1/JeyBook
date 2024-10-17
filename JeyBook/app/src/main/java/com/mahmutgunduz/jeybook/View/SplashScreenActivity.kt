package com.mahmutgunduz.jeybook.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.mahmutgunduz.jeybook.R

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        auth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser

        if (currentUser != null) {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }






        button= findViewById(R.id.button)
        button.setOnClickListener(View.OnClickListener {


            val intent =Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()



        })



    }
}