package com.thkoeln.kmm_project.controller

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle as LifeCycleArkinov
import com.arkivanov.mvikotlin.core.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.thkoeln.kmm_project.mapper.eventToIntent
import com.thkoeln.kmm_project.mapper.stateToModel
import com.thkoeln.kmm_project.store.TweetStoreFactory
import com.thkoeln.kmm_project.view.TweetAddView
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.observable.map


class TweetAddController(lifecycle: Lifecycle) {
    private val store = TweetStoreFactory(DefaultStoreFactory).create()
    private var binder: Binder? = null

    init {
        lifecycle.doOnDestroy(store::dispose)
    }


    fun onViewCreated(view: TweetAddView) {

        binder = bind {
            store.states.map(stateToModel) bindTo view
            // Use store.labels to bind Labels to a consumer
            view.events.map(eventToIntent) bindTo store
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