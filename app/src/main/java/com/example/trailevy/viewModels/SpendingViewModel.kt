package com.example.trailevy.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trailevy.data.AddExpenseRequest
import com.example.trailevy.data.AddExpenseResponse
import com.example.trailevy.data.CategoriesRequest
import com.example.trailevy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpendingViewModel : ViewModel() {


    private  var spendingCategoriesLiveData  = MutableLiveData<List<CategoriesRequest>>()

    private val _addExpenseResult = MutableLiveData<Boolean>()
    val addExpenseResult: LiveData<Boolean>
        get() = _addExpenseResult



    fun getSpendingCategories(){

        //getting categories from Api

        val call= RetrofitInstance.apiService.getCategories()


        call.enqueue(object : Callback<List<CategoriesRequest>> {
            override fun onResponse(
                call: Call<List<CategoriesRequest>>,
                response: Response<List<CategoriesRequest>>
            ) {
                if (response.isSuccessful)
                {
                    val categories  = response.body()!!

                    spendingCategoriesLiveData.value = categories





                    }



                }

            override fun onFailure(call: Call<List<CategoriesRequest>>, t: Throwable) {

            }
        })


    }


    fun observespendingCategoriesLiveData(): LiveData<List<CategoriesRequest>> {

        return spendingCategoriesLiveData

    }





    //add Expense

    fun addExpense(categoryName: String, amountSpent: Float) {
        val addExpenseRequest = AddExpenseRequest(categoryName, amountSpent)
        val call = RetrofitInstance.apiService.addExpense(addExpenseRequest)

        call.enqueue(object : Callback<AddExpenseResponse> {
            override fun onResponse(call: Call<AddExpenseResponse>, response: Response<AddExpenseResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    // Handle the successful response here if needed
                    _addExpenseResult.value = true

                } else{
                    _addExpenseResult.value = false
                }
            }

            override fun onFailure(call: Call<AddExpenseResponse>, t: Throwable) {
                _addExpenseResult.value = false
            }
        })
    }
}