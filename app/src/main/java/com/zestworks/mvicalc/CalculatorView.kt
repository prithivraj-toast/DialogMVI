package com.zestworks.mvicalc

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface CalculatorView : MvpView {
    fun render(viewModel: BaseViewModel)

    fun inputAModified(): Observable<InputAModified>
    fun inputBModified(): Observable<InputBModified>
    fun addClicked(): Observable<AddClicked>
}