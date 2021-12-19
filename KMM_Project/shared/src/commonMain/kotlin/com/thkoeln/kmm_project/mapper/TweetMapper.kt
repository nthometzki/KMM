package com.thkoeln.kmm_project.mapper

import com.thkoeln.kmm_project.store.TweetStore
import com.thkoeln.kmm_project.view.TweetView

internal val STATE_TO_MODEL: TweetStore.State.() -> TweetView.Model =
    {
        TweetView.Model(
            tweets = value
        )
    }

internal val EVENT_TO_INTENT: TweetView.Event.() -> TweetStore.Intent =
    {
        when (this) {
            is TweetView.Event.TweetAdd -> TweetStore.Intent.AddTweet(tweet)
            is TweetView.Event.ToggleLiked -> TweetStore.Intent.ToggleLiked(id)
        }
    }
