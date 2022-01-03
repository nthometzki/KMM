package com.thkoeln.kmm_project.android.comment;

import android.app.Activity;
import android.content.Context
import android.view.View;
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ListView
import com.thkoeln.kmm_project.view.CommentView.Event
import com.thkoeln.kmm_project.view.CommentView.Model
import com.arkivanov.mvikotlin.core.view.BaseMviView;
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.datastructures.Comment
import com.thkoeln.kmm_project.view.CommentView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class CommentViewImpl(
    root: View,
    private val activity: Activity,
    val tweetId: String
) : BaseMviView<Model, Event>(), CommentView {
    private var listView: ListView = root.findViewById(R.id.comment_list_view)

    fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                func()
            }
            true
        }
    }

    init {
        val editText = root.findViewById<EditText>(R.id.comment_edit_text)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        editText.onSubmit {
            dispatch(
                Event.AddComment(
                    Comment(
                        tweetId,
                        UUID.randomUUID().toString(),
                        "USER B.",
                        current.format(formatter),
                        editText.text.toString(),
                    ),
                    3 //Todo: get postid from a post and place it here
                )
            )
            editText.text = null
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(root.windowToken, 0)
        }

    }


    override fun render(model: Model) {
        super.render(model)
        val adapter = CommentListAdapter(activity, model.comments, object : Listener {
            override fun onItemLiked(id: String) {
                dispatch(Event.ToggleLiked(id))
            }
        })

        listView.adapter = adapter
    }
}
