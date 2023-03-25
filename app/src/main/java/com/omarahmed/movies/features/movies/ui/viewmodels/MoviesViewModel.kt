package com.omarahmed.movies.features.movies.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.movies.features.movies.data.models.MovieDetailsResponse
import com.omarahmed.movies.features.movies.data.models.MoviesErrorResponse
import com.omarahmed.movies.features.movies.data.models.MoviesResponse
import com.omarahmed.movies.features.movies.data.repos.MoviesRepo
import com.omarahmed.movies.features.movies.data.repos.MoviesRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepo: MoviesRepo) : ViewModel() {

    private val _moviesListResponse = MutableLiveData<MoviesResponse>()
    val moviesListResponse: LiveData<MoviesResponse>
        get() = _moviesListResponse

    private val _movieDetailsResponse = MutableLiveData<MovieDetailsResponse>()
    val movieDetailsResponse: LiveData<MovieDetailsResponse>
        get() = _movieDetailsResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String>
        get() = _errorResponse

    fun getMovieDetails(movieId: String) = viewModelScope.launch {
        _isLoading.postValue(true)
        try {
            moviesRepo.getMovieDetails(movieId).let {
                _movieDetailsResponse.postValue(it.value?.body())
                _errorResponse.postValue(it.error?.statusMessage.toString())
            }
            _isLoading.postValue(false)
        } catch (exception: Exception) {
            _errorResponse.postValue(exception.message)
            _isLoading.postValue(false)

        }
    }

    fun getMoviesList() = viewModelScope.launch {
        _isLoading.postValue(true)
        try {
            moviesRepo.getMoviesList().let {
                _moviesListResponse.postValue(it.value?.body())
                _errorResponse.postValue(it.error?.statusMessage.toString())
            }
            _isLoading.postValue(false)
        } catch (exception: Exception) {
            _errorResponse.postValue(exception.message)
            _isLoading.postValue(false)

        }
    }

}