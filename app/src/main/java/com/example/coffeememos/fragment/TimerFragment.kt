package com.example.coffeememos.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {
    private var _binding: FragmentTimerBinding? = null
    private val binding
        get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.timer)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}