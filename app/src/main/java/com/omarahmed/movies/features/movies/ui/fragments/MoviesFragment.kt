package com.omarahmed.movies.features.movies.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.omarahmed.movies.databinding.FragmentMoviesBinding
import com.omarahmed.movies.features.movies.data.models.MoviesResponse
import com.omarahmed.movies.features.movies.ui.adapters.MoviesRecyclerViewAdapter
import com.omarahmed.movies.features.movies.ui.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val moviesRecyclerViewAdapter: MoviesRecyclerViewAdapter by lazy {
        MoviesRecyclerViewAdapter(::setupMovieClickedAction)
    }

    private fun setupMovieClickedAction(movieId: Int) {
        findNavController().navigate(
            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                movieId
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callGetMoviesListApi()
    }

    private fun callGetMoviesListApi() {
        moviesViewModel.getMoviesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.moviesViewModel = moviesViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMoviesRecyclerView()
        observeMoviesListApiResponse()
        setupLoadingObserver()
        setupErrorObserver()
    }

    private fun setupErrorObserver() {
        moviesViewModel.errorResponse.observe(viewLifecycleOwner) { errorResponseString ->
            if (!errorResponseString.equals("null")) {
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorTextView.text = errorResponseString
            }
        }
    }

    private fun setupLoadingObserver() {
        moviesViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.loadingView.loadingContainer.visibility = View.VISIBLE
            } else {
                binding.loadingView.loadingContainer.visibility = View.GONE
            }
        }
    }

    private fun observeMoviesListApiResponse() {
        moviesViewModel.moviesListResponse.observe(viewLifecycleOwner) { moviesResponse ->
            addResponseToRecyclerViewAdapter(moviesResponse)
        }
    }

    private fun addResponseToRecyclerViewAdapter(moviesResponse: MoviesResponse?) {
        moviesRecyclerViewAdapter.submitList(moviesResponse?.moviesList)
    }

    private fun setupMoviesRecyclerView() {
        binding.homeRecyclerView.adapter = moviesRecyclerViewAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}