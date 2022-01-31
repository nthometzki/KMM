package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.ValueObserver
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.rx.Disposable
import com.thkoeln.kmm_project.datastructures.Tweet
import com.thkoeln.kmm_project.mapper.STATE_TO_MODEL
import com.thkoeln.kmm_project.store.TweetStore
import ui.widgets.AddTweet
import ui.widgets.Tweet
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.thkoeln.kmm_project.view.TweetView
import kotlinx.coroutines.flow.MutableStateFlow
import ui.widgets.ModifyStore

// Code by https://github.com/JetBrains/compose-jb/tree/master/examples/todoapp

fun <T : Any> Store<*, T, *>.asValue(): Value<T> =
    object : Value<T>() {
        override val value: T get() = state
        private var disposables = emptyMap<ValueObserver<T>, Disposable>()

        override fun subscribe(observer: ValueObserver<T>) {
            val disposable = states(com.arkivanov.mvikotlin.rx.observer(onNext = observer))
            this.disposables += observer to disposable
        }

        override fun unsubscribe(observer: ValueObserver<T>) {
            val disposable = disposables[observer] ?: return
            this.disposables -= observer
            disposable.dispose()
        }
    }


@Composable
fun TwitterScreen(store: TweetStore) {

    val models = store.asValue().map(STATE_TO_MODEL)
    val model by models.subscribeAsState()

    Column(
        content = {
            AddTweet(object : AddTweet {
                override fun addTweet(tweet: Tweet) {
                    addTweet(store, tweet)
                }
            })
            TweetList(model.tweets, store)
        }, modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}

@Composable
private fun TweetList(
    tweets: Array<Tweet>,
    store: TweetStore,
) {

    // val tweetCopy = tweets.copyOf().toMutableList()

    Box {
        tweets.sortByDescending { it.tweetDate }

        LazyColumn(modifier = Modifier.width(500.dp).border(0.5.dp, Color(0xFF828282))) {
            items(items = tweets, key = { tweet -> tweet.id }) { tweet ->
                Tweet(
                    tweet,
                    object : ModifyStore {
                        override fun onItemLiked(id: String) {
                            toggleLiked(store, id, "User A") // TODO add real user name

                        }
                    }
                )

                Divider()

            }


        }
        /*Column(
            modifier = Modifier
                .width(500.dp)
                .verticalScroll(rememberScrollState())
                .width(500.dp).border(0.5.dp, Color(0xFF828282))
        ) {
            tweets.forEachIndexed { index, tweet ->
                Tweet(
                    tweet,
                    object : ModifyStore {
                        override fun onItemLiked(id: String) {
                            toggleLiked(store, id, "User A") // TODO add real user name

                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
*/
    }
}


// Methods to change store

fun toggleLiked(store: TweetStore, id: String, userName: String) {
    store.accept(TweetStore.Intent.ToggleLiked(id, userName))
}

fun addTweet(store: TweetStore, tweet: Tweet) {
    store.accept(TweetStore.Intent.AddTweet(tweet))
}



