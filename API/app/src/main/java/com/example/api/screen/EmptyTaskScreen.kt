package com.example.api.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api.R

@Composable
fun EmptyTaskScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top, // Đưa nội dung lên trên
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header - Căn lề trái
        Row(
            modifier = Modifier
                .align(Alignment.Start), // Căn lề trái cho toàn bộ Row
            verticalAlignment = Alignment.Top // Căn trên để logo và "SmartTasks" thẳng hàng
        ) {
            // Logo UTH
            Image(
                painter = painterResource(id = R.drawable.ic_uth_logo),
                contentDescription = "UTH Logo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )
            // Column chứa 2 dòng chữ
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

        // Empty State Image - Sửa lại để dùng vector drawable
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_empty_task_new),
            contentDescription = "Empty Task",
            modifier = Modifier.size(120.dp)
        )

        Text(
            text = "No Tasks Yet!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121),
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Stay productive—add something to do",
            fontSize = 16.sp,
            color = Color(0xFF757575),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyTaskScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top, // Đưa nội dung lên trên
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header - Căn lề trái
        Row(
            modifier = Modifier
                .align(Alignment.Start), // Căn lề trái cho toàn bộ Row
            verticalAlignment = Alignment.Top
        ) {
            // Logo UTH
            Image(
                painter = painterResource(id = R.drawable.ic_uth_logo),
                contentDescription = "UTH Logo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )
            // Column chứa 2 dòng chữ
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

        // Empty State Image - Dùng placeholder trong preview
        Text(
            text = "No Tasks Yet!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121),
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Stay productive—add something to do",
            fontSize = 16.sp,
            color = Color(0xFF757575),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}