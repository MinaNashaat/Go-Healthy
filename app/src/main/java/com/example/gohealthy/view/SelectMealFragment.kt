package com.example.gohealthy.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gohealthy.Adapter.ListAdapter
import com.example.gohealthy.data.StaticData
import com.example.gohealthy.databinding.FragmentSimpleListBinding
import com.example.gohealthy.viewModel.NutritionixVM

class SelectMealFragment : Fragment() {

  private lateinit var binding: FragmentSimpleListBinding
  private val nutritionVM: NutritionixVM by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentSimpleListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val mealNames = StaticData.meals.map { "${it.name} - ${it.calories} kcal" }

    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.recyclerView.adapter = ListAdapter(mealNames) { selected ->
      val calories = selected.substringAfterLast(" ").substringBefore("kcal").trim().toInt()
      nutritionVM.addManualMeal(calories, requireContext())
      findNavController().popBackStack()
    }
  }
}
