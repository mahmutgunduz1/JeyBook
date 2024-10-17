package com.mahmutgunduz.jeybook.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth=Firebase.auth

        firestore=Firebase.firestore
    }

    fun signUp(view: View) {




        val name = binding.nameEditText.text.toString()

        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val passwordAgain = binding.againPasswordEditText.text.toString()


        if (name.equals("") || email.equals("") || password.equals("") || passwordAgain.equals("")) {







            // burası boş değer olunca
            Toast.makeText(this@SignUpActivity," Lütfen gerekli yerleri doldurun !!!  ",Toast.LENGTH_LONG).show()
        }else{

            view.isEnabled=true




            if (password== passwordAgain){
                auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener { resulsts ->



                    view.isEnabled=true
                    val userId = resulsts.user?.uid

                    if (userId !=null){
                        val userss = hashMapOf(
                           "name" to name
                            ,"email" to email
                            ,"password" to password
                            ,"userId" to userId


                        )


                        firestore.collection("users").document(userId).set(userss).addOnSuccessListener {




                            auth.signOut()
                            val intent =Intent(this@SignUpActivity,LoginActivity::class.java)

                            intent.putExtra("name",name)
                            startActivity(intent)
                            finish()

                            Toast.makeText(this@SignUpActivity," Bilgiler kaydedildi Giris yap !!!  ",Toast.LENGTH_LONG).show()


                        }.addOnFailureListener {
                            Toast.makeText(this@SignUpActivity," Bilgiler kaydedilmedii !!!  ",Toast.LENGTH_LONG).show()

                        }



                    }




                }.addOnFailureListener {

                    Toast.makeText(this@SignUpActivity," kayıt başarısızz !!!  ",Toast.LENGTH_LONG).show()
                }








            }else{

                Toast.makeText(this@SignUpActivity," şifreler uyuşmuyor  !!!  ",Toast.LENGTH_LONG).show()
            }






        }

    }
}