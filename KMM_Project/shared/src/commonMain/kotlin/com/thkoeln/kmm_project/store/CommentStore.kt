package com.thkoeln.kmm_project.store

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.thkoeln.kmm_project.datastructures.Comment
import com.thkoeln.kmm_project.networking.database.TweetDatabaseImpl
import com.thkoeln.kmm_project.store.CommentStore.Intent
import com.thkoeln.kmm_project.store.CommentStore.State
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


internal interface CommentStore : Store<Intent, State, Nothing> {

    sealed class Intent : JvmSerializable {
        data class AddComment(val comment: Comment, val postid: String) : Intent()
        //data class ToggleLiked(val id: String) : Intent()
    }

    @Parcelize
    data class State(
        val value: Array<Comment> = arrayOf(),
    ) : Parcelable
}

internal class CommentStoreFactory(
    private val storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val tweetId: String
) {

    private val database = TweetDatabaseImpl()

    protected sealed class Action : JvmSerializable {
        class AddAll(val comments: Array<Comment>) : Action()
    }

    protected fun createExecutor(): Executor<Intent, Action, State, Result, Nothing> = ExecutorImpl()

    protected fun createBootstrapper(): CoroutineBootstrapper<Action> = BootstrapperImpl()

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                val comments = withContext(ioContext) { TweetDatabaseImpl().getComments(tweetId) }
                dispatch(Action.AddAll(comments))
            }
        }
    }

    sealed class Result {
        class AddComment(val comment: Comment) : Result()
        //class ToggleLiked(val id: String) : Result()
        class AddAllComments(val comments: Array<Comment>) : Result()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Result, Nothing>(mainContext) {

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.AddComment -> {
                    addComment(intent.comment, intent.postid)
                }
                //is Intent.ToggleLiked -> dispatch(Result.ToggleLiked(intent.id))
            }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.AddAll -> dispatch(Result.AddAllComments(action.comments))
            }
        }

        fun addComment(comment: Comment, postid: String) {
            scope.launch {
                database.postComment(
                    "testid", // Todo: Change Username
                    postid,
                    comment.tweetContent
                )
                dispatch(Result.AddComment(comment))
            }
        }
    }

    fun create(comments: Array<Comment>): CommentStore =
        object : CommentStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "CommentStore",
            initialState = State(comments),
            bootstrapper = createBootstrapper(),
            executorFactory = ::createExecutor,
            reducer = ReducerImpl,
        ) {

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
                is Result.AddAllComments -> copy(value = result.comments)
                /*is Result.ToggleLiked -> copy(value = value.mapInPlace {
                    if (it.id == result.id) {
                        it.liked = !it.liked
                    }
                    println(">>> CHANGE LIKED-ID ${it.id} TO: ${it.liked}")
                })*/

            }
    }
}
