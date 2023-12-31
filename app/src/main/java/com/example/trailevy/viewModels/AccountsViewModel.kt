package com.example.trailevy.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trailevy.data.GetTatalsRequest
import com.example.trailevy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountsViewModel: ViewModel() {

    private var totalsLiveData = MutableLiveData<GetTatalsRequest>()
    //get Totals

    fun getTatals(){

        val call = RetrofitInstance.apiService.getTotals()

        call.enqueue(object: Callback<GetTatalsRequest> {
            override fun onResponse(
                call: Call<GetTatalsRequest>,
                response: Response<GetTatalsRequest>
            ) {
                if (response.isSuccessful){
                    val totals = response.body()!!
                    totalsLiveData.value  =totals

                    Log.e("RecordsViewModel","totals ready!!")

                }else{
                    Log.e("RecordsViewModel",response.code().toString())
                }

            }

            override fun onFailure(call: Call<GetTatalsRequest>, t: Throwable) {
                Log.e("RecordsViewModel","totals failed!!")

            }

        })
    }

    fun observeTotalsLiveData(): LiveData<GetTatalsRequest> {
        return totalsLiveData
    }



}