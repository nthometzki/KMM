package com.thkoeln.kmm_project.android.comment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.datastructures.Comment


class CommentListAdapter(
    private val context: Activity,
    private val comments: Array<Comment>,
) :
    ArrayAdapter<Comment>(context, R.layout.comment_tweet, comments) {

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.comment_tweet, null, true)

        val userName = rowView.findViewById<TextView>(R.id.comment_list_user_name)
        val tweetDate = rowView.findViewById<TextView>(R.id.comment_list_tweet_date)
        val tweetContent = rowView.findViewById<TextView>(R.id.comment_list_tweet_content)

        userName.text = comments[position].userName
        tweetDate.text = "• " + comments[position].tweetDate
        tweetContent.text = comments[position].tweetContent

        return rowView
    }
}