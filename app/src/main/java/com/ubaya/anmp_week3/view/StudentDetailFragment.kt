package com.ubaya.anmp_week3.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ubaya.anmp_week3.R
import com.ubaya.anmp_week3.databinding.FragmentStudentDetailBinding
import com.ubaya.anmp_week3.model.Student
import com.ubaya.anmp_week3.viewmodel.DetailViewModel
import com.ubaya.anmp_week3.viewmodel.ListViewModel

class StudentDetailFragment : Fragment() {
private lateinit var binding: FragmentStudentDetailBinding
private lateinit var viewmodel: DetailViewModel
private lateinit var student:Student

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // baca id student lalu panggil fetch viewmodel untuk load data student tersebut
        val id = StudentDetailFragmentArgs.fromBundle(requireArguments()).id // untuk membaca id yg didapatkan
        viewmodel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewmodel.fetch(id)

        observeViewModel()
    }

    fun observeViewModel(){
        // observe - live data - student
        viewmodel.studentLD.observe(viewLifecycleOwner, Observer{
            student = it
            Toast.makeText(context, "Data loaded", Toast.LENGTH_SHORT).show()

            // update UI
            binding.txtID.setText(student.id)
            binding.txtName.setText(student.name)
            binding.txtBod.setText(student.bod)
            binding.txtPhone.setText(student.phone)

        })
    }
}