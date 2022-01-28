import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.thkoeln.kmm_project.factory.TweetFactory
import kotlinx.coroutines.Dispatchers
import ui.NavigationDrawer

fun main() = application {
    val store =
        TweetFactory(
            LoggingStoreFactory(DefaultStoreFactory()),
            Dispatchers.Main.immediate,
            Dispatchers.IO,
            "userName"
        ).create("userName")

    Window(
        onCloseRequest = ::exitApplication,
        title = "Twitter Clone",
        state = rememberWindowState(width = 1920.dp, height = 1080.dp)
    ) {

        NavigationDrawer()
    }
}

