package ua.com.suntime;

import ua.com.suntime.http.PhotoLoader;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class SuntimePhotosWorker extends
        AsyncTask<PhotoLoader, Void, PhotoLoader> {

    @Override
    protected PhotoLoader doInBackground(PhotoLoader... loaders) {
        loaders[0].download();
        return loaders[0];
    }

}
