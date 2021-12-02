package com.thkoeln.kmm_project.view

import com.arkivanov.mvikotlin.core.view.MviView
import com.thkoeln.kmm_project.Tweet


interface TweetAddView : MviView<TweetAddView.Model, TweetAddView.Event> {

    data class Model(
        val value: Array<Tweet> = arrayOf()
    )

    sealed class Event {
        data class TweetAdd(val tweet: Tweet) : Event()
    }
}