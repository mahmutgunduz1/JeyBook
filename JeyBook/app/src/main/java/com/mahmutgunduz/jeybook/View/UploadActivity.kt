package com.mahmutgunduz.jeybook.View

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat

import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage


import com.mahmutgunduz.jeybook.databinding.ActivityUploadBinding
import java.util.UUID

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding

    private lateinit var activtyResultLauncer: ActivityResultLauncher<Intent>
    private lateinit var storage: FirebaseStorage
    private lateinit var permissionLauncler: ActivityResultLauncher<String>
    private lateinit var fireStore: FirebaseFirestore


    private lateinit var auth: FirebaseAuth


    var selecetedImage: Uri? = null
    //hangi izin iznin türü string


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        fireStore = Firebase.firestore
        storage = Firebase.storage

        binding.showButton.setOnClickListener(View.OnClickListener {
            val input2 = binding.editTxt.text.toString()
            Toast.makeText(this@UploadActivity, " input 2 " + input2, Toast.LENGTH_LONG).show()

        })





        registerLuncer()


    }


    /*    fun progressBar(view: View){
          binding.  progressBar.visibility = View.VISIBLE


            Handler(Looper.getMainLooper()).postDelayed({

              binding.progressBar.visibility = View.GONE

            }, 2000)

        }
        */

    // view.isEnabled=false
    //  progressBar(view)


    fun shareButton(view: View) {

        view.isEnabled = false


        val comment = binding.editTxt.text.toString()


        if (selecetedImage != null || comment.isNotEmpty()) {


            val uuid = UUID.randomUUID()
            val imageName = "$uuid.jpg"

            val reference = storage.reference
            val imageReference = reference.child("images").child(imageName)


            imageReference.putFile(selecetedImage!!).addOnSuccessListener {
                // urlyi al fireStore yükle
                //        val uploadReference = storage.reference.child("images").child(imageName)

                imageReference.downloadUrl.addOnSuccessListener {
                    val downlandUrl = it.toString()

                    val postMap = hashMapOf<String, Any>()
                    postMap.put("userId", auth.currentUser!!.uid)
                    postMap.put("downloadUrl", downlandUrl)
                    postMap.put("comment", comment)
                    postMap.put("date", FieldValue.serverTimestamp())
                    fireStore.collection("Posts").add(postMap).addOnSuccessListener { document ->
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this@UploadActivity, "  başarısısızzzzz ", Toast.LENGTH_LONG)
                            .show()
                    }

                }.addOnFailureListener {
                }
            }
        } else {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Uyarı")
            builder.setMessage("Fotoğraf seçmediniz devam etmek istiyor  musunuz?")
            builder.setPositiveButton("Evet") { dialog, which ->
                postWithoutImage(comment)
                Toast.makeText(applicationContext, "İşlem onaylandı.", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Hayır") { dialog, which ->

                dialog.dismiss() // Dialog'u kapat
                view.isEnabled = true
            }
            builder.setNeutralButton("Daha sonra") { dialog, which ->
                // Neutral butonuna tıklanınca yapılacak işlemler
                Toast.makeText(applicationContext, "İşlem ertelendi.", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }
    }
    private fun postWithoutImage(comment: String) {
        val postMap = hashMapOf<String, Any>(
            "comment" to comment,
            "date" to FieldValue.serverTimestamp()
        )
        fireStore.collection("Posts").add(postMap)
            .addOnSuccessListener {
                finish()
                Toast.makeText(this@UploadActivity, "Başarıyla paylaşıldı", Toast.LENGTH_LONG)
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(this@UploadActivity, "Paylaşım başarısız", Toast.LENGTH_LONG).show()
            }
    }

    fun select(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // izin yok
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this@UploadActivity,
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                ) {
                    Snackbar.make(view, " izin  er amıankee ", Snackbar.LENGTH_INDEFINITE)
                        .setAction("  izin veriir misiinnn ") {
                            // izin verme mantığını çalışyırım mı
                            permissionLauncler.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }.show()
                } else {
                    permissionLauncler.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            } else {
                // izin verilmiş galeriye git
                val intnetToGalery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                activtyResultLauncer.launch(intnetToGalery)
            }

        } else {
            // android 32
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                // izin yok

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this@UploadActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    Snackbar.make(view, " izin  er amıankee ", Snackbar.LENGTH_INDEFINITE)
                        .setAction("  izin veriir misiinnn ") {


                            // izin verme mantığını çalışyırım mı

                            permissionLauncler.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

                        }.show()


                } else {

                    permissionLauncler.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

                }
            } else {

                // izin verilmiş galeriye git

                val intnetToGalery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                activtyResultLauncer.launch(intnetToGalery)
            }
        }


        //-----------------------------------------------------

    }


    fun registerLuncer() {
        activtyResultLauncer =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->


                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {

                        intentFromResult?.data?.let {

                            selecetedImage = it
                            binding.animationView.setImageURI(it)
                        }

                        // burda imageye yüklicez.
                    }

                }

            }


        permissionLauncler =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { resullt ->
                if (resullt) {


                    val intnetToGalery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                    activtyResultLauncer.launch(intnetToGalery)


                } else {


                    Toast.makeText(this@UploadActivity, " izin ver annı sıkerm ", Toast.LENGTH_LONG)
                        .show()


                }

            }


    }


}
