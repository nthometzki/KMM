package com.thkoeln.kmm_project.mapper

import com.thkoeln.kmm_project.store.TweetStore
import com.thkoeln.kmm_project.view.TweetAddView

internal val stateToModel: TweetStore.State.() -> TweetAddView.Model =
    {
        TweetAddView.Model(
            tweets = value
        )
    }

internal val eventToIntent: TweetAddView.Event.() -> TweetStore.Intent =
    {
        when (this) {
            is TweetAddView.Event.TweetAdd -> TweetStore.Intent.AddTweet(tweet)
            is TweetAddView.Event.ToggleLiked -> TweetStore.Intent.ToggleLiked(id)
        }
    }
