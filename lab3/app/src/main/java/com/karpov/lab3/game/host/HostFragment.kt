package com.karpov.lab3.game.host

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.karpov.lab3.R
import com.karpov.lab3.databinding.HostFragmentBinding
import com.karpov.lab3.game.GameAdapter
import com.karpov.lab3.game.GameListener
import com.karpov.lab3.game.client.ClientFragmentDirections
import com.karpov.lab3.others.*
import com.karpov.lab3.others.Constants.CLIENT
import com.karpov.lab3.others.Constants.GAME
import com.karpov.lab3.others.Constants.HOST
import com.karpov.lab3.others.Constants.STATUS

class HostFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var viewModel: HostViewModel
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        database = Firebase.database.reference
        val binding: HostFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.host_fragment, container, false
        )
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(HostViewModel::class.java)

        val email = auth.currentUser?.email.toString().replace(".", "")
        viewModel.gameId = email
        binding.hostDisplayName.text = auth.currentUser?.displayName

        fun showDialog(finish: Finish) {
            val result: String
            if (finish.Draw) {
                result = Constants.DRAW
            } else {
                result = if (finish.Winner == Constants.X) {
                    "You won"
                } else {
                    "You lose"
                }

            }
            context?.let {
                MaterialAlertDialogBuilder(it)
                    .setTitle(result)
                    .setNeutralButton(resources.getString(R.string.rematch)) { dialog, which ->
                        viewModel.createGame()
                    }
                    .setPositiveButton(resources.getString(R.string.exit)) { dialog, which ->
                        this.findNavController().navigate(
                            HostFragmentDirections.actionHostFragmentToMainFragment()
                        )
                    }
                    .show()
            }
        }

        val hostListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val client = dataSnapshot.child(email).child(CLIENT).getValue<Client>()
                val host = dataSnapshot.child(email).child(HOST).getValue<Host>()
                val listGame = dataSnapshot.child(email).child(GAME).getValue<List<Nucleus>>()
                val status = dataSnapshot.child(email).child(STATUS).getValue<Step>()
                val finish = dataSnapshot.child(email).child(Constants.FINISH).getValue<Finish>()
                if (finish != null) {
                    showDialog(finish)
                }
                if (client != null) {
                    binding.clientDisplayName.text = client.displayName
                    viewModel.clientDisplayName = client.displayName.toString()
                    viewModel.clientEmail.value = client.email.toString().replace(".","")
                }
                if (listGame != null) {
                    viewModel.list.value = listGame as MutableList<Nucleus>?
                }
                if (host != null) {
                    binding.hostDisplayName.text = host.displayName
                }
                if (status != null) {
                    viewModel.step.value = status.Step.toString()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "LoadHost:onCancelled", databaseError.toException())
            }
        }
        viewModel.clientEmail.observe(viewLifecycleOwner, {
            val stats = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val listHost = dataSnapshot.child(Constants.STATS).child(email)
                        .getValue<List<Stat>>() as MutableList<Stat>?
                    val listClient = dataSnapshot.child(Constants.STATS).child(viewModel.clientEmail.value!!.replace(".", ""))
                        .getValue<List<Stat>>() as? MutableList<Stat>
                    if (listHost != null) {
                        viewModel.listHost = listHost
                    }
                    if (listClient != null)
                    {
                        viewModel.listClient = listClient
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(ContentValues.TAG, "LoadHost:onCancelled", databaseError.toException())
                }
            }
            database.addValueEventListener(stats)
        })
        val manager = GridLayoutManager(activity, 3)
        binding.Game.layoutManager = manager
        val adapter = GameAdapter(GameListener { id ->
            viewModel.click(id, email)
        })

        viewModel.step.observe(viewLifecycleOwner, {
            binding.stepData.text = it
        })
        viewModel.list.observe(viewLifecycleOwner,
            {
                binding.Game.adapter = adapter
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            })
        database.addValueEventListener(hostListener)
        return binding.root
    }
}