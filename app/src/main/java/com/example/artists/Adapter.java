package com.example.artists;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;



public class Adapter extends ArrayAdapter<AboutArtist> {

    public Adapter(Context context, ArrayList<AboutArtist> aboutArtistArrayList) {
        super(context, R.layout.adapter, aboutArtistArrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AboutArtist aboutArtist = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter, null);
        }

        ((TextView) convertView.findViewById(R.id.nameTextView)).setText(aboutArtist.getName());
        ((TextView) convertView.findViewById(R.id.genreTextView)).setText(aboutArtist.getGenres().replaceAll("\\[|\\]|\"", "").replaceAll(",", ", "));//удаление лишних жлементов из строчки
        ((TextView)convertView.findViewById(R.id.AlbumTextView)).setText(aboutArtist.getAlbums() + " альбомов " + aboutArtist.getTracks() + " песен");
        ((SimpleDraweeView) convertView.findViewById(R.id.coverSmallImageView)).setImageURI(Uri.parse(aboutArtist.getCover_Small()));

        return convertView;
    }
}
