package ui

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val currentScreen: MutableState<DrawerScreens> = remember { mutableStateOf(DrawerScreens.Twitter) }
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Drawer(
                selectedScreen = currentScreen.value,
                onMenuSelected = { drawerScreen: DrawerScreens ->
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    currentScreen.value = drawerScreen
                })
        },
        content = {
            currentScreen.value.screenToLoad()
        },
        topBar = {
            TopAppBarLayout(currentScreen.value, scope, scaffoldState)
        },
        )
}