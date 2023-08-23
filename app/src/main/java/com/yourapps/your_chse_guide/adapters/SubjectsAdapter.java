package com.yourapps.your_chse_guide.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yourapps.your_chse_guide.databinding.SubjectLayoutBinding;
import com.yourapps.your_chse_guide.models.SubjectsModel;
import com.yourapps.your_chse_guide.ui.activity.SelectBookActivity;

import java.util.List;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectsViewHolder> {
    Context context;
    List<SubjectsModel> subjects;

    String subjectName;

    public SubjectsAdapter(Context context, List<SubjectsModel> subjects, String subjectName) {
        this.context = context;
        this.subjects = subjects;
        this.subjectName = subjectName;
    }

    @NonNull
    @Override
    public SubjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubjectsViewHolder(SubjectLayoutBinding.inflate(((Activity) context).getLayoutInflater(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        SubjectsModel model = subjects.get(position);
        holder.binding.subjectName.setText(model.getChapterName());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, SelectBookActivity.class);
            intent.putExtra("stream", subjectName);
            intent.putExtra("subject", subjects.get(position).getChapterName());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class SubjectsViewHolder extends RecyclerView.ViewHolder {

        SubjectLayoutBinding binding;

        public SubjectsViewHolder(@NonNull SubjectLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
