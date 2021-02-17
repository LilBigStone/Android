package com.karpov.lab1.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.karpov.lab1.R
import com.karpov.lab1.databinding.NumpadFragmentBinding

class NumPadFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: NumpadFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.numpad_fragment, container, false
        )
        binding.lifecycleOwner = this
        binding.mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        return binding.root
    }
}