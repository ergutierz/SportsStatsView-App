package com.exmple.remote;

import com.exmple.model.SportsResponse;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit interface for making API calls to fetch sports data.
 * This interface defines the HTTP operations that can be executed.
 */
interface SportsClient {

    /**
     * Fetches team statistics for a specific team and season.
     * This method makes a GET request to the specified endpoint to retrieve sports team statistics.
     * The API response is wrapped in a Retrofit Response object which contains the data or any errors.
     *
     * @return A Retrofit Response object containing a SportsResponse or error information.
     */
    @GET("search/nfl/searchHandler?fileType=inline&statType=teamStats&season=2020&teamName=26")
    suspend fun getTeamStats(): Response<SportsResponse>;
}
