package com.karpov.lab2.timer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.karpov.lab2.R
import com.karpov.lab2.database.WorkoutDatabase
import com.karpov.lab2.databinding.TimerFragmentBinding
import com.karpov.lab2.main.MainFragmentDirections
import com.karpov.lab2.others.Constants.ACTION_BACK_BUTTON
import com.karpov.lab2.others.Constants.ACTION_NEXT_BUTTON
import com.karpov.lab2.others.Constants.ACTION_PAUSE_BUTTON
import com.karpov.lab2.others.Constants.ACTION_START_OR_RESUME_SERVICE
import com.karpov.lab2.others.Constants.ACTION_STOP_SERVICE
import java.util.*

class TimerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: TimerFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.timer_fragment, container, false
        )

        val arguments = TimerFragmentArgs.fromBundle(requireArguments())
        binding.lifecycleOwner = this
        sendCommandToService(ACTION_START_OR_RESUME_SERVICE, arguments.key)
        TimerService.time.observe(viewLifecycleOwner, { newTime ->
            binding.timer.text = DateUtils.formatElapsedTime(newTime)
        })
        TimerService.title.observe(viewLifecycleOwner, { titleString ->
            binding.name.text = titleString
        })
        TimerService.color.observe(viewLifecycleOwner, { color ->
            binding.constraintLayout.setBackgroundColor(Color.parseColor(color.toString()))

        })
        binding.left.setOnClickListener()
        {
            sendCommandToService(ACTION_BACK_BUTTON, arguments.key)
        }


        binding.left2.setOnClickListener()
        {
            sendCommandToService(ACTION_PAUSE_BUTTON, arguments.key)
        }

        binding.left3.setOnClickListener()
        {
            sendCommandToService(ACTION_NEXT_BUTTON, arguments.key)
        }

        binding.left4.setOnClickListener()
        {
            sendCommandToService(ACTION_STOP_SERVICE, arguments.key)
            this.findNavController().navigate(
                TimerFragmentDirections
                    .actionTimerFragmentToMainFragment()
            )
        }
        return binding.root
    }

    private fun sendCommandToService(action: String, id: Long) =
        Intent(requireContext(), TimerService::class.java).also {
            it.putExtra("key", id)
            it.action = action
            requireContext().startService(it)
        }

}