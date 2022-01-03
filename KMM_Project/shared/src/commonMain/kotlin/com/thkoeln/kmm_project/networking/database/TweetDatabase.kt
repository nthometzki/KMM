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
    fun getTweet(postId: String): Tweet
    fun getLikes(postId: String): Array<Tweet>
    fun getComments(postId: String): Array<Comment>

    fun postTweet(googleId: String, post: String)
    fun postLike(googleId: String, postId: String)
    fun postComment(googleId: String, postId: String, comment: String)

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

    override fun getTweet(postId: String): Tweet {
        TODO("Not yet implemented")
    }

    override fun getLikes(postId: String): Array<Tweet> {
        TODO("Not yet implemented")
    }

    override fun getComments(postId: String): Array<Comment> {
        TODO("Not yet implemented")
    }

    override fun postTweet(googleId: String, post: String) {
        TODO("Not yet implemented")
    }

    override fun postLike(googleId: String, postId: String) {
        TODO("Not yet implemented")
    }

    override fun postComment(googleId: String, postId: String, comment: String) {
        TODO("Not yet implemented")
    }
}