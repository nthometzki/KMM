package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            AddTweet()
            TweetList(model.tweets.toList())
        }, modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}


@Composable
private fun TweetList(
    items: List<Tweet>,
) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState, modifier = Modifier.width(500.dp)) {
            items(items) {
                Tweet(
                    userName = it.userName,
                    date = it.tweetDate,
                    content = it.tweetContent,
                    liked = it.liked
                )

                Divider()
            }
        }

    }
}



