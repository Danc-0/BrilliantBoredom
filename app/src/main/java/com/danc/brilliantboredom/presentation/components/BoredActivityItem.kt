package com.danc.brilliantboredom.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danc.brilliantboredom.R.*
import com.danc.brilliantboredom.domain.models.BoredActivity

@Composable
fun BoredActivityItem(
    boredActivity: BoredActivity,
    modifier: Modifier = Modifier,
    bookmark: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .size(80.dp)
            .border(
                border = BorderStroke(width = 1.dp, Color.DarkGray),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            modifier = modifier.padding(10.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = boredActivity.type,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Image(
                    painter = painterResource(id = drawable.ic_baseline_bookmark),
                    contentDescription = stringResource(id = string.bookmark_icon),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = Color.Black),
                    modifier = modifier.clickable(
                        onClick = bookmark
                    )
                )
            }


            Text(
                text = boredActivity.activity,
                fontSize = 15.sp,
                fontWeight = FontWeight.Light,
            )
        }
    }
}