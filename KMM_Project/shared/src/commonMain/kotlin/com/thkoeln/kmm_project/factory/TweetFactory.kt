package com.thkoeln.kmm_project.factory

import com.arkivanov.mvikotlin.core.store.Bootstrapper
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveBootstrapper
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.scheduler.ioScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.scheduler.newThreadScheduler
import com.badoo.reaktive.single.map
import com.badoo.reaktive.single.observeOn
import com.badoo.reaktive.single.singleFromFunction
import com.badoo.reaktive.single.subscribeOn
import com.thkoeln.kmm_project.datastructures.Tweet
import com.thkoeln.kmm_project.networking.database.TweetDatabaseImpl
import com.thkoeln.kmm_project.store.TweetStore.*
import kotlinx.coroutines.*


class TweetFactory(storeFactory: StoreFactory) : AbstractTweetFactory(storeFactory = storeFactory) {
    override fun createExecutor(): Executor<Intent, Action, State, Result, Nothing> = ExecutorImpl()

    override fun createBootstrapper(): Bootstrapper<Action> = BootstrapperImpl()

    private inner class BootstrapperImpl : ReaktiveBootstrapper<Action>() {
        override fun invoke() {
            singleFromFunction {

                TweetDatabaseImpl().getAll()


            }
                .subscribeOn(ioScheduler)
                .map { it.let(Action::AddAll) }
                .observeOn(mainScheduler)
                .subscribeScoped(isThreadLocal = true, onSuccess = ::dispatch)
        }
    }


    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Action, State, Result, Nothing>() {

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.AddTweet -> dispatch(Result.TweetAdd(intent.tweet))
                is Intent.ToggleLiked -> dispatch(Result.ToggleLiked(intent.id))
                is Intent.AddAllTweets -> dispatch(Result.AddAllTweets(intent.tweets))
            }


        @DelicateCoroutinesApi
        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.AddAll -> dispatch(Result.AddAllTweets(action.tweets))
            }
        }
    }
}