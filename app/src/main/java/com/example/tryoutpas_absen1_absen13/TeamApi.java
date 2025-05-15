package com.example.tryoutpas_absen1_absen13;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.tryoutpas_absen1_absen13.TeamResponse;

public interface TeamApi {
    @GET("search_all_teams.php")
    Call<TeamResponse> getAllTeams(@Query("l") String league);

    @GET//new
    Call<TeamResponse> getTeamsFromUrl(@retrofit2.http.Url String url);//new

}