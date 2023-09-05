package com.example.trailevy.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trailevy.data.GetTatalsRequest
import com.example.trailevy.data.NotBudgetedCategories
import com.example.trailevy.data.RecordsRequest
import com.example.trailevy.repository.RecordsRepository
import com.example.trailevy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecordsViewModel : ViewModel() {


   private val recordsRepository = RecordsRepository()


    fun getRecords() {
       recordsRepository.getRecords()
    }


        //get records

    fun observeRecordsLiveData(): LiveData<List<RecordsRequest>>{
        return recordsRepository.getRecordsLiveData()
    }



    fun getTotals() {
       recordsRepository.getTatals()
    }

    //get Totals

    fun observeTotalsLiveData(): LiveData<GetTatalsRequest>{
        return recordsRepository.getTotalsLiveData()
    }
}