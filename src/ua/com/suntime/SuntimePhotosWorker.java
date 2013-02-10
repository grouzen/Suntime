package ua.com.suntime;

import ua.com.suntime.http.PhotoLoader;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class SuntimePhotosWorker extends
        AsyncTask<PhotoLoader, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(PhotoLoader... loaders) {
        return loaders[0].download();
    }

}
