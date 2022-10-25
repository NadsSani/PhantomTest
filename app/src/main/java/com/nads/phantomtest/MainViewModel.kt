package com.nads.phantomtest

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.nads.phantomtest.model.Person
import com.nads.phantomtest.utils.SwipeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    val personlist = mutableListOf(Person("2","nadeem",R.drawable.diancee,false),
            Person("2","nadeem",R.drawable.kleavor,false))
    private val _isUpOrDown: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isUpOrDown
    var update: Long = 0L
    fun executeNext(swipeResult: SwipeResult, person: Person) {
        when (swipeResult) {
            SwipeResult.ACCEPT -> {

            }
            SwipeResult.REJECT -> {

            }
            SwipeResult.SWIPEUP->{
                _isUpOrDown.value = true
            }
            SwipeResult.SWIPEDOWN->{
                _isUpOrDown.value = false
            }
        }
    }

}