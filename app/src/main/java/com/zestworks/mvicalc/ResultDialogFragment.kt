package com.zestworks.mvicalc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_result.*

class ResultDialogFragment : DialogFragment() {

    private lateinit var currentResult: ResultDialog

    init {
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_result, container, false)

    override fun onStart() {
        super.onStart()
        sumResult.text = currentResult.sum.toString()
    }

    fun setData(resultDialog: ResultDialog) {
        this.currentResult = resultDialog
    }
}