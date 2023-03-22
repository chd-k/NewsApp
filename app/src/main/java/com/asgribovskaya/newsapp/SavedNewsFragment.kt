package com.asgribovskaya.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asgribovskaya.newsapp.databinding.FragmentSavedNewsBinding

class SavedNewsFragment : Fragment() {

    private lateinit var binding: FragmentSavedNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }
}