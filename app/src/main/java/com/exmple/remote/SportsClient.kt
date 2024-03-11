package com.exmple.remote

import com.exmple.model.SportsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SportsClient {
    @GET("search/nfl/searchHandler?fileType=inline&statType=teamStats&season=2020&teamName={teamNumber}")
    suspend fun getTeamStats(@Path("teamNumber") teamNumber: Int): Response<SportsResponse>
}