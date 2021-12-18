package com.thkoeln.kmm_project.android.ui.home

import TweetAddViewImpl
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.essentyLifecycle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.controller.TweetAddController

class HomeFragment() : Fragment(), View.OnClickListener {

    private lateinit var controller: TweetAddController

    private fun createController(lifecycle: Lifecycle): TweetAddController =
        TweetAddController(lifecycle)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val fab = root.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener(this)

        val closeButton = root.findViewById<ImageButton>(R.id.tweet_close_button)
        closeButton.setOnClickListener(this)

        val submitButton = root.findViewById<Button>(R.id.tweet_submit_button)
        submitButton.setOnClickListener(this)

        controller = createController(essentyLifecycle())

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { TweetAddViewImpl(view, it) }?.let { controller.onViewCreated(it) }
        controller.onStart()
    }


    override fun onClick(v: View?) {

        val tweetArea = view?.findViewById<RelativeLayout>(R.id.tweeting)
        val fab = view?.findViewById<FloatingActionButton>(R.id.fab)

        when (v?.id) {
            R.id.fab -> if (tweetArea?.visibility == View.INVISIBLE) {
                fab?.visibility = View.INVISIBLE
                tweetArea.visibility = View.VISIBLE

            } else {
                tweetArea?.visibility = View.INVISIBLE
                fab?.visibility = View.VISIBLE
            }

            R.id.tweet_close_button -> {
                tweetArea?.visibility = View.INVISIBLE
                fab?.visibility = View.VISIBLE
            }
        }
    }

}

