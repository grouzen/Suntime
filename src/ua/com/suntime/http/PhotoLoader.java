package ua.com.suntime.http;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class PhotoLoader extends HttpConnection {

    public static final String SIZE_SMALL = "small";
    public static final String SIZE_BIG = "big";
    public static final String SIZE_THUMB = "thumb";

    private static final String URL = "http://suntime.com.ua/img/content/sight/";

    public Bitmap bitmap = null;
    public final ImageView photoView;
    public final int sightId;
    public final String photoName;

    public PhotoLoader(int sightId, String photoName, ImageView photoView,
            String size) {
        this.url = URL + sightId + "/" + size + "_" + photoName;
        
        this.photoView = photoView;
        this.sightId = sightId;
        this.photoName = photoName;
    }

    public Bitmap download() {
        HttpResponse response = get();

        try {
            bitmap = BitmapFactory.decodeStream(new BufferedInputStream(
                    response.getEntity().getContent()));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public void applyPhoto() {
        if (bitmap != null) {
            photoView.setImageBitmap(bitmap);
        }
    }

}
