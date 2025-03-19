package com.example.uthsmarttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.uthsmarttasks.ui.theme.UTHSmartTasksTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

// Enum để theo dõi các màn hình trong ứng dụng
enum class AppScreen {
    SplashScreen,
    GetStartedFirst,
    GetStartedSecond,
    GetStartedThird
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UTHSmartTasksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White // Màu nền sáng
                ) {
                    SmartTasksApp()
                }
            }
        }
    }
}

@Composable
fun SmartTasksApp() {
    // State để theo dõi màn hình hiện tại
    var currentScreen by remember { mutableStateOf(AppScreen.SplashScreen) }
    val coroutineScope = rememberCoroutineScope()

    // Tự động chuyển từ Splash Screen sang GetStartedFirst sau 2 giây
    LaunchedEffect(key1 = true) {
        delay(2000)
        currentScreen = AppScreen.GetStartedFirst
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (currentScreen) {
            AppScreen.SplashScreen -> SplashScreen()
            AppScreen.GetStartedFirst -> GetStartedFirstScreen {
                currentScreen = AppScreen.GetStartedSecond
            }
            AppScreen.GetStartedSecond -> GetStartedSecondScreen(
                onBack = { currentScreen = AppScreen.GetStartedFirst },
                onNext = { currentScreen = AppScreen.GetStartedThird }
            )
            AppScreen.GetStartedThird -> GetStartedThirdScreen(
                onBack = { currentScreen = AppScreen.GetStartedSecond },
                onGetStarted = {
                    // Để trống cho việc mở rộng sau này
                    coroutineScope.launch {
                        // Có thể xử lý logic sau khi nhấn nút "Get Started"
                    }
                }
            )
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(
                if (isSelected) Color(0xFF0066CC) else Color(0xFFD3D3D3)
            )
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    UTHSmartTasksTheme {
        SmartTasksApp()
    }
}

@Preview(showBackground = true)
@Composable
fun PageIndicatorsPreview() {
    UTHSmartTasksTheme {
        Box(modifier = Modifier.padding(20.dp)) {
            Row {
                Indicator(isSelected = true)
                Spacer(modifier = Modifier.width(8.dp))
                Indicator(isSelected = false)
                Spacer(modifier = Modifier.width(8.dp))
                Indicator(isSelected = false)
            }
        }
    }
}