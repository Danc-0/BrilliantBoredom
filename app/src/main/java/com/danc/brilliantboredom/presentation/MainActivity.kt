package com.danc.brilliantboredom.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danc.brilliantboredom.presentation.components.BoredActivityItem
import com.danc.brilliantboredom.presentation.theme.BrilliantBoredomTheme
import com.danc.brilliantboredom.presentation.viewmodel.GetBoredActivitiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrilliantBoredomTheme {
                val viewModel: GetBoredActivitiesViewModel = hiltViewModel()
                val state = viewModel.state.value
                val scaffoldState = rememberScaffoldState()

                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is GetBoredActivitiesViewModel.UIEvent.ShowSnackBar -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    Box(
                        modifier = Modifier.background(MaterialTheme.colors.background)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "CREATIVE SELF",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Black

                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                text = "How spacing out can UNLOCK your most PRODUCTIVE and CREATIVE SELF",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Black

                            )
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(state.boredActivity.size) { index ->
                                    val boredActivity = state.boredActivity[index]
                                    BoredActivityItem(boredActivity = boredActivity, bookmark = {

                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
