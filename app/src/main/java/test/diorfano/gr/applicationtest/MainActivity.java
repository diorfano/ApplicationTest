package test.diorfano.gr.applicationtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	static String apiKey = "52b922cca3a249299aef5baf02c3b9b0";
	static int currentPage = 1;

	static int perPage = 15;
	static int allPages;
	static int allItems;
	static String param = "";

	boolean isloading = false;

	EditText et_searchInput;
	ImageButton ib_searchButton;
	RecyclerView rv_recyclerList;
	ArrayList<FlickrItem> items = new ArrayList<FlickrItem>();
	MyRecyclerAdapter mAdapter;

	private boolean loading = true;
	int pastVisiblesItems, visibleItemCount, totalItemCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		et_searchInput = (EditText) findViewById(R.id.et_searchInput);
		ib_searchButton = (ImageButton) findViewById(R.id.ib_searchButton);
		rv_recyclerList = (RecyclerView) findViewById(R.id.rv_recyclerList);

		final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
		rv_recyclerList.setLayoutManager(mLayoutManager);

		mAdapter = new MyRecyclerAdapter(MainActivity.this, items, R.layout.list_item);
		rv_recyclerList.setAdapter(mAdapter);

		
		ib_searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				param = et_searchInput.getText().toString();

				items.clear();
				getData(param, currentPage);
			}
		});

		et_searchInput.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					param = et_searchInput.getText().toString();

					items.clear();
					getData(param, currentPage);
					handled = true;
				}
				return handled;
			}
		});

		rv_recyclerList.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
			@Override
			public void onLoadMore() {
				if (!isloading) {
					currentPage++;
					getData(param, currentPage);
				}
				isloading = true;
			}
		});

	}

	public void getData(String param, int currentPage) {

		Log.v("", "currentPage is -> " + currentPage);

		new WebPost(getApplicationContext(), apiKey, param, currentPage, perPage) {

			@Override
			public void parseValidJson(JSONObject jMain) throws JSONException, Exception {

				JSONObject jsonResponseData = jMain.getJSONObject("photos");

				allPages = jsonResponseData.getInt("pages");
				allItems = jsonResponseData.getInt("total");
				JSONArray jsonItems = jsonResponseData.getJSONArray("photo");

				Log.v("", "jMain -> " + jMain.toString());

				for (int i = 0; i < jsonItems.length(); i++) {

					JSONObject jsonPhoto = jsonItems.getJSONObject(i);

					String id = jsonPhoto.getString("id");
					String owner = jsonPhoto.getString("owner");
					String secret = jsonPhoto.getString("secret");
					String server = jsonPhoto.getString("server");
					int farm = jsonPhoto.getInt("farm");
					String title = jsonPhoto.getString("title");
					boolean ispublic = (jsonItems.getJSONObject(i).getInt("ispublic") == 1) ? true : false;
					boolean isfriend = (jsonItems.getJSONObject(i).getInt("isfriend") == 1) ? true : false;
					boolean isfamily = (jsonItems.getJSONObject(i).getInt("isfamily") == 1) ? true : false;
					JSONObject Jsondescription = jsonPhoto.getJSONObject("description");
					String description = Jsondescription.getString("_content");

					String imageUrlMedium = "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_z.jpg";
					String imageUrlSmall = "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_s.jpg";

					items.add(new FlickrItem(id, owner, secret, server, farm, title, ispublic, isfriend, isfamily, description, imageUrlMedium, imageUrlSmall));
					mAdapter.notifyDataSetChanged();
					isloading = false;

				}
			}

			@Override
			public void onPostFailed() {

				Toast.makeText(getApplicationContext(), "Loading failed", Toast.LENGTH_SHORT).show();

				isloading = false;
			}
		}.post(false);

	}

	public boolean isIsloading() {
		return isloading;
	}

	public void setIsloading(boolean isloading) {
		this.isloading = isloading;
	}
}