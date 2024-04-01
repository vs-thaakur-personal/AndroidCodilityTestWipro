package com.example.utils

object FileUtils {
    fun readFile(path: String): String? {
            val inputStream = javaClass.classLoader!!.getResourceAsStream(path)
            return inputStream?.bufferedReader()?.use { it.readText() }
    }
}