package com.thkoeln.kmm_project.android


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.thkoeln.kmm_project.Tweet

class TweetListAdapter(private val context: Activity, private val tweet: Array<Tweet>)
    : ArrayAdapter<Tweet>(context, R.layout.tweet, tweet) {

    private var items: Array<Tweet> = arrayOf()

    fun setItems(items: Array<Tweet>) {
        val oldItems = this.items
        this.items = items
        println(this.items)
        //diff(oldItems, items, this)
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.tweet, null, true)

        val userName = rowView.findViewById<TextView>(R.id.user_name)
        val tweetDate = rowView.findViewById<TextView>(R.id.tweet_date)
        val tweetContent = rowView.findViewById<TextView>(R.id.tweet_content)

        val likeButton = rowView.findViewById<Button>(R.id.tweet_like_button)

        println(tweet[position].userName)
        changeButtonColor(likeButton, tweet[position].liked)

        userName.text = tweet[position].userName
        tweetDate.text = "• " + tweet[position].tweetDate
        tweetContent.text = tweet[position].tweetContent

        likeButton.setOnClickListener {
            changeButtonColor(likeButton, tweet[position].liked)

            notifyDataSetChanged() //And refresh the adapter
        }

        return rowView
    }

    fun changeButtonColor(likeButton: Button, liked: Boolean) {
        if(liked) {
            likeButton.setTextColor(Color.parseColor("#1B5BFF"))
        } else {
            likeButton.setTextColor(Color.parseColor("#AAAAAA"))
        }
    }
}