package com.zestworks.mvicalc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_calculator.*

class CalculatorFragment : MviFragment<CalculatorView, CalculatorPresenter>(), CalculatorView {

    override fun inputAModified(): Observable<InputAModified> = inputA
        .textChanges()
        .filter { it.isNotEmpty() }
        .map {
        InputAModified(
            text = it.toString()
        )
    }

    override fun inputBModified(): Observable<InputBModified> = inputB
        .textChanges()
        .filter { it.isNotEmpty() }
        .map {
        InputBModified(
            text = it.toString()
        )
    }

    override fun addClicked(): Observable<AddClicked> = add.clicks().map {
        AddClicked
    }

    override fun createPresenter(): CalculatorPresenter {
        return CalculatorPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_calculator, container, false)

    override fun render(viewModel: CalcViewModel) {
        inputA.text.replace(0, inputA.text.length, viewModel.inputA.toString())
        inputB.text.replace(0, inputB.text.length, viewModel.inputB.toString())

        if(viewModel.shouldShowDialog){
            Toast.makeText(context, viewModel.result.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
