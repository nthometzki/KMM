package com.thkoeln.kmm_project.store

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.essenty.statekeeper.consume
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.thkoeln.kmm_project.Tweet
import com.thkoeln.kmm_project.store.TweetStore.Intent
import com.thkoeln.kmm_project.store.TweetStore.State
import com.thkoeln.kmm_project.main



internal interface TweetStore : Store<Intent, State, Nothing> {

    sealed class Intent : JvmSerializable {
        data class AddTweet(val tweet: Tweet) : Intent()
    }

    @Parcelize
    data class State(
        val value: Array<Tweet> = arrayOf(
            Tweet(
                "1234",
                "Nico T.",
                "10 Nov 2021",
                "Test",
                false,
                true
            )
        ),

    ) : Parcelable
}

internal class TweetStoreFactory(private val storeFactory: StoreFactory) {

    sealed class Result {
        class TweetAdd(val tweet: Tweet) : Result()
    }

    private class ExecutorImpl : ReaktiveExecutor<Intent, Nothing, State, Result, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.AddTweet -> dispatch(Result.TweetAdd(intent.tweet))
            }
    }

    fun create(stateKeeper: StateKeeper): TweetStore =
        object : TweetStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "CounterStore",
            initialState = stateKeeper.consume(key = "TweetStoreState") ?: State(),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl,
        ) {
            val main = main()

        }.also {
            stateKeeper.register(key = "TweetStoreState") {
                println(">>> STATE IN ALSO: " + it.state)
                it.state.copy(value = it.state.value) // We can reset any transient state here
            }
        }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State =
            when (result) {
                is Result.TweetAdd -> copy(value = value + result.tweet)
            }
    }
}
