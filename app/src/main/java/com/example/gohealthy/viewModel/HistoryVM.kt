package com.example.gohealthy.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gohealthy.history.HistoryItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HistoryVM : ViewModel() {

  private val historyList = mutableListOf<HistoryItem>()
  private var _liveHistoryList = MutableLiveData<MutableList<HistoryItem>>()
  val liveHistoryList: MutableLiveData<MutableList<HistoryItem>> get() = _liveHistoryList

  fun fetchHistoryData() {
    historyList.clear()
    addDummyHistory()
    _liveHistoryList.value = historyList
  }

  private fun addDummyHistory() {
    historyList.add(
      HistoryItem("2025-12-29", kcalIn = 1800, kcalOut = 300, steps = 4500)
    )
    historyList.add(
      HistoryItem("2025-12-28", kcalIn = 2200, kcalOut = 500, steps = 6400)
    )
    historyList.add(
      HistoryItem("2025-12-27", kcalIn = 1500, kcalOut = 250, steps = 3200)
    )
    historyList.add(
      HistoryItem("2025-12-26", kcalIn = 2000, kcalOut = 400, steps = 5200)
    )
    historyList.add(
      HistoryItem("2025-12-25", kcalIn = 2500, kcalOut = 600, steps = 8000)
    )
  }
}

//class HistoryVM: ViewModel() {
//    private val historyList = mutableListOf<HistoryItem>()
//    private val auth = FirebaseAuth.getInstance()
//    private var _liveHistoryList = MutableLiveData<MutableList<HistoryItem>>()
//    val liveHistoryList: MutableLiveData<MutableList<HistoryItem>> get() = _liveHistoryList
//
//    fun fetchHistoryData() {
//        val userId = auth.currentUser?.uid ?: return
//        // Clear the list before fetching new data
//        historyList.clear()
//
//        val db = FirebaseFirestore.getInstance()
//        db.collection("users")
//            .document(userId)
//            .collection("history").orderBy("date", Query.Direction.DESCENDING)
//            .get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    val historyItem = document.toObject(HistoryItem::class.java)
//                    // Avoid adding null or invalid items to the list
//                    historyItem?.let { historyList.add(it) }
//                }
//                // Update the live data with the new list
//                _liveHistoryList.value = historyList
//            }
//            .addOnFailureListener { exception ->
//                Log.w("HistoryFragment", "Error getting documents: ", exception)
//            }
//    }
//}



