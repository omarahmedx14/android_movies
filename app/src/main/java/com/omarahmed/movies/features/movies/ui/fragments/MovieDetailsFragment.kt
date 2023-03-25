package com.omarahmed.movies.features.movies.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.omarahmed.movies.R
import com.omarahmed.movies.core.Constants
import com.omarahmed.movies.databinding.FragmentMovieDetailsBinding
import com.omarahmed.movies.features.movies.data.models.MovieDetailsResponse
import com.omarahmed.movies.features.movies.ui.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private val movieDetailsFragmentArgs: MovieDetailsFragmentArgs by navArgs()
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callGetMovieDetailsApi()
    }

    private fun callGetMovieDetailsApi() = moviesViewModel.getMovieDetails(getMovieIdArgument())

    private fun getMovieIdArgument() = movieDetailsFragmentArgs.movieId.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.homeViewModel = moviesViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeMovieDetailsApiResponse()
        // as you can see this loading & error are being copied from MoviesFragment
        // and for better refactoring, a BaseFragment and a BaseViewModel would be great
        // in making things more generalized and reusable.
        setupLoadingObserver()
        setupErrorObserver()
    }

    private fun observeMovieDetailsApiResponse() {
        moviesViewModel.movieDetailsResponse.observe(viewLifecycleOwner) { movieDetailsResponse ->
            bindViews(movieDetailsResponse)
        }
    }

    private fun setupErrorObserver() {
        moviesViewModel.errorResponse.observe(viewLifecycleOwner) { errorResponseString ->
            if (!errorResponseString.equals("null")) {
                binding.movieImageView.visibility = View.GONE
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorTextView.text = errorResponseString
            }
        }
    }

    private fun bindViews(movieDetailsResponse: MovieDetailsResponse?) {
        binding.overviewTextView.text = movieDetailsResponse?.overview
        binding.movieImageView.visibility = View.VISIBLE
        Glide.with(requireContext())
            .load("${Constants.IMAGES_URL}${Constants.IMAGES_SIZE}${movieDetailsResponse?.posterPath}")
            .placeholder(R.drawable.placeholder_ic)
            .into(binding.movieImageView)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}