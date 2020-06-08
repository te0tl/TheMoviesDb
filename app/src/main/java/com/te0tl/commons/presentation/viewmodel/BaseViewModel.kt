package com.te0tl.commons.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel<VS> : ViewModel() {

    private val viewStateLiveData: MutableLiveData<VS> = MutableLiveData()

    private val innersObservers = mutableListOf<Observer<VS>>()

    protected val currentState : VS? get() = viewStateLiveData.value

    private val viewModelJob = SupervisorJob()

    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //TODO CHECK IF NECESSARY
    protected open fun initialLoad() { }

    override fun onCleared() {
        super.onCleared()

        innersObservers.forEach {
            viewStateLiveData.removeObserver(it)
        }

        viewModelJob.cancel()

    }

    protected fun updateViewModelState(newState: VS?, post: Boolean = true) {
        if (post) viewStateLiveData.postValue(newState) else viewStateLiveData.value = newState
    }

    fun observeViewModelState(observer: Observer<VS>) {
        innersObservers.add(observer)
        viewStateLiveData.observeForever(observer)
        currentState?.also {
            updateViewModelState(currentState)
        }
    }

    fun observeViewModelState(owner: LifecycleOwner, onChanged: (newState: VS) -> Unit) {
        viewStateLiveData.observe(owner, Observer { onChanged(it) })
        currentState?.also {
            updateViewModelState(currentState)
        }
    }

}
