package com.elegion.test.behancer.ui.usersProjects;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.test.behancer.R;
import com.elegion.test.behancer.data.model.project.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladislav Falzan.
 */

public class UserProjectsAdapter extends RecyclerView.Adapter<UserProjectsHolder> {

    @NonNull
    private final List<Project> mProjects = new ArrayList<>();

    @NonNull
    @Override
    public UserProjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.li_projects, parent, false);
        return new UserProjectsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProjectsHolder holder, int position) {
        Project project = mProjects.get(position);
        holder.bind(project);
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }

    public void addData(List<Project> data, boolean isRefreshed) {
        if (isRefreshed) {
            mProjects.clear();
        }

        // TODO: 09.04.2018 ДЗ обработать кейс с data.size == 0 || data == null

        mProjects.addAll(data);
        notifyDataSetChanged();
    }
}
