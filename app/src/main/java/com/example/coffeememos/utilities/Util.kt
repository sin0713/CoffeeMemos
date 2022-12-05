package com.example.coffeememos.utilities

import android.util.Log
import com.example.coffeememos.Constants
import kotlinx.coroutines.delay

class Util {
    companion object {
        // 空文字列を0に変換
        fun convertStringIntoIntIfPossible(value: String): Int =
            if (value.isEmpty()) 0 else value.toInt()

        fun isDefaultProcess(process: String): Boolean = Constants.processList.contains(process)

        // 動作確認用
        suspend fun printHello(num: Int) {
            delay(1000L)
            Log.d("sample", "hello$num")
        }


        private fun getMinutes(timeMills: Long) = timeMills / 1000 / 60
        private fun getSeconds(timeMills: Long) = timeMills / 1000 % 60
    }
}