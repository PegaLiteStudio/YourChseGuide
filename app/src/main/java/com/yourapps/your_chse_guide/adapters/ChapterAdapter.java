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
import com.yourapps.your_chse_guide.ui.activity.PdfActivity;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.SubjectsViewHolder> {
    Context context;

    String stream, subject, book;

    List<SubjectsModel> subjectsModelList;

    public ChapterAdapter(String stream, String subject, String book, List<SubjectsModel> subjectsModelList) {
        this.stream = stream;
        this.subject = subject;
        this.book = book;
        this.subjectsModelList = subjectsModelList;
    }

    @NonNull
    @Override
    public SubjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SubjectsViewHolder(SubjectLayoutBinding.inflate(((Activity) context).getLayoutInflater(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SubjectsModel model = subjectsModelList.get(position);
        holder.binding.subjectName.setText(model.getChapterName());
        String finalPath = stream.replaceAll(" ", "-") + "/" + subject.replaceAll(" ", "-") + "/" + book.replaceAll(" ", "-") + "/" + model.getChapterName().replaceAll(" ", "-");
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, PdfActivity.class);
            intent.putExtra("name", model.getChapterName());
            intent.putExtra("path", finalPath);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return subjectsModelList.size();
    }

    public static class SubjectsViewHolder extends RecyclerView.ViewHolder {

        SubjectLayoutBinding binding;

        public SubjectsViewHolder(@NonNull SubjectLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
