package com.thkoeln.kmm_project.factory

import com.arkivanov.mvikotlin.core.store.Bootstrapper
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendBootstrapper
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveBootstrapper
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.badoo.reaktive.scheduler.ioScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.scheduler.newThreadScheduler
import com.badoo.reaktive.single.map
import com.badoo.reaktive.single.observeOn
import com.badoo.reaktive.single.singleFromFunction
import com.badoo.reaktive.single.subscribeOn
import com.thkoeln.kmm_project.datastructures.Tweet
import com.thkoeln.kmm_project.networking.Networking
import com.thkoeln.kmm_project.networking.database.TweetDatabaseImpl
import com.thkoeln.kmm_project.store.TweetStore.*
import com.thkoeln.kmm_project.view.TweetView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class TweetFactory(
    storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext
) : AbstractTweetFactory(storeFactory = storeFactory) {

    private val database = TweetDatabaseImpl()

    override fun createExecutor(): Executor<Intent, Action, State, Result, Nothing> = ExecutorImpl()

    override fun createBootstrapper(): CoroutineBootstrapper<Action> = BootstrapperImpl()

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                val tweets = withContext(ioContext) {TweetDatabaseImpl().getAll()}
                println(tweets[0].tweetContent)
                dispatch(Action.AddAll(tweets))
            }
        }
    }


    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Result, Nothing>(mainContext) {

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.AddTweet -> addTweet(intent.tweet)
                is Intent.ToggleLiked -> dispatch(Result.ToggleLiked(intent.id))
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
            }
            dispatch(Result.TweetAdd(tweet))
            getAllTweets()
        }

        fun toggleLiked(id: String) {
            scope.launch {
                //TweetDatabaseImpl().postLike()
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