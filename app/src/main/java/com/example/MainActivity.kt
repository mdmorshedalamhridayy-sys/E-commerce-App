package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.data.AppDatabase
import com.example.data.MarketplaceRepository
import com.example.ui.MarketplaceScreen
import com.example.ui.MarketplaceViewModel
import com.example.ui.MarketplaceViewModelFactory
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Initialize Room Database, Repository, and ViewModel
    val database = AppDatabase.getDatabase(this)
    val repository = MarketplaceRepository(database.marketplaceDao())
    val viewModel: MarketplaceViewModel by viewModels {
      MarketplaceViewModelFactory(application, repository)
    }

    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
         MarketplaceScreen(viewModel = viewModel)
      }
    }
  }
}
