package com.example.mobilechallenge.data.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.mobilechallenge.BR

class ShoppingCart : BaseObservable() {

    private var _totalItems = 0
    var totalItems: Int
        @Bindable get() = _totalItems
        set(value) {
            _totalItems = value
            notifyPropertyChanged(BR.totalItems)
        }

    private var _initialPrice = 0
    var initialPrice: Int
        @Bindable get() = _initialPrice
        set(value) {
            _initialPrice = value
            notifyPropertyChanged(BR.initialPrice)
        }

    private var _subTotal = 0
    var subTotal: Int
        @Bindable get() = _subTotal
        set(value) {
            _subTotal = value
            notifyPropertyChanged(BR.subTotal)
        }

    private var _freightPrice = 0
    var freightPrice: Int
        @Bindable get() = _freightPrice
        set(value) {
            _freightPrice = value
            notifyPropertyChanged(BR.freightPrice)
        }

    private var _finalPrice = 0
    var finalPrice: Int
        @Bindable get() = _finalPrice
        set(value) {
            _finalPrice = value
            notifyPropertyChanged(BR.finalPrice)
        }
}