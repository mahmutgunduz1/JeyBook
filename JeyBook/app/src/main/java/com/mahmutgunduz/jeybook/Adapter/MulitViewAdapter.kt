package com.mahmutgunduz.jeybook.Adapter

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.jeybook.Fragment.BottomNavFragment.DesignFragment
import com.mahmutgunduz.jeybook.Model.CardİnfoModel
import com.mahmutgunduz.jeybook.R

import com.mahmutgunduz.jeybook.databinding.RecyclerRowFirstBinding
import com.mahmutgunduz.jeybook.databinding.RecyclerRowSecondBinding
import kotlin.random.Random
class MultiViewAdapter(
    private val list: ArrayList<CardİnfoModel>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_SQUARE = 0
        const val VIEW_TYPE_RECTANGLE = 1
    }

    // Kare görünüm için ViewHolder
    class SquareViewHolder(val binding: RecyclerRowFirstBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Dikdörtgen görünüm için ViewHolder
    class RectangleViewHolder(val binding: RecyclerRowSecondBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (position < 12) VIEW_TYPE_SQUARE else VIEW_TYPE_RECTANGLE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SQUARE -> {
                val binding = RecyclerRowFirstBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SquareViewHolder(binding)
            }

            VIEW_TYPE_RECTANGLE -> {
                val binding = RecyclerRowSecondBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RectangleViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]

        when (holder) {
            is SquareViewHolder -> {
                holder.binding.txtCard.text = item.txt
                holder.binding.cartKare.setCardBackgroundColor(getRandomColor())

                // Set onClickListener for the card view
                holder.binding.cartKare.setOnClickListener {
                    onCardClick(item.txt)
                }
            }

            is RectangleViewHolder -> {
                holder.binding.txtBig.text = item.txt
                holder.binding.cartKare.setCardBackgroundColor(getRandomColor())

                holder.binding.cartKare.setOnClickListener {
                    onCardClick(item.txt)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // Rastgele bir renk döndür
    private fun getRandomColor(): Int {
        val random = Random.Default
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }


    private fun onCardClick(categor: String) {
        Toast.makeText(context, "Clicked: $categor", Toast.LENGTH_SHORT).show()
        val bundle = Bundle().apply {
            putString("Category_Name", categor)
        }

        val designFragment = DesignFragment().apply {
            arguments = bundle
        }

        val transaction = (context as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame_layout, designFragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}
