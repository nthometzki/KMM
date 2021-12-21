package com.thkoeln.kmm_project.store

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.thkoeln.kmm_project.datastructures.Tweet
import com.thkoeln.kmm_project.store.TweetStore.Intent
import com.thkoeln.kmm_project.store.TweetStore.State
import com.thkoeln.kmm_project.main
import com.thkoeln.kmm_project.networking.Networking
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


internal interface TweetStore : Store<Intent, State, Nothing> {

    sealed class Intent : JvmSerializable {
        data class AddTweet(val tweet: Tweet) : Intent()
        data class ToggleLiked(val id: String) : Intent()
    }

    @Parcelize
    data class State(
        val value: Array<Tweet> = arrayOf(),
    ) : Parcelable
}

internal class TweetStoreFactory(private val storeFactory: StoreFactory) {

    sealed class Result {
        class TweetAdd(val tweet: Tweet) : Result()
        class ToggleLiked(val id: String) : Result()
    }

    private class ExecutorImpl : ReaktiveExecutor<Intent, Nothing, State, Result, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.AddTweet -> dispatch(Result.TweetAdd(intent.tweet))
                is Intent.ToggleLiked -> dispatch(Result.ToggleLiked(intent.id))
            }
    }

    fun create(): TweetStore =
        object : TweetStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "TweetStore",
            initialState = State(),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl,
        ) {
            val main = main()
            lateinit var posts: Array<Networking.Data>

            init {
                GlobalScope.launch {
                    posts = Networking().getPosts()
                    println("Print from store: ${posts[0]}")
                }
            }
        }


    private object ReducerImpl : Reducer<State, Result> {
        fun <T> Array<T>.mapInPlace(transform: (T) -> Unit): Array<T> {
            for (i in this.indices) {
                transform(this[i])
            }
            return this
        }

        override fun State.reduce(result: Result): State =
            when (result) {
                is Result.TweetAdd -> copy(value = value + result.tweet)
                is Result.ToggleLiked -> copy(value = value.mapInPlace {
                    if (it.id == result.id) {
                        it.liked = !it.liked
                    }
                    println(">>> CHANGE LIKED-ID ${it.id} TO: ${it.liked}")
                })

            }
    }
}
