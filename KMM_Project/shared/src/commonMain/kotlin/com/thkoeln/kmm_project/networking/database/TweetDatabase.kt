package com.thkoeln.kmm_project.networking.database

import com.thkoeln.kmm_project.datastructures.Comment
import com.thkoeln.kmm_project.datastructures.Tweet
import com.thkoeln.kmm_project.networking.Networking
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface TweetDatabase {
    suspend fun getAll(): Array<Tweet>
    suspend fun getTweet(postId: String): Tweet
    suspend fun getLikes(postId: String): Int
    suspend fun getComments(postId: String): Array<Comment>

    fun postTweet(googleId: String, post: String, id: String)
    fun postLike(googleId: String, postId: String)
    fun postComment(googleId: String, postId: String, comment: String)

}

class TweetDatabaseImpl : TweetDatabase {

    @DelicateCoroutinesApi
    override suspend fun getAll(): Array<Tweet> {
        lateinit var posts: Array<Networking.Data>
        val mappedTweets = arrayOf<Tweet>()
        val job = GlobalScope.launch {
            posts = Networking().getPosts()

            println(">>>> POSTS: ${posts[0]}")

            for (p in posts) {
                mappedTweets + Tweet(
                    p.id,
                    p.username,
                    p.timestamp,
                    p.text,
                    false,
                    arrayOf()
                )
            }
        }

        job.join()

        return mappedTweets
    }

    override suspend fun getTweet(postId: String): Tweet {
        var mappedTweet = Tweet("", "", "", "", false, arrayOf())

        val job = GlobalScope.launch {
            val post = Networking().getTweet(postId)

            mappedTweet = Tweet(
                post.id,
                post.username,
                post.timestamp,
                post.text,
                false,
                arrayOf()
            )
        }

        job.join()

        return mappedTweet
    }

    override suspend fun getLikes(postId: String): Int {
        var likes = 0
        val job = GlobalScope.launch {
            likes = Networking().getLikes(postId)
        }

        job.join()
        return likes
    }

    override suspend fun getComments(postId: String): Array<Comment> {
        lateinit var comments: Array<Networking.Comment>
        val mappedComments = arrayOf<Comment>()
        val job = GlobalScope.launch {
            comments = Networking().getComments(postId)

            println(">>>> COMMENTS: ${comments[0]}")

            for (p in comments) {
                mappedComments + Comment(
                    p.post_id,
                    p.account_id,
                    p.username,
                    p.timestamp,
                    p.text,
                    false
                )
            }
        }

        job.join()

        return mappedComments
    }

    override fun postTweet(googleId: String, post: String, id: String) {
        GlobalScope.launch {
            Networking().submitPost(googleId, post, id)
        }
    }

    override fun postLike(googleId: String, postId: String) {
        GlobalScope.launch {
            Networking().postLike(googleId, postId)
        }
    }

    override fun postComment(googleId: String, postId: String, comment: String) {
        GlobalScope.launch {
            Networking().submitComment(googleId, postId, comment)
        }
    }
}