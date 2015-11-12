package test.diorfano.gr.applicationtest;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class WebPost implements Response.ErrorListener {
	
	
    protected static final String tag = "Debug WebPost";
    private String baseUrl = "";
    //    "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=52b922cca3a249299aef5baf02c3b9b0&tags=cat&format=json&nojsoncallback=1&per_page=4&page=1&extras=description"
    
    protected Context mContext;

    public WebPost(Context context, String apiKey, String param, int page, int perPage) {
        this.mContext = context;
        this.baseUrl = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="+ apiKey + "&tags=" + param + "&format=json&nojsoncallback=1&per_page=" + perPage + "&page=" + page + "&extras=description";
    }

    Response.Listener<JSONObject> getResponseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jMain) {
                handleResponse(jMain);
            }
        };
    }

    private void handleResponse(JSONObject jMain) {

        try {
            parseValidJson(jMain);
        } catch (JSONException e) {
            // TODO handle JSON errorMessage
        } catch (Exception e) {
            // TODO handle errorMessage
            Log.e(tag, e.getMessage() + "");
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        if (error instanceof NoConnectionError) {
            Log.d("VolleyError", "NoConnectionError.......");
        } else if (error instanceof AuthFailureError) {
            Log.d("VolleyError", "AuthFailureError.......");
        } else if (error instanceof ServerError) {
            Log.d("VolleyError", "ServerError.......");
        } else if (error instanceof NetworkError) {
            Log.d("VolleyError", "NetworkError.......");
        } else if (error instanceof ParseError) {
            Log.d("VolleyError", "ParseError......." + error.toString());
            
        } else if (error instanceof TimeoutError) {
            Log.d("VolleyError", "TimeoutError.......");
        }
        onPostFailed();
    }

    public abstract void parseValidJson(JSONObject jMain) throws JSONException, Exception;

    public abstract void onPostFailed();

    public void post(boolean isSerial) {
    	
    	Log.v("","baseUrl -> " + baseUrl);
    	
        CustomRequest cr = new CustomRequest(mContext, baseUrl, getResponseListener(), this);


        if (isSerial) {
            SerialQueue.getSerialRequestQueue(mContext).add(cr);
        } else {
            Volley.newRequestQueue(mContext).add(cr);
        }
    }
}
