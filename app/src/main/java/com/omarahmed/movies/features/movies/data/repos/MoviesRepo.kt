package com.omarahmed.movies.features.movies.data.repos

import com.omarahmed.movies.core.ApiResult
import com.omarahmed.movies.features.movies.data.models.MovieDetailsResponse
import com.omarahmed.movies.features.movies.data.models.MoviesErrorResponse
import com.omarahmed.movies.features.movies.data.models.MoviesResponse
import retrofit2.Response

interface MoviesRepo {

    suspend fun getMoviesList(): ApiResult<Response<MoviesResponse>, MoviesErrorResponse>

    suspend fun getMovieDetails(movieId: String): ApiResult<Response<MovieDetailsResponse>, MoviesErrorResponse>

}