package com.example.trailevy.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trailevy.R
import com.example.trailevy.adapters.SpendingAdapter
import com.example.trailevy.adapters.spendingItemClick_Listener
import com.example.trailevy.data.CategoriesRequest
import com.example.trailevy.databinding.FragmentSpendingBinding
import com.example.trailevy.viewModels.SpendingViewModel


class SpendingFragment : Fragment() {

        private lateinit var binding:FragmentSpendingBinding
        lateinit var layoutManager: GridLayoutManager
        private val txtChoose:String = "Choose Category"

    private lateinit var spendingMvvm: SpendingViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSpendingBinding.inflate(inflater,container,false)

        spendingMvvm = ViewModelProvider(this)[SpendingViewModel::class.java]

        val tvChooseCategory = binding.tvChooseCategory
        tvChooseCategory.text =txtChoose

        observeAddExpenseLiveData()

        return  binding.root
    }


    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //choose Category onclick listener
    binding.cardViewChooseCategory.setOnClickListener {
        showPopupWindow()
    }


        //Expense amount


        // Request focus on the EditText to show keyboard automatically
        val editText = binding.edtAmountToSpend
        /*editText.requestFocus()*/

        // Set input type as number
        editText.inputType = InputType.TYPE_CLASS_NUMBER

        editText.postDelayed({
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }, 200)








        binding.btnSaveSpending.setOnClickListener {
            addExpense()

        }



        //navigate back to records Fragment
        binding.btnCancelSpending.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvCancelSpending.setOnClickListener {
            findNavController().popBackStack()
        }

        //navigate to records with back button
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Navigate back to the previous fragment
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }




    //Showing the choose category popup

    private var popupWindow: PopupWindow? = null
    private lateinit var popupRecyclerView:RecyclerView
    private  var parentView : CardView? = null


    private fun showPopupWindow() {
        val inflater = LayoutInflater.from(requireContext())
        parentView = view?.findViewById<CardView>(R.id.cardViewChooseCategory)
        val popupView = inflater.inflate(R.layout.choose_category_popup,parentView,false)


        val width = 600
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true

        val layoutParams = LinearLayout.LayoutParams(width, height)

        popupWindow = PopupWindow(popupView, layoutParams.width,layoutParams.height, focusable)

        // Adjust this value to move the popup higher
        val offsetY =0
        popupWindow?.showAtLocation(popupView, Gravity.CENTER, 0, offsetY)


        popupRecyclerView = popupView.findViewById<RecyclerView>(R.id.recyclerViewChooseCategory)
        layoutManager = GridLayoutManager(context,3, LinearLayoutManager.VERTICAL,false)
        popupRecyclerView.layoutManager = layoutManager

        spendingMvvm.getSpendingCategories()
        observeSpendingCategories()




    }

    //spending categories Observer

    private fun observeSpendingCategories(){

        spendingMvvm.observespendingCategoriesLiveData().observe(viewLifecycleOwner,object :Observer<List<CategoriesRequest>>{
            override fun onChanged(t: List<CategoriesRequest>?) {
                val adapter = SpendingAdapter(requireContext(),t!!)

                adapter.setOnItemClickListener(object : spendingItemClick_Listener {
                    override fun onItemClick(position: Int) {
                        // Handle the item click event here
                        val clickedCategory = t[position]
                        val selectedCategoryName = parentView?.findViewById<TextView>(R.id.tvChooseCategory)
                        selectedCategoryName?.text = clickedCategory.categoryName
                        selectedCategoryName?.setTextColor(Color.RED)
                        closePopupWindow()

            }

        })
                popupRecyclerView.adapter = adapter
                adapter .notifyDataSetChanged()


    }
        })
    }


    fun closePopupWindow() {
        popupWindow?.dismiss()
    }





    private fun addExpense(){
        val categoryName = binding.tvChooseCategory.text.toString()
        val amountSpentText = binding.edtAmountToSpend.text.toString()
        val amountSpent = amountSpentText.toFloatOrNull() ?: 0.0f



        if (amountSpent > 0 && categoryName != txtChoose) {
                spendingMvvm.addExpense(categoryName, amountSpent)
        } else if (amountSpent <= 0) {
            Toast.makeText(context, "Amount Cannot be Zero", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Choose Category", Toast.LENGTH_SHORT).show()
        }


    }



    private  fun observeAddExpenseLiveData(){
        spendingMvvm.addExpenseResult.observe(viewLifecycleOwner) { isSignupSuccessful ->
            if (isSignupSuccessful) {
                Toast.makeText(context, "Expense Added", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_spendingFragment_to_recordsFragment)
            } else {
                Toast.makeText(context, "failed try again.", Toast.LENGTH_LONG).show()
            }
        }
    }






}


