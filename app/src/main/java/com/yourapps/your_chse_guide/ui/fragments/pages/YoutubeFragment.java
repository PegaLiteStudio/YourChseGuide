package com.yourapps.your_chse_guide.ui.fragments.pages;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.yourapps.your_chse_guide.R;
import com.yourapps.your_chse_guide.databinding.FragmentYoutubeBinding;
import com.yourapps.your_chse_guide.databinding.VideoThumbnailBinding;
import com.yourapps.your_chse_guide.functions.retrofit.RetrofitClient;
import com.yourapps.your_chse_guide.functions.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class YoutubeFragment extends Fragment {


    FragmentYoutubeBinding binding;

    JSONObject newObject, trendingObject, playlistObject;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentYoutubeBinding.inflate(inflater, container, false);


        try {
            loadData();
            loadSearch();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        return binding.getRoot();
    }

    private void loadSearch() throws JSONException {
        JSONObject search = Utils.data.getJSONObject("Search");
        Iterator<String> keys = search.keys();
        List<String> queryText = new ArrayList<>();
        List<String> query = new ArrayList<>();
        while (keys.hasNext()) {
            String s = keys.next();
            try {
                Object object = search.get(s);

                /* Checks if object is JSONArray */
                if (object instanceof JSONArray) {
                    JSONArray a = search.getJSONArray(s);

                    if (a.length() == 2) {
                        query.add(s);
                        queryText.add(a.getString(0).toLowerCase(Locale.ROOT));
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        binding.searchContainer.setVisibility(View.VISIBLE);
        binding.searchVideo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (!newText.isEmpty()) {
                    newText = newText.toLowerCase(Locale.ROOT);
                    JSONObject object = new JSONObject();
                    for (int i = 0; i < queryText.size(); i++) {
                        if (queryText.get(i).contains(newText) || queryText.get(i).startsWith(newText)) {
                            try {
                                if (!object.has(query.get(i))) {
                                    object.put(query.get(i), search.getJSONArray(query.get(i)).getString(1));
                                }
                                break;
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        for (String s : queryText.get(i).split(" ")) {
                            for (String s2 : newText.split(" ")) {
                                if (s.contains(s2) || s.startsWith(s2) || s.endsWith(s2)) {
                                    try {
                                        if (!object.has(query.get(i))) {
                                            object.put(query.get(i), search.getJSONArray(query.get(i)).getString(1));
                                        }
                                        break;
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                    }
                    try {
                        if (object.length() == 0) {
                            binding.cantFind.setVisibility(View.VISIBLE);
                            binding.videoThumbnailContainerSearch.setVisibility(View.GONE);
                            binding.contentsContainer.setVisibility(View.GONE);
                        } else {
                            binding.cantFind.setVisibility(View.GONE);
                            binding.videoThumbnailContainerSearch.setVisibility(View.VISIBLE);
                            loadView(object, binding.videoThumbnailContainerSearch);
                            binding.contentsContainer.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    binding.contentsContainer.setVisibility(View.VISIBLE);
                    binding.videoThumbnailContainerSearch.setVisibility(View.GONE);
                    binding.cantFind.setVisibility(View.GONE);
                }

                return false;
            }
        });
    }

    private void loadData() throws JSONException {
        JSONObject videos = Utils.data.getJSONObject("Videos");

        newObject = videos.getJSONObject("New");
        trendingObject = videos.getJSONObject("Trending");
        playlistObject = videos.getJSONObject("Playlist");

        loadView(newObject, binding.videoThumbnailContainerNew);
        loadView(trendingObject, binding.videoThumbnailContainerTrending);
        loadView(playlistObject, binding.videoThumbnailContainerPlaylists);

        binding.scrollView.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.INVISIBLE);

        binding.newVideo.setOnClickListener(view -> {
            binding.videoThumbnailContainerNew.setVisibility(View.VISIBLE);
            binding.videoThumbnailContainerTrending.setVisibility(View.GONE);
            binding.videoThumbnailContainerPlaylists.setVisibility(View.GONE);

            binding.trending.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.un_selected_tab));
            binding.newVideo.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.selected_tab));
            binding.playlist.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.un_selected_tab));

        });
        binding.trending.setOnClickListener(view -> {
            binding.videoThumbnailContainerNew.setVisibility(View.GONE);
            binding.videoThumbnailContainerTrending.setVisibility(View.VISIBLE);
            binding.videoThumbnailContainerPlaylists.setVisibility(View.GONE);

            binding.trending.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.selected_tab));
            binding.newVideo.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.un_selected_tab));
            binding.playlist.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.un_selected_tab));

        });
        binding.playlist.setOnClickListener(v -> {
            binding.videoThumbnailContainerNew.setVisibility(View.GONE);
            binding.videoThumbnailContainerTrending.setVisibility(View.GONE);
            binding.videoThumbnailContainerPlaylists.setVisibility(View.VISIBLE);

            binding.trending.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.un_selected_tab));
            binding.newVideo.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.un_selected_tab));
            binding.playlist.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.selected_tab));

        });
    }


    private void openURL(String url) {
        Uri uri = Uri.parse("https://www.youtube.com/" + url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void loadView(JSONObject newJSON, LinearLayout container) throws JSONException {
        if (newJSON != null) {
            Iterator<String> iterator = newJSON.keys();
            while (iterator.hasNext()) {
                VideoThumbnailBinding videoThumbnailBinding = VideoThumbnailBinding.inflate(getLayoutInflater(), binding.videoThumbnailContainerNew, false);
                String key = iterator.next();
                Glide.with(requireActivity()).load(RetrofitClient.BASE_IMAGE_URL + key + newJSON.getString(key)).placeholder(new ColorDrawable(Color.parseColor("#FAFAFA"))).into(videoThumbnailBinding.videoThumbnail);
                videoThumbnailBinding.videoThumbnail.setOnClickListener(v -> {
                    if (container == binding.videoThumbnailContainerPlaylists) {
                        openURL("playlist?list=PL8iq4Zp4XDC" + key);
                        return;
                    }
                    openURL("watch?v=" + key);

                });
                container.addView(videoThumbnailBinding.getRoot());
            }
        }
    }

}