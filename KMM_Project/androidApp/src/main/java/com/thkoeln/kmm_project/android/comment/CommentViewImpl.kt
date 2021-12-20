package com.thkoeln.kmm_project.android.comment;

import android.app.Activity;
import android.view.View;
import android.widget.ListView
import com.thkoeln.kmm_project.view.CommentView.Event
import com.thkoeln.kmm_project.view.CommentView.Model
import com.arkivanov.mvikotlin.core.view.BaseMviView;
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.datastructures.Comment
import com.thkoeln.kmm_project.view.CommentView

class CommentViewImpl(
    root: View,
    private val activity: Activity
) : BaseMviView<Model, Event>(), CommentView {
    private var listView: ListView = root.findViewById(R.id.comment_list_view)

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
