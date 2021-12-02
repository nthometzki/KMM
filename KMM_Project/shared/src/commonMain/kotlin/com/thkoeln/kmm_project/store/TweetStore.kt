package com.thkoeln.kmm_project.store

import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle.*
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.store.create
import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.thkoeln.kmm_project.Tweet
//import android.content.Intent
import com.thkoeln.kmm_project.store.TweetStore.Intent
import com.thkoeln.kmm_project.store.TweetStore.State



internal interface TweetStore : Store<Intent, State, Nothing> {

    sealed class Intent {
        data class AddTweet(val tweet: Tweet) : Intent()
    }

    data class State(
        val value: Array<Tweet> = arrayOf()
    )
}

internal class TweetStoreFactory(private val storeFactory: StoreFactory) {

    private sealed class Result {
        class Tweet(val tweet: Tweet) : Result()
    }

    fun create(): TweetStore =
        object : TweetStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "CounterStore",
            initialState = State(),
            reducer = ReducerImpl
        ) {
        }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State =
            when (result) {
                is Result.Tweet -> copy(value = arrayOf(result.tweet))
            }
    }
}

//fun copy(name: String = this.name, age: Int = this.age) = User(value)
//fun copy(value: Long) = value