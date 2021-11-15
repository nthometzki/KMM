package com.thkoeln.kmm_project.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.thkoeln.kmm_project.android.databinding.FragmentHomeBinding
import android.widget.ListView
import com.thkoeln.kmm_project.Tweets
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.android.TweetListAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var listView: ListView

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

        listView = root.findViewById(R.id.tweet_list_view)

        val tweets = Tweets().getAllTweets()

        val arrayAdapter = activity?.let { TweetListAdapter(it, tweets) }

        listView.adapter = arrayAdapter



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}