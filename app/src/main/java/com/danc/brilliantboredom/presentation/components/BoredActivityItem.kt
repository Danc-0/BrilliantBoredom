package com.danc.brilliantboredom.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.danc.brilliantboredom.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .border(
                border = BorderStroke(width = 1.dp, Color.DarkGray),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            modifier = modifier.padding(10.dp)
        ) {
            Text(
                text = (boredActivity.type).uppercase(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = boredActivity.activity,
                fontSize = 15.sp,
                fontWeight = FontWeight.Light,
            )

            val link = boredActivity.link
            if (link.isNotEmpty()){
                Text(
                    text = stringResource(id = R.string.learn_more) + " ${boredActivity.link}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Thin,
                )
            }
        }
    }
}