package com.danc.brilliantboredom.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danc.brilliantboredom.R
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
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        SearchButton(viewModel)
                    }
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
                                text = stringResource(id = R.string.header),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.primary

                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                text = stringResource(id = R.string.header_description),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.primary

                            )
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(state.boredActivity.size) { index ->
                                    val boredActivity = state.boredActivity[index]
                                    BoredActivityItem(boredActivity = boredActivity, bookmark = {
                                        viewModel.bookmarkActivity(boredActivity.toBookmarked())
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

@Composable
fun SearchButton(viewModel: GetBoredActivitiesViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(16.dp),
            onClick = {
                viewModel.onGetBoredActivity()
            }) {
            Text(
                style = TextStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal),
                text = stringResource(R.string.get_new_activity).uppercase()
            )
        }
    }
}
