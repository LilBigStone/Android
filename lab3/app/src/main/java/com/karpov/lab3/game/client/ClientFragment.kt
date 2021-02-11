package com.karpov.lab3.game.client

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
import com.karpov.lab3.databinding.ClientFragmentBinding
import com.karpov.lab3.game.GameAdapter
import com.karpov.lab3.game.GameListener
import com.karpov.lab3.others.Constants.DRAW
import com.karpov.lab3.others.Constants.FINISH
import com.karpov.lab3.others.Constants.GAME
import com.karpov.lab3.others.Constants.HOST
import com.karpov.lab3.others.Constants.STATUS
import com.karpov.lab3.others.Constants.X
import com.karpov.lab3.others.Finish
import com.karpov.lab3.others.Host
import com.karpov.lab3.others.Nucleus
import com.karpov.lab3.others.Step

class ClientFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: ClientViewModel
    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        auth = Firebase.auth
        database = Firebase.database.reference
        val binding: ClientFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.client_fragment, container, false
        )
        val email = ClientFragmentArgs.fromBundle(requireArguments()).email.replace(".", "")
        viewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        binding.lifecycleOwner = this
        binding.clientDisplayName.text = auth.currentUser?.displayName

        fun showDialog(finish: Finish)
        {
            val result: String
            if (finish.Draw)
            {
                result = DRAW
            }
            else
            {
                result = if(finish.Winner == X) {
                    "You lose"
                } else {
                    "You won"
                }

            }
            context?.let {
                MaterialAlertDialogBuilder(it)
                    .setTitle(result)
                    .setNeutralButton(resources.getString(R.string.rematch)) { dialog, which ->

                    }
                    .setPositiveButton(resources.getString(R.string.exit)) { dialog, which ->
                        this.findNavController().navigate(
                            ClientFragmentDirections.actionClientFragmentToMainFragment()
                        )
                    }
                    .show()
            }
        }
        val hostListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val host = dataSnapshot.child(email).child(HOST).getValue<Host>()
                val listGame = dataSnapshot.child(email).child(GAME).getValue<List<Nucleus>>()
                val status = dataSnapshot.child(email).child(STATUS).getValue<Step>()
                val finish = dataSnapshot.child(email).child(FINISH).getValue<Finish>()
                if (finish != null)
                {
                    showDialog(finish)
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
                Log.w(ContentValues.TAG, "LoadClient:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(hostListener)


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
        return binding.root
    }
}