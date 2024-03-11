package com.exmple.remote

import android.util.Log
import com.exmple.model.SportsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SportsRepository @Inject constructor(
    private val apiService: SportsClient
) {
    suspend fun getTeamStats(teamNumber: Int): SportsResponse? {
        return withContext(Dispatchers.IO) {
            runCatching {
                apiService.getTeamStats(teamNumber)
            }.fold(
                onSuccess = { response ->
                    if (response.isSuccessful) {
                        response.body()
                    } else null
                },
                onFailure = { throwable ->
                    Log.d("SportsRepository", "Failed to fetch team stats $throwable")
                    null
                }
            )
        }
    }
}
