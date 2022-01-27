package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kotlinx.coroutines.Dispatchers
import java.util.*


@Composable
fun TwitterScreen() {

   val store =
        Tweet(
            LoggingStoreFactory(DefaultStoreFactory()),
            Dispatchers.Main.immediate,
            Dispatchers.IO
        ).create()

    Column(
        content = {
            Text(text = "You are in Twitter Screen")
        }, modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

