package com.thkoeln.kmm_project.android


import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.thkoeln.kmm_project.Tweet

class TweetListAdapter(private val context: Activity, private val tweet: Array<Tweet>)
    : ArrayAdapter<Tweet>(context, R.layout.tweet, tweet) {

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.tweet, null, true)

        val userName = rowView.findViewById<TextView>(R.id.user_name)
        val tweetDate = rowView.findViewById<TextView>(R.id.tweet_date)
        val tweetContent = rowView.findViewById<TextView>(R.id.tweet_content)


        userName.text = tweet[position].userName
        tweetDate.text = "• " + tweet[position].tweetDate
        tweetContent.text = tweet[position].tweetContent

        return rowView
    }
}