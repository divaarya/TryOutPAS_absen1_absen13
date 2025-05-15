package com.example.tryoutpas_absen1_absen13;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar pbLoading;
    private TeamAdapter adapter;
    private List<Team> teamList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        pbLoading = view.findViewById(R.id.pbLoading);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TeamAdapter(teamList);
        recyclerView.setAdapter(adapter);

        loadPremierLeagueTeams();

        return view;
    }

    private void loadPremierLeagueTeams() {
        TeamApi api = ApiClient.getClient().create(TeamApi.class);
        api.getAllTeams("English Premier League").enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(Call<TeamResponse> call, Response<TeamResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    teamList.addAll(response.body().getTeams());
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                }
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TeamResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
                pbLoading.setVisibility(View.GONE);
            }
        });
    }
}

