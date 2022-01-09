package com.thkoeln.kmm_project.store

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.thkoeln.kmm_project.datastructures.Comment
import com.thkoeln.kmm_project.main
import com.thkoeln.kmm_project.networking.Networking
import com.thkoeln.kmm_project.networking.database.TweetDatabaseImpl
import com.thkoeln.kmm_project.store.CommentStore.Intent
import com.thkoeln.kmm_project.store.CommentStore.State
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


internal interface CommentStore : Store<Intent, State, Nothing> {

    sealed class Intent : JvmSerializable {
        data class AddComment(val comment: Comment, val postid: String) : Intent()
        data class ToggleLiked(val id: String) : Intent()
    }

    @Parcelize
    data class State(
        val value: Array<Comment> = arrayOf(),
    ) : Parcelable
}

internal class CommentStoreFactory(private val storeFactory: StoreFactory) {

    sealed class Result {
        class AddComment(val comment: Comment, val postid: String) : Result()
        class ToggleLiked(val id: String) : Result()
    }

    private class ExecutorImpl : ReaktiveExecutor<Intent, Nothing, State, Result, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.AddComment -> {
                    //TweetDatabaseImpl().postComment("testid", intent.postid, intent.comment.tweetContent)  // Todo("change googleid")
                    dispatch(Result.AddComment(intent.comment, intent.postid))
                }
                is Intent.ToggleLiked -> dispatch(Result.ToggleLiked(intent.id))
            }
    }

    fun create(comments: Array<Comment>): CommentStore =
        object : CommentStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "CommentStore",
            initialState = State(comments),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl,
        ) {
            val main = main()
        }


    private object ReducerImpl : Reducer<State, Result> {
        fun <T> Array<T>.mapInPlace(transform: (T) -> Unit): Array<T> {
            for (i in this.indices) {
                transform(this[i])
            }
            return this
        }

        override fun State.reduce(result: Result): State =
            when (result) {
                is Result.AddComment -> copy(value = value + result.comment)
                is Result.ToggleLiked -> copy(value = value.mapInPlace {
                    if (it.id == result.id) {
                        it.liked = !it.liked
                    }
                    println(">>> CHANGE LIKED-ID ${it.id} TO: ${it.liked}")
                })

            }
    }
}
