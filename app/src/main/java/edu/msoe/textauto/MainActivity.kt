package edu.msoe.textauto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.msoe.textauto.DataBase.TextRepository


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TextRepository.initialize(this)
        setContentView(R.layout.activity_main)
    }
}