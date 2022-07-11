package com.ajijul.ahoytestdata.ui.main.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ajijul.ahoytestdata.R
import com.ajijul.ahoytestdata.base.BaseFragment
import com.ajijul.ahoytestdata.databinding.FragmentWeatherBinding
import com.ajijul.ahoytestdata.utils.Constants
import com.ajijul.ahoytestdata.utils.Helper
import com.ajijul.ahoytestdata.utils.ScreenState
import com.ajijul.network.utils.Network
import com.ajijul.network.utils.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WeatherFragment : BaseFragment() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    lateinit var binding: FragmentWeatherBinding
    private var favouriteList: HashSet<String> = HashSet()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObsever()
        initializeListener()
        lifecycleScope.launch {
            favouriteList = weatherViewModel.getFavouriteList()
            getWeather("Dubai")
        }
    }

    private fun initializeListener() {
        weatherFragment_searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Helper.hideKeyboard(activity)
                getWeather(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        weatherFragment_searchView.setOnClickListener {
            weatherFragment_searchView.isIconified =
                false
        }

        favButton.setOnCheckedChangeListener { _, b ->
            Log.e("TAG",favButton.tag.toString() +"   " + b.toString())
            if (b) favouriteList.add(favButton.tag.toString()) else favouriteList.remove(favButton.tag.toString())
            weatherViewModel.setFavouriteList(favouriteList)
        }
    }

    private fun subscribeToObsever() {
        weatherViewModel.observeScreenState().observe(viewLifecycleOwner) {
            if (it != null) {

                when (it) {
                    ScreenState.RENDER -> handleProgress(false)
                    ScreenState.LOADING -> handleProgress(true)
                    ScreenState.ERROR -> handleProgress(false)
                }
            }
        }

    }


    private fun getWeather(p0: String?) {
        weatherViewModel.getWeatherObserver().removeObservers(viewLifecycleOwner)
        weatherViewModel.observeWeather(
            p0 ?: "Dubai", Constants.API_KEY,
            Network.checkConnectivity(requireContext())
        )
            .observe(viewLifecycleOwner) {

                if (it != null) {
                    when (it) {
                        is ResultWrapper.Success -> {
                            manipulateFavouriteButton(p0)
                            binding.data = it.value
                            mainView?.let { it1 ->
                                messageHandlerImp.showSnackSuccess(
                                    it1,
                                    R.string.succesfullyFetch,
                                    true
                                )
                            }
                        }
                        is ResultWrapper.GenericError -> {

                            mainView?.let { it1 ->
                                messageHandlerImp.showSnackErrorWithAction(it1, it.error ?: "") {
                                    getWeather(p0)

                                }
                            }

                        }
                        ResultWrapper.NetworkError -> {

                            mainView?.let { it1 ->
                                messageHandlerImp.showSnackErrorWithAction(
                                    it1,
                                    R.string.retry_text
                                ) {

                                }
                            }
                        }
                    }
                }
            }


    }

    private fun manipulateFavouriteButton(p0: String?) {
        favButton.tag = p0
        favButton.isChecked = favouriteList.contains(p0)
    }

    private fun handleProgress(isShow: Boolean) {
        if (isShow) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }
}