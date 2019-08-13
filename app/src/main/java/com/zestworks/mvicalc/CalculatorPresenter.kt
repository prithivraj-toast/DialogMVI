package com.zestworks.mvicalc

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

class CalculatorPresenter: ViewModel() {

    val renderStream : Observable<CalcViewModel>

    // Things we must redraw on config change
    private val viewState = BehaviorRelay.createDefault(StatelessCalcViewModel())

    // Fire and forget
    private val viewEffects = PublishRelay.create<StatefulCalcViewModel>()

    init {
        renderStream = viewState.map { it as CalcViewModel }.mergeWith(viewEffects)
    }

    fun onEvent(intent: Intent){
        when(intent){

            is InputAModified -> {
                val currentState = viewState.value!!
                viewState.accept(
                    currentState.copy(
                        inputA = intent.text
                    )
                )
            }

            is InputBModified -> {
                val currentState = viewState.value!!
                viewState.accept(
                    currentState.copy(
                        inputB = intent.text
                    )
                )
            }

            AddClicked -> {
                val currentState = viewState.value!!
                if(currentState.inputA.isEmpty() || currentState.inputB.isEmpty()){
                    viewEffects.accept(
                        ErrorToast
                    )
                } else {
                    val sum = currentState.inputA.toInt() + currentState.inputB.toInt()
                    viewEffects.accept(
                        ResultDialog(
                            sum = sum
                        )
                    )
                }
            }
        }

    }

}
