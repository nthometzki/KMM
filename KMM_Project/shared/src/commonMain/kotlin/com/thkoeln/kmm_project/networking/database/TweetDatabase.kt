package com.thkoeln.kmm_project.networking.database

import com.thkoeln.kmm_project.datastructures.Comment
import com.thkoeln.kmm_project.datastructures.Tweet
import com.thkoeln.kmm_project.networking.Networking
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface TweetDatabase {
    fun getAll(): Array<Tweet>
    suspend fun getTweet(postId: Int): Tweet
    suspend fun getLikes(postId: Int): Int
    suspend fun getComments(postId: Int): Array<Comment>

    fun postTweet(googleId: String, post: String)
    fun postLike(googleId: String, postId: Int)
    fun postComment(googleId: String, postId: Int, comment: String)

}

class TweetDatabaseImpl : TweetDatabase {

    @DelicateCoroutinesApi
    override fun getAll(): Array<Tweet> {
        lateinit var posts: Array<Networking.Data>
        val mappedTweets = arrayOf<Tweet>()
        val job = GlobalScope.launch {
            posts = Networking().getPosts()

            println(">>>> POSTS: ${posts[0]}")

            for (p in posts) {
                mappedTweets + Tweet(
                    p.account_id,
                    p.id.toInt(),
                    p.username,
                    p.timestamp,
                    p.text,
                    false,
                    arrayOf()
                )
            }
        }

        //job.join()

        // This would work - however asynchronous call...
        // val tweets = arrayOf(Tweet("TEST", "TEST", "TEST", "TEST", false, arrayOf()))
        // return tweets

        return mappedTweets
    }

    override suspend fun getTweet(postId: Int): Tweet {
        var mappedTweet = Tweet("", 0, "", "", "", false, arrayOf())

        val job = GlobalScope.launch {
            val post = Networking().getTweet(postId)

            mappedTweet = Tweet(
                post.account_id,
                post.id.toInt(),
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

    override suspend fun getLikes(postId: Int): Int {
        var likes = 0
        val job = GlobalScope.launch {
            likes = Networking().getLikes(postId)
        }

        job.join()
        return likes
    }

    override suspend fun getComments(postId: Int): Array<Comment> {
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

    override fun postTweet(googleId: String, post: String) {
        GlobalScope.launch {
            Networking().submitPost(googleId, post)
        }
    }

    override fun postLike(googleId: String, postId: Int) {
        GlobalScope.launch {
            Networking().postLike(googleId, postId)
        }
    }

    override fun postComment(googleId: String, postId: Int, comment: String) {
        GlobalScope.launch {
            Networking().submitComment(googleId, postId, comment)
        }
    }
}