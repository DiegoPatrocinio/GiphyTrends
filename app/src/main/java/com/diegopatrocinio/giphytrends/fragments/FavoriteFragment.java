package com.diegopatrocinio.giphytrends.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diegopatrocinio.giphytrends.storage.Database;
import com.giphy.sdk.core.models.Media;
import com.diegopatrocinio.giphytrends.R;
import com.diegopatrocinio.giphytrends.activities.GifDetailActivity;
import com.diegopatrocinio.giphytrends.adapters.MediaAdapter;
import com.diegopatrocinio.giphytrends.storage.DatabaseManager;

public class FavoriteFragment extends Fragment {

    private DatabaseManager databaseManager;
    private RecyclerView mRecyclerView;
    private MediaAdapter mAdapter;

    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseManager = new DatabaseManager(Database.getDatabase(getContext()));
        mAdapter = new MediaAdapter();
        mAdapter.setOnItemClickListener(new MediaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Media media = mAdapter.getMedia(position);
                Intent intent = GifDetailActivity.createIntent(getContext(), media);
                startActivity(intent);
            }
        });

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(MediaAdapter.getViewItemDecoration());
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.refresh(databaseManager.getAllStoredGif());
    }
}
