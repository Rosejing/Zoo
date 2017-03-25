package com.example.rose.zoo.fragments;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.rose.zoo.R;
import com.example.rose.zoo.activities.GalleryDetailActivity;
import com.example.rose.zoo.adapters.GalleryImageAdapter;
import com.example.rose.zoo.models.GalleryImage;
import com.example.rose.zoo.utils.GalleryApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rose on 12/28/2016.
 */

public class GalleryFragment extends Fragment implements AdapterView.OnItemClickListener{

    private GridView mGridView;
    private GalleryImageAdapter mAdapter;

    public static GalleryFragment getInstance() {
        GalleryFragment fragment = new GalleryFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_gallery_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGridView = (GridView) view.findViewById(R.id.grid);
        mGridView.setOnItemClickListener( this );
        mGridView.setDrawSelectorOnTop( true );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        mAdapter = new GalleryImageAdapter(getActivity(), 0);
        mGridView.setAdapter( mAdapter );

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(getString(R.string.gallery_feed))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GalleryApiInterface galleryApiInterface = restAdapter.create(GalleryApiInterface.class);
        Call<List<GalleryImage>> call = galleryApiInterface.getStreams();
        call.enqueue(new Callback<List<GalleryImage>>() {
            @Override
            public void onResponse(Call<List<GalleryImage>> call, Response<List<GalleryImage>> response) {
                if (response.isSuccessful()) {
                    List<GalleryImage> galleryImages = response.body();
                    if ( !isAdded()|| galleryImages == null || galleryImages.isEmpty()) {
                        return;
                    }
                    for (GalleryImage galleryImage : galleryImages) {
                        Log.e("Zoo", galleryImage.getThumbnail());
                        mAdapter.add(galleryImage);
                    }

                    mAdapter.notifyDataSetChanged();
                } else {
                    Log.e("Zoo", "retrofit error: " + "No response from the URL");
                    Log.e("Zoo", "retrofit error: " + response.code() + "406 means that there is sth wrong with the URL");
                }
            }

            @Override
            public void onFailure(Call<List<GalleryImage>> call, Throwable t) {
                Log.e("Zoo", "retrofit error "  + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GalleryImage image = (GalleryImage) parent.getItemAtPosition( position );
        Intent intent = new Intent(getActivity(), GalleryDetailActivity.class);
        intent.putExtra(GalleryDetailActivity.EXTRA_IMAGE, image.getImage());
        intent.putExtra(GalleryDetailActivity.EXTRA_CAPTION, image.getCaption());
        startActivity( intent );
    }
}
