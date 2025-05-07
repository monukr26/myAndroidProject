package com.example.mycalculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.callbackFlow
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel() {

    private val _eText = MutableLiveData("")
    val eText : LiveData<String> = _eText

    private val _rText = MutableLiveData("0")
    val rText : LiveData<String> = _rText
    
    fun onButtonClick(btn: String) {

        _eText.value?.let {
            if(btn=="AC"){
                _eText.value = ""
                _rText.value = "0"
                return

            }

            if(btn=="C"){
                if(it.isNotEmpty()){
                    _eText.value = it.substring(0,it.length-1)
                    return
                }
            }

            if(btn == "="){
                _eText.value = _rText.value
                return
            }

            _eText.value = it+btn

            try {

                _rText.value = calculateResult(_eText.value.toString())

            } catch (_ : Exception){}

        }



    }
    fun calculateResult(equation : String): String {
        val context : Context = Context.enter()
        context.optimizationLevel=-1

        val scriptable : Scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scriptable,equation,"Javascript",1,null).toString()
        if(finalResult.endsWith("0")){
            finalResult = finalResult.replace(".0", "")
        }
        return finalResult

    }
}