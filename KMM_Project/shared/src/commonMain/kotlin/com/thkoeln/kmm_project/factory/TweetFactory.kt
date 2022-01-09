package com.thkoeln.kmm_project.factory

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.thkoeln.kmm_project.datastructures.Tweet
import com.thkoeln.kmm_project.networking.Networking
import com.thkoeln.kmm_project.networking.database.TweetDatabaseImpl
import com.thkoeln.kmm_project.store.TweetStore.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class TweetFactory(storeFactory: StoreFactory, private val ioContext: CoroutineContext) : AbstractTweetFactory(storeFactory = storeFactory) {
    override fun createExecutor(): Executor<Intent, Action, State, Result, Nothing> = ExecutorImpl()

    override fun createBootstrapper(): CoroutineBootstrapper<Action> = BootstrapperImpl()

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {

                val tweets = withContext(ioContext) { TweetDatabaseImpl().getAll() }


                dispatch(Action.AddAll(tweets))
            }
        }
    }



    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Result, Nothing>() {

        override fun handleIntent(intent: Intent) {
            when (intent) {
                is Intent.AddTweet -> dispatch(Result.TweetAdd(intent.tweet))
                is Intent.ToggleLiked -> dispatch(Result.ToggleLiked(intent.id))
                is Intent.AddAllTweets -> dispatch(Result.AddAllTweets(intent.tweets))
            }
        }

        override fun handleAction(action: Action) {
            when (action) {
                is Action.AddAll -> dispatch(Result.AddAllTweets(action.tweets))
            }
        }

    }
}