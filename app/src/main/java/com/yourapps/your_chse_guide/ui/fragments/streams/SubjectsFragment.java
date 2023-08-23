package com.yourapps.your_chse_guide.ui.fragments.streams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yourapps.your_chse_guide.adapters.SubjectsAdapter;
import com.yourapps.your_chse_guide.databinding.FragmentSubjectsBinding;
import com.yourapps.your_chse_guide.functions.utils.Utils;
import com.yourapps.your_chse_guide.models.SubjectsModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubjectsFragment extends Fragment {

    List<SubjectsModel> subjectsModelList = new ArrayList<>();

    FragmentSubjectsBinding binding;

    String[] streams = {"Arts", "Commerce", "Science", "Optional"};

    String STREAM;

    public SubjectsFragment(int position) {
        STREAM = streams[position];
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSubjectsBinding.inflate(inflater, container, false);


        try {
            Iterator<String> subjects = Utils.data.getJSONObject(STREAM).keys();
            while (subjects.hasNext()) {
                subjectsModelList.add(new SubjectsModel(subjects.next()));
            }
            SubjectsAdapter adapter = new SubjectsAdapter(requireContext(), subjectsModelList, STREAM);
            binding.recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        return binding.getRoot();
    }
}