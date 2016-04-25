package com.example.artists;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class AboutArtist implements Parcelable{
    private int id;
    private String name;
    private String genres;
    private int tracks;
    private int albums;
    private String link;
    private String description;
    private String cover_Small;
    private String cover_Big;

    public AboutArtist(int id, String name, String genres, int tracks, int albums, String link, String description, String cover_Small, String cover_Big) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;
        this.cover_Small = cover_Small;
        this.cover_Big = cover_Big;
    }

    protected AboutArtist(Parcel in) {
        id = in.readInt();
        name = in.readString();
        genres = in.readString();
        tracks = in.readInt();
        albums = in.readInt();
        link = in.readString();
        description = in.readString();
        cover_Small = in.readString();
        cover_Big = in.readString();
    }

    public static final Creator<AboutArtist> CREATOR = new Creator<AboutArtist>() {
        @Override
        public AboutArtist createFromParcel(Parcel in) {
            return new AboutArtist(in);
        }

        @Override
        public AboutArtist[] newArray(int size) {
            return new AboutArtist[size];
        }
    };

    public static AboutArtist ArtistFromJson(JSONObject jsonObject) { // создание объекта AboutArtist из объекта json

        int id = jsonObject.optInt("id", 0);
        String name = jsonObject.optString("name", "");
        String genres = jsonObject.optString("genres", "");
        int tracks = jsonObject.optInt("tracks", 0);
        int albums = jsonObject.optInt("albums", 0);
        String link = jsonObject.optString("link", "");
        String description = jsonObject.optString("description", "");
        String cover_Small = "";
        String cover_Big = "";

        try {
            cover_Small = jsonObject.getJSONObject("cover").optString("small", "");
            cover_Big = jsonObject.getJSONObject("cover").optString("big", "");
        } catch (JSONException e){

        }
        return new AboutArtist(id, name, genres, tracks, albums, link, description, cover_Small, cover_Big);
    }

    public static ArrayList<AboutArtist> listFromJsonArtist(JSONArray jsonArray){ // создание списка AboutArtist

        ArrayList<AboutArtist> aboutArtists = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                AboutArtist aboutArtist = ArtistFromJson(jsonArray.getJSONObject(i));
                if (aboutArtist != null) {
                    aboutArtists.add(aboutArtist);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return aboutArtists;
    }

    public String getName() {
        return name;
    }

    public String getGenres() {
        return genres;
    }

    public String getDescription() {
        return description;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public String getCover_Small() {
        return cover_Small;
    }

    public String getCover_Big() {
        return cover_Big;
    }

    public String getLink() {
        return link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(genres);
        parcel.writeInt(tracks);
        parcel.writeInt(albums);
        parcel.writeString(link);
        parcel.writeString(description);
        parcel.writeString(cover_Big);
        parcel.writeString(cover_Small);

    }
}




