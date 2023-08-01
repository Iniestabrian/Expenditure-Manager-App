package com.example.trailevy.fragments

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trailevy.R
import com.example.trailevy.adapters.BudgetedAdapter
import com.example.trailevy.adapters.NotBudgetedAdapter
import com.example.trailevy.data.BudgetedCategoriesRequest
import com.example.trailevy.data.CurrencyValueFormatter
import com.example.trailevy.data.GetTatalsRequest
import com.example.trailevy.data.NotBudgetedCategories
import com.example.trailevy.databinding.FragmentBudgetBinding
import com.example.trailevy.viewModels.BudgetViewModel

class BudgetFragment : Fragment(),NotBudgetedAdapter.SetLimitClickListener {

    private lateinit var binding: FragmentBudgetBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var budgetedlayoutManager: LinearLayoutManager
    private lateinit var budgetViewModel:BudgetViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentBudgetBinding.inflate(inflater,container,false)

        budgetViewModel = ViewModelProvider(this)[BudgetViewModel::class.java]

        observeTotals()
        observeNotBudgetedCategories()
        observeBudgetedCategories()

        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
     budgetedlayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerNotBudgeted.layoutManager = budgetedlayoutManager
        binding.recyclerNotBudgeted.setHasFixedSize(true)


        binding.recyclerBudgeted.layoutManager = layoutManager
        binding.recyclerBudgeted.setHasFixedSize(true)





        budgetViewModel.getNotBudgetedCategories()



        budgetViewModel.getBudgetedCategories()


        budgetViewModel.getTatals()

    }



    // setting adapter for "Not Budgeted Categories"

    private fun observeNotBudgetedCategories() {
        budgetViewModel.observeNotBudgetedCategoriesLiveData().observe(viewLifecycleOwner,object :Observer<List<NotBudgetedCategories>>{

            override fun onChanged(t: List<NotBudgetedCategories>?) {

                val adapter = NotBudgetedAdapter(requireContext(), t!!, this@BudgetFragment )
                adapter.notifyDataSetChanged()
                binding.recyclerNotBudgeted.adapter = adapter

            }

        })

    }

    // setting adapter for "Budgeted Categories"

    private fun observeBudgetedCategories(){
        budgetViewModel.observeBudgetedCategoriesLiveData().observe(viewLifecycleOwner,object :Observer<List<BudgetedCategoriesRequest>>{
            override fun onChanged(t: List<BudgetedCategoriesRequest>?) {
                val adapter = BudgetedAdapter(requireContext(),t!!)
                adapter.notifyDataSetChanged()
                binding.recyclerBudgeted.adapter =adapter
            }

        })
    }


    private var popupWindow: PopupWindow? = null
    private fun showPopupWindowSetBudget(category: NotBudgetedCategories){
        val inflater = LayoutInflater.from(requireContext())
       /* val parentView = view?.findViewById<CardView>(R.id.cardViewChooseCategory)*/
        val popupView = inflater.inflate(R.layout.set_budget_popup,null)

        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true


         val categoryName = popupView.findViewById<TextView>(R.id.tvSetbudgetCategoryName)
         categoryName.text = category.categoryName.toString()


        popupWindow = PopupWindow(popupView, width,height, focusable)

        //Adjust this value to move the popup higher
        val offsetY = -250
        popupWindow?.showAtLocation(popupView, Gravity.CENTER, 0, offsetY)


       // Request focus on the EditText to show keyboard automatically
       val editText = popupView.findViewById<EditText>(R.id.edtSetLimit)
       editText.requestFocus()

       // Set input type as number
       editText.inputType = InputType.TYPE_CLASS_NUMBER

       editText.postDelayed({
           val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
           imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
       }, 200)


    }

    fun closePopupWindow() {

        popupWindow?.dismiss()
    }



    override fun onButtonClick(category: NotBudgetedCategories) {
        showPopupWindowSetBudget(category)

    }






    private fun observeTotals(){
        budgetViewModel.observeTotalsLiveData().observe(viewLifecycleOwner,object :Observer<GetTatalsRequest>{
            override fun onChanged(t: GetTatalsRequest?) {
                if (t != null) {
                    val valueFormatter = CurrencyValueFormatter()
                    val balance = valueFormatter.getFormattedValue(t.balance?.toFloatOrNull() ?: 0f)
                    val expense = valueFormatter.getFormattedValue(t.expenses?.toFloatOrNull() ?: 0f)



                    binding.tvValueBalance.text = balance
                    binding.tvValueExpense.text = expense





                } else {

                }


            }

        })
    }


}






