package com.ubaya.anmp_week3.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.anmp_week3.model.Student
import com.ubaya.anmp_week3.util.FileHelper

class ListViewModel(app: Application):AndroidViewModel(app) {
    // MutableLiveData = isinya bisa diubah2
    val studentsLD = MutableLiveData<ArrayList<Student>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()
    val TAG = "volleytag" // digunakan unutk membatalkan volley klo lagi berjalan
    private var queue: RequestQueue? = null

    // data dummy yg hard code
    fun refresh(){
        loadingLD.value = true // progress bar start muncul
        errorLD.value = false // tdk ada error

        // volley ke API
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/LLMW"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                //sukses-> mereturn gson dengan mengconvert json ke gson
                val sType = object : TypeToken<List<Student>>() {

                }.type
                val result = Gson().fromJson<List<Student>>(it, sType)
                studentsLD.value = result as ArrayList<Student>

                loadingLD.value = false

                // simpan jg ake file
                val fileHelper = FileHelper(getApplication())
                val jsonString = Gson().toJson(result) // ini mengkonvert array ke json
                fileHelper.writeToFile(jsonString)
                Log.d("print_file", jsonString)

                // baca json string dari file
                val hasil = fileHelper.readFromFile()
                val listStudent = Gson().fromJson<List<Student>>(hasil, sType)
                Log.d("print_file", listStudent.toString())
            },
            {
                //failed
                errorLD.value = true
                loadingLD.value = false
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }


    fun testSaveFile(){
        val fileHelper = FileHelper(getApplication())
        fileHelper.writeToFile("Hello World!") // menambah "Hello World!" ke filenya

        val content = fileHelper.readFromFile()
        Log.d("print_file", content) // menampilkan isi filenya
        Log.d("print_file", fileHelper.getFilePath()) // menambhakan path menuju filenya
    }

    // method yg dipanggil klo appnya di tutup,
    // jadi volleynya dipaksa dimatikan biar kalo pas appnya ditutup nga jalan terus volleynya
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}