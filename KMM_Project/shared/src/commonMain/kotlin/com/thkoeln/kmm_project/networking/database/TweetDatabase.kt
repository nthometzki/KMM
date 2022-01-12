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

    suspend fun postTweet(googleId: String, post: String, id: String)
    suspend fun postLike(googleId: String, postId: String)
    suspend fun postComment(googleId: String, postId: String, comment: String)

}

class TweetDatabaseImpl : TweetDatabase {

    @DelicateCoroutinesApi
    override suspend fun getAll(): Array<Tweet> {
        lateinit var posts: Array<Networking.Data>
        val mappedTweets = arrayListOf<Tweet>()
        val job = GlobalScope.launch {
            posts = Networking().getPosts()

            for (p in posts) {
                mappedTweets.add(
                    Tweet(
                        p.id,
                        p.account_id,
                        p.timestamp,
                        p.text,
                        false,
                        arrayOf()
                    )
                )
            }
        }

        job.join()

        println(mappedTweets[0].tweetContent)

        return mappedTweets.toTypedArray()
    }

    override suspend fun getTweet(postId: String): Tweet {
        var mappedTweet = Tweet("", "", "", "", false, arrayOf())

        val job = GlobalScope.launch {
            val post = Networking().getTweet(postId)

            mappedTweet = Tweet(
                post.id,
                post.account_id,
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
        val mappedComments = arrayListOf<Comment>()
        val job = GlobalScope.launch {
            comments = Networking().getComments(postId)

            for (p in comments) {
                mappedComments.add(Comment(
                    p.post_id,
                    p.id,
                    p.account_id,
                    p.timestamp,
                    p.text,
                    false
                ))
            }
        }

        job.join()

        return mappedComments.toTypedArray()
    }

    override suspend fun postTweet(googleId: String, post: String, id: String) {
        val job = GlobalScope.launch {
            Networking().submitPost(googleId, post, id)
        }
        job.join()
    }

    override suspend fun postLike(googleId: String, postId: String) {
        val job = GlobalScope.launch {
            Networking().postLike(googleId, postId)
        }
        job.join()
    }

    override suspend fun postComment(googleId: String, postId: String, comment: String) {
        val job = GlobalScope.launch {
            Networking().submitComment(googleId, postId, comment)
        }
        job.join()
    }
}