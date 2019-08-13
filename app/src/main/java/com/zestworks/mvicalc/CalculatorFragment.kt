package com.zestworks.mvicalc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_calculator.*

class CalculatorFragment : Fragment() {

    private lateinit var calculatorPresenter: CalculatorPresenter

    private lateinit var calculatorFragmentDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_calculator, container, false)

    override fun onStart() {
        super.onStart()
        calculatorPresenter = ViewModelProviders.of(activity!!).get(CalculatorPresenter::class.java)
        calculatorFragmentDisposable = CompositeDisposable()

        calculatorFragmentDisposable.addAll(
            inputA.textChanges().subscribe {
                calculatorPresenter.onEvent(
                    InputAModified(
                        text = it.toString()
                    )
                )
            },
            inputB.textChanges().subscribe {
                calculatorPresenter.onEvent(
                    InputBModified(
                        text = it.toString()
                    )
                )
            },
            add.clicks().subscribe {
                calculatorPresenter.onEvent(AddClicked)
            },
            calculatorPresenter.renderStream.subscribe(this::render)
        )
    }

    override fun onStop() {
        super.onStop()
        calculatorFragmentDisposable.dispose()
    }

    private fun render(calcViewModel: CalcViewModel) {
        when (calcViewModel) {

            is StatelessCalcViewModel -> {
                // TODO : Rerender textboxes.
            }

            is StatefulCalcViewModel -> {
                when (calcViewModel) {
                    ErrorToast -> {
                        Toast.makeText(context, "Can't add empty strings!", Toast.LENGTH_SHORT).show()
                    }
                    is ResultDialog -> {
                        val resultDialogFragment = ResultDialogFragment()
                        resultDialogFragment.setData(calcViewModel)
                        resultDialogFragment.showNow(activity!!.supportFragmentManager, "test")
                    }
                }
            }
        }
    }
}
