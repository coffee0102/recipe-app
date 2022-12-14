package com.example.firebasedemo2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.firebasedemo2.data.CategoryViewModel
import com.example.firebasedemo2.data.FruitViewModel
import com.example.firebasedemo2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.navHost)!!.findNavController() }

    // View models
    private val categoryVM: CategoryViewModel by viewModels()
    private val fruitVM: FruitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBarWithNavController(nav)

        // Initialize view models
        categoryVM.init()
        fruitVM.init()
    }

    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp() || super.onSupportNavigateUp()
    }

}