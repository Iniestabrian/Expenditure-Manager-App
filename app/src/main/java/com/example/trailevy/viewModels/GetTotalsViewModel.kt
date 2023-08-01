package com.example.trailevy.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trailevy.data.GetTatalsRequest
import com.example.trailevy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetTotalsViewModel:ViewModel() {

    private lateinit var totalsLiveData: MutableLiveData<GetTatalsRequest>
    fun getTatals(){

        val call = RetrofitInstance.apiService.getTotals()

        call.enqueue(object:Callback<GetTatalsRequest>{
            override fun onResponse(
                call: Call<GetTatalsRequest>,
                response: Response<GetTatalsRequest>
            ) {
                if (response.isSuccessful){
                    val totals = response.body()!!
                    totalsLiveData.value  =totals

                }

            }

            override fun onFailure(call: Call<GetTatalsRequest>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun observeTotalsLiveData(): LiveData<GetTatalsRequest>{
        return totalsLiveData
    }



}