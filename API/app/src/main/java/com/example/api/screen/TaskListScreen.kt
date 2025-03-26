package com.example.api.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api.R
import com.example.api.TaskViewModel
import com.example.api.Tasks
import com.example.api.UiState

@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onTaskClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Start),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_uth_logo),
                contentDescription = "UTH Logo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)) // Bo góc logo
                    .padding(end = 16.dp)
            )
            Column {
                Text(
                    text = "SmartTasks",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 28.sp),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E88E5),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "A simple and efficient to-do app",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }

        when (uiState) {
            is UiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.material3.CircularProgressIndicator(
                        color = Color(0xFF1E88E5)
                    )
                    Text(
                        text = "Loading tasks...",
                        modifier = Modifier.padding(top = 16.dp),
                        color = Color(0xFF757575)
                    )
                }
            }
            is UiState.Success -> {
                val tasks = (uiState as UiState.Success<List<Tasks>>).data
                if (tasks.isEmpty()) {
                    EmptyTaskScreen()
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(tasks) { task ->
                            TaskCard(
                                task = task,
                                onClick = { onTaskClick(task.id) },
                                onCheck = { checked -> viewModel.updateTaskStatus(task.id, checked) }
                            )
                        }
                    }
                }
            }
            is UiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error: ${(uiState as UiState.Error).message}",
                        color = Color(0xFFD32F2F),
                        modifier = Modifier.padding(16.dp)
                    )
                    androidx.compose.material3.Button(
                        onClick = { viewModel.fetchTasks() },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1E88E5)
                        )
                    ) {
                        Text("Retry", color = Color.White)
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
fun TaskCard(
    task: Tasks,
    onClick: () -> Unit,
    onCheck: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (task.priority) {
                "High" -> Color(0xFFFFCDD2)
                "Medium" -> Color(0xFFC8E6C9)
                else -> Color(0xFFE3F2FD)
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.status == "Completed",
                onCheckedChange = onCheck,
                modifier = Modifier.padding(end = 12.dp),
                colors = androidx.compose.material3.CheckboxDefaults.colors(
                    checkedColor = Color(0xFF1E88E5),
                    uncheckedColor = Color(0xFF757575)
                )
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Text(
                    text = task.description,
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(top = 4.dp),
                    maxLines = 2
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "STATUS: ${task.status.uppercase()}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (task.status == "Completed") Color(0xFF388E3C) else Color(0xFFD32F2F)
                    )
                    Text(
                        text = task.dueDate,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1E88E5)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Start),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_uth_logo),
                contentDescription = "UTH Logo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)) // Bo góc logo
                    .padding(end = 16.dp)
            )
            Column {
                Text(
                    text = "SmartTasks",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 28.sp),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E88E5),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "A simple and efficient to-do app",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }

        Text(
            text = "[Task List Placeholder - Data from API]",
            modifier = Modifier.padding(16.dp),
            color = Color(0xFF757575)
        )
    }
}