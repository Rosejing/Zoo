package com.example.rose.zoo.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rose.zoo.R;
import com.example.rose.zoo.models.Animal;
import com.squareup.picasso.Picasso;

/**
 * Created by Rose on 12/30/2016.
 */

public class ExhibitDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ANIMAL = "extra_animal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibit_detail);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_exhibit_detail);
        //setSupportActionBar(toolbar);

        //if(getSupportActionBar() != null)
           // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Animal animal = getIntent().getExtras().getParcelable(EXTRA_ANIMAL);

        TextView species = (TextView) findViewById(R.id.species);
        TextView description = (TextView) findViewById(R.id.description);
        ImageView image = (ImageView) findViewById(R.id.image);

        species.setText(animal.getSpecies());
        description.setText(animal.getDescription());
        Picasso.with( this ).load(animal.getImage()).into(image);
    }

}
