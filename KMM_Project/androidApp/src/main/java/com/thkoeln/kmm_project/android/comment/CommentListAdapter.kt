package com.thkoeln.kmm_project.android.comment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import com.thkoeln.kmm_project.datastructures.Tweet
import androidx.navigation.findNavController
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.datastructures.Comment


interface Listener {
    fun onItemLiked(id: String)
}

class CommentListAdapter(private val context: Activity, private val comments: Array<Comment>, private val listener: Listener?) :
    ArrayAdapter<Comment>(context, R.layout.comment_tweet, comments) {


    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.comment_tweet, null, true)

        val userName = rowView.findViewById<TextView>(R.id.comment_list_user_name)
        val tweetDate = rowView.findViewById<TextView>(R.id.comment_list_tweet_date)
        val tweetContent = rowView.findViewById<TextView>(R.id.comment_list_tweet_content)
        val likeButton = rowView.findViewById<Button>(R.id.comment_list_tweet_like_button)

        changeButtonColor(likeButton, comments[position].liked)

        userName.text = comments[position].userName
        tweetDate.text = "• " + comments[position].tweetDate
        tweetContent.text = comments[position].tweetContent

        likeButton.setOnClickListener {
            //listener.onItemLiked(comments[position].id)
            changeButtonColor(likeButton, comments[position].liked)
            notifyDataSetChanged() //And refresh the adapter
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