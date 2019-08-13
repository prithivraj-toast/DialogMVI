package com.zestworks.mvicalc

sealed class CalcViewModel

data class StatelessCalcViewModel(
    val inputA : String = "",
    val inputB : String = ""
): CalcViewModel()

sealed class StatefulCalcViewModel: CalcViewModel()

object ErrorToast: StatefulCalcViewModel()
data class ResultDialog(
    val sum: Int
): StatefulCalcViewModel()