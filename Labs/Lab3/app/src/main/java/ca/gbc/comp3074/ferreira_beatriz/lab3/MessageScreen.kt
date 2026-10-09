package ca.gbc.comp3074.ferreira_beatriz.lab3

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageScreen() {
    val context = LocalContext.current
    val messages = remember { loadMessages(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ){
        Text(
            text = "Messages",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyMessageList(messages = messages)
    }
}

@Composable
fun MessageItem(message: Message) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(55.dp)
                .background(
                    color = if (message.sender == "Gwyn")
                        Color(0xFFB0EAFC)
                    else
                        Color(0xFFDDD8F5),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                // gets the first letter of the name of the sender to show in the box
                text = message.sender.take(1),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF220659)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column() {
            Text(
                text = message.sender,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFEFF5FA),
                shadowElevation = 1.dp
            ) {
                Text(
                    text = message.text,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun LazyMessageList(messages: List<Message>) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(messages) { message ->
            MessageItem(message = message)
        }
    }
}