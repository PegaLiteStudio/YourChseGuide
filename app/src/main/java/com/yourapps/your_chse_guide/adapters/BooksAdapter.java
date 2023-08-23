package com.yourapps.your_chse_guide.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yourapps.your_chse_guide.databinding.SubjectLayoutBinding;
import com.yourapps.your_chse_guide.models.SelectModel;
import com.yourapps.your_chse_guide.ui.activity.SelectChapterActivity;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.SubjectsViewHolder> {
    Context context;

    String stream, subject;

    List<SelectModel> selectModelList;

    public BooksAdapter(Context context, String stream, String subject, List<SelectModel> selectModelList) {
        this.context = context;
        this.stream = stream;
        this.subject = subject;
        this.selectModelList = selectModelList;
    }

    @NonNull
    @Override
    public SubjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubjectsViewHolder(SubjectLayoutBinding.inflate(((Activity) context).getLayoutInflater(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SelectModel select = selectModelList.get(position);
        holder.binding.subjectName.setText(select.getName());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, SelectChapterActivity.class);
            intent.putExtra("book", select.getName());
            intent.putExtra("subject", subject);
            intent.putExtra("stream", stream);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return selectModelList.size();
    }

    public static class SubjectsViewHolder extends RecyclerView.ViewHolder {

        SubjectLayoutBinding binding;

        public SubjectsViewHolder(@NonNull SubjectLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
