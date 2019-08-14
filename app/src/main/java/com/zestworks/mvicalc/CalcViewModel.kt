package com.zestworks.mvicalc

sealed class BaseViewModel

data class CalcViewModel(
    val inputA: Int = 0,
    val inputB: Int = 0
) : BaseViewModel()

class ShowDialog(val sum: Int) : BaseViewModel()

data class AggregatedState(
    val state: BaseViewModel = CalcViewModel(),
    val lastUserIntent: Intent = NoIntent
)