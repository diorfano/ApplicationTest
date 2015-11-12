package test.diorfano.gr.applicationtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SecondActivity extends AppCompatActivity {
	
	ImageView iv_flickrItemImage;
	TextView tv_flickrItemTitle;
	TextView tv_flickrItemDescription;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		
		FlickrItem mFlickrItem = (FlickrItem) getIntent().getParcelableExtra("item");
		
		iv_flickrItemImage = (ImageView)findViewById(R.id.iv_flickrItemImage);
		tv_flickrItemTitle = (TextView)findViewById(R.id.tv_flickrItemTitle);
		tv_flickrItemDescription = (TextView)findViewById(R.id.tv_flickrItemDescription);
		
		iv_flickrItemImage.setImageBitmap(null);
		Picasso.with(getApplicationContext()).cancelRequest(iv_flickrItemImage);
		Picasso.with(getApplicationContext()).load(mFlickrItem.getImageUrlMedium()).into(iv_flickrItemImage);
		
		tv_flickrItemTitle.setText(Html.fromHtml(mFlickrItem.getImageTitle()));
		tv_flickrItemDescription.setText(Html.fromHtml(mFlickrItem.getImageDescription()));
		
	}
	
}