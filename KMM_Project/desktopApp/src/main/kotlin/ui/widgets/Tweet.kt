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
fun Tweet(userName: String, date: String, content: String, liked: Boolean) {
    val likedButton = remember { mutableStateOf(liked) }

    Column(modifier = Modifier.padding(10.dp)) {


        Row {
            Text(userName, modifier = Modifier.padding(10.dp))
            Text(date, modifier = Modifier.padding(10.dp))
        }

        Text(content, modifier = Modifier.padding(10.dp))

        Row {
            TextButton(
                onClick = { likedButton.value = !likedButton.value },
                //colors = ButtonDefaults.buttonColors(
                //    contentColor = if (likedButton.value) Color.Blue else Color.Gray
                //),
            ) {
                Text("Like")
            }
            TextButton(onClick = { println("GO TO COMMENT") }) { Text("Comment") }

        }


    }


}


