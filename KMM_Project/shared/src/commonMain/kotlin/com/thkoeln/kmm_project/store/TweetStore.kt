package com.thkoeln.kmm_project.store

import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle.*
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.store.create
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor

import com.thkoeln.kmm_project.store.TweetStore.Intent

internal interface TweetStore : Store<Intent, State, Nothing> {

    sealed class Intent {
        object Increment : Intent()
        object Decrement : Intent()
        data class Sum(val n: Int) : Intent()
    }

    data class State(
        val value: Long = 0L
    )
}

internal class TweetStoreFactory(private val storeFactory: StoreFactory) {


    private sealed class Result {
        class Value(val value: Long) : Result()
    }

    fun create(): TweetStore =
        object : TweetStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "CounterStore",
            initialState = State.INITIALIZED,
            reducer = ReducerImpl,

            ) {
        }




    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State =
            when (result) {
                is Result.Value -> copy(value = result.value)
            }
    }



    private class ExecutorImpl : ReaktiveExecutor<Intent, Nothing, State, Result, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.Increment -> dispatch(Result.Value(getState().value + 1))
                is Intent.Decrement -> dispatch(Result.Value(getState().value - 1))
                is Intent.Sum -> sum(intent.n)
            }

        private fun sum(n: Int) {
            singleFromFunction { (1L..n.toLong()).sum() }
                .subscribeOn(computationScheduler)
                .map(Result::Value)
                .observeOn(mainScheduler)
                .subscribeScoped(onSuccess = ::dispatch)
        }
    }
}
