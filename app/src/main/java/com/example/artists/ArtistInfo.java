package com.example.artists;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;



public class ArtistInfo extends Activity {
    String artistName;
    String artistGenres;
    String artistDescription;
    int artistAlbums;
    int artistTracks;
    String artistCoverBig;
    String artistLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_info);


        artistName = getIntent().getExtras().getString("com.example.artists.Name");
        artistGenres = getIntent().getExtras().getString("com.example.artists.Genres");
        artistDescription = getIntent().getExtras().getString("com.example.artists.Description");
        artistAlbums = getIntent().getExtras().getInt("com.example.artists.Albums");
        artistTracks = getIntent().getExtras().getInt("com.example.artists.Tracks");
        artistCoverBig = getIntent().getExtras().getString("com.example.artists.CoverBig");
        artistLink = getIntent().getExtras().getString("com.example.artists.Link");

        setTitle(artistName);

        TextView genreTextView = (TextView)findViewById(R.id.genreTextView);
        genreTextView.setText(artistGenres.replaceAll("\\[|\\]|\"", "").replaceAll(",", ", "));

        TextView descriptionTextView = (TextView) findViewById(R.id.DescriptionTextView);
        descriptionTextView.setText(artistDescription);

        TextView albumsTextView = (TextView) findViewById(R.id.AlbumsTextView);
        albumsTextView.setText(artistAlbums + " альбомов · " + artistTracks + " песни");

        SimpleDraweeView coverBigImageView = (SimpleDraweeView) findViewById(R.id.coverSmallImageView);
        coverBigImageView.setImageURI(Uri.parse(artistCoverBig));

        TextView linkTextView = (TextView) findViewById(R.id.LinktextView);
        linkTextView.setText(artistLink);

    }

    public void LinkOnClick(View view) { // делаем ссылку кликабельной
        Uri uri = Uri.parse(artistLink);
        Intent browser = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(browser);
    }
}
