package com.thkoeln.kmm_project.controller

import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.thkoeln.kmm_project.mapper.EVENT_TO_INTENT
import com.thkoeln.kmm_project.mapper.STATE_TO_MODEL
import com.thkoeln.kmm_project.factory.TweetFactory
import com.thkoeln.kmm_project.view.TweetView
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.badoo.reaktive.observable.map
import kotlin.coroutines.CoroutineContext


class TweetController(
    mainContext: CoroutineContext,
    ioContext: CoroutineContext,
    userName: String
) {
    private val store = TweetFactory(LoggingStoreFactory(DefaultStoreFactory()), mainContext, ioContext, userName).create(userName)
    private var binder: Binder? = null

    fun onViewCreated(view: TweetView) {
        binder = bind {
            store.states.map(STATE_TO_MODEL) bindTo view
            // Use store.labels to bind Labels to a consumer
            view.events.map(EVENT_TO_INTENT) bindTo store
        }
    }

    fun onStart() {
        binder?.start()
    }

    fun onStop() {
        binder?.stop()
    }

    fun onViewDestroyed() {
        binder = null
    }

    fun onDestroy() {
        store.dispose()
    }
}