package com.ajijul.ahoytestdata.ui.main.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajijul.ahoytestdata.R
import com.ajijul.ahoytestdata.base.BaseFragment
import com.ajijul.ahoytestdata.databinding.FragmentFavouriteBinding
import com.ajijul.ahoytestdata.ui.main.weather.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouriteFragment : BaseFragment() {

    private lateinit var adapter: FavouriteAdapter
    private val favourViewModel: FavourViewModel by activityViewModels()
    private val weatherViewModel: WeatherViewModel by activityViewModels()
    lateinit var binding: FragmentFavouriteBinding
    private var favouriteList: ArrayList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
        favourViewModel._favouriteList.observe(viewLifecycleOwner) {
            adapter.informMe(it)
        }
    }

    private fun initializeRecyclerView() {
        binding.rvFavCity.layoutManager = LinearLayoutManager(requireContext())
        adapter = FavouriteAdapter(favouriteList){
            weatherViewModel.fetchForCity(it)
            findNavController().navigate(R.id.move_to_weather_screen)
        }
        binding.rvFavCity.adapter = adapter
    }
}