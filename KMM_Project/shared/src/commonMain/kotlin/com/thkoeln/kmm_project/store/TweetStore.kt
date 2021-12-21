package com.thkoeln.kmm_project.store

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.thkoeln.kmm_project.datastructures.Tweet
import com.thkoeln.kmm_project.store.TweetStore.Intent
import com.thkoeln.kmm_project.store.TweetStore.State


interface TweetStore : Store<Intent, State, Nothing> {

    sealed class Intent : JvmSerializable {
        data class AddTweet(val tweet: Tweet) : Intent()
        data class ToggleLiked(val id: String) : Intent()
        data class AddAllTweets(val tweets: Array<Tweet>) : Intent()
    }

    @Parcelize
    data class State(
        val value: Array<Tweet> = arrayOf(),
    ) : Parcelable
}
