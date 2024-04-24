package com.example.memomate.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.memomate.Activties.StudySetDetailActivity;
import com.example.memomate.Models.StudySet;
import com.example.memomate.R;

import java.util.ArrayList;

public class StudySetPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<StudySet> studySetArrayList;

    public StudySetPagerAdapter() {
    }

    public StudySetPagerAdapter(Context context, ArrayList<StudySet> studySetArrayList) {
        this.context = context;
        this.studySetArrayList = studySetArrayList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_studyset, container, false);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, StudySetDetailActivity.class);
                context.startActivity(i);
                //Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return studySetArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
