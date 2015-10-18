package com.example.trabinerson.moviefiend;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Used to keep only one instance of the request queue throughout the application.
 */
public class RequestQueueSingleton {
    private static RequestQueueSingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private static String LOG_TAG = RequestQueueSingleton.class.getSimpleName();

    private RequestQueueSingleton() {

        // Make sure we've been initialized
        if (mCtx == null) {
            throw new ExceptionInInitializerError(
                    "RequestQueueSingleton constructor called without having initialized it first");
        }

        // Initialize request queue
        mRequestQueue = getRequestQueue();

        // Initialize image loader
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized void init(Context context) {
        if (context == null) {
            Log.w(LOG_TAG, "Trying to initialize RequestQueueSingleton with null context");
            return;
        }
        Log.i(LOG_TAG, "Initializing RequestQueueSingleton");
        mCtx = context;
    }

    public static synchronized RequestQueueSingleton getInstance() {
        if (mInstance == null) {
            mInstance = new RequestQueueSingleton();
        }
        return mInstance;
    }

    public void cancelAllRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
