package com.exmple.remote;

import android.util.Log;
import com.exmple.model.SportsResponse;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.withContext;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository class for fetching sports data from a remote source.
 * This class abstracts the data layer and provides a clean API for fetching sports data
 * which can be used by ViewModel classes.
 */
@Singleton
class SportsRepository @Inject constructor(
    private val apiService: SportsClient // The API service for making network requests
) {
    /**
     * Fetches team statistics from the remote API service.
     * This function makes an asynchronous network call to obtain sports team statistics.
     * If the call is successful and receives a response, it returns the data;
     * otherwise, it handles errors gracefully by logging them and returning null.
     *
     * @return A SportsResponse object containing the fetched data or null if an error occurs.
     */
    suspend fun getTeamStats(): SportsResponse? {
        return withContext(Dispatchers.IO) {
            // Switching to IO dispatcher for network operation
            runCatching {
                apiService.getTeamStats()
            }.fold(
                onSuccess = { response ->
                    // If the response is successful, return the body; otherwise, return null
                    if (response.isSuccessful) {
                        response.body()
                    } else null
                },
                onFailure = { throwable ->
                    // Log the error and return null in case of failure
                    Log.d("SportsRepository", "Failed to fetch team stats: $throwable")
                    null
                }
            )
        }
    }
}
