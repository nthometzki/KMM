package com.arkivanov.mvikotlin.sample.todo.android.details

import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import com.thkoeln.kmm_project.Tweet
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.view.TweetAddView
import com.thkoeln.kmm_project.view.TweetAddView.Event
import com.thkoeln.kmm_project.view.TweetAddView.Model


class TodoDetailsViewImpl(root: View) : BaseMviView<Model, Event>(), TweetAddView {

    init {
        val input = root.findViewById<EditText>(R.id.tweet_input)
        root.findViewById<Button>(R.id.tweet_submit_button).setOnClickListener {
            dispatch(Event.TweetAdd(Tweet("input.text1","user", "02.12.2021", input.text.toString(), false, false)))
        }
    }
}
