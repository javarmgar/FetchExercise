package com.example.fetchexercise.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ListAdapter
import com.example.core.data.remote.model.HiringResponseModel
import com.example.core.data.remote.model.params.HiringParamsModel
import com.example.fetchexercise.R
import com.example.fetchexercise.databinding.ActivityMainBinding
import com.example.fetchexercise.framework.library.utils.Resource
import com.example.fetchexercise.framework.library.utils.Status
import com.example.fetchexercise.framework.library.utils.setGone
import com.example.fetchexercise.framework.library.utils.setVisible
import com.example.fetchexercise.presentation.view.adapter.HiringAdapter
import com.example.fetchexercise.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Comparator

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val filterByName = { item: HiringResponseModel -> item.name != "null" && item.name != "" }
    val filterNone = { _: HiringResponseModel -> true }

    val comparatorByFirst = compareBy<HiringResponseModel> { it.listId }
    val comparatorByFirstSecond = comparatorByFirst.thenBy { it.name }

    private var filter: (HiringResponseModel) -> Boolean = filterNone
    private lateinit var comparator: Comparator<HiringResponseModel>

    private var isFilterByName = MutableLiveData(false)
    private var sortingCriteria = MutableLiveData(0)
    private lateinit var adapter: HiringAdapter
    private lateinit var filterByNameSpinner: Spinner
    private lateinit var sortingFieldSpinner: Spinner

    private lateinit var binding: ActivityMainBinding
    private val LOG_TAG: String = "LT_MainActivity"

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() ")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSortingFieldSpinner()
        setFilterByNameSpinner()
        setRecyclerView()


    }

    private fun setSortingFieldSpinner() {
        sortingFieldSpinner = binding.spnSortByField
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.hiring_fields,
            android.R.layout.simple_spinner_item
        )
        sortingFieldSpinner.adapter = adapter
        sortingFieldSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val itemSelected: String? = parent?.getItemAtPosition(position) as String?
                Log.d(LOG_TAG, "setSortingFieldSpinner() itemSelected: $itemSelected")
                sortingCriteria.value = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(LOG_TAG, "setSortingFieldSpinner() onNothingSelected")
            }
        }
    }

    private fun setFilterByNameSpinner() {
        filterByNameSpinner = binding.spnFilterByName
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.filter_name_options,
            android.R.layout.simple_spinner_item
        )
        filterByNameSpinner.adapter = adapter
        filterByNameSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val itemSelected: String? = parent?.getItemAtPosition(position) as String?
                Log.d(LOG_TAG, "setFilterByNameSpinner() itemSelected: $itemSelected")
                isFilterByName.value = when (position) {
                    0 -> false
                    else -> true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(LOG_TAG, "setFilterByNameSpinner() onNothingSelected")
            }
        }
    }


    private fun setRecyclerView() {
        adapter = HiringAdapter()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hiringListState.collect { resource ->
                    Log.d(LOG_TAG, "onCreate() viewModel.hiringListState.collect -> $resource")
                    updateRecyclerView(resource)

                }
            }
        }
        viewModel.fetchHiringList(object : HiringParamsModel {})
    }

    private fun updateRecyclerView(resource: Resource<List<HiringResponseModel>>) {
        Log.d(LOG_TAG, "updateRecyclerView() resource.status ${resource.status}")
        when (resource.status) {
            Status.LOADING -> {
                setLoadingState()
            }

            Status.ERROR -> {
                setErrorState(resource.message)
            }

            Status.SUCCESS -> {
                setSuccessState(resource.data)
            }

            else -> {}
        }
    }

    private fun setLoadingState() {
        binding.apply {
            progressBar.setVisible()
            ivMsgError.setGone()
            tvMsgError.setGone()
            rvHirings.root.setGone()
        }
    }

    private fun setErrorState(message: String?) {
        Log.d(LOG_TAG, "setErrorState() resource.message $message")
        binding.apply {
            progressBar.setGone()
            ivMsgError.setVisible()
            tvMsgError.setVisible()
            rvHirings.root.setGone()
        }
    }

    private fun setSuccessState(hiringList: List<HiringResponseModel>?) {
        Log.d(LOG_TAG, "setSuccessState() hiringList $hiringList")
        hiringList?.run {
            binding.rvHirings.recyclerView.adapter = adapter
            setSortingMethods(this)
            setFilterMethods(this)
            adapter.submitList(this)
        }
        binding.apply {
            progressBar.setGone()
            ivMsgError.setGone()
            tvMsgError.setGone()
            rvHirings.root.setVisible()
        }
    }

    private fun setSortingMethods(hiringList: List<HiringResponseModel>) {
        sortingCriteria.observe(this) {
            comparator = when (it) {
                1 -> comparatorByFirst
                else -> comparatorByFirstSecond
            }
            setListAttributes(hiringList)
            Log.d(LOG_TAG, "setSortingMethods() sortingCriteria: $it")
        }
    }

    private fun setFilterMethods(hiringList: List<HiringResponseModel>) {
        isFilterByName.observe(this) { it ->
            filter = if (it) filterByName else filterNone
            setListAttributes(hiringList)
            Log.d(LOG_TAG, "setFilterMethods() isFilterByName: $it")
        }
    }

    private fun setListAttributes(hiringList: List<HiringResponseModel>) {
        val list = hiringList.filter(filter).sortedWith(comparator)
        (binding.rvHirings.recyclerView.adapter as HiringAdapter).submitList(list)
    }
}
