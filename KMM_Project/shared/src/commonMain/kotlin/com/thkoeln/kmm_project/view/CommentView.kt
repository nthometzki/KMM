package com.thkoeln.kmm_project.view

import com.arkivanov.mvikotlin.core.view.MviView
import com.thkoeln.kmm_project.datastructures.Comment


interface CommentView : MviView<CommentView.Model, CommentView.Event> {

    data class Model(
        val comments: Array<Comment>
    )

    sealed class Event {
        data class AddComment(val comment: Comment) : Event()
        data class ToggleLiked(val id: String) : Event()
    }
}