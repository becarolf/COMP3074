package ca.gbc.comp3074.Ferreira_Beatriz.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.gbc.comp3074.Ferreira_Beatriz.lab2.ui.theme.Lab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    CounterApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CounterApp(modifier: Modifier = Modifier) {
    var count by remember {
        mutableStateOf(0)
    }

    var step by remember {
        mutableStateOf(1)
    }

    Column(modifier = modifier.fillMaxSize()
            // 0x indicates a hexadecimal color value
            .background(Color(0xFFFFFFFF)).padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)

    {
        Image(
            painter = painterResource(id = R.drawable.gbc_logo), // reference to res/drawable/my_logo.png
            contentDescription = "George Brown Polytechnic Logo",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "COMP3074 - LAB 2 ",
            fontSize = 24.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        // counter display
        Text(
            text = count.toString(),
            fontSize = 48.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // shows the current increment/decrement mode
        Text(
            text = "Step: ±$step",
            fontSize = 18.sp,
            color = Color.DarkGray
        )


        Spacer(modifier = Modifier.height(40.dp))



        // buttons + and -
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Button(onClick = {
                    if (count - step >= 0) {
                        count -= step
                    }
                }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color(
                0xFF69D26D
            )
            ))
            {
                Text(text = "-", fontSize = 26.sp)
            }

            Button(
                onClick = { count += step },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF69D26D))
            )
            {
                Text(text = "+", fontSize = 26.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))


        // Rsest and Step
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(
                onClick = {
                    count = 0
                    step = 1
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFD0000))
            ) {
                Text(text = "Reset", fontSize = 26.sp)
            }


            Button(
                onClick = {
                    step = 2
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A86B))
            ) {
                Text(text = "Step", fontSize = 26.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterAppPreview() {
    Lab2Theme {
        CounterApp()
    }
}