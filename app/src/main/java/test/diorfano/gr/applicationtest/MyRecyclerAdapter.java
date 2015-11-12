package test.diorfano.gr.applicationtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

	private List<FlickrItem> flickrItems;
	private int res;
	private Activity ctx;

	public MyRecyclerAdapter(Activity ctx, List<FlickrItem> flickrItems, int res) {
		this.flickrItems = flickrItems;
		this.res = res;
		this.ctx = ctx;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
		return new ViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		
		final FlickrItem item = flickrItems.get(position);
		
		holder.tv_imageTitle.setText(position + " " + item.getImageTitle());
		holder.iv_imageSmall.setImageBitmap(null);
		Picasso.with(ctx).cancelRequest(holder.iv_imageSmall);
		Picasso.with(ctx).load(item.getImageUrlSmall()).into(holder.iv_imageSmall);
		
		holder.ll_listItemLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Log.v("", "get " + position + " is clicked");
				Log.v("", "get " + item.getImageUrlSmall() + " item.getImageUrlSmall()");
				
				FlickrItem mItem = new FlickrItem(item.getImageId(), item.getImageOwner(), item.getImageSecret(), item.getImageServer(), item.getImageFarm(),
						item.getImageTitle(), item.getImageIspublic(), item.getImageIsfriend(), item.getImageIsfamily(), item.getImageDescription(), item
								.getImageUrlMedium(), item.getImageUrlSmall());
				
				Intent mIntent = new Intent(ctx, SecondActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putParcelable("item", mItem);
				mIntent.putExtras(mBundle);
				ctx.startActivity(mIntent);
				ctx.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				
			}
		});
		
		holder.itemView.setTag(item);
	}
	
	@Override
	public int getItemCount() {
		return flickrItems.size();
	}
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ImageView iv_imageSmall;
		public TextView tv_imageTitle;
		public LinearLayout ll_listItemLayout;

		public ViewHolder(View itemView) {
			super(itemView);
			iv_imageSmall = (ImageView) itemView.findViewById(R.id.iv_listItemPreview);
			tv_imageTitle = (TextView) itemView.findViewById(R.id.tv_listItemTitle);
			ll_listItemLayout = (LinearLayout) itemView.findViewById(R.id.ll_listItemLayout);
		}
	}
}