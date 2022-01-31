import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.thkoeln.kmm_project.factory.TweetFactory
import kotlinx.coroutines.Dispatchers
import ui.NavigationDrawer

fun main() = application {

    val windowState = rememberWindowState()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Twitter Clone",
        state = windowState
    ) {

        NavigationDrawer()
    }
}



