package com.example.jetcompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentListScreen(
    onBackClick: () -> Unit,
    onTextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Thanh tiêu đề
        TopAppBar(
            title = {
                Text(
                    text = "UI Components List",
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Medium
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Quay lại"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        // Danh sách components với khả năng cuộn
        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                // Tiêu đề phần Display
                Text(
                    text = "Display",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Component Text
                ComponentItem(
                    name = "Text",
                    description = "Displays text",
                    onClick = onTextClick
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Component Image
                ComponentItem(
                    name = "Image",
                    description = "Displays an image",
                    onClick = { /* Không xử lý */ }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tiêu đề phần Input
                Text(
                    text = "Input",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Component TextField
                ComponentItem(
                    name = "TextField",
                    description = "Input field for text",
                    onClick = { /* Không xử lý */ }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Component PasswordField
                ComponentItem(
                    name = "PasswordField",
                    description = "Input field for passwords",
                    onClick = { /* Không xử lý */ }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tiêu đề phần Layout
                Text(
                    text = "Layout",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Component Column
                ComponentItem(
                    name = "Column",
                    description = "Arranges elements vertically",
                    onClick = { /* Không xử lý */ }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Component Row
                ComponentItem(
                    name = "Row",
                    description = "Arranges elements horizontally",
                    onClick = { /* Không xử lý */ }
                )
            }
        }
    }
}

@Composable
fun ComponentItem(
    name: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFE3F2FD))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}