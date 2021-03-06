package io.github.mohamedisoliman.fancy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.github.mohamedisoliman.fancy.R
import io.github.mohamedisoliman.fancy.data.entities.toProductDetails
import io.github.mohamedisoliman.fancy.databinding.HomeFragmentBinding
import io.github.mohamedisoliman.fancy.domain.RetrieveProducts
import io.github.mohamedisoliman.fancy.ui.productdetails.ProductDetailsFragment
import kotlinx.android.synthetic.main.home_activity.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            HomeViewModel.FACTORY(RetrieveProducts())
        )[HomeViewModel::class.java]

        initViews()
        initObservers()
    }

    private fun initViews() {
        with(binding.productsListView) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            productsAdapter = ProductsAdapter {
                val toDetailsFragment =
                    HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it.toProductDetails())
                findNavController().navigate(toDetailsFragment)
            }.also {
                adapter = it
            }
        }
    }

    private fun initObservers() {

        viewModel.error().observe(viewLifecycleOwner, Observer {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.progress().observe(viewLifecycleOwner, Observer {
            if (it) binding.progressBar.show() else binding.progressBar.hide()
        })

        viewModel.productsList().observe(viewLifecycleOwner, Observer {
            Timber.d("Data $it")
            productsAdapter.setData(it)
        })
    }

}
