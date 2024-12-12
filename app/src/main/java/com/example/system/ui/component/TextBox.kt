package com.example.system.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.TextBox(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    textAlign: TextAlign = TextAlign.Center,
    fontSize: TextUnit = 10.sp,
    alignment: Alignment.Vertical = Alignment.CenterVertically
) {
    // 텍스트를 감싸는 박스 만들어줘


    Box(modifier = modifier.align(alignment)) {
        Text(
            text = "    $text    ",
            textAlign = textAlign,
            fontSize = fontSize
        )
    }
}

@Composable
fun ColumnScope.TextBox(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    textAlign: TextAlign = TextAlign.Center,
    fontSize: TextUnit = 10.sp,
) {
    // 텍스트를 감싸는 박스 만들어줘


    Box(modifier = modifier) {
        Text(
            text = "    $text    ",
            textAlign = textAlign,
            fontSize = fontSize
        )
    }
}

@Composable
fun BoxScope.TextBox(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    textAlign: TextAlign = TextAlign.Center,
    fontSize: TextUnit = 10.sp
) {
    // 텍스트를 감싸는 박스 만들어줘


    Box(modifier = modifier) {
        Text(
            text = "    $text    ",
            textAlign = textAlign,
            fontSize = fontSize
        )
    }
}

@Preview
@Composable
private fun TextBoxPreview() {
    Row {
        TextBox(text = "test")
    }

}