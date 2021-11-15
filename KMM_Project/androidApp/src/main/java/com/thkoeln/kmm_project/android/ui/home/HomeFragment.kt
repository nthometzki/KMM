package com.thkoeln.kmm_project.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.android.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tweet = root.findViewById<RelativeLayout>(R.id.tweet_1)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}