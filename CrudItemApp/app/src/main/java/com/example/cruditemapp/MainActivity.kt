package com.example.cruditemapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.cruditemapp.ui.theme.CrudItemAppTheme
import com.example.cruditemapp.ui.view.ItemScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrudItemAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ItemScreen()
                }
            }
        }
    }
}
