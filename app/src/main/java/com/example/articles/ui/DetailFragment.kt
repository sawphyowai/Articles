package com.example.articles.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bitcoinapp.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private val binding by lazy { FragmentDetailBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val bundle=DetailFragmentArgs.fromBundle(requireArguments()).property
        binding.viewModel=bundle
        binding.lifecycleOwner=this
        return binding.root
    }

}