package com.thkoeln.kmm_project.factory

import com.arkivanov.mvikotlin.core.store.Store
import com.thkoeln.kmm_project.store.TweetStore
import com.thkoeln.kmm_project.store.TweetStore.Intent
import com.thkoeln.kmm_project.store.TweetStore.State
import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.thkoeln.kmm_project.datastructures.Tweet

abstract class AbstractTweetFactory(
    private val storeFactory: StoreFactory
) {
    fun create(userName: String): TweetStore =
        object : TweetStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "TweetStore",
            initialState = State(arrayOf(), userName),
            bootstrapper = createBootstrapper(),
            executorFactory = ::createExecutor,
            reducer = ReducerImpl
        ) {
        }

    protected abstract fun createExecutor(): Executor<Intent, Action, State, Result, Nothing>

    protected abstract fun createBootstrapper(): Bootstrapper<Action>

    protected sealed class Result : JvmSerializable {
        class TweetAdd(val tweet: Tweet) : Result()
        class ToggleLiked(val id: String) : Result()
        class AddAllTweets(val tweets: Array<Tweet>) : Result()
    }

    protected sealed class Action : JvmSerializable {
        class AddAll(val tweets: Array<Tweet>) : Action()
    }

    private object ReducerImpl : Reducer<State, Result> {
             override fun State.reduce(result: Result): State =
            when (result) {
                is Result.TweetAdd -> {
                    copy(value = value + result.tweet)
                }
                is Result.ToggleLiked -> copy(value = value.map { if (it.id == result.id) it.copy(liked = !it.liked) else it }.toTypedArray())
                is Result.AddAllTweets -> copy(value = result.tweets)
            }
    }
}