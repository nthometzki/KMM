package com.arkivanov.mvikotlin.sample.todo.android.details

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import com.thkoeln.kmm_project.Tweet
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.android.TweetListAdapter
import com.thkoeln.kmm_project.view.TweetAddView
import com.thkoeln.kmm_project.view.TweetAddView.Event
import com.thkoeln.kmm_project.view.TweetAddView.Model


class TweetAddViewImpl(private val root: View, private val activity: Activity) : BaseMviView<Model, Event>(), TweetAddView {
    private  var listView: ListView = root.findViewById(R.id.tweet_list_view)

   /* override val renderer: ViewRenderer<Model> =
        diff {
            println(Model::tweets)
            diff(get = Model::tweets, set = adapter::setItems)
        }*/


    init {
        val input = root.findViewById<EditText>(R.id.tweet_input)
        root.findViewById<Button>(R.id.tweet_submit_button).setOnClickListener {
            dispatch(Event.TweetAdd(Tweet("input.text1","user", "02.12.2021", input.text.toString(), false, false)))
        }


    }

    override fun render(model: Model) {
        super.render(model)
        val adapter = TweetListAdapter(activity, model.tweets)

        listView.adapter = adapter
    }
}
