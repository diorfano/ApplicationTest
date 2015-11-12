package test.diorfano.gr.applicationtest;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class FlickrItem implements Parcelable {
	private String imageId;
	private String imageOwner;
	private String imageSecret;
	private String imageServer;
	private int imageFarm;
	private String imageTitle;
	private boolean imageIspublic;
	private boolean imageIsfriend;
	private boolean imageIsfamily;
	private String imageDescription;
	private String imageUrlMedium;
	private String imageUrlSmall;

	public FlickrItem(String imageId, String imageOwner, String imageSecret, String imageServer, int imageFarm, String imageTitle, boolean imageIspublic,
			boolean imageIsfriend, boolean imageIsfamily, String imageDescription, String imageUrlMedium, String imageUrlSmall) {
		this.setImageId(imageId);
		this.setImageOwner(imageOwner);
		this.setImageSecret(imageSecret);
		this.setImageServer(imageServer);
		this.setImageFarm(imageFarm);
		this.setImageTitle(imageTitle);
		this.setImageIspublic(imageIspublic);
		this.setImageIsfriend(imageIsfriend);
		this.setImageIsfamily(imageIsfamily);
		this.setImageDescription(imageDescription);
		this.setImageUrlMedium(imageUrlMedium);
		this.setImageUrlSmall(imageUrlSmall);
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageOwner() {
		return imageOwner;
	}

	public void setImageOwner(String imageOwner) {
		this.imageOwner = imageOwner;
	}

	public String getImageSecret() {
		return imageSecret;
	}

	public void setImageSecret(String imageSecret) {
		this.imageSecret = imageSecret;
	}

	public String getImageServer() {
		return imageServer;
	}

	public void setImageServer(String imageServer) {
		this.imageServer = imageServer;
	}

	public int getImageFarm() {
		return imageFarm;
	}

	public void setImageFarm(int imageFarm) {
		this.imageFarm = imageFarm;
	}

	public boolean getImageIspublic() {
		return imageIspublic;
	}

	public void setImageIspublic(boolean imageIspublic) {
		this.imageIspublic = imageIspublic;
	}

	public boolean getImageIsfriend() {
		return imageIsfriend;
	}

	public void setImageIsfriend(boolean imageIsfriend) {
		this.imageIsfriend = imageIsfriend;
	}

	public boolean getImageIsfamily() {
		return imageIsfamily;
	}

	public void setImageIsfamily(boolean imageIsfamily) {
		this.imageIsfamily = imageIsfamily;
	}

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	public String getImageUrlMedium() {
		return imageUrlMedium;
	}

	public void setImageUrlMedium(String imageUrlMedium) {
		this.imageUrlMedium = imageUrlMedium;
	}

	public String getImageUrlSmall() {
		return imageUrlSmall;
	}

	public void setImageUrlSmall(String imageUrlSmall) {
		this.imageUrlSmall = imageUrlSmall;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	public static final Creator<FlickrItem> CREATOR = new Creator<FlickrItem>() {
		public FlickrItem createFromParcel(Parcel source) {
			FlickrItem mFlickrItem = new FlickrItem(source.readString(), source.readString(), source.readString(), source.readString(), source.readInt(),
					source.readString(), source.readByte() != 0, source.readByte() != 0, source.readByte() != 0, source.readString(), source.readString(),
					source.readString());

			return mFlickrItem;
		}

		@Override
		public FlickrItem[] newArray(int size) {
			return new FlickrItem[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(imageId);
		dest.writeString(imageOwner);
		dest.writeString(imageSecret);
		dest.writeString(imageServer);
		dest.writeInt(imageFarm);
		dest.writeString(imageTitle);
		dest.writeInt((Boolean) imageIspublic ? 1 : 0);
		dest.writeInt((Boolean) imageIsfriend ? 1 : 0);
		dest.writeInt((Boolean) imageIsfamily ? 1 : 0);
		dest.writeString(imageDescription);
		dest.writeString(imageUrlMedium);
		dest.writeString(imageUrlSmall);
		Log.v("","imageUrlSmall -> "+imageUrlSmall);

	}

}