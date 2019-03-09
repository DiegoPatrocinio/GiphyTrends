package com.diegopatrocinio.giphytrends.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.giphy.sdk.core.models.Media;
import com.diegopatrocinio.giphytrends.R;
import com.diegopatrocinio.giphytrends.adapters.MediaAdapter;
import com.diegopatrocinio.giphytrends.storage.DatabaseManager;
import com.diegopatrocinio.giphytrends.storage.Database;

import android.net.Uri;

public class GifDetailActivity extends AppCompatActivity {

    static public final String mediaExtra = "media_extra";

    private DatabaseManager databaseManager;
    private Menu menu;
    private ImageView mImageView;
    private Button mShareButton;

    private Media mMedia;
    private boolean isFavorite;

    public static Intent createIntent(Context context, Media media) {
        Intent previewIntent = new Intent(context, GifDetailActivity.class);
        previewIntent.putExtra(GifDetailActivity.mediaExtra, media);
        return previewIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_preview);

        databaseManager = new DatabaseManager(Database.getDatabase(getApplicationContext()));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mImageView =  findViewById(R.id.image_view);
        mShareButton = findViewById(R.id.share_button);

        Intent intent = getIntent();

        if (intent != null) {
            mMedia = intent.getParcelableExtra(GifDetailActivity.mediaExtra);
        }

        if (mMedia != null) {
            setImage(mMedia);
        }

        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = MediaAdapter.getImageUrlFrom(mMedia);

                if (url != null) {
                    Uri uri = Uri.parse(url);

                    if (uri != null) {
                        Intent shareIntent = ShareCompat.IntentBuilder.from(GifDetailActivity.this)
                                .setType("image/gif")
                                .setStream(uri)
                                .createChooserIntent()
                                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        startActivity(shareIntent);
                    }
                }
            }
        });
    }

    protected void setImage(Media media) {
        String url = MediaAdapter.getImageUrlFrom(media);

        if (url != null) {
            Glide.with(mImageView)
                    .load(url)
                    .into(mImageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gif_preview, this.menu = menu);

        if (this.isFavorite = databaseManager.isStoredGif(mMedia)) {
            tintMenuIcon(true);
        }
        else {
            tintMenuIcon(false);
        }
        return true;
    }

    public void tintMenuIcon(boolean useItemColor) {
        MenuItem menuItem = menu.findItem(R.id.action_favorite);
        int color = android.R.color.background_light;

        if (useItemColor) {
            color = android.R.color.holo_red_dark;
        }

        if (menuItem != null) {
            Drawable normalDrawable = menuItem.getIcon();
            Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
            DrawableCompat.setTint(wrapDrawable, getBaseContext().getResources().getColor(color));

            menuItem.setIcon(wrapDrawable);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_favorite:
                toggleFavorite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleFavorite() {
        this.isFavorite = !this.isFavorite;

        if (this.isFavorite) {
            databaseManager.insertStoredGif(mMedia);
            tintMenuIcon(true);
        }
        else {
            databaseManager.removeStoredGif(mMedia);
            tintMenuIcon(false);
        }

        Toast.makeText(this,
                isFavorite ? "Saved as favorite" : "Removed from favorites",
                Toast.LENGTH_LONG).show();
    }
}