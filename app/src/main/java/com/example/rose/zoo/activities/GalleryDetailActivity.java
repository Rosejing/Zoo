package com.example.rose.zoo.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rose.zoo.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Rose on 01/02/2017.
 */

public class GalleryDetailActivity extends AppCompatActivity {
    public static final String EXTRA_CAPTION = "extra_caption";
    public static final String EXTRA_IMAGE = "extra_image";

    TextView mCaptionTextView;
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        mCaptionTextView = (TextView) findViewById(R.id.caption);
        mImageView = (ImageView) findViewById(R.id.image);

        if( getIntent() != null || getIntent().getExtras() != null){
            if(getIntent().getExtras().containsKey(EXTRA_IMAGE)) {
                Picasso.with( this ).load(getIntent().getExtras().getString(EXTRA_IMAGE)).into(mImageView);
            }

            if(getIntent().getExtras().containsKey(EXTRA_CAPTION)) {
                mCaptionTextView.setText(getIntent().getExtras().getString(EXTRA_CAPTION));
            }
        }
    }
}
