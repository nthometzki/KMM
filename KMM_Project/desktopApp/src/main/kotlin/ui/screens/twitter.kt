package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.ValueObserver
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.arkivanov.mvikotlin.rx.Disposable
import com.thkoeln.kmm_project.factory.TweetFactory
import com.thkoeln.kmm_project.mapper.STATE_TO_MODEL
import com.thkoeln.kmm_project.store.TweetStore
import kotlinx.coroutines.Dispatchers
import java.util.*


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
fun TwitterScreen() {

   val store =
       TweetFactory(LoggingStoreFactory(DefaultStoreFactory()), Dispatchers.Main.immediate, Dispatchers.IO, "userName").create("userName")

    val models = store.asValue().map(STATE_TO_MODEL)
    println(models)

    Column(
        content = {
            Text(text = "You are in Twitter Screen")
        }, modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

