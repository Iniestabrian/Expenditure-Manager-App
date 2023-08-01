package com.example.trailevy.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trailevy.data.BudgetedCategoriesRequest
import com.example.trailevy.data.GetTatalsRequest
import com.example.trailevy.data.NotBudgetedCategories
import com.example.trailevy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BudgetViewModel : ViewModel() {

    private  var notBudgetedCategoriesLiveData = MutableLiveData<List<NotBudgetedCategories>>()
    private  var BudgetedCategoriesLiveData = MutableLiveData<List<BudgetedCategoriesRequest>>()

    private var totalsLiveData = MutableLiveData<GetTatalsRequest>()

     fun getNotBudgetedCategories() {

        val call = RetrofitInstance.apiService.getNotBudgetedCategories()

        call.enqueue(object : Callback<List<NotBudgetedCategories>> {
            override fun onResponse(
                call: Call<List<NotBudgetedCategories>>,
                response: Response<List<NotBudgetedCategories>>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body()!!
                    notBudgetedCategoriesLiveData.value = categories

                    /*Toast.makeText(context,"It Worked!!", Toast.LENGTH_LONG).show()*/

                }
            }

            override fun onFailure(call: Call<List<NotBudgetedCategories>>, t: Throwable) {
                /*Toast.makeText("BudgetFragment", t.message, Toast.LENGTH_LONG).show()*/
                Log.d("BudgetFragment",t.message.toString())
            }

        })

    }


    fun observeNotBudgetedCategoriesLiveData(): LiveData<List<NotBudgetedCategories>> {
        return notBudgetedCategoriesLiveData
    }




    //get budgeted categories

    fun getBudgetedCategories(){

        val call = RetrofitInstance.apiService.getBudgetedCategories()

        call.enqueue(object : Callback<List<BudgetedCategoriesRequest>> {
            override fun onResponse(
                call: Call<List<BudgetedCategoriesRequest>>,
                response: Response<List<BudgetedCategoriesRequest>>
            ) {
                if (response.isSuccessful){
                    val categories  = response.body()!!
                    BudgetedCategoriesLiveData.value=categories


                }
            }

            override fun onFailure(call: Call<List<BudgetedCategoriesRequest>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    fun observeBudgetedCategoriesLiveData(): LiveData<List<BudgetedCategoriesRequest>>{
        return BudgetedCategoriesLiveData
    }







    //get Totals

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

    fun observeTotalsLiveData(): LiveData<GetTatalsRequest>{
        return totalsLiveData
    }

}