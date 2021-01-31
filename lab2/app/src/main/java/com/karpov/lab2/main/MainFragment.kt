package com.karpov.lab2.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.karpov.lab2.database.WorkoutDatabase
import com.karpov.lab2.R
import com.karpov.lab2.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: MainFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = WorkoutDatabase.getInstance(application).workoutDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        val mainViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(MainViewModel::class.java)

        val adapter = WorkoutAdapter(
            WorkoutListener { workoutId ->
                mainViewModel.onWorkoutClicked(workoutId)
            }, WorkoutDeleteListener { workoutId ->
                mainViewModel.deleteWorkout(workoutId)
            })

        binding.workoutList.adapter = adapter
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this


        mainViewModel.navigateToWorkoutDetail.observe(
            viewLifecycleOwner,
            { workout ->
                workout?.let {
                    this.findNavController().navigate(
                        MainFragmentDirections
                            .actionMainFragmentToDetailFragment(workout)
                    )
                    mainViewModel.onNavigated()
                }
            }
        )
        binding.floatingActionButton.setOnClickListener()
        {
            mainViewModel.onCreateWorkout()
        }


        mainViewModel.listWorkout.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
            binding.workoutList.smoothScrollToPosition(0)
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