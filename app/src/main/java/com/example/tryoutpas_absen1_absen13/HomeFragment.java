package com.example.tryoutpas_absen1_absen13;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.annotation.NonNull;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private List<Team> teamList = new ArrayList<>();
    private ProgressBar pbLoading;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnPremier = view.findViewById(R.id.btnPremier);
        Button btnLaLiga = view.findViewById(R.id.btnLaLiga);
        pbLoading = view.findViewById(R.id.pbLoading);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TeamAdapter(teamList);
        recyclerView.setAdapter(adapter);

        btnPremier.setOnClickListener(v -> loadTeams("English Premier League"));
        btnLaLiga.setOnClickListener(v -> loadFromUrl("https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?s=Soccer&c=Spain"));

        return view;
    }

    private void loadTeams(String league) {
        pbLoading.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        teamList.clear();
        adapter.notifyDataSetChanged();

        TeamApi api = ApiClient.getClient().create(TeamApi.class);
        api.getAllTeams(league).enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(@NonNull Call<TeamResponse> call, @NonNull Response<TeamResponse> response) {
                pbLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    teamList.addAll(response.body().getTeams());
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TeamResponse> call, @NonNull Throwable t) {
                pbLoading.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Gagal ambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFromUrl(String url) {
        pbLoading.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        teamList.clear();
        adapter.notifyDataSetChanged();

        TeamApi api = ApiClient.getClient().create(TeamApi.class);
        api.getTeamsFromUrl(url).enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(@NonNull Call<TeamResponse> call, @NonNull Response<TeamResponse> response) {
                pbLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    teamList.addAll(response.body().getTeams());
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TeamResponse> call, @NonNull Throwable t) {
                pbLoading.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Gagal ambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}