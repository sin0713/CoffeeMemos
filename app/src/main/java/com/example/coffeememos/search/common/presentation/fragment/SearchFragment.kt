package com.example.coffeememos.search.common.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coffeememos.search.recipe.presentation.model.SearchKeyWord
import com.example.coffeememos.search.recipe.presentation.model.SearchType
import com.example.coffeememos.adapter.SearchViewPagerAdapter
import com.example.coffeememos.databinding.FragmentSearchBinding
import com.example.coffeememos.search.recipe.presentation.fragment.SearchRecipeFragment
import com.example.coffeememos.search.bean.presentation.fragment.SearchBeanFragment
import com.example.coffeememos.search.common.presentation.view_model.MainSearchViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    // viewBinding
    private  var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: MainSearchViewModel by viewModels()

    private val tabTitles: List<String> = listOf("Recipe", "Bean")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList: List<Fragment> = listOf(SearchRecipeFragment(), SearchBeanFragment())

        binding.searchViewPager.adapter = SearchViewPagerAdapter(this, fragmentList)

        TabLayoutMediator(binding.tabLayout, binding.searchViewPager) {tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        binding.searchIcon.setOnClickListener {
            if (binding.searchViewPager.currentItem == 0) {
                viewModel.setSearchKeyWord(
                    SearchKeyWord(binding.searchInput.text.toString(), SearchType.RECIPE)
                )
            } else {
                viewModel.setSearchKeyWord(
                    SearchKeyWord(binding.searchInput.text.toString(), SearchType.BEAN)
                )
            }
        }

        binding.backBtn.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}