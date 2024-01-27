package com.example.articles.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.filter
import com.example.articles.presentation.OverViewViewModel
import com.example.articles.ui.adapter.ArticleAdapter
import com.example.bitcoinapp.databinding.FragmentOverViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class OverViewFragment : Fragment() {
    private val binding by lazy { FragmentOverViewBinding.inflate(layoutInflater) }
    private val overViewModel by activityViewModels<OverViewViewModel>()
    private var articleAdapter = ArticleAdapter({})
    private var textChangeForRequest: String = "bitcoin"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        overViewModel.pullRefresh(textChangeForRequest)
        binding.viewmodel = overViewModel
        binding.lifecycleOwner = this
        bindAdapter()
        observer()
        catchEvent()
        return binding.root
    }

    private fun catchEvent() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(afterChange: Editable?) {
                textChangeForRequest = afterChange.toString()
                overViewModel.searchOnQuery(afterChange.toString())
            }
        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            overViewModel.pullRefresh(textChangeForRequest)
        }
    }

    private fun bindAdapter() {
        articleAdapter = ArticleAdapter { articlesDataVO ->
            overViewModel.navigateToDetail(articlesDataVO)
        }

        lifecycleScope.launchWhenStarted {
            overViewModel.data.collectLatest { pagingData ->
                articleAdapter.submitData(pagingData)
            }
        }

        binding.rvArticle.adapter = articleAdapter
    }


    private fun observer() {
        overViewModel.navigateToDetail.observe(this.viewLifecycleOwner) { articles ->
            if (articles != null) {
                this.findNavController().navigate(
                    OverViewFragmentDirections.actionOverViewFragmentToDetailFragment(articles)
                )
                overViewModel.completeNavigation()
            }
        }

        overViewModel.pullRefreshList.observe(this.viewLifecycleOwner) {
            if (it?.message?.isNotEmpty() == true) {
                Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    overViewModel.data.collect { pagingData ->
                        articleAdapter.submitData(pagingData)
                    }
                }
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        overViewModel.searchQuery.observe(this.viewLifecycleOwner) { query ->
            lifecycleScope.launch {
                overViewModel.data.collectLatest { pagingData ->
                    val filteredData = if (!query.isNullOrBlank()) {
                        pagingData.filter { it.author.contains(query, true) }
                    } else {
                        pagingData
                    }
                    articleAdapter.submitData(filteredData)
                }
            }
        }

    }

}