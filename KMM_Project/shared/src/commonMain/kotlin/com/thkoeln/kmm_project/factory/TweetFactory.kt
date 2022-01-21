package com.thkoeln.kmm_project.factory

import com.arkivanov.mvikotlin.core.store.Bootstrapper
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.thkoeln.kmm_project.datastructures.Tweet
import com.thkoeln.kmm_project.networking.database.TweetDatabaseImpl
import com.thkoeln.kmm_project.store.TweetStore.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class TweetFactory(
    storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val userName: String
) : AbstractTweetFactory(storeFactory = storeFactory) {

    private val database = TweetDatabaseImpl()

    override fun createExecutor(): Executor<Intent, Action, State, Result, Nothing> = ExecutorImpl()

    override fun createBootstrapper(): Bootstrapper<Action> = BootstrapperImpl()

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                val tweets = withContext(ioContext) {TweetDatabaseImpl().getAll()}
                dispatch(Action.AddAll(tweets))
            }
        }
    }


    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Result, Nothing>(mainContext) {

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.AddTweet -> addTweet(intent.tweet)
                is Intent.ToggleLiked -> toggleLiked(intent.id, intent.userName)
                is Intent.AddAllTweets -> dispatch(Result.AddAllTweets(intent.tweets))
            }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.AddAll -> dispatch(Result.AddAllTweets(action.tweets))
            }
        }

        fun addTweet(tweet: Tweet) {
            scope.launch {
                database.postTweet(tweet.userName, tweet.tweetContent, tweet.id)
                dispatch(Result.TweetAdd(tweet))
                getAllTweets()
            }
        }

        fun toggleLiked(id: String, userName: String) {
            scope.launch {
                database.postLike(id, userName)
                dispatch(Result.ToggleLiked(id))
            }
        }

        fun getAllTweets() {
            scope.launch {
                val tweets = database.getAll()
                dispatch(Result.AddAllTweets(tweets))
            }
        }
    }
}