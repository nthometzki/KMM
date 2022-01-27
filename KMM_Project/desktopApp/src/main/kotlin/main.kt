import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.NavigationDrawer

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "Twitter Clone",
        state = rememberWindowState(width = 1920.dp, height = 1080.dp)
    ) {

        NavigationDrawer()
    }
}

