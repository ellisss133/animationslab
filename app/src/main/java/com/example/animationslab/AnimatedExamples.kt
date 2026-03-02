package com.example.animationslab

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LikeButton() {
    var liked by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (liked) 1.4f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "likeScale"
    )


    val tint by animateColorAsState(
        targetValue = if (liked) Color(0xFFE53935) else Color.Unspecified,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "likeTint"
    )

    Icon(
        imageVector = if (liked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = "Like",
        tint = if (tint == Color.Unspecified) MaterialTheme.colorScheme.onBackground else tint,
        modifier = Modifier
            .size(56.dp)
            .scale(scale)
            .clickable { liked = !liked }
            .padding(8.dp)
    )
}

@Composable
fun DetailsCard(visible: Boolean) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(420, easing = FastOutSlowInEasing)
        ) + slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight / 2 },
            animationSpec = tween(420, easing = FastOutSlowInEasing)
        ) + expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(420)
        ),
        exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight / 2 },
            animationSpec = tween(300)
        ) + shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        )
    ) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Детали", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Здесь содержится расширенная информация. Она плавно появляется/исчезает с комбинированной анимацией.")
            }
        }
    }
}

enum class Mood { Neutral, Happy, Angry }

@Composable
fun MoodIcon() {
    var mood by remember { mutableStateOf(Mood.Neutral) }

    val transition = updateTransition(targetState = mood, label = "moodTransition")

    val scale by transition.animateFloat(
        transitionSpec = { tween(350, easing = FastOutSlowInEasing) },
        label = "moodScale"
    ) { state ->
        when (state) {
            Mood.Happy -> 1.25f
            Mood.Angry -> 0.85f
            Mood.Neutral -> 1.0f
        }
    }

    val color by transition.animateColor(label = "moodColor") { state ->
        when (state) {
            Mood.Happy -> Color(0xFF4CAF50)
            Mood.Angry -> Color(0xFFF44336)
            Mood.Neutral -> Color.Gray
        }
    }

    val rotation by transition.animateFloat(label = "moodRotation") { state ->
        when (state) {
            Mood.Angry -> 12f
            else -> 0f
        }
    }

    Box(
        modifier = Modifier
            .size(110.dp)
            .background(color = color, shape = CircleShape)
            .clickable {
                mood = when (mood) {
                    Mood.Neutral -> Mood.Happy
                    Mood.Happy -> Mood.Angry
                    Mood.Angry -> Mood.Neutral
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.SentimentSatisfied,
            contentDescription = "Mood",
            tint = Color.White,
            modifier = Modifier
                .size(56.dp)
                .scale(scale)
                .rotate(rotation)
        )
    }
}

@Composable
fun PulseButton(onClick: () -> Unit) {
    val infinite = rememberInfiniteTransition(label = "pulse")
    val scale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = Modifier
            .size(84.dp)
            .scale(scale)
            .background(color = Color(0xFF1E88E5), shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text("Go", color = Color.White)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CrossfadeDemo(showFirst: Boolean) {
    // simple crossfade
    Crossfade(targetState = showFirst, label = "crossfadeDemo") { first ->
        if (first) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFFB3E5FC)),
                contentAlignment = Alignment.Center
            ) {
                Text("Первое содержимое", style = MaterialTheme.typography.titleMedium)
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFFC8E6C9)),
                contentAlignment = Alignment.Center
            ) {
                Text("Другое содержимое", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}