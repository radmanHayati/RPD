package com.codinginflow.exchangeApp.ui.exchangeList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codinginflow.exchangeApp.data.remote.response.Currency
import com.codinginflow.exchangeApp.repository.ExchangeRepository
import com.codinginflow.exchangeApp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeListViewModel @Inject constructor(
    private val repository: ExchangeRepository
) : ViewModel() {
    val currencies = MutableLiveData<Resource<List<Currency>>>()
    val isLoading = MutableLiveData(false)
    val loadError = MutableLiveData<String>()
    fun loadCurrenciesList() {
        // currentQuery.value = query
        isLoading.value = true
        viewModelScope.launch {
           // val result = repository.getCurrenciesList()
            val result = Resource.Success(data = listOf(
                Currency("bitcoin",29733.0,"https://assets.coingecko.com/coins/images/1/small/bitcoin.png?1547033579"),
                Currency("tether",1.32,"https://assets.coingecko.com/coins/images/325/small/Tether-logo.png?1598003707"),
                Currency("solana",39.36,"https://assets.coingecko.com/coins/images/4128/small/solana.png?1640133422"),
            ))
            Log.i("checkk", "i've been here")
            when (result) {
                is Resource.Success -> {
                    loadError.value = ""
                    isLoading.value = false
                    currencies.postValue(result)
                }
                else -> {
                    loadError.value = result.message
                    isLoading.value = false
                }
            }

        }
    }
}