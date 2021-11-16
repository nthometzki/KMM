package com.thkoeln.kmm_project

class Tweets(
    private var tweets: Array<Tweet> = arrayOf(
        Tweet("1234", "Nico T.", "10 Nov 2021", "Lorem ipsum 123", false, true),
        Tweet(
            "5678",
            "Nico T.",
            "10 Nov 2021",
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren.",
            false,
            true
        ),
    )
) {

    // TODO: Fetch tweets from backend ?

    fun getAllTweets(): Array<Tweet> {
        return tweets
    }

    fun addTweet(tweet: Tweet) {
        tweets += tweet
    }
}