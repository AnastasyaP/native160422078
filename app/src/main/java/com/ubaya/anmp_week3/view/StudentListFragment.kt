package com.ubaya.anmp_week3.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.anmp_week3.R
import com.ubaya.anmp_week3.databinding.FragmentStudentListBinding
import com.ubaya.anmp_week3.databinding.StudentListItemBinding
import com.ubaya.anmp_week3.viewmodel.ListViewModel

class StudentListFragment : Fragment() {
private lateinit var binding: FragmentStudentListBinding
private lateinit var viewmodel: ListViewModel
private val studentListAdapter = StudentListAdapter(arrayListOf()) // nga pakai var soalnya klo valuenya diganti nanti isi listnya di create adapternya ulang lalu di set value barunya, kalo val in ibisa pake func update\

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

// dipanggil setelah layoutnya selesay di load
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewmodel.refresh() // untuk load/fetch data

        //testing file
        viewmodel.testSaveFile()

        //setup recycle view
        binding.recViewStudent.layoutManager = LinearLayoutManager(context)
        binding.recViewStudent.adapter = studentListAdapter

        //swipe refresh action
        binding.refreshLayout.setOnRefreshListener {
            // mendownload ulang isi studentnya
            viewmodel.refresh()
            binding.refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    fun observeViewModel(){
        // observe - live data - arraylist student
        viewmodel.studentsLD.observe(viewLifecycleOwner, Observer {
            studentListAdapter.updateStudentList(it)
        })

        // observe - live data - loadingLD
        viewmodel.loadingLD.observe(viewLifecycleOwner, Observer{
            if(it == true){
                //masih loading
                binding.progressLoad.visibility = View.VISIBLE
                binding.recViewStudent.visibility =View.INVISIBLE
            }else{
                // sudah tidak loading
                binding.progressLoad.visibility =View.INVISIBLE
                binding.recViewStudent.visibility = View.VISIBLE
            }
        })

        // observe - live data - errorLD
        viewmodel.errorLD.observe(viewLifecycleOwner, Observer{
            if(it == true){
                // ada error
                binding.txtError.text = "Something went wrong when load the student data😔"
                binding.txtError.visibility = View.VISIBLE
                binding.recViewStudent.visibility = View.INVISIBLE

            }else{
                // tidak ada error
                binding.txtError.visibility = View.INVISIBLE
                binding.recViewStudent.visibility = View.VISIBLE
            }
        })
    }
// tidak perlu companion object karna ngaperlu ngirim2 data pakai companion object

}