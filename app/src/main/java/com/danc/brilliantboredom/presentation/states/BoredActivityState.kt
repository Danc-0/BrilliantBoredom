package com.danc.brilliantboredom.presentation.states

import com.danc.brilliantboredom.domain.models.BoredActivity

data class BoredActivityState(
    val boredActivity: List<BoredActivity> = emptyList(),
    val isLoading: Boolean = false
)