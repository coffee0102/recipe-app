package com.example.firebasedemo2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.firebasedemo2.R
import com.example.firebasedemo2.data.Category
import com.example.firebasedemo2.data.FRUITS
import com.example.firebasedemo2.data.FruitViewModel
import com.example.firebasedemo2.databinding.FragmentFruitBinding
import com.example.firebasedemo2.util.FruitAdapter
import com.google.firebase.firestore.FieldValue.delete
import kotlinx.coroutines.launch

class FruitFragment : Fragment() {

    private lateinit var binding: FragmentFruitBinding
    private val nav by lazy { findNavController() }
    private val vm: FruitViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentFruitBinding.inflate(inflater, container, false)
        binding.btnAdd.setOnClickListener { nav.navigate(R.id.insertFragment)}

        (activity as AppCompatActivity).supportActionBar?.title = "Recipe"

        // -----------------------------------------------------------------------------------------

        //Default search, filter and sort
        vm.search("")
        vm.filter("")
        sort("id")

        // -----------------------------------------------------------------------------------------

        val adapter = FruitAdapter() { holder, fruit ->
            holder.binding.root.setOnClickListener {
                nav.navigate(R.id.fruitDetailFragment, bundleOf("id" to fruit.id))
            }
            holder.binding.btnDelete.setOnClickListener {
                delete(fruit.id)
            }
        }
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.getResult().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.txtCount.text = "${it.size} Record(s)"
        }

        // -----------------------------------------------------------------------------------------

        val arrayAdapter = ArrayAdapter<Category>(requireContext(), android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spn.adapter = arrayAdapter

        lifecycleScope.launch {
            val categories = vm.getCategories()
            arrayAdapter.add(Category("", "All"))
            arrayAdapter.addAll(categories)
        }

        // -----------------------------------------------------------------------------------------

        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String) = true
            override fun onQueryTextChange(name: String): Boolean {
                vm.search(name)
                return true
            }
        })

        // -----------------------------------------------------------------------------------------

        binding.spn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val category = arrayAdapter.getItem(position)!!
                vm.filter(category.id)
            }
        }

        // -----------------------------------------------------------------------------------------
        binding.btnId.setOnClickListener { sort("id") }
        binding.btnName.setOnClickListener { sort("name") }

        // -----------------------------------------------------------------------------------------

        return binding.root

        // -----------------------------------------------------------------------------------------

    }

    fun delete(id: String){
        FRUITS.document(id).delete()
    }

    private fun sort(field: String) {
        val reverse = vm.sort(field)

        binding.btnId.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        binding.btnName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        val res = if (reverse) R.drawable.ic_down else R.drawable.ic_up

        when (field) {
            "id"    -> binding.btnId.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
            "name"  -> binding.btnName.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
        }
    }
}