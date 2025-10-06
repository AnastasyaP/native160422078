package com.ubaya.anmp_week3.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileHelper(val context: Context) {
    val folderName = "myfolder"
    val fileName = "mydata.txt"

    private fun getFile(): File{
        val dir = File(context.filesDir, folderName)
        // nge cek directorynya suda ada atau nga
        if(!dir.exists()){
            dir.mkdirs() // buat directornya kalo ngaada
        }
        return File(dir, fileName)
    }

    fun writeToFile(data: String){
        try {
            val file = getFile()
            // append = false -> data baru dilanjutkan
            // append = true -> data baru mereplace data lama
            FileOutputStream(file, false).use {
                output -> output.write(data.toByteArray())
            }
        } catch (e: IOException){
            e.printStackTrace()
        }
    }

    fun readFromFile(): String{
        try {
            val file = getFile()
            return file.bufferedReader().useLines { lines -> lines.joinToString("\n")} // membaca baris demi baris
        } catch (e: IOException){
            return e.printStackTrace().toString()
        }
    }

    fun deleteFile(): Boolean{
        return getFile().delete()
    }

    fun getFilePath():String{
        return getFile().absolutePath // untuk melihat path lengkap menuju ke file txtnya
    }
}