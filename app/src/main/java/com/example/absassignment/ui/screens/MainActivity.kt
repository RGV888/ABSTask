package com.example.absassignment.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.absassignment.ui.navigation.NavHostComponent
import com.example.absassignment.ui.theme.ABSAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           ABSAssignmentTheme {
               NavHostComponent()
           }
        }
    }
}
