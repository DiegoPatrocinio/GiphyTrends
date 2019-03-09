package com.diegopatrocinio.giphytrends.fragments;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

class InfiniteScrollListener extends RecyclerView.OnScrollListener {

    private boolean isLoading, hasMorePages, isRefreshing;
    private int pageNumber = 0, pastVisibleItems;
    private int pageLimit = 20;
    private RefreshList refreshList;

    InfiniteScrollListener(RefreshList refreshList) {
        this.isLoading = false;
        this.hasMorePages = true;
        this.refreshList = refreshList;
    }

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

        int visibleItemCount = manager.getChildCount();
        int totalItemCount = manager.getItemCount();
        int[] firstVisibleItems = manager.findFirstVisibleItemPositions(null);

        if (firstVisibleItems != null && firstVisibleItems.length > 0) {
            pastVisibleItems = firstVisibleItems[0];
        }

        if ((visibleItemCount + pastVisibleItems) >= totalItemCount && !isLoading && pageNumber <= pageLimit) {
            isLoading = true;
            if (hasMorePages && !isRefreshing) {
                isRefreshing = true;
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        refreshList.onRefresh(pageNumber);
                    }
                }, 200);
            }
        }
        else {
            isLoading = false;
        }
    }

    public void notifyNoMorePages() {
        this.hasMorePages = false;
    }

    public void notifyMorePages() {
        isRefreshing = false;
        pageNumber++;
    }

    public void reset() {
        isRefreshing = false;
        isLoading = false;
        pageNumber = 0;
    }

    interface RefreshList {
        void onRefresh(int pageNumber);
    }
}