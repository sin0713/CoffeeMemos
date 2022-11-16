package com.example.coffeememos.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeememos.R
import com.example.coffeememos.adapter.SearchViewPagerAdapter
import com.example.coffeememos.databinding.FragmentHomeRecipeBinding
import com.example.coffeememos.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    // viewBinding
    private  var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList: List<Fragment> = listOf(SearchRecipeFragment(), SearchBeanFragment())

        binding.searchViewPager.adapter = SearchViewPagerAdapter(this, fragmentList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}