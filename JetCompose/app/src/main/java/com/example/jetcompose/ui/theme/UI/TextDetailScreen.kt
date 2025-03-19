package com.example.jetcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDetailScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Thanh tiêu đề
        TopAppBar(
            title = {
                Text(
                    text = "Text Detail",
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

        // Nội dung màn hình
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ví dụ: Đoạn văn "The quick Brown fox jumps over the lazy dog"
            val foxText = buildAnnotatedString {
                append("The ")
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Black
                    )
                ) {
                    append("quick ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFFE65100),

                        )
                ) {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 32.sp
                        )
                    ) {
                        append("B")
                    }
                    append("rown")
                }
                append(" fox j u m p s ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontStyle = FontStyle.Italic
                    )
                ) {
                    append("over ")
                }
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("the")
                }
                withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Cursive
                    )
                ) {
                    append(" lazy")
                }
                append(" dog.")
            }

            Text(
                text = foxText,
                fontSize = 24.sp,
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Divider()
        }
    }
}