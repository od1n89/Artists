package com.example.artists;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.facebook.drawee.backends.pipeline.Fresco;
import java.io.File;
import java.util.ArrayList;


public class MainActivity extends ListActivity {

    ArrayList<AboutArtist> AAlist;
    MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this); // инициализация библиотеки для работы с изображениеями

        enableHttpResponseCache(); // включаем кеш
        Intent jsonIntent = new Intent(this, GetJsonIntent.class);
        if (CheckNetwork.isInternetAvailable(this)) { // проверка интернета
            startService(jsonIntent);
        }
        else {
            Toast.makeText(this, "нет интернета, проверьте соединение и перезапустите приложение ", Toast.LENGTH_LONG).show();
        }
        IntentFilter intentFilter = new IntentFilter(GetJsonIntent.TRANSACTION_DONE);
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcastReceiver, intentFilter);// запуск слушателя

        LayoutAnimationController controller = AnimationUtils // добавление анимации
                .loadLayoutAnimation(this, R.anim.list_layout_controller);
        getListView().setLayoutAnimation(controller);
    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){ // обработка нажатий элементов списка

        Intent intent = new Intent(MainActivity.this, ArtistInfo.class);
        intent.putExtra("com.example.artists.Name", AAlist.get(position).getName());
        intent.putExtra("com.example.artists.Genres", AAlist.get(position).getGenres());
        intent.putExtra("com.example.artists.Description", AAlist.get(position).getDescription());
        intent.putExtra("com.example.artists.Albums", AAlist.get(position).getAlbums());
        intent.putExtra("com.example.artists.Tracks", AAlist.get(position).getTracks());
        intent.putExtra("com.example.artists.CoverBig", AAlist.get(position).getCover_Big());
        intent.putExtra("com.example.artists.Link", AAlist.get(position).getLink());

        if (CheckNetwork.isInternetAvailable(this)){ // проверка интернета

            startActivity(intent);
        }
        else {
            Toast.makeText(this, "нет интернета, проверьте соединение и перезапустите приложение", Toast.LENGTH_LONG).show();
        }
    }
    private void enableHttpResponseCache() { // создание кэша
        try {
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            File httpCacheDir = new File(getCacheDir(), "http");
            Class.forName("android.net.http.HttpResponseCache")
                    .getMethod("install", File.class, long.class)
                    .invoke(null, httpCacheDir, httpCacheSize);
        } catch (Exception httpResponseCacheNotAvailable) {
            httpResponseCacheNotAvailable.printStackTrace();
        }
    }


    class MyBroadcastReceiver extends BroadcastReceiver { //создание слушателя


        @Override
        public void onReceive(Context context, Intent intent) {
            AAlist = intent.getParcelableArrayListExtra("com.example.artists.AA");
            ArrayAdapter<AboutArtist> adapter = new Adapter(MainActivity.this, AAlist);
            setListAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() { // закрытие слушателя

        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myBroadcastReceiver);
    }

}




