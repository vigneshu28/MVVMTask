package com.example.mvvmtask.Fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exampleproject.responsemodel.TrendingResponseItem
import com.example.mvvmtask.Adapter.HomeAdapter
import com.example.mvvmtask.Network.ApiHelper
import com.example.mvvmtask.Network.RetrofitBuilder
import com.example.mvvmtask.R
import com.example.mvvmtask.Util.Status
import com.example.mvvmtask.Util.ViewModelFactory
import com.example.mvvmtask.ViewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var viewModel: HomeViewModel
    lateinit var adapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()

        swipe.setOnRefreshListener {
            swipe.setColorSchemeColors(Color.WHITE)
            swipe.setProgressBackgroundColorSchemeColor(Color.rgb(2, 101, 213))
            setupObservers()
        }
        try_again.setOnClickListener {
            setupObservers()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(HomeViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getList().observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        list_view.visibility = View.VISIBLE
                        progressbar.visibility = View.GONE
                        no_internet.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        swipe.isRefreshing = false
                        list_view.visibility = View.GONE
                        progressbar.visibility = View.GONE
                        no_internet.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        list_view.visibility = View.GONE
                        no_internet.visibility = View.GONE
                        if (swipe.visibility == View.VISIBLE) {
                        } else {
                            progressbar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun retrieveList(users: List<TrendingResponseItem>) {
        setHasOptionsMenu(true)
        swipe.isRefreshing = false
        list_view.layoutManager = LinearLayoutManager(requireContext())
        adapter = HomeAdapter(requireContext(), users, users, { trendingResponseItem -> })
        list_view.addItemDecoration(
            DividerItemDecoration(
                list_view.context,
                (list_view.layoutManager as LinearLayoutManager).orientation
            )
        )
        list_view.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filter.filter(newText)
                }
                return true
            }
        })
    }

}