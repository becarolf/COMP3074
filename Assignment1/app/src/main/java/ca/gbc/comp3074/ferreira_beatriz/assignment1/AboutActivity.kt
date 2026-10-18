package ca.gbc.comp3074.ferreira_beatriz.assignment1

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.widget.Toolbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ca.gbc.comp3074.ferreira_beatriz.assignment1.ui.theme.Assignment1Theme

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        // use the toolbar as this activity's ActionBar
                        AndroidView(
                            factory = { context ->
                                Toolbar(context).apply {
                                    title = "About"

                                    setActionBar(this)

                                    actionBar?.setDisplayHomeAsUpEnabled(true)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Column(
                            modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Beatriz Ferreira",
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Text(
                                text = "Student ID: 101176410",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Text(
                                text = "COMP3074 — Assignment 1",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // close the activity and return to the calculator
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}