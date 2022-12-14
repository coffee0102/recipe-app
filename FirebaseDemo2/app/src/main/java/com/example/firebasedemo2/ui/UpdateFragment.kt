package com.example.firebasedemo2.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.firebasedemo2.data.Fruit
import com.example.firebasedemo2.data.FruitViewModel
import com.example.firebasedemo2.databinding.FragmentUpdateBinding
import com.example.firebasedemo2.util.cropToBlob
import com.example.firebasedemo2.util.errorDialog
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val nav by lazy { findNavController() }
    private val vm: FruitViewModel by activityViewModels()

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding.imgPhoto.setImageURI(it.data?.data)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        reset()
        binding.imgPhoto.setOnClickListener  { select() }
        binding.btnReset.setOnClickListener  { reset()  }
        binding.btnSubmit.setOnClickListener { submit() }

        return binding.root
    }

    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        binding.edtId.text.clear()
        binding.edtName.text.clear()
        binding.edtCid.text.clear()
        binding.imgPhoto.setImageDrawable(null)
        binding.edtId.requestFocus()
    }

    private fun submit() {
        val f = Fruit(
            id   = binding.edtId.text.toString().trim(),
            name = binding.edtName.text.toString().trim(),
            categoryId  = binding.edtCid.text.toString().trim(),
            photo = binding.imgPhoto.cropToBlob(300, 300),
            description = binding.edtDesc.text.toString().trim()
        )

        val err = vm.validate(f)
        if (err != "") {
            errorDialog(err)
            return
        }
        vm.set(f)

        nav.navigateUp()
    }

}