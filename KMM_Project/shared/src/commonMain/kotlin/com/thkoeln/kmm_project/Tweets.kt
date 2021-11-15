package com.thkoeln.kmm_project

class Tweets {

    // TODO: Fetch tweets from backend ?
    private val tweets = arrayOf(
        Tweet("Nico T.", "10 Nov 2021", "Lorem ipsum 123", true, true),
        Tweet(
            "Nico T.",
            "10 Nov 2021",
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren.",
            true,
            true
        ),
    )
    fun getAllTweets() : Array<Tweet>{
        return tweets
    }
}