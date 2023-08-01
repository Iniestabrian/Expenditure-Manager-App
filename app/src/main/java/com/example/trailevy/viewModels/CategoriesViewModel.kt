package com.example.trailevy.viewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trailevy.adapters.CategoriesAdapter
import com.example.trailevy.data.CategoriesRequest
import com.example.trailevy.data.GetTatalsRequest
import com.example.trailevy.data.PieChartDataRequest
import com.example.trailevy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel: ViewModel() {


    private  var categoriesLiveData = MutableLiveData<List<CategoriesRequest>>()



     fun getCategories(){


        val call= RetrofitInstance.apiService.getCategories()


        call.enqueue(object : Callback<List<CategoriesRequest>> {

            override fun onResponse(call: Call<List<CategoriesRequest>>, response: Response<List<CategoriesRequest>>) {
                if (response.isSuccessful)
                {
                    val categories  = response.body()!!

                    categoriesLiveData.value = categories



                }
            }

            override fun onFailure(call: Call<List<CategoriesRequest>>, t: Throwable) {
                // Handle error response here
              /*  Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
*/
            }

        })




    }


    fun observeTotalsLiveData(): LiveData<List<CategoriesRequest>> {
        return categoriesLiveData
    }




}