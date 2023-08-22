package com.example.lokaltask

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class FilterDialogFragment : DialogFragment() {
    private var brandList: MutableList<String> = mutableListOf()
    private var categoryList: MutableList<String> = mutableListOf()

    enum class PriceSort {
        NONE, INCREASING, DECREASING
    }

    enum class DiscountSort {
        NONE, LOW_TO_HIGH, HIGH_TO_LOW
    }

    interface FilterListener {
        fun onFilterApplied(brand: String, category: String, priceSort: PriceSort, discountSort: DiscountSort)
    }

    var filterListener: FilterListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_filter, container, false)
        val brandSpinner: Spinner = view.findViewById(R.id.spinnerBrand)
        val categorySpinner: Spinner = view.findViewById(R.id.spinnerCategory)
        val radioGroupPriceSort: RadioGroup = view.findViewById(R.id.radioGroupPriceSort)
        val radioGroupDiscountSort: RadioGroup = view.findViewById(R.id.radioGroupDiscountSort)
        val btnApply: Button = view.findViewById(R.id.btnApply)
        brandList.add("Select Brand")
        categoryList.add("Select Category")

        arguments?.let {
            brandList.addAll(it.getStringArrayList("brandList") ?: listOf())
            categoryList.addAll(it.getStringArrayList("categoryList") ?: listOf())
        }

        brandSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, brandList)
        categorySpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryList)

        btnApply.setOnClickListener {
            val selectedBrand = brandSpinner.selectedItem.toString()
            val selectedCategory = categorySpinner.selectedItem.toString()

            val priceSort = when (radioGroupPriceSort.checkedRadioButtonId) {
                R.id.radioPriceIncreasing -> PriceSort.INCREASING
                R.id.radioPriceDecreasing -> PriceSort.DECREASING
                else -> PriceSort.NONE
            }

            val discountSort = when (radioGroupDiscountSort.checkedRadioButtonId) {
                R.id.radioDiscountLowToHigh -> DiscountSort.LOW_TO_HIGH
                R.id.radioDiscountHighToLow -> DiscountSort.HIGH_TO_LOW
                else -> DiscountSort.NONE
            }

            filterListener?.onFilterApplied(
                selectedBrand,
                selectedCategory,
                priceSort,
                discountSort
            )
            dismiss()
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        val window: Window? = dialog?.window
        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        val width = size.x * 0.9 // Set width to 90% of screen width
        window?.setLayout(width.toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
    }

}
