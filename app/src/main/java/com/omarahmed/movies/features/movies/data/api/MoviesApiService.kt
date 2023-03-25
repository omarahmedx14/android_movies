package com.omarahmed.movies.features.movies.data.api

import com.omarahmed.movies.core.Constants
import com.omarahmed.movies.features.movies.data.models.MovieDetailsResponse
import com.omarahmed.movies.features.movies.data.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET(Constants.MOVIES_LIST_EP)
    suspend fun getMoviesList(
        @Query(Constants.API_QUERY_KEY) apiKey: String = Constants.API_KEY,
    ): Response<MoviesResponse>

    @GET("${Constants.MOVIE_DETAILS_EP}{${Constants.MOVIE_DETAILS_EP_PATH}}")
    suspend fun getMovieDetails(
        @Path(Constants.MOVIE_DETAILS_EP_PATH) movieId : String,
        @Query(Constants.API_QUERY_KEY) apiKey: String = Constants.API_KEY,
        ): Response<MovieDetailsResponse>
}