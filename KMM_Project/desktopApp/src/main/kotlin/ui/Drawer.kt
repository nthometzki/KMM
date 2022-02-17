package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.thkoeln.kmm_project.factory.TweetFactory
import com.thkoeln.kmm_project.store.TweetStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val screens = listOf(
    DrawerScreens.Twitter,
    DrawerScreens.Login,
)

sealed class DrawerScreens(
    val route: String,
    val title: String,
    val screenToLoad: @Composable () -> Unit
) {
    object Twitter : DrawerScreens("twitter", "Twitter", {
        val store =
            TweetFactory(
                LoggingStoreFactory(DefaultStoreFactory()),
                Dispatchers.Main.immediate,
                Dispatchers.IO,
                "userName"
            ).create("userName")

        TwitterScreen(store)
    })

    object Login : DrawerScreens("logout", "Logout", {
        LoginScreen()
    })
}


@Composable
fun DrawerHeader() {
    Column(
        Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                color = Color.White
            ).padding(16.dp), content = {
            Text("Welcome User!", color = Color.Black, fontSize = 20.sp)
            Text("mail@email.com", color = Color(0xFFC4C4C4), fontSize= 14.sp)
        }, verticalArrangement = Arrangement.Bottom,


    )
    Divider()
}

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    selectedScreen: DrawerScreens,
    onMenuSelected: ((drawerScreen: DrawerScreens) -> Unit)? = null,
) {
    Column(
        modifier
            .fillMaxSize()
    ) {
        DrawerHeader()
        screens.forEach { screen ->

            Row(
                content = {
                    Text(
                        text = screen.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (screen.route == selectedScreen.route) Color.White else Color.Black
                    )
                }, modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(
                        color = if (screen.route == selectedScreen.route) Color(0XFF6200EE) else Color.White,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .fillMaxWidth()
                    .clickable(onClick = {
                        onMenuSelected?.invoke(screen)
                    })
                    .padding(vertical = 8.dp, horizontal = 16.dp)

            )
            Divider()
        }
    }
}

