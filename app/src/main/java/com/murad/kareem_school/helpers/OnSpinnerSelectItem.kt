package com.murad.kareem_school.helpers

import android.view.View
import android.widget.AdapterView

class OnSpinnerSelectItem(val onCustomOnSelectListener: OnCustomOnSelectListener) : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        onCustomOnSelectListener.onItemSelected(parent,view,position,id)
    }
}