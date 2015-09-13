package com.pankaj.zoomcars;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

//Add name attribute in application tag of manifest
public class ZoomCarsApplication extends Application {
    
   private static ZoomCarsApplication sInstance;
   private RequestQueue mRequestQueue;
   private ImageLoader mImageLoader;
    
   public static ZoomCarsApplication getInstance(){
       return sInstance;
   }

   @Override
   public void onCreate() {
       super.onCreate();
       sInstance = this;
        
       mRequestQueue = Volley.newRequestQueue(this);
       mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            
           private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
    
           public Bitmap getBitmap(String url) {
               return mCache.get(url);
           }

			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				mCache.put(url, bitmap);
			}

       });
   }
    
   public RequestQueue getRequestQueue(){
       return mRequestQueue;
   }
 
   public ImageLoader getImageLoader(){
       return mImageLoader;
   }   
}
