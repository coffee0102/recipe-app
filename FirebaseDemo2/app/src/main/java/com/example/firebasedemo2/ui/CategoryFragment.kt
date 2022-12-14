package com.example.firebasedemo2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.firebasedemo2.R
import com.example.firebasedemo2.data.CategoryViewModel
import com.example.firebasedemo2.databinding.FragmentCategoryBinding
import com.example.firebasedemo2.util.CategoryAdapter
import kotlinx.coroutines.launch

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val nav by lazy { findNavController() }
    private val vm: CategoryViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        // -----------------------------------------------------------------------------------------

        val adapter = CategoryAdapter() { holder, category ->
            holder.binding.root.setOnClickListener {
                nav.navigate(R.id.categoryDetailFragment, bundleOf("id" to category.id))
            }
        }
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        // -----------------------------------------------------------------------------------------

        lifecycleScope.launch {
            val categories = vm.getAll()
            adapter.submitList(categories)
            binding.txtCount.text = "${categories.size} Record(s)"
        }

        // -----------------------------------------------------------------------------------------

        return binding.root
    }

}