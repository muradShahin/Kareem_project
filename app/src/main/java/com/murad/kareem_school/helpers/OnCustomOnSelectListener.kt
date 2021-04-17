package com.murad.kareem_school.helpers

import android.view.View
import android.widget.AdapterView

interface OnCustomOnSelectListener {

    fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
}