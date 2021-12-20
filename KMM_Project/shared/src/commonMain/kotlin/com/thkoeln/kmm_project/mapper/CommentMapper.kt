package com.thkoeln.kmm_project.mapper

import com.thkoeln.kmm_project.store.CommentStore
import com.thkoeln.kmm_project.view.CommentView


internal val STATE_TO_COMMENT_MODEL: CommentStore.State.() -> CommentView.Model =
    {
        CommentView.Model(
            comments = value
        )
    }

internal val EVENT_TO_COMMENT_INTENT: CommentView.Event.() -> CommentStore.Intent =
    {
        when (this) {
            is CommentView.Event.AddComment -> CommentStore.Intent.AddComment(comment)
            is CommentView.Event.ToggleLiked -> CommentStore.Intent.ToggleLiked(id)
        }
    }