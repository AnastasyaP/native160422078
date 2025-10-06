package com.ubaya.anmp_week3.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.anmp_week3.databinding.StudentListItemBinding
import com.ubaya.anmp_week3.model.Student

class StudentListAdapter(val studentList:ArrayList<Student>):RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {
    class StudentViewHolder(var binding: StudentListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        // untuk nge load layout cardnya
        val binding = StudentListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return studentList.size
        // override fun getItemCount() = studentList.size // -> bisa disingkat jadi gini
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
//        holder.binding.txtID.text = studentList[position].id
//        holder.binding.txtName.text = studentList[position].name

        holder.binding.txtID.setText(studentList[position].id)
        holder.binding.txtName.setText(studentList[position].name)

        holder.binding.btnDetail.setOnClickListener{
            val id = studentList[position].id.toString()
            val action = StudentListFragmentDirections.actionStudentDetail(id)
            it.findNavController().navigate(action)
        }

    }

    fun updateStudentList(newStudentList: ArrayList<Student>){
        studentList.clear()
        studentList.addAll(newStudentList)
        this.notifyDataSetChanged()
    }
}