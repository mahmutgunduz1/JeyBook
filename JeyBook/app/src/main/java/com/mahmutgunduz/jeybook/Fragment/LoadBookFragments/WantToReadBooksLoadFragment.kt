package com.mahmutgunduz.jeybook.Fragment.LoadBookFragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.mahmutgunduz.jeybook.Fragment.BottomNavFragment.SaveFragment
import com.mahmutgunduz.jeybook.Model.BookLoadModel2
import com.mahmutgunduz.jeybook.Model.BookLoadModel3
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.View.MainActivity
import com.mahmutgunduz.jeybook.databinding.BottomSheetLoadFinishBinding
import com.mahmutgunduz.jeybook.databinding.FragmentWantToReadBooksLoadBinding
import java.util.Calendar

class WantToReadBooksLoadFragment : Fragment() {

    private var _binding: FragmentWantToReadBooksLoadBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWantToReadBooksLoadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseFirestore.getInstance()


        binding.textInputLayout3.setEndIconOnClickListener {
            showDatePickerDialog()
        }

        binding.addBookSaveButton.setOnClickListener {
            saveBookData()


        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.addBookNameEdit3.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun saveBookData() {


        val bookName = binding.addBookNameEdit1.text.toString().trim()
        val comment  = binding.addBookNameEdit2.text.toString().trim()
        val bookDate = binding.addBookNameEdit3.text.toString().trim()

        if (bookName.isEmpty() || comment.isEmpty() || bookDate.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lütfen gerekli yerleri doldurun !!!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val bookData = BookLoadModel3(bookName, comment, bookDate)
            database.collection("WantToReadBooks").add(bookData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Kitap başarıyla eklendi!", Toast.LENGTH_SHORT)
                    .show()
                showBottomSheetDialog()
            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Kitap eklenirken hata oluştu.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetDialogBinding = BottomSheetLoadFinishBinding.inflate(layoutInflater)

        bottomSheetDialog.setContentView(bottomSheetDialogBinding.root)
        BottomSheetBehavior.from(bottomSheetDialogBinding.root.parent as View).state =
            BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetDialogBinding.closeButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialogBinding.startReadingButton.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("navigate_to", "SaveFragment")
            startActivity(intent)
            Toast.makeText(
                requireContext(),
                "hayirlisiylan",
                Toast.LENGTH_LONG
            ).show()


        }

        bottomSheetDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
