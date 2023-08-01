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
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.trailevy.R
import com.example.trailevy.data.CurrencyValueFormatter
import com.example.trailevy.data.GetTatalsRequest
import com.example.trailevy.databinding.FragmentAccountsBinding
import com.example.trailevy.viewModels.AccountsViewModel
import com.example.trailevy.viewModels.AnalyticsViewModel


class AccountsFragment : Fragment() {

    private lateinit var binding:FragmentAccountsBinding
    private lateinit var  accountsMvvm: AccountsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAccountsBinding.inflate(inflater,container,false)

       accountsMvvm = ViewModelProvider(this)[AccountsViewModel::class.java]

        observeTotals()


        return binding.root
    }


    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountsMvvm.getTatals()


        binding.showpipup.setOnClickListener {
            showPopupWindow()
        }



    }




    var popupWindow:PopupWindow?= null
      lateinit var popupView:View

    private fun showPopupWindow() {
        val inflater = LayoutInflater.from(requireContext())
        val popupView = inflater.inflate(R.layout.deposit_popup, null)



        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true

         popupWindow = PopupWindow(popupView, width, height, focusable)

        val offsetY = -250
        popupWindow?.showAtLocation(popupView, Gravity.CENTER, 0, offsetY)


        // Request focus on the EditText to show keyboard automatically
        val editText = popupView.findViewById<EditText>(R.id.edtSetAmount)
        editText.requestFocus()

        // Set input type as number

        editText.inputType = InputType.TYPE_CLASS_NUMBER

        editText.postDelayed({
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }, 200)


        val saveButton= popupView?.findViewById<Button>(R.id.btnSave)

        val cancelButton = popupView?.findViewById<Button>(R.id.btnCancel)



        saveButton?.setOnClickListener {
            onCancelButtonClick()
        }
        cancelButton?.setOnClickListener {
            onSaveButtonClick()
        }


    }



    private fun onSaveButtonClick() {
        closePopupWindow()
    }

    private fun onCancelButtonClick() {

        closePopupWindow()
    }



    private fun closePopupWindow() {
        popupWindow?.dismiss()
    }









    private fun observeTotals(){
       accountsMvvm.observeTotalsLiveData().observe(viewLifecycleOwner,object :
            Observer<GetTatalsRequest> {
            override fun onChanged(t: GetTatalsRequest?) {
                if (t != null) {
                    val valueFormatter = CurrencyValueFormatter()
                    val balance = valueFormatter.getFormattedValue(t.balance?.toFloatOrNull() ?: 0f)
                    val expense = valueFormatter.getFormattedValue(t.expenses?.toFloatOrNull() ?: 0f)
                    val initialBalance = valueFormatter.getFormattedValue(t.initialBalance?.toFloatOrNull() ?: 0f)



                    binding.tvValueBalance.text = balance
                    binding.tvValueExpense.text = expense
                    binding.tvValueInitialAmount.text = initialBalance





                } else {

                }


            }

        })
    }


}