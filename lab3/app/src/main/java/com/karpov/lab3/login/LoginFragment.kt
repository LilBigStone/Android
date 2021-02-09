package com.karpov.lab3.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.karpov.lab3.R
import com.karpov.lab3.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: LoginFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.login_fragment, container, false
        )

        binding.lifecycleOwner = this

        auth = Firebase.auth

        binding.logIn.setOnClickListener()
        {
            activity?.let { activity ->
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()
                if (email.isNotEmpty() and password.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity) { task ->
                            if (task.isSuccessful) {
                                this.findNavController().navigate(
                                    LoginFragmentDirections
                                        .actionLoginFragmentToMainFragment()
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
        binding.signUp.setOnClickListener()
        {
            this.findNavController().navigate(
                LoginFragmentDirections
                    .actionLoginFragmentToRegistrationFragment()
            )
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            this.findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToMainFragment()
            )
        }
    }
}