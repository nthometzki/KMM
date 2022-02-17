import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.NavigationDrawer

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Twitter Clone",
        state = rememberWindowState()
    ) {
        NavigationDrawer()
    }
}



