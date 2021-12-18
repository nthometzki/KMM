package com.thkoeln.kmm_project

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Tweet(var id: String, var userName: String, var tweetDate: String, var tweetContent: String, var liked: Boolean = false, var comment: Boolean): Parcelable