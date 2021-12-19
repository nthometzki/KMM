package com.thkoeln.kmm_project.view

import com.arkivanov.mvikotlin.core.view.MviView
import com.thkoeln.kmm_project.datastructures.Tweet

interface TweetView : MviView<TweetView.Model, TweetView.Event> {

    data class Model(
        val tweets: Array<Tweet>
    )

    sealed class Event {
        data class TweetAdd(val tweet: Tweet) : Event()
        data class ToggleLiked(val id: String) : Event()
    }
}