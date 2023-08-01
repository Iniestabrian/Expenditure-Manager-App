package com.example.trailevy.fragments

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trailevy.R
import com.example.trailevy.adapters.CategoriesAdapter
import com.example.trailevy.data.CategoriesRequest
import com.example.trailevy.databinding.FragmentCategoryBinding
import com.example.trailevy.retrofit.RetrofitInstance.apiService
import com.example.trailevy.viewModels.AnalyticsViewModel
import com.example.trailevy.viewModels.CategoriesViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryFragment : Fragment(){


    private lateinit var binding: FragmentCategoryBinding
    lateinit var layoutManager: GridLayoutManager
    private lateinit var categoriesMvvm: CategoriesViewModel



    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)






        setHasOptionsMenu(true)


    }

    //To Revisit
  /*  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_category, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }*/



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater,container,false)

        categoriesMvvm = ViewModelProvider(this)[CategoriesViewModel::class.java]




        observecategoriesLiveData()


        return binding.root
    }







    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        categoriesMvvm.getCategories()

        binding.btnAddCategory.setOnClickListener {
            showPopupWindow()
        }
        binding.recyclerExpenseCategories.setHasFixedSize(true)
        layoutManager = GridLayoutManager(context,3,LinearLayoutManager.VERTICAL,false)



        binding.recyclerExpenseCategories.layoutManager =layoutManager
    }










//method to get categories from api
    private fun observecategoriesLiveData(){


      categoriesMvvm.observeTotalsLiveData().observe(viewLifecycleOwner,object :Observer<List<CategoriesRequest>>{
          override fun onChanged(t: List<CategoriesRequest>?) {

       val adapter = CategoriesAdapter(requireContext(),t!!)
        adapter .notifyDataSetChanged()

            binding.recyclerExpenseCategories.adapter =adapter


          }


      })




    }




    private var popupWindow: PopupWindow? = null
    private lateinit var popupView: View


    private fun showPopupWindow() {
        val inflater = LayoutInflater.from(requireContext())
       popupView = inflater.inflate(com.example.trailevy.R.layout.add_category_popup, null)





        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true

         popupWindow = PopupWindow(popupView, width, height, focusable)

        // Adjust this value to move the popup higher
        val offsetY = -250
        popupWindow?.showAtLocation(popupView, Gravity.CENTER, 0, offsetY)



         val saveButton= popupView?.findViewById<Button>(R.id.btnSave)

         val cancelButton = popupView?.findViewById<Button>(R.id.btnCancel)






        saveButton?.setOnClickListener {
                onCancelButtonClick()
        }
        cancelButton?.setOnClickListener {
            onSaveButtonClick()
        }




             // Request focus on the EditText to show keyboard automatically
             val editText = popupView.findViewById<EditText>(R.id.edtCategoryName)
             editText.requestFocus()

             // Set input type as number


             editText.postDelayed({
                 val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                 imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
             }, 200)


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










}