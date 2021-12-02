package com.thkoeln.kmm_project.store

import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle.*
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.store.create
import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.scheduler.computationScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.single.map
import com.badoo.reaktive.single.singleFromFunction
import com.badoo.reaktive.single.subscribeOn
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

    sealed class Result {
        class TweetAdd(val tweet: Tweet) : Result()
    }

    private class ExecutorImpl : ReaktiveExecutor<Intent, Nothing, State, Result, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.AddTweet -> dispatch(Result.TweetAdd( intent.tweet))
            }


    }


    fun create(): TweetStore =
        object : TweetStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "CounterStore",
            initialState = State(),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl,
        ) {
        }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State =
            when (result) {
                is Result.TweetAdd -> copy(value = value + result.tweet)
            }
    }
}
