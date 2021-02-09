package com.karpov.lab3.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.karpov.lab3.R
import com.karpov.lab3.databinding.RegistrationFragmentBinding

class RegistrationFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: RegistrationFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.registration_fragment, container, false
        )
        binding.lifecycleOwner = this
        auth = Firebase.auth
        binding.textViewLogIn.setOnClickListener()
        {
            this.findNavController().navigate(
                RegistrationFragmentDirections
                    .actionRegistrationFragmentToLoginFragment()
            )
        }
        binding.register.setOnClickListener()
        {
            activity?.let { it1 ->

                val email = binding.email.text.toString()
                val password = binding.password.text.toString()
                if (email.isNotEmpty() and password.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(it1) { task ->
                            if (task.isSuccessful) {
                                this.findNavController().navigate(
                                    RegistrationFragmentDirections
                                        .actionRegistrationFragmentToLoginFragment()
                                )
                            } else {
                                Toast.makeText(
                                    context, task.exception?.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                }
            }
        }
        return binding.root
    }

}