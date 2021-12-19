package com.thkoeln.kmm_project.datastructures

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Comment( val tweetId: String, var id: String, var userName: String, var tweetDate: String, var tweetContent: String, var liked: Boolean = false): Parcelable
