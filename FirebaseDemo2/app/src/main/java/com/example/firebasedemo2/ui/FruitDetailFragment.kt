package com.example.firebasedemo2.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.firebasedemo2.R
import com.example.firebasedemo2.data.Fruit
import com.example.firebasedemo2.data.FruitViewModel
import com.example.firebasedemo2.databinding.FragmentFruitDetailBinding
import com.example.firebasedemo2.util.FruitAdapter
import com.example.firebasedemo2.util.cropToBlob
import com.example.firebasedemo2.util.errorDialog
import com.example.firebasedemo2.util.setImageBlob
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.DecimalFormat

class FruitDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFruitDetailBinding
    private val nav by lazy { findNavController() }
    private val vm: FruitViewModel by activityViewModels()

    private val id by lazy { arguments?.getString("id", "") ?: "" }
    private val formatter = DecimalFormat("0.00")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentFruitDetailBinding.inflate(inflater, container, false)
        binding.btnClose.setOnClickListener { dismiss() }
        binding.btnUpdate.setOnClickListener {nav.navigate(R.id.updateFragment) }

        // -----------------------------------------------------------------------------------------

        val fruit = vm.get(id)!!

        binding.txtId.text    = fruit.id
        binding.txtName.text  = fruit.name
        binding.txtCategoryId.text = fruit.categoryId
        binding.imgPhoto.setImageBlob(fruit.photo)
        binding.txtDesc.text = fruit.description

        // -----------------------------------------------------------------------------------------

        return binding.root

    }
}