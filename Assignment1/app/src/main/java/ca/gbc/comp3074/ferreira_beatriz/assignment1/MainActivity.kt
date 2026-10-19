package ca.gbc.comp3074.ferreira_beatriz.assignment1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ca.gbc.comp3074.ferreira_beatriz.assignment1.ui.theme.Assignment1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment1Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                        containerColor = Color(0xFFF3EEFA),
                        contentColor = Color(0xFF2F1365)) { innerPadding ->
                    PayCalculatorScreen(
                        modifier = Modifier.padding(innerPadding), onAboutClick = {
                            val intent = Intent(this, AboutActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}
