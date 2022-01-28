package ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun AddTweet() {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.border(0.5.dp, Color(0xFF828282)).width(500.dp).height(150.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {

        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("What's happening right now?") },
            modifier = Modifier
                .padding(20.dp),
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = { println("TEST") }, modifier = Modifier
                    .padding(10.dp).align(Alignment.BottomEnd)
            ) {
                Text("Tweet")
            }
        }

    }


}


