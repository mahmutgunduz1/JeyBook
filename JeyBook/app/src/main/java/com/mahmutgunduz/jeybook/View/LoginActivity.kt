package com.mahmutgunduz.jeybook.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.databinding.ActivityLoginBinding
import com.mahmutgunduz.jeybook.databinding.ActivitySignUpBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var  txtNameUp:TextView

     lateinit var auth:FirebaseAuth
    private lateinit var fireStore:FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth=Firebase.auth
        fireStore=Firebase.firestore

        txtNameUp =findViewById(R.id.txtNameUp)


        val currentUser =auth.currentUser
        if (currentUser!=null){

            val intent =Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
            finish()



        }



        val name = intent.getStringExtra("name")
        if (name!=null){


      binding.txtNameUp.text = "Aramıza Hoş geldin \n   $name!"
        binding.txtNameUp.visibility = View.VISIBLE
        }else{

        binding.txtNameUp.visibility = View.INVISIBLE
        }





    }


     fun emailTxt(view: View){


         val intent = Intent(this@LoginActivity,SignUpActivity::class.java)
         startActivity(intent)


    }


    fun loginButton (view: View){

        val email =binding.emailEditText.text.toString()
        val password =binding.passwordEditText.text.toString()



        if (email.equals("")|| password.equals("")){
            Toast.makeText(this@LoginActivity," Lütfen gerekli yerleri doldurun !!!  ", Toast.LENGTH_LONG).show()


        }else{
            Toast.makeText(this@LoginActivity," başşarılı ",Toast.LENGTH_LONG).show()

            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
             //  val email = it.user?.email.toString()


                val intent =Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {


                Toast.makeText(this@LoginActivity,it.localizedMessage,Toast.LENGTH_LONG).show()


            }

        }









    }


}