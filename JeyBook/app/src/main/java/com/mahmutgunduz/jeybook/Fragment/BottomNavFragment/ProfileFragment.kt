package com.mahmutgunduz.jeybook.Fragment.BottomNavFragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.View.LoginActivity
import com.mahmutgunduz.jeybook.databinding.DrawerHeaderBinding
import com.mahmutgunduz.jeybook.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso
import java.util.UUID

class ProfileFragment : Fragment() {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var storage: FirebaseStorage

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    var selecetedImage: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        setupActivityResultLauncher()
        setupPermissionLauncher()

        imgOn()
        getData()
        profilePhotoGetData()  // Fotoğraf URL'sini çek

        binding.imageViewSettings.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)

            getdrawerData()
            val navigationView = binding.navigationView
            val headerView: View =
                navigationView.getHeaderView(0) // 0, ilk header'ı temsil eder
            val headerBinding: DrawerHeaderBinding =
                DrawerHeaderBinding.bind(headerView) // Header binding



            val drawer = binding.drawerLayout
            val drawerId = drawer.id
            val drawerLayout = binding.drawerLayout






            navigatonItem()
        }
    }

    fun getdrawerData() {
        firestore.collection("Posts")
           .addSnapshotListener { value, error ->

                if (error != null) {
                    //hataa
                    Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    if (value != null) {
                        val documents = value.documents
                        for (documentss in documents) {

                            val userId = documentss.get("userId") as? String

                            if (userId != null) {
                                firestore.collection("users").whereEqualTo("userId", userId).addSnapshotListener { value, error ->
                                    if (value != null) {
                                        val  dc = value.documents


                                        if (error != null) {
                                            //hataa
                                            Toast.makeText(
                                                requireContext(),
                                                error.localizedMessage,
                                                Toast.LENGTH_LONG
                                            )
                                                .show()
                                        } else {
                                            val navigationView = binding.navigationView
                                            val headerView: View = navigationView.getHeaderView(0) // 0, ilk header'ı temsil eder
                                            val headerBinding: DrawerHeaderBinding = DrawerHeaderBinding.bind(headerView) // Header binding

                                            for (document in dc) {

                                                val namee = document.get("name") as? String
                                                    ?: "Profil adı gelmedi"
                                                println("-----------------------------------------"+namee)


                                                val  username = document.get("email") as? String
                                                val profileImageUrl =
                                                    document.get("profileImageUrl") as? String
                                                        ?: " profılefoto gelmedı "


                                                headerBinding.name.text=namee
                                                headerBinding.email.text=username
                                                Picasso.get().load(profileImageUrl).into(headerBinding.profileImage)

                                            }

                                        }
                                    }
                                }

                            }else{
                                println("-------------user null----------------------------")
                            }
                        }
                        //bir degerler vermiss.
                    }
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    val name = document.getString("name")
                    val email = auth.currentUser?.email

                    // binding'in null olmadığını kontrol edin
                    if (_binding != null) {
                        binding.textViewName.text = name ?: "İsim yok"
                        binding.textViewUsername.text = email ?: "Mail yok"
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("UserProfile", "Hata", exception)
                }
        }
    }


    private fun navigatonItem() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.hesap -> true
                R.id.abonelik -> true
                R.id.uygulama -> true
                R.id.gizlilik -> true
                R.id.yardim -> true
                R.id.sartlar -> true
                R.id.cikis -> {
                    auth.signOut()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun imgOn() {
        binding.imgProfile.setOnClickListener {
            val context = requireContext()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Android 33 ve üzeri  READ_MEDIA_IMAGES
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_MEDIA_IMAGES
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.READ_MEDIA_IMAGES
                        )
                    ) {
                        Snackbar.make(binding.root, "İzin gerekli", Snackbar.LENGTH_INDEFINITE)
                            .setAction("İzin Ver") {
                                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                            }.show()
                    } else {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }
                } else {
                    galleryOpen()
                }
            } else {
                // Android 32 ve altı  READ_EXTERNAL_STORAGE
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    ) {
                        Snackbar.make(binding.root, "İzin gerekli", Snackbar.LENGTH_INDEFINITE)
                            .setAction("İzin Ver") {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }.show()
                    } else {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                } else {
                    galleryOpen()
                }
            }
        }
    }

    private fun setupActivityResultLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val imageUri = result.data?.data
                    if (imageUri != null) {
                        selecetedImage = imageUri
                        binding.imgProfile.setImageURI(imageUri)
                        updateProfilePhoto()
                    }
                }
            }
    }

    private fun setupPermissionLauncher() {
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    galleryOpen()
                } else {
                    Snackbar.make(binding.root, "İzin yok ", Snackbar.LENGTH_LONG).show()
                }
            }
    }

    private fun galleryOpen() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityResultLauncher.launch(intent)
    }

    // Profil fotoğrafı yükleyen metod.
    private fun updateProfilePhoto() {


        val userId = auth.currentUser?.uid
        if (userId != null && selecetedImage != null) {
            val uuid = UUID.randomUUID()
            val imageName = "$uuid.jpg"
            val reference = storage.reference
            val imageReference =
                reference.child("profileimages/$userId/$imageName") // Kullanıcıya özgü dizin.


            imageReference.putFile(selecetedImage!!)
                .addOnSuccessListener {

                    imageReference.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString() // Fotoğrafın Firebase URL'si.

                        // Fotoğraf URL'sini Firestore'da kullanıcının profil bilgilerine kaydediyoruz.
                        firestore.collection("users").document(userId)
                            .update(
                                "profileImageUrl",
                                downloadUrl
                            )
                            .addOnSuccessListener {
                                Toast.makeText(
                                    requireContext(),
                                    "Profil fotoğrafı güncellendi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    requireContext(),
                                    "Fotoğraf güncellenemedi: ${e.localizedMessage}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        requireContext(),
                        "Fotoğraf yüklenemedi: ${exception.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(
                requireContext(),
                "Kullanıcı oturumu yok veya fotoğraf seçilmedi",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun profilePhotoGetData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    val url = document.getString("profileImageUrl")
                    if (url != null && _binding != null) { // Null kontrolü
                        Picasso.get()
                            .load(url)
                            .into(binding.imgProfile)


                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        requireContext(),
                        "Fotoğraf yüklenemedi: ${e.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }


}
