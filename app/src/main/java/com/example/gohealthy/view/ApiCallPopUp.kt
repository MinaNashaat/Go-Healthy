//package com.example.gohealthy.view
//
//import android.app.AlertDialog
//import android.app.Dialog
//import android.os.Bundle
//import android.view.LayoutInflater
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.activityViewModels
//import androidx.fragment.app.viewModels
//import com.example.gohealthy.R
//import com.example.gohealthy.databinding.FragmentApiCallPopUpBinding
//import com.example.gohealthy.nutritionixAPI.CallDecider
//import com.example.gohealthy.viewModel.GrokVM
//import com.example.gohealthy.viewModel.NutritionixVM
//import androidx.lifecycle.lifecycleScope
//import kotlinx.coroutines.launch
//
//class ApiCallPopUp(private val callDecider: CallDecider) : DialogFragment() {
//
//  private lateinit var binding: FragmentApiCallPopUpBinding
//  private val nutritionixVM: NutritionixVM by activityViewModels()
//  private val grokVM: GrokVM by viewModels()
//
//  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//
//    binding = FragmentApiCallPopUpBinding.inflate(LayoutInflater.from(requireContext()))
//
//    val builder = AlertDialog.Builder(requireContext())
//    builder.setView(binding.root)
//
//    val dialog = builder.create()
//    dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_bg)
//
//    setupUI()
//
//    binding.save.setOnClickListener {
//      val text = binding.inputField.editText!!.text.toString().trim()
//      if (text.isEmpty()) return@setOnClickListener
//
//      // Optional: translate if Arabic
//      lifecycleScope.launch {
//        val query = grokVM.getResponse("Translate to English only: $text")
//        if (callDecider == CallDecider.EXERCISE) {
//          nutritionixVM.exercisCall(query, requireContext())
//        } else {
//          nutritionixVM.foodCall(query, callDecider, requireContext())
//        }
//      }
//
//      dismiss()
//    }
//
//    return dialog
//  }
//
//  private fun setupUI() {
//    when (callDecider) {
//      CallDecider.FOOD -> {
//        binding.title.text = getString(R.string.add_your_breakfast)
//        binding.inputField.hint = getString(R.string.breakfastHint)
//      }
//      CallDecider.EXERCISE -> {
//        binding.title.text = getString(R.string.add_your_workout)
//        binding.inputField.hint = getString(R.string.workoutHint)
//      }
//    }
//  }
//}
