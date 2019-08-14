package com.zestworks.mvicalc

data class CalcViewModel(
    val inputA: Int = 0,
    val inputB: Int = 0,
    val shouldShowDialog: Boolean = false,
    val result: Int = 0
)

data class AggregatedState(
    val state: CalcViewModel = CalcViewModel(),
    val lastUserIntent: Intent = NoIntent
)