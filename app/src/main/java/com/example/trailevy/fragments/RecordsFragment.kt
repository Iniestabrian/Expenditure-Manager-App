package com.example.trailevy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trailevy.R
import com.example.trailevy.adapters.RecordsAdapter
import com.example.trailevy.data.CurrencyValueFormatter
import com.example.trailevy.data.GetTatalsRequest
import com.example.trailevy.data.RecordsRequest
import com.example.trailevy.databinding.FragmentCategoryBinding
import com.example.trailevy.databinding.FragmentRecordsBinding
import com.example.trailevy.viewModels.BudgetViewModel
import com.example.trailevy.viewModels.RecordsViewModel


class RecordsFragment : Fragment() {

    private lateinit var binding: FragmentRecordsBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recordsMvvm: RecordsViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecordsBinding.inflate(inflater,container,false)


        recordsMvvm = ViewModelProvider(this)[RecordsViewModel::class.java]


        observeTotals()
        observeRecords()

        return binding.root



    }


    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerViewRecords.layoutManager = layoutManager

        binding.recyclerViewRecords.setHasFixedSize(true)


        //floating button to Spending Fragment
        binding.btnSpend.setOnClickListener {
            findNavController().navigate(R.id.action_recordsFragment_to_spendingFragment)

        }

        recordsMvvm.getRecords()
        recordsMvvm.getTatals()



        disappearBitton()

    }



    fun observeRecords(){
        recordsMvvm.observeRecordsLiveData().observe(viewLifecycleOwner,object : Observer<List<RecordsRequest>>{
            override fun onChanged(records: List<RecordsRequest>?) {

                val adapter = RecordsAdapter(requireContext(),records!!)
                adapter.notifyDataSetChanged()
                binding.recyclerViewRecords.adapter = adapter

            }

        })
    }





//make floating button to disappear on scroll

    private fun  disappearBitton(){

        val recyclerView: RecyclerView = binding.recyclerViewRecords


        val floatingButton = binding.btnSpend


        // Track the last scroll position
        var lastScrollPosition = 0

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && floatingButton.isShown) {
                    // Scrolling up, show the button
                    floatingButton.hide()
                } else if (dy < 0 && !floatingButton.isShown) {
                    // Scrolling down, hide the button
                    floatingButton.show()
                }
            }
        })
    }


    private fun observeTotals(){
        recordsMvvm.observeTotalsLiveData().observe(viewLifecycleOwner,object :Observer<GetTatalsRequest>{
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

