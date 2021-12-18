import android.app.Activity
import android.view.View
import android.widget.*
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thkoeln.kmm_project.Tweet
import com.thkoeln.kmm_project.android.R
import com.thkoeln.kmm_project.android.TweetListAdapter
import com.thkoeln.kmm_project.view.TweetAddView
import com.thkoeln.kmm_project.view.TweetAddView.Event
import com.thkoeln.kmm_project.view.TweetAddView.Model


class TweetAddViewImpl(private val root: View, private val activity: Activity) :
    BaseMviView<Model, Event>(), TweetAddView {
    private var listView: ListView = root.findViewById(R.id.tweet_list_view)

    init {
        val input = root.findViewById<EditText>(R.id.tweet_input)
        val tweetArea = root.findViewById<RelativeLayout>(R.id.tweeting)
        val fab = root.findViewById<FloatingActionButton>(R.id.fab)
        root.findViewById<Button>(R.id.tweet_submit_button).setOnClickListener {
            dispatch(
                Event.TweetAdd(
                    Tweet(
                        "input.text1",
                        "user",
                        "02.12.2021",
                        input.text.toString(),
                        false,
                        false
                    )
                )
            )
            if (input?.text != null) {
                input.text = null
                tweetArea?.visibility = View.INVISIBLE
                fab?.visibility = View.VISIBLE
            }
        }
    }

    override fun render(model: Model) {
        super.render(model)
        val adapter = TweetListAdapter(activity, model.tweets)

        listView.adapter = adapter
    }
}
