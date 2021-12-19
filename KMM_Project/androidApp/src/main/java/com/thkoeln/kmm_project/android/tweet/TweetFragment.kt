package com.thkoeln.kmm_project.android.tweet

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.android.ui.home.TweetViewImpl
import com.thkoeln.kmm_project.controller.TweetController
import com.thkoeln.kmm_project.datastructures.Tweet
import org.w3c.dom.Text

class TweetFragment() : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.comment_section, container, false)

         val tweetData = arguments?.getParcelable<Tweet>("tweet")

        // Creating data for original tweet
        if(tweetData != null)  {
            val user = root.findViewById<TextView>(R.id.comment_user_name)
            user.text = tweetData.userName

            val date = root.findViewById<TextView>(R.id.comment_tweet_date)
            date.text = tweetData.tweetDate

            val content = root.findViewById<TextView>(R.id.comment_tweet_content)
            content.text = tweetData.tweetContent
        }



        // Creating data for comments




        return root
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

}
