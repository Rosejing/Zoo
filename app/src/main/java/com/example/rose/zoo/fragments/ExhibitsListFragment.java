package com.example.rose.zoo.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.rose.zoo.R;
import com.example.rose.zoo.activities.ExhibitDetailActivity;
import com.example.rose.zoo.activities.MainActivity;
import com.example.rose.zoo.adapters.ExhibitsAdapter;
import com.example.rose.zoo.models.Animal;
import com.example.rose.zoo.utils.AnimalApiInterface;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rose on 12/28/2016.
 */

public class ExhibitsListFragment extends ListFragment {
    private ExhibitsAdapter mAdapter;

    public static ExhibitsListFragment getInstance(){
        ExhibitsListFragment fragment = new ExhibitsListFragment();

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListShown( false );
        getListView().setPadding(16, 16, 16, 16);
        getListView().setDivider(new ColorDrawable(Color.GRAY));
        getListView().setDividerHeight( 3 );
        getListView().setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        getListView().setClipToPadding( true );

        mAdapter = new ExhibitsAdapter(getActivity(), 0 );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl (getString( R.string.exhibits_feed ))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AnimalApiInterface animalApiInterface = retrofit.create( AnimalApiInterface.class );
        Call<List<Animal>> call = animalApiInterface.getStreams();
        call.enqueue(new Callback<List<Animal>>(){
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if(response.isSuccessful()){
                    List<Animal> animals = response.body();
                    if(  !isAdded()|| animals == null || animals.isEmpty()) {
                        Log.e("Zoo", "retrofit error: "  + "Animal list from URL is empty");
                        return;
                    }
                    for (Animal animal : animals){
                        mAdapter.add(animal);
                    }

                    mAdapter.notifyDataSetChanged();
                    setListAdapter( mAdapter );
                    setListShown( true );
                }
                else{
                    Log.e("Zoo", "retrofit error: "  + "No response from the URL");
                    Log.e("Zoo", "retrofit response code: "  + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Log.e("Zoo", "retrofit error: "  + t.getMessage());
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent( this.getActivity(), ExhibitDetailActivity.class );
        Animal curAnimal = mAdapter.getItem(position);
        //Log.e("Zoo", "current animal is: " + curAnimal.getName());
        intent.putExtra(ExhibitDetailActivity.EXTRA_ANIMAL, curAnimal);

        startActivity( intent );

    }
}
