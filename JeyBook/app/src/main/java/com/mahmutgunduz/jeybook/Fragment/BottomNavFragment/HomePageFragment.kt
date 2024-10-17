package com.mahmutgunduz.jeybook.Fragment.BottomNavFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mahmutgunduz.jeybook.Adapter.HomePagePostAdapter
import com.mahmutgunduz.jeybook.Model.PostModel
import com.mahmutgunduz.jeybook.Model.PostProfileModel
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.View.BookLoadActivity
import com.mahmutgunduz.jeybook.View.UploadActivity
import com.mahmutgunduz.jeybook.databinding.FragmentHomePageBinding
import java.util.Date


class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private var isExpanded = false
    lateinit var auth: FirebaseAuth

    lateinit var storage: FirebaseStorage
    private lateinit var db: FirebaseFirestore// database


    lateinit var postAdapter: HomePagePostAdapter

    lateinit var postArrayList: ArrayList<PostModel>
    lateinit var postListProfile: ArrayList<PostProfileModel>

    private val fromBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_fab)
    }
    private val toBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_fab)
    }
    private val rotateClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clock_wise)
    }
    private val rotateAntiClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_anti_clock_wise)
    }
    private val fromBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim)
    }
    private val toBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postArrayList = ArrayList<PostModel>()
        postListProfile = ArrayList<PostProfileModel>()
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        binding.rcv.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = HomePagePostAdapter(requireContext(), postArrayList, postListProfile)
        binding.rcv.adapter = postAdapter
        getData()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {


            if (isExpanded) {
                shrink()
            } else {
                requireActivity().onBackPressed()
            }
        }
        super.onViewCreated(view, savedInstanceState)


        binding.mainFabBtn.setOnClickListener {
            if (isExpanded) {
                shrink()
            } else {
                expand()
            }
        }

        binding.sendFabBtn.setOnClickListener {

            val intent = Intent(activity,BookLoadActivity ::class.java)
            startActivity(intent)
        }
        binding.shareFabBtn.setOnClickListener {
            bottomSheet()
        }
        binding.galleryFabBtn.setOnClickListener {
            val intent = Intent(activity, UploadActivity::class.java)
            startActivity(intent)
            // buradan bir sayfafay gidicen orada text ve image secicen falan
        }
    }

    private fun getTimeAgo(timestamp: Date): String {
        val now = Date()
        val seconds = (now.time - timestamp.time) / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            seconds < 60 -> "${seconds.toInt()} saniye önce"
            minutes < 60 -> "${minutes.toInt()} dakika önce"
            hours < 24 -> "${hours.toInt()} saat önce"
            else -> "${days.toInt()} gün önce"
        }
    }

    private fun bottomSheet(){





        // BottomSheetDialog oluştur
        val bottomSheetDialog = BottomSheetDialog(requireContext())

        // BottomSheet layoutunu dialoga bağla
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom, null)
        bottomSheetDialog.setContentView(bottomSheetView)
        BottomSheetBehavior.from(bottomSheetView.parent as View).state = BottomSheetBehavior.STATE_EXPANDED

        // Dialogu göster
        bottomSheetDialog.show()

    }
    private fun getData() {



        db.collection("Posts")
            .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            postArrayList.clear()
            if (error != null) {
                //hataa
                Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    val documents = value.documents
                    for (documentss in documents) {
                        val timestamp = documentss.getTimestamp("date")
                        val date: Date? = timestamp?.toDate()
                        val datee = if (date != null) {
                            getTimeAgo(date)
                        } else {
                            "Tarih bilinmiyor"
                        }


                        val comment = documentss.get("comment") as String
                        val downloadUrl = documentss.get("downloadUrl") as? String ?: " url gelmedı hjaaa"
                        val userId = documentss.get("userId") as? String
                        val postName =documentss.get("postName")  as? String   ?: " post name yok "



                        if (userId != null) {
                            db.collection("users").whereEqualTo("userId", userId).addSnapshotListener { value, error ->
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

                                        for (document in dc) {

                                            val namee = document.get("name") as? String
                                                ?: "Profil adı gelmedi"
                                            println("-----------------------------------------"+namee)


                                            val profileImageUrl =
                                                document.get("profileImageUrl") as? String
                                                    ?: " profılefoto gelmedı "
                                            val post = PostModel(
                                                comment,
                                                downloadUrl,
                                                datee,
                                                userId.toString(),
                                                downloadUrl,
                                                namee,
                                                profileImageUrl,postName
                                            )
                                            postArrayList.add(post)

                                            postAdapter.notifyDataSetChanged()
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


    private fun shrink() {
        binding.galleryFabBtn.startAnimation(toBottomFabAnim)
        //   binding.transparentBg.startAnimation(toBottomBgAnim)
        binding.mainFabBtn.startAnimation(rotateAntiClockWiseFabAnim)
        binding.galleryFabBtn.startAnimation(toBottomFabAnim)
        binding.shareFabBtn.startAnimation(toBottomFabAnim)
        binding.sendFabBtn.startAnimation(toBottomFabAnim)
        binding.galleryTv.startAnimation(toBottomFabAnim)
        binding.shareTv.startAnimation(toBottomFabAnim)
        binding.sendTv.startAnimation(toBottomFabAnim)



        isExpanded = !isExpanded
    }

    private fun expand() {

        // binding.transparentBg.startAnimation(fromBottomBgAnim)

        binding.galleryFabBtn.startAnimation(fromBottomFabAnim)
        binding.mainFabBtn.startAnimation(rotateClockWiseFabAnim)
        binding.galleryFabBtn.startAnimation(fromBottomFabAnim)
        binding.shareFabBtn.startAnimation(fromBottomFabAnim)
        binding.sendFabBtn.startAnimation(fromBottomFabAnim)
        binding.galleryTv.startAnimation(fromBottomFabAnim)
        binding.shareTv.startAnimation(fromBottomFabAnim)
        binding.sendTv.startAnimation(fromBottomFabAnim)


        isExpanded = !isExpanded
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}