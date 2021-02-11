package com.karpov.lab3.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.karpov.lab3.R
import com.karpov.lab3.databinding.MainFragmentBinding
import com.karpov.lab3.others.Constants
import com.karpov.lab3.others.Constants.HOST
import com.karpov.lab3.others.Host
import com.karpov.lab3.others.Stat

class MainFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: MainFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            binding.email.text = currentUser.email
            binding.displayName.text = currentUser.displayName
        }
        binding.logOut.setOnClickListener()
        {
            auth.signOut()
            this.findNavController().navigate(
                MainFragmentDirections
                    .actionMainFragmentToLoginFragment()
            )
        }
        binding.CreateGame.setOnClickListener()
        {
            viewModel.createGame()
        }
        binding.connect.setOnClickListener()
        {
            val database = Firebase.database.reference
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val email = binding.EmailAddress.text.toString().replace(".", "")
                    val host = dataSnapshot.child(email).child(HOST).getValue<Host>()
                    val listStats = dataSnapshot.child(Constants.STATS).child(auth.currentUser!!.email.toString().replace(".",""))
                        .getValue<List<Stat>>() as? MutableList<Stat>
                    if (listStats != null)
                    {
                        viewModel.list.value  = listStats
                    }
                    if (host != null) {
                        viewModel.clientConnect(
                            id = email,
                            displayName = currentUser!!.displayName!!,
                            email = currentUser.email!!
                        )
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }
            database.addValueEventListener(postListener)
        }
        val database = Firebase.database.reference
        val listListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listStats = dataSnapshot.child(Constants.STATS).child(auth.currentUser!!.email.toString().replace(".",""))
                    .getValue<List<Stat>>() as? MutableList<Stat>
                if (listStats != null)
                {
                    viewModel.list.value  = listStats
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(listListener)

        viewModel.navigateToHost.observe(viewLifecycleOwner,
            {
                if (it) {
                    viewModel.doneNavigate()
                    this.findNavController().navigate(
                        MainFragmentDirections
                            .actionMainFragmentToClientFragment(
                                binding.EmailAddress.text.toString().replace(".", "")
                            )
                    )
                    binding.EmailAddress.setText("")
                }
            })
        viewModel.navigate.observe(viewLifecycleOwner,
            {
                if (it) {
                    viewModel.doneNavigate()
                    this.findNavController().navigate(
                        MainFragmentDirections
                            .actionMainFragmentToHostFragment()
                    )
                }

            })
        val adapter = StatAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.list.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        )
                || super.onOptionsItemSelected(item)
    }

}