package test.diorfano.gr.applicationtest;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

@SuppressWarnings("deprecation")
public class SerialQueue {
    private static final int MAX_CACHE_SIZE = 2097152; //2 MB
    private static final int MAX_SERIAL_THREAD_POOL_SIZE = 1;
    private static final int MAX_MULTI_THREAD_POOL_SIZE = 4;
    private static RequestQueue serialRequestQueue;

    /**
     * Use to fetch the serial request queue
     */
    public static RequestQueue getSerialRequestQueue(Context context) {
        if (serialRequestQueue == null) {
            serialRequestQueue = prepareSerialRequestQueue(context);
            serialRequestQueue.start();
        }
        return serialRequestQueue;
    }

    private static RequestQueue prepareSerialRequestQueue(Context context) {
        Cache cache = new DiskBasedCache(context.getCacheDir(), MAX_CACHE_SIZE);
        Network network = getNetwork();
        return new RequestQueue(cache, network, MAX_SERIAL_THREAD_POOL_SIZE);
    }

    private static Network getNetwork() {
        HttpStack stack;
        String userAgent = "volley/0";
        stack = new HurlStack();

        return new BasicNetwork(stack);
    }
}