package com.thkoeln.kmm_project.android.ui.twitter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thkoeln.kmm_project.datastructures.Tweet
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.datastructures.Comment
import com.thkoeln.kmm_project.view.TweetView
import com.thkoeln.kmm_project.view.TweetView.Event
import com.thkoeln.kmm_project.view.TweetView.Model
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

import java.util.*


class TweetViewImpl(root: View, private val activity: Activity) :
    BaseMviView<Model, Event>(), TweetView {
    private var listView: ListView = root.findViewById(R.id.tweet_list_view)


    init {
        val input = root.findViewById<EditText>(R.id.tweet_input)
        val tweetArea = root.findViewById<RelativeLayout>(R.id.tweeting)
        val fab = root.findViewById<FloatingActionButton>(R.id.fab)
        root.findViewById<Button>(R.id.tweet_submit_button).setOnClickListener {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

            val id = UUID.randomUUID().toString()
            dispatch(
                Event.TweetAdd(
                    Tweet(
                        id,
                        3, // TODO add real post it (return when added, or count posts and increment)
                        "User A.",              // TODO add real user name
                        current.format(formatter),
                        input.text.toString(),
                        false,
                        arrayOf(
                            Comment(
                                id,
                                UUID.randomUUID().toString(),
                                "User B",
                                current.format(formatter),
                                "LMAO",
                                false
                            )
                        )
                    )
                )
            )
            input.text = null
            tweetArea?.visibility = View.INVISIBLE
            fab?.visibility = View.VISIBLE
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(root.windowToken, 0)
        }

    }

    override fun render(model: Model) {
        super.render(model)
        println(">>> MODEL $model")
        val adapter = TweetListAdapter(activity, model.tweets, object : TweetListener {
            override fun onItemLiked(id: String) {
                dispatch(Event.ToggleLiked(id))
            }
        })

        listView.adapter = adapter
    }
}
