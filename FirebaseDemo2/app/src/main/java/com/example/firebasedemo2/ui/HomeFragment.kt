package com.example.firebasedemo2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebasedemo2.R
import com.example.firebasedemo2.data.FRUITS
//import com.example.firebasedemo2.data.RESTORE_DATA
import com.example.firebasedemo2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnFruit.setOnClickListener { nav.navigate(R.id.fruitFragment) }
//        binding.btnRestore.setOnClickListener { restore() }

        return binding.root
    }

//    private fun restore() {
//        RESTORE_DATA()
//        toast("Done")
//    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}