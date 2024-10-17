package com.mahmutgunduz.jeybook.Fragment.LoadBookFragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.mahmutgunduz.jeybook.Model.BookLoadModel2
import com.mahmutgunduz.jeybook.View.MainActivity
import com.mahmutgunduz.jeybook.databinding.BottomSheetLoadFinishBinding
import com.mahmutgunduz.jeybook.databinding.FragmentReadBooksLoadBinding
import java.util.Calendar

class ReadBooksLoadFragment : Fragment() {

    private var _binding: FragmentReadBooksLoadBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReadBooksLoadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseFirestore.getInstance()


        binding.addBookSaveButton.setOnClickListener {
            saveBookData()
        }

        // Sağdaki ikona tıklanma özelliği eklemek
        binding.textInputLayout3.setEndIconOnClickListener {
            // Tıklama sonrası yapılacak işlemler
            Toast.makeText(requireContext(), "Tarih ikonuna tıklandı!", Toast.LENGTH_SHORT).show()
            showDatePickerDialog()
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

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


    private fun saveBookData() {
        val bookName = binding.addBookNameEdit1.text.toString().trim()
        val dayCount = binding.addBookNameEdit2.text.toString().trim()
        val bookDate = binding.addBookNameEdit3.text.toString().trim()


        if (bookName.isEmpty() || dayCount.isEmpty() || bookDate.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lütfen gerekli yerleri doldurun !!!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val bookData = BookLoadModel2(bookName, dayCount.toInt(), bookDate)

            database.collection("ReadedBooks").add(bookData).addOnSuccessListener {
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
        val bottomSheetBinding = BottomSheetLoadFinishBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)


        BottomSheetBehavior.from(bottomSheetBinding.root.parent as View).state =
            BottomSheetBehavior.STATE_COLLAPSED


        bottomSheetBinding.closeButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        bottomSheetBinding.startReadingButton.setOnClickListener {
            Toast.makeText(requireContext(), "Button tıklandı!", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("navigate_to", "SaveFragment")
            startActivity(intent)
        }


        bottomSheetDialog.show()
    }
}
