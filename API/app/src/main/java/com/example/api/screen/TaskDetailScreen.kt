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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api.R
import com.example.api.Subtask
import com.example.api.TaskViewModel
import com.example.api.Tasks
import com.example.api.UiState

@Composable
fun TaskDetailScreen(
    taskId: Int,
    viewModel: TaskViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState

    when (uiState) {
        is UiState.Success -> {
            val tasks = (uiState as UiState.Success<List<Tasks>>).data
            val task = tasks.find { it.id == taskId }
            if (task == null) {
                Text(
                    text = "Task not found",
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFFD32F2F)
                )
                return
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(16.dp)
            ) {
                // Top Bar: Back, Detail, Delete
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF1E88E5)
                        )
                    }
                    Text(
                        text = "Detail",
                        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E88E5),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { /* TODO: Xử lý xóa task */ }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFFFFA500)
                        )
                    }
                }

                // Task Title and Description
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = task.description,
                    fontSize = 16.sp,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Task Info (Category, Status, Priority) with Icons and Labels
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFCDD2)) // Nền đỏ nhạt
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_category),
                            contentDescription = "Category",
                            tint = Color(0xFF757575),
                            modifier = Modifier
                                .size(28.dp)
                                .padding(end = 8.dp)
                        )
                        Column {
                            Text(
                                text = "Category",
                                fontSize = 12.sp,
                                color = Color(0xFF757575),
                                modifier = Modifier.padding(bottom = 1.dp) // Giảm khoảng cách
                            )
                            Text(
                                text = "Work",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF757575)
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_status),
                            contentDescription = "Status",
                            tint = Color(0xFF757575),
                            modifier = Modifier
                                .size(28.dp)
                                .padding(end = 8.dp)
                        )
                        Column {
                            Text(
                                text = "Status",
                                fontSize = 12.sp,
                                color = Color(0xFF757575),
                                modifier = Modifier.padding(bottom = 1.dp) // Giảm khoảng cách
                            )
                            Text(
                                text = "In Progress",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF757575)
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_priority),
                            contentDescription = "Priority",
                            tint = Color(0xFF757575),
                            modifier = Modifier
                                .size(28.dp)
                                .padding(end = 8.dp)
                        )
                        Column {
                            Text(
                                text = "Priority",
                                fontSize = 12.sp,
                                color = Color(0xFF757575),
                                modifier = Modifier.padding(bottom = 1.dp) // Giảm khoảng cách
                            )
                            Text(
                                text = "High",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF757575)
                            )
                        }
                    }
                }

                // Subtasks Section
                if (task.subtasks.isNotEmpty()) {
                    Text(
                        text = "Subtasks",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121),
                        modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(task.subtasks) { subtask ->
                            SubtaskCard(subtask = subtask)
                        }
                    }
                }

                // Attachments Section
                if (task.attachments.isNotEmpty()) {
                    Text(
                        text = "Attachments",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121),
                        modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                    )
                    task.attachments.forEach { attachment ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "📎 ${attachment.fileName}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF212121)
                                )
                            }
                        }
                    }
                }
            }
        }
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
                    text = "Loading task details...",
                    modifier = Modifier.padding(top = 16.dp),
                    color = Color(0xFF757575)
                )
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
                Button(
                    onClick = { viewModel.fetchTasks() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
                ) {
                    Text("Retry", color = Color.White)
                }
            }
        }

        else -> {}
    }
}

@Composable
fun SubtaskCard(subtask: Subtask) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Checkbox(
                checked = subtask.isCompleted,
                onCheckedChange = { /* TODO: Xử lý checkbox sau */ },
                modifier = Modifier.padding(end = 4.dp),
                colors = androidx.compose.material3.CheckboxDefaults.colors(
                    checkedColor = Color(0xFF1E88E5),
                    uncheckedColor = Color(0xFF757575)
                )
            )
            Text(
                text = subtask.title,
                fontSize = 12.sp,
                color = Color(0xFF212121)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        // Top Bar: Back, Detail, Delete
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF1E88E5)
                )
            }
            Text(
                text = "Detail",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFFFA500)
                )
            }
        }

        // Task Title and Description
        Text(
            text = "Complete Android Project",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Finish the UI, integrate API, and write",
            fontSize = 16.sp,
            color = Color(0xFF757575),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Task Info (Category, Status, Priority) with Icons and Labels
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFCDD2))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_category),
                    contentDescription = "Category",
                    tint = Color(0xFF757575),
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp)
                )
                Column {
                    Text(
                        text = "Category",
                        fontSize = 12.sp,
                        color = Color(0xFF757575),
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                    Text(
                        text = "Work",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF757575)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_status),
                    contentDescription = "Status",
                    tint = Color(0xFF757575),
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp)
                )
                Column {
                    Text(
                        text = "Status",
                        fontSize = 12.sp,
                        color = Color(0xFF757575),
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                    Text(
                        text = "In Progress",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF757575)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_priority),
                    contentDescription = "Priority",
                    tint = Color(0xFF757575),
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp)
                )
                Column {
                    Text(
                        text = "Priority",
                        fontSize = 12.sp,
                        color = Color(0xFF757575),
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                    Text(
                        text = "High",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF757575)
                    )
                }
            }
        }

        Text(
            text = "Subtasks and Attachments will be loaded from API",
            modifier = Modifier.padding(top = 8.dp),
            color = Color(0xFF757575)
        )
    }
}