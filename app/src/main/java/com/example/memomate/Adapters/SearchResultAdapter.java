package com.example.memomate.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memomate.R;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.CustomHolder> {
    private ArrayList<String> searchResults;
    private RecyclerView recyclerViewSearchResult, recyclerViewStudySet;
    public SearchResultAdapter(ArrayList<String> searchResults) {
        this.searchResults = searchResults;
    }
    public SearchResultAdapter(ArrayList<String> searchResults, RecyclerView recyclerViewSearchResult, RecyclerView recyclerViewStudySet) {
        this.searchResults = searchResults;
        this.recyclerViewSearchResult = recyclerViewSearchResult;
        this.recyclerViewStudySet = recyclerViewStudySet;
    }
    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        parent.findViewById(R.id.recyclerview_search_result);
        parent.findViewById(R.id.recyclerview_searched_study_set);
        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.name.setText(searchResults.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewSearchResult.setVisibility(View.GONE);
                recyclerViewStudySet.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    class CustomHolder extends RecyclerView.ViewHolder{
        TextView name;
        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.study_set_name);
        }
    }
}
