package com.omarahmed.movies.features.movies.data.repos

import com.omarahmed.movies.core.ApiResult
import com.omarahmed.movies.features.movies.data.api.MoviesApiService
import com.omarahmed.movies.features.movies.data.models.MovieDetailsResponse
import com.omarahmed.movies.features.movies.data.models.MoviesErrorResponse
import com.omarahmed.movies.features.movies.data.models.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class MoviesRepoImpl @Inject constructor(private val homeApiService: MoviesApiService) :
    MoviesRepo {


    override suspend fun getMoviesList(): ApiResult<Response<MoviesResponse>, MoviesErrorResponse> =
        withContext(Dispatchers.IO) {
            homeApiService.getMoviesList().let { moviesResponse ->
                if (moviesResponse.isSuccessful) {
                    return@let ApiResult<Response<MoviesResponse>, MoviesErrorResponse>(
                        value = moviesResponse,
                        error = null,
                    )
                } else {
                    return@let ApiResult<Response<MoviesResponse>, MoviesErrorResponse>(
                        value = null,
                        // this error handling can be better but kinda will be an overkill for this project
                        error = MoviesErrorResponse(
                            statusMessage = moviesResponse.errorBody()?.let { errorBody ->
                                JSONObject(errorBody.string()).getString("status_message")
                            },
                        ),
                    )
                }
            }
        }

    override suspend fun getMovieDetails(movieId: String): ApiResult<Response<MovieDetailsResponse>, MoviesErrorResponse> =
        withContext(Dispatchers.IO)
        {
            homeApiService.getMovieDetails(movieId = movieId).let { movieDetailsResponse ->
                if (movieDetailsResponse.isSuccessful) {
                    return@let ApiResult<Response<MovieDetailsResponse>, MoviesErrorResponse>(
                        value = movieDetailsResponse,
                        error = null,
                    )
                } else {
                    return@let ApiResult<Response<MovieDetailsResponse>, MoviesErrorResponse>(
                        value = null,
                        error = MoviesErrorResponse(
                            statusMessage = movieDetailsResponse.errorBody()?.let { errorBody ->
                                JSONObject(errorBody.string()).getString("status_message")
                            },
                        ),
                    )
                }
            }
        }

}

