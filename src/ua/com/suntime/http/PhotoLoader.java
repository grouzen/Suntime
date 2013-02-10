package ua.com.suntime.http;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PhotoLoader extends HttpConnection {
	
    public static final String SIZE_SMALL = "small";
    public static final String SIZE_BIG   = "big";
    public static final String SIZE_THUMB = "thumb";
    
    private static final String URL = "http://suntime.com.ua/img/content/sight/";
    
	public PhotoLoader(int sightId, String name, String size) {
		this.url = URL + sightId + "/" + size + "_" + name;
	}
	
	public Bitmap download() {
	    Bitmap bitmap = null;
	    HttpResponse response = get();
	    
	    try {
            bitmap = BitmapFactory.decodeStream(new BufferedInputStream(response.getEntity().getContent()));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	    
	    return bitmap;
	}
	
}
