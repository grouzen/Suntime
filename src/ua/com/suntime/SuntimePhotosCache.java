package ua.com.suntime;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class SuntimePhotosCache extends LruCache<String, Bitmap> {
    
    private final static int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024);
    
    public SuntimePhotosCache() {
        super(maxSize);
    }
    
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }
    
    public void addPhotoToCache(String key, Bitmap photo) {
        if(getPhotoFromCache(key) == null) {
            put(key, photo);
        }
    }
    
    public Bitmap getPhotoFromCache(String key) {
        return get(key);
    }
    
    /*
     * Function for generating key based on id of sight
     * and base name of photo.
     */
    public static String getKey(int id, String name) {
        return String.valueOf(id) + "Suntime" + name;
    }
    
    
}
