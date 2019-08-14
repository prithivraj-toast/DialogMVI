package com.zestworks.mvicalc

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable

class CalculatorPresenter : MviBasePresenter<CalculatorView, CalcViewModel>() {

    override fun bindIntents() {
        val inputAModifiedIntent = intent(CalculatorView::inputAModified)
        val inputBModified = intent(CalculatorView::inputBModified)
        val buttonClick = intent(CalculatorView::addClicked)

        val allIntents = Observable.merge(inputAModifiedIntent, inputBModified, buttonClick)

        val stateToEmit = allIntents
            .scan(AggregatedState(), this::reduce)
            .map {
                it.state
            }
            .distinctUntilChanged()

        subscribeViewState(stateToEmit, CalculatorView::render)
    }

    private fun reduce(previousState: AggregatedState, currentIntent: Intent): AggregatedState {
        return when (currentIntent) {
            is InputAModified -> {
                val sum = currentIntent.text.toInt().plus(previousState.state.inputB)
                previousState.copy(
                    state = previousState.state.copy(
                        inputA = currentIntent.text.toInt(),
                        result = sum
                    )
                )
            }
            is InputBModified -> {
                val sum = previousState.state.inputA.plus(currentIntent.text.toInt())
                previousState.copy(
                    state = previousState.state.copy(
                        inputB = currentIntent.text.toInt(),
                        result = sum
                    )
                )
            }
            AddClicked -> {
                previousState.copy(
                    state = previousState.state.copy(
                        shouldShowDialog = true
                    )
                )
            }
            NoIntent -> {
                previousState
            }
        }
    }
}
