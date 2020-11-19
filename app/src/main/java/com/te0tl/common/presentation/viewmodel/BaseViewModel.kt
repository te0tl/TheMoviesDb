package com.te0tl.common.presentation.viewmodel

import androidx.lifecycle.*

abstract class BaseViewModel<VS> : ViewModel() {

    private val viewStateLiveData: MutableLiveData<VS> = MutableLiveData()

    private val innersObservers = mutableListOf<Observer<VS>>()

    private val currentState : VS? get() = viewStateLiveData.value

    protected val uiScope = viewModelScope

    override fun onCleared() {
        super.onCleared()

        innersObservers.forEach {
            viewStateLiveData.removeObserver(it)
        }
    }

    protected fun updateViewModelState(newState: VS, post: Boolean = true) {
        if (post) viewStateLiveData.postValue(newState) else viewStateLiveData.value = newState
    }

    fun observeViewModelState(observer: Observer<VS>) {
        innersObservers.add(observer)
        viewStateLiveData.observeForever(observer)
        currentState?.also {
            updateViewModelState(it)
        }
    }

    fun observeViewModelState(owner: LifecycleOwner, onChanged: (newState: VS) -> Unit) {
        viewStateLiveData.observe(owner, Observer { onChanged(it) })
        currentState?.also {
            updateViewModelState(it)
        }
    }

}
