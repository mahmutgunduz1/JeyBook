package com.mahmutgunduz.jeybook.Fragment.LoadBookFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.mahmutgunduz.jeybook.Model.BookLoadModel
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.View.MainActivity
import com.mahmutgunduz.jeybook.databinding.BottomSheetLoadFinishBinding
import com.mahmutgunduz.jeybook.databinding.BottomSheetSelfUpdateBinding
import com.mahmutgunduz.jeybook.databinding.FragmentCurrentlyLoadBinding

class CurrentlyLoadFragment : Fragment() {

    private var _binding: FragmentCurrentlyLoadBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentlyLoadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseFirestore.getInstance()


        binding.updateTxt.setOnClickListener {
            showBottomSheetDialog(BottomSheetType.SELF_UPDATE)
        }

        binding.addBookSaveButton.setOnClickListener {
            saveBookData()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private enum class BottomSheetType {
        SELF_UPDATE,
        LOAD_FINISH
    }

    private fun showBottomSheetDialog(type: BottomSheetType) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())


        when(type){

            BottomSheetType.SELF_UPDATE -> {

                val bottomSheetBinding = BottomSheetSelfUpdateBinding.inflate(layoutInflater)
                bottomSheetDialog.setContentView(bottomSheetBinding.root)
                BottomSheetBehavior.from(bottomSheetBinding.root.parent as View).state =
                    BottomSheetBehavior.STATE_COLLAPSED


                bottomSheetBinding.cancelButton.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
                bottomSheetBinding.selfSaveButton.setOnClickListener {
                    val BookNameSelf = bottomSheetBinding.addBookNameEdit.text.toString().trim()
                    val ReadDaysSelf = bottomSheetBinding.addBookDayEdit.text.toString().trim()
                    val PageCountSelf = bottomSheetBinding.addBookPageCountEdit.text.toString().trim()


                    if (BookNameSelf.isEmpty() || ReadDaysSelf.isEmpty() || PageCountSelf.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Lütfen gerekli yerleri doldurun !!!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val bookData = BookLoadModel(BookNameSelf, ReadDaysSelf.toInt(), PageCountSelf.toInt())
                        database.collection("CurrentlyBooksSelf").add(bookData).addOnSuccessListener {
                            Toast.makeText(requireContext(), "Kitap başarıyla eklendi!", Toast.LENGTH_SHORT)
                                .show()

                            showBottomSheetDialog(BottomSheetType.LOAD_FINISH)

                        }.addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                "Kitap eklenirken hata oluştu.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }


                }
                bottomSheetDialog.show()

            }
            BottomSheetType.LOAD_FINISH -> {
                val bottomSheetDialog = BottomSheetDialog(requireContext())

                val bottomSheetBinding = BottomSheetLoadFinishBinding.inflate(layoutInflater)

                bottomSheetDialog.setContentView(bottomSheetBinding.root)
                BottomSheetBehavior.from(bottomSheetBinding.root.parent as View).state =
                    BottomSheetBehavior.STATE_COLLAPSED


                bottomSheetBinding.startReadingButton.setOnClickListener {

                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra("navigate_to", "SaveFragment")
                    startActivity(intent)
                }
                bottomSheetBinding.closeButton.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
                bottomSheetDialog.show()

            }

        }

    }

    private fun saveBookData() {
        val bookName = binding.addBookNameEdit.text.toString().trim()
        val readDays = binding.addBookDayEdit.text.toString().trim()
        val pageCount = binding.addBookPageCountEdit.text.toString().trim()

        if (bookName.isEmpty() || readDays.isEmpty() || pageCount.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lütfen gerekli yerleri doldurun !!!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val bookData = BookLoadModel(bookName, readDays.toInt(), pageCount.toInt())
            database.collection("CurrentlyBooks").add(bookData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Kitap başarıyla eklendi!", Toast.LENGTH_SHORT)
                    .show()

                showBottomSheetDialog(BottomSheetType.LOAD_FINISH)

            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Kitap eklenirken hata oluştu.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
