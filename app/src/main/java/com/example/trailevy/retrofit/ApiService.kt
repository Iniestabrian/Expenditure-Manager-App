package com.example.trailevy.retrofit

import com.example.trailevy.data.AddExpenseRequest
import com.example.trailevy.data.AddExpenseResponse
import com.example.trailevy.data.BudgetedCategoriesRequest
import com.example.trailevy.data.UserSignupRequest
import com.example.trailevy.data.UserSignupResponse
import com.example.trailevy.data.CategoriesRequest
import com.example.trailevy.data.GetTatalsRequest
import com.example.trailevy.data.NotBudgetedCategories
import com.example.trailevy.data.PieChartDataRequest
import com.example.trailevy.data.RecordsRequest
import com.example.trailevy.data.UserLoginRequest
import com.example.trailevy.data.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    //get categories
    @GET("/categories")

    fun getCategories():Call<List<CategoriesRequest>>

    //get not budgeted Categories

    @GET("/notBudgeted")

    fun getNotBudgetedCategories(): Call<List<NotBudgetedCategories>>


 //get  budgeted Categories

    @GET("/budgeted")

    fun getBudgetedCategories(): Call<List<BudgetedCategoriesRequest>>


    //get Records
 @GET("/records")

    fun getRecords(): Call<List<RecordsRequest>>


 //get Piechart Data
 @GET("/piedata")

    fun getPieData(): Call<List<PieChartDataRequest>>

 //get Piechart Data
 @GET("/totals")

    fun getTotals(): Call<GetTatalsRequest>



    @POST("/signup")

    fun signup(@Body UserSignupRequest: UserSignupRequest): Call<UserSignupResponse>


    @POST("/login")
    fun login (@Body UserLoginRequest: UserLoginRequest):Call<UserLoginResponse>


    @POST("/Expenses")
    fun addExpense(@Body addExpenseRequest: AddExpenseRequest):Call<AddExpenseResponse>




}


