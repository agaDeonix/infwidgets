package com.pinkunicorp.infwidgets

import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.VibratorManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pinkunicorp.infwidgets.ui.theme.InfWidgetsTheme
import kotlin.random.Random
import kotlin.random.nextInt


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vibe = (getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        setContent {
            InfWidgetsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black//MaterialTheme.colorScheme.background
                ) {
                    WidgetsScreen {
                        vibe.vibrate(VibrationEffect.createOneShot(100, 255))
                        audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, 2.0f)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

data class WidgetItemData(
    val width: Int,
    val height: Int,
    val color: Color
)

@Composable
fun WidgetsScreen(onClick: () -> Unit) {
    val items = (0..20).map {
        WidgetItemData(
            width = Random.nextInt(50..220),
            height = Random.nextInt(50..220),
            color = generateRandomColor()
        )
    }
    val listState = rememberLazyStaggeredGridState(Int.MAX_VALUE / 2)
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(1),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(10.dp),
        state = listState,
        content = {
            items(Int.MAX_VALUE, itemContent = {
                val index = it % items.size
                val item = items[index]
                WidgetItem(
                    width = item.width,
                    height = item.height,
                    color = item.color,
                    onClick = onClick
                )
            })
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun WidgetItem(
    modifier: Modifier = Modifier,
    color: Color,
    width: Int,
    height: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .width(width.dp)
            .height(height.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            color
        ),
    ) {
    }
}

fun generateRandomColor(): Color {
    val base = 255
    val min = 26
    val max = 85
    val red: Int = base - Random.nextInt(min, max)
    val green: Int = base - Random.nextInt(min, max)
    val blue: Int = base - Random.nextInt(min, max)
    return Color(red, green, blue)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InfWidgetsTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black//MaterialTheme.colorScheme.background
        ) {
            InfWidgetsTheme {
                WidgetsScreen {

                }
            }
        }
    }
}