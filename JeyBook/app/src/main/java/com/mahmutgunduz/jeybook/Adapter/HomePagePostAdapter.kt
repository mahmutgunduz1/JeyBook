package com.mahmutgunduz.jeybook.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.mahmutgunduz.jeybook.Model.PostModel
import com.mahmutgunduz.jeybook.Model.PostProfileModel
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.databinding.RecyclerRowPostBinding
import com.squareup.picasso.Picasso


class HomePagePostAdapter(
    private val context: Context,
    private val postList: ArrayList<PostModel>,
    private val postListProfile: ArrayList<PostProfileModel>

) : RecyclerView.Adapter<HomePagePostAdapter.PostViewHolder>() {

    // Firestore instance
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    private val user: FirebaseUser? = auth.currentUser
    private val nowUser: String? = user?.uid
    var likes: HashSet<String> = HashSet()

    class PostViewHolder(val binding: RecyclerRowPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding =
            RecyclerRowPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size


    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val likeRef: DocumentReference = db.collection("UserData").document(user.toString())
        val user = postList.get(position).userId
        if (user != null) {
            val post = postList.get(position)
            holder.binding.username.text = post.name
            holder.binding.postText.text = post.comment
            holder.binding.dateTxt.text = post.date
            val imageUrl = post.downloadUrl
            if (imageUrl != null) {
                holder.binding.postImage.visibility = View.VISIBLE
                Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.load)
                    .into(holder.binding.postImage)
            } else {
                holder.binding.postImage.visibility = View.GONE
            }
            val profileImageUrl = post.profileImageUrl
            Picasso.get()
                .load(profileImageUrl)
                .error(R.drawable.home)
                .into(holder.binding.profileImage)
        } else {
            Toast.makeText(
                context,
                "  tannainisisisnsnss" + postList[position].comment,
                Toast.LENGTH_LONG
            ).show()
        }


        val userUid = postList.get(position).userId


        val postName = postList.get(position).postName



        holder.binding.likeImage.setOnClickListener {


                holder.binding.likeImage.setImageResource(R.drawable.home
                )

                val usersLike = HashMap<String, Any>()
                usersLike[nowUser!!] = nowUser

                if (userUid == nowUser) {
                    Toast.makeText(context , "kendini begendin ", Toast.LENGTH_LONG)
                        .show()
                }



                db.collection("Posts").document(postName)
                    .collection("Likes").document(nowUser!!).set(usersLike).addOnSuccessListener {

                        holder.binding.likeImage.setImageResource(R.drawable.like_red)

                        //Update number of like
                        likeRef.update("likePoint", FieldValue.increment(1))
                    }











        }


    }


}
