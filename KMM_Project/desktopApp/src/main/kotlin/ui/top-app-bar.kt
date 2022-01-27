package ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Composable
fun TopAppBarLayout(
    currentScreen: DrawerScreens,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    TopAppBar(title = { Text(currentScreen.title) }, navigationIcon = {
        IconButton(onClick = {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }) {
            Icon(Icons.Filled.Menu, "")
        }
    },  backgroundColor = Color(0XFF6200EE), contentColor = Color.White)
}