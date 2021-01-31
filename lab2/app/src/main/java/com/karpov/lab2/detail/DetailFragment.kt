package com.karpov.lab2.detail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.karpov.lab2.R
import com.karpov.lab2.database.WorkoutDatabase
import com.karpov.lab2.databinding.DetailFragmentBinding
import com.karpov.lab2.main.MainFragmentDirections
import com.karpov.lab2.main.WorkoutAdapter
import com.karpov.lab2.main.WorkoutDeleteListener
import com.karpov.lab2.main.WorkoutListener
import com.karpov.lab2.others.Color


class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var title: String
    private lateinit var binding: DetailFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.detail_fragment, container, false
        )


        val application = requireNotNull(this.activity).application
        val dataSource = WorkoutDatabase.getInstance(application).workoutDatabaseDao
        val key = DetailFragmentArgs.fromBundle(requireArguments()).key
        val viewModelFactory = DetailViewModelFactory(key, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.workout.observe(viewLifecycleOwner,
            {
                binding.ready.setText(it.readyTime.toString())
                binding.work.setText(it.workTime.toString())
                binding.cycles.setText(it.cycles.toString())
                binding.chill.setText(it.chill.toString())
            }
        )
        viewModel.name.observe(viewLifecycleOwner,
            {
                binding.name.setText(viewModel.name.value.toString())
            })
        fun update() {
            viewModel.workout.value = viewModel.workout.value
        }
        binding.readyMinus.setOnClickListener()
        {
            viewModel.workout.value?.readyTime = viewModel.workout.value?.readyTime!! - 1
            update()
        }
        binding.readyPlus.setOnClickListener()
        {
            viewModel.workout.value?.readyTime = viewModel.workout.value?.readyTime?.plus(1)!!
            update()
        }
        binding.workMinus.setOnClickListener()
        {
            viewModel.workout.value?.workTime = viewModel.workout.value?.workTime?.minus(1)!!
            update()
        }
        binding.workPlus.setOnClickListener()
        {
            viewModel.workout.value?.workTime = viewModel.workout.value?.workTime?.plus(1)!!
            update()
        }
        binding.chillMinus.setOnClickListener()
        {
            viewModel.workout.value?.chill = viewModel.workout.value?.chill?.minus(1)!!
            update()
        }
        binding.chillPlus.setOnClickListener()
        {
            viewModel.workout.value?.chill = viewModel.workout.value?.chill?.plus(1)!!
            update()
        }
        binding.cyclesMinus.setOnClickListener()
        {
            viewModel.workout.value?.cycles = viewModel.workout.value?.cycles?.minus(1)!!
            update()
        }
        binding.cyclesPlus.setOnClickListener()
        {
            viewModel.workout.value?.cycles = viewModel.workout.value?.cycles?.plus(1)!!
            update()
        }
        viewModel.workoutTime.observe(viewLifecycleOwner,
            {
                viewModel.init()
            })


        binding.save.setOnClickListener()
        {
            viewModel.update(binding.name.text.toString())
        }

        viewModel.navigateToTimer.observe(viewLifecycleOwner,
            { workout ->
                workout?.let {
                    this.findNavController().navigate(
                        DetailFragmentDirections
                            .actionDetailFragmentToTimerFragment(workout)
                    )
                    viewModel.onNavigated()
                }
            })

        binding.button2.setOnClickListener()
        {
            viewModel.startTimer(key)
        }
        val manager = GridLayoutManager(activity, 6)
        binding.colorList.layoutManager = manager
        val adapter = ColorAdapter(
            ColorListener { color ->
                viewModel.onColorSelect(color)
            })

        binding.colorList.adapter = adapter

        viewModel.selectColor.observe(viewLifecycleOwner,
            {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
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

    override fun onPause() {
        viewModel.update(binding.name.text.toString())
        super.onPause()
    }

}