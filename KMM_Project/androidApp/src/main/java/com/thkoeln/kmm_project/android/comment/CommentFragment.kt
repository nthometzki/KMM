package com.thkoeln.kmm_project.android.comment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.controller.CommentController
import com.thkoeln.kmm_project.datastructures.Tweet
import kotlinx.coroutines.Dispatchers

class CommentFragment() : Fragment() {

    private lateinit var controller: CommentController

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.comment_section, container, false)

        val tweetData = arguments?.getParcelable<Tweet>("tweet")

        if (tweetData != null) {
            val user = root.findViewById<TextView>(R.id.comment_user_name)
            user.text = tweetData.userName

            val date = root.findViewById<TextView>(R.id.comment_tweet_date)
            date.text = "• " + tweetData.tweetDate

            val content = root.findViewById<TextView>(R.id.comment_tweet_content)
            content.text = tweetData.tweetContent

            val numberOfLikes = root.findViewById<TextView>(R.id.number_likes)
            // TODO fetch number of likes after backend change
            numberOfLikes.text = "0 Likes"



            controller = CommentController(tweetData.comments, Dispatchers.Main.immediate, Dispatchers.IO, tweetData.id)
            activity?.let { CommentViewImpl(root, it, tweetData.id) }
                ?.let { controller.onViewCreated(it) }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller.onStart()

    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

}
