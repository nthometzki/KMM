package com.thkoeln.kmm_project.datastructures

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Tweet(
    var id: String,
    var postid: String,
    var userName: String,
    var tweetDate: String,
    var tweetContent: String,
    var liked: Boolean = false,
    var comments: Array<Comment>
) : Parcelable
