package com.example.animationslab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.animationslab.ui.theme.AnimationsLabTheme
import androidx.compose.foundation.layout.statusBarsPadding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationsLabTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ExamplesScreen()
                }
            }
        }
    }
}

@Composable
fun ExamplesScreen() {
    var showDetails by remember { mutableStateOf(false) }
    var showCross by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            SectionTitle("1. Like Button — animate*AsState (scale + tint)")
            LikeButton()
        }

        item {
            SectionTitle("2. Visibility card — AnimatedVisibility (enter/exit)")
            Button(onClick = { showDetails = !showDetails }) {
                Text(if (showDetails) "Скрыть карточку" else "Показать карточку")
            }
            DetailsCard(showDetails)
        }

        item {
            SectionTitle("3. Mood icon — updateTransition (color + scale + rotation)")
            MoodIcon()
        }

        item {
            SectionTitle("4. Pulsing button — rememberInfiniteTransition")
            PulseButton(onClick = { /* demo */ })
        }

        item {
            SectionTitle("5. Crossfade / AnimatedContent demo")
            Button(onClick = { showCross = !showCross }) {
                Text("Переключить содержание")
            }
            CrossfadeDemo(showCross)
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
}