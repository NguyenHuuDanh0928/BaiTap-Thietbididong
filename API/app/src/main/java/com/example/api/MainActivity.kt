package com.example.api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.api.R
import com.example.api.TaskViewModel
import com.example.api.ui.screen.AddTaskScreen
import com.example.api.ui.screen.TaskDetailScreen
import com.example.api.ui.screen.TaskListScreen
import com.example.api.ui.theme.APITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APITheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel = TaskViewModel()

    // Bottom Navigation Items
    val bottomNavItems = listOf(
        BottomNavItem("Home", R.drawable.ic_home, "home"),
        BottomNavItem("Calendar", R.drawable.ic_calendar, "calendar"),
        BottomNavItem("Add Task", R.drawable.ic_add, "add_task"),
        BottomNavItem("Documents", R.drawable.ic_document, "documents"),
        BottomNavItem("Settings", R.drawable.ic_settings, "settings")
    )

    // Kiểm tra nếu đang ở màn hình TaskDetailScreen thì ẩn tabbar
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute != "task_detail/{taskId}"

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 1.dp) // Thêm padding dưới để tạo khoảng cách từ mép dưới
                ) {
                    BottomNavigationBar(navController = navController, items = bottomNavItems)
                }
            }
        },
        containerColor = Color(0xFFF5F5F5) // Màu trắng xám cho nền của Scaffold
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                TaskListScreen(
                    viewModel = viewModel,
                    onTaskClick = { taskId -> navController.navigate("task_detail/$taskId") }
                )
            }
            composable("task_detail/{taskId}") { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull() ?: 0
                TaskDetailScreen(
                    taskId = taskId,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("add_task") {
                AddTaskScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("calendar") {
                Text("Calendar Screen", modifier = Modifier.padding(16.dp))
            }
            composable("documents") {
                Text("Documents Screen", modifier = Modifier.padding(16.dp))
            }
            composable("settings") {
                Text("Settings Screen", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

data class BottomNavItem(val label: String, val iconRes: Int, val route: String)

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<BottomNavItem>) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color.White) // Nền màu trắng cho tabbar
            .clip(RoundedCornerShape(16.dp)) // Bo góc của tabbar
            .border(2.dp, Color.White, RoundedCornerShape(16.dp)), // Viền xung quanh tabbar
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                if (item.route != "add_task") { // Bỏ qua nút "Add Task"
                    Box(
                        modifier = Modifier
                            .size(48.dp) // Kích thước vùng nhấn
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null, // Tắt hiệu ứng ripple
                                onClick = {
                                    if (currentRoute != item.route) {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp),
                            tint = if (currentRoute == item.route) Color(0xFF1E88E5) else Color(0xFF757575)
                        )
                    }
                } else {
                    // Nút "Add Task" nằm ngang cùng các biểu tượng
                    Box(
                        modifier = Modifier
                            .size(48.dp) // Kích thước nút
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null, // Tắt hiệu ứng ripple
                                onClick = {
                                    if (currentRoute != item.route) {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp) // Kích thước hình tròn
                                .background(
                                    color = Color(0xFF1E88E5), // Màu xanh dương
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.label,
                                modifier = Modifier.size(24.dp),
                                tint = Color.White // Màu trắng cho biểu tượng
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppNavigationPreview() {
    APITheme {
        AppNavigation()
    }
}