package ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thkoeln.kmm_project.datastructures.Tweet

interface ModifyStore {
    fun onItemLiked(id: String)
}

@Composable
fun Tweet(tweet: Tweet, storeModifier: ModifyStore) {
   // val tweetCopy  = remember { tweet }
    Column(modifier = Modifier.padding(10.dp)) {
        Row {
            Text(tweet.userName, modifier = Modifier.padding(10.dp))
            Text(tweet.tweetDate, modifier = Modifier.padding(10.dp))
        }

        Text(tweet.tweetContent, modifier = Modifier.padding(10.dp))

        println(tweet.liked)

        Row {
            TextButton(
                onClick = {
                    // https://stackoverflow.com/questions/68703030/forcing-a-recomposition-android-compose
                    storeModifier.onItemLiked(tweet.id)
                },

                ) {
                Text(
                    "Like", color = if (tweet.liked) Color.Blue else Color.Gray
                )
            }
            TextButton(onClick = { println("GO TO COMMENT") }) { Text("Comment", color = Color.Gray) }
        }
    }
}


