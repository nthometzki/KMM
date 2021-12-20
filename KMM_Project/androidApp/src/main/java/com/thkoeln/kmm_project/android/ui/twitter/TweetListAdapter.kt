package com.thkoeln.kmm_project.android.ui.twitter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.thkoeln.kmm_project.datastructures.Tweet
import androidx.navigation.findNavController
import com.thkoeln.kmm_project.android.R


interface TweetListener {
    fun onItemLiked(id: String)
}

class TweetListAdapter(
    private val context: Activity,
    private val tweet: Array<Tweet>,
    private val listener: TweetListener
) :
    ArrayAdapter<Tweet>(context, R.layout.tweet, tweet) {

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.tweet, null, true)

        val userName = rowView.findViewById<TextView>(R.id.user_name)
        val tweetDate = rowView.findViewById<TextView>(R.id.tweet_date)
        val tweetContent = rowView.findViewById<TextView>(R.id.tweet_content)

        val likeButton = rowView.findViewById<Button>(R.id.tweet_like_button)

        changeButtonColor(likeButton, tweet[position].liked)

        userName.text = tweet[position].userName
        tweetDate.text = "• " + tweet[position].tweetDate
        tweetContent.text = tweet[position].tweetContent

        likeButton.setOnClickListener {
            listener.onItemLiked(tweet[position].id)
            changeButtonColor(likeButton, tweet[position].liked)
            notifyDataSetChanged() //And refresh the adapter
        }

        val commentButton = rowView.findViewById<Button>(R.id.tweet_comment_button)
        val navController = context.findNavController(R.id.nav_host_fragment_content_main)

        commentButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("tweet", tweet[position])
            navController.navigate(R.id.nav_tweet, bundle)
        }

        return rowView
    }


    private fun changeButtonColor(likeButton: Button, liked: Boolean) {
        if (liked) {
            likeButton.setTextColor(Color.parseColor("#1B5BFF"))
        } else {
            likeButton.setTextColor(Color.parseColor("#AAAAAA"))
        }
    }
}