package com.karpov.lab1.converter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.karpov.lab1.BuildConfig
import com.karpov.lab1.R
import com.karpov.lab1.databinding.MainFragmentBinding
import com.karpov.lab1.others.Constants.LENGTH
import com.karpov.lab1.others.Constants.SPEED
import com.karpov.lab1.others.Constants.WEIGHT

class MainFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var arrayFrom: Array<String>
    private lateinit var arrayTo: Array<String>
    private var position: Int = -12
    private fun swap() {
        val temp = binding.inputSpinner.adapter
        binding.inputSpinner.adapter = binding.outputSpinner.adapter
        binding.outputSpinner.adapter = temp
        viewModel.data.value = viewModel.result.value
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

        when (parent.id) {

            binding.measureSpinner.id -> {
                when (binding.measureSpinner.getItemAtPosition(position).toString()) {
                    WEIGHT -> {
                        arrayFrom = resources.getStringArray(R.array.wordWeight)
                        arrayTo = resources.getStringArray(R.array.americanWeight)
                    }
                    LENGTH -> {
                        arrayFrom = resources.getStringArray(R.array.wordLength)
                        arrayTo = resources.getStringArray(R.array.americanLength)
                    }
                    SPEED -> {
                        arrayFrom = resources.getStringArray(R.array.wordSpeed)
                        arrayTo = resources.getStringArray(R.array.americanSpeed)
                    }
                }
                val inputSpinnerAdapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_spinner_item,
                    arrayFrom
                )
                val outputSpinnerAdapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_spinner_item,
                    arrayTo
                )
                inputSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                outputSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.outputSpinner.adapter = outputSpinnerAdapter
                binding.inputSpinner.adapter = inputSpinnerAdapter
                this.position = position
            }
            binding.inputSpinner.id -> {
                val mainCategory = binding.measureSpinner.getItemAtPosition(position).toString()
                val inputSpinner = binding.inputSpinner.getItemAtPosition(position).toString()
                val outputSpinner = binding.outputSpinner.selectedItem.toString()
                this.position = position
                viewModel.logic(inputSpinner, outputSpinner, mainCategory)
            }

            binding.outputSpinner.id -> {
                val mainCategory = binding.measureSpinner.getItemAtPosition(position).toString()
                val inputSpinner = binding.inputSpinner.getItemAtPosition(position).toString()
                val outputSpinner = binding.outputSpinner.selectedItem.toString()
                this.position = position
                viewModel.logic(inputSpinner, outputSpinner, mainCategory)
            }
        }
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: MainFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        this.binding = binding
        binding.lifecycleOwner = this
        viewModel.data.observe(viewLifecycleOwner, {
            binding.worldVal.setText(it)

            if (position != -12) {
                val inputSpinner = binding.inputSpinner.getItemAtPosition(position).toString()
                val outputSpinner = binding.outputSpinner.selectedItem.toString()
                viewModel.logic(
                    inputSpinner,
                    outputSpinner,
                    binding.measureSpinner.getItemAtPosition(position).toString()
                )
            }
        })
        viewModel.result.observe(viewLifecycleOwner, {
            binding.americanVal.setText(it)
        })

        if (!BuildConfig.IS_DEMO) {
            binding.americanVal.setOnClickListener { viewModel.result.value?.let { text -> copy(text) } }
            binding.swap.setOnClickListener { swap() }
            binding.swap.visibility=View.VISIBLE
        }else{
            binding.swap.visibility=View.INVISIBLE
        }
        binding.inputSpinner.onItemSelectedListener = this
        binding.outputSpinner.onItemSelectedListener = this
        binding.measureSpinner.onItemSelectedListener = this
        return binding.root
    }


    fun copy(textToCopy: String) {
        val clipboardManager =
            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Text copied", Toast.LENGTH_LONG).show()
    }
}