package com.thkoeln.kmm_project.controller

import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.badoo.reaktive.observable.map
import com.thkoeln.kmm_project.datastructures.Comment
import com.thkoeln.kmm_project.mapper.EVENT_TO_COMMENT_INTENT
import com.thkoeln.kmm_project.mapper.STATE_TO_COMMENT_MODEL
import com.thkoeln.kmm_project.store.CommentStoreFactory
import com.thkoeln.kmm_project.view.CommentView


class CommentController(comments: Array<Comment>) {
    private val store =
        CommentStoreFactory(LoggingStoreFactory(DefaultStoreFactory())).create(comments)
    private var binder: Binder? = null


    fun onViewCreated(view: CommentView) {
        binder = bind {
            store.states.map(STATE_TO_COMMENT_MODEL) bindTo view
            // Use store.labels to bind Labels to a consumer
            view.events.map(EVENT_TO_COMMENT_INTENT) bindTo store
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