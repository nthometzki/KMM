package com.thkoeln.kmm_project.android.ui.home

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thkoeln.kmm_project.Tweet
import com.thkoeln.kmm_project.android.Listener
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.android.TweetListAdapter
import com.thkoeln.kmm_project.view.TweetAddView
import com.thkoeln.kmm_project.view.TweetAddView.Event
import com.thkoeln.kmm_project.view.TweetAddView.Model
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

import java.util.*


class TweetAddViewImpl(root: View, private val activity: Activity) :
    BaseMviView<Model, Event>(), TweetAddView {
    private var listView: ListView = root.findViewById(R.id.tweet_list_view)


    init {


        val input = root.findViewById<EditText>(R.id.tweet_input)
        val tweetArea = root.findViewById<RelativeLayout>(R.id.tweeting)
        val fab = root.findViewById<FloatingActionButton>(R.id.fab)
        root.findViewById<Button>(R.id.tweet_submit_button).setOnClickListener {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

            dispatch(
                Event.TweetAdd(
                    Tweet(
                        UUID.randomUUID().toString(),
                        "User A.",              // TODO add real user name
                        current.format(formatter),
                        input.text.toString(),
                        false,
                        arrayOf()
                    )
                )
            )
            input.text = null
            tweetArea?.visibility = View.INVISIBLE
            fab?.visibility = View.VISIBLE
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(root.getWindowToken(), 0)
        }

    }

    override fun render(model: Model) {
        super.render(model)
        val adapter = TweetListAdapter(activity, model.tweets, object : Listener {
            override fun onItemLiked(id: String) {
                dispatch(Event.ToggleLiked(id))
            }
        })

        listView.adapter = adapter
    }
}
