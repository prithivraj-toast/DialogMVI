package com.zestworks.mvicalc

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CalculatorPresenter : MviBasePresenter<CalculatorView, BaseViewModel>() {

    private val viewEffects = PublishSubject.create<ShowDialog>()

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
            .mergeWith(viewEffects)
            .distinctUntilChanged()

        subscribeViewState(stateToEmit, CalculatorView::render)
    }

    private fun reduce(previousState: AggregatedState, currentIntent: Intent): AggregatedState {
        return when (currentIntent) {
            is InputAModified -> {
                when(previousState.state){
                    is CalcViewModel -> {
                        previousState.copy(
                            state = previousState.state.copy(
                                inputA = currentIntent.text.toInt()
                            )
                        )
                    }
                    is ShowDialog -> {
                        previousState
                    }
                }
            }
            is InputBModified -> {
                when(previousState.state){
                    is CalcViewModel -> {
                        previousState.copy(
                            state = previousState.state.copy(
                                inputB = currentIntent.text.toInt()
                            )
                        )
                    }
                    is ShowDialog -> {
                        previousState
                    }
                }
            }
            AddClicked -> {
                when(previousState.state){
                    is CalcViewModel -> {
                        val showDialog = ShowDialog(
                            sum = previousState.state.inputA.plus(previousState.state.inputB)
                        )
                        viewEffects.onNext(showDialog)
                        previousState
                    }
                    is ShowDialog -> {
                        previousState
                    }
                }
            }
            NoIntent -> {
                previousState
            }
        }
    }
}
