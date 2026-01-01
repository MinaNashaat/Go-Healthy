package com.example.gohealthy.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.gohealthy.data.StaticData
import com.example.gohealthy.databinding.DialogSimpleListBinding
import com.example.gohealthy.viewModel.NutritionixVM

class MealSelectionDialog : DialogFragment() {

  private lateinit var binding: DialogSimpleListBinding
  private val nutritionVM: NutritionixVM by activityViewModels()

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DialogSimpleListBinding.inflate(LayoutInflater.from(context))

    val list = StaticData.meals.map { "${it.name} - ${it.calories} kcal" }
    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
    binding.listView.adapter = adapter

    binding.listView.setOnItemClickListener { _, _, position, _ ->
      val item = list[position]
      val calories = item.substringAfter("-").replace("kcal", "").trim().toInt()
      nutritionVM.addManualMeal(calories, requireContext())
      dismiss()
    }

    return AlertDialog.Builder(requireContext())
      .setTitle("Choose Meal")
      .setView(binding.root)
      .create()
  }
}
