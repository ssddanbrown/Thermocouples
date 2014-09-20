package com.southerntemp.thermocouples;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TcArrayAdapter<TcSet> extends ArrayAdapter<TcSet> {
	
	private final Context context;
	private final List<TcSet> objects;

	public TcArrayAdapter(Context context, int resource,
			int textViewResourceId, List<TcSet> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.objects = objects;
	}
	
  static class ViewHolder {
	    public TextView type;
	    public TextView standard;
	    public ImageView image;
	  }
	
	@Override
	  public View getView(final int position, View convertView, final ViewGroup parent) {
		View rowView = convertView;
		if(rowView == null){
			//Inflate layout to view
		    LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    rowView = inflater.inflate(R.layout.tcsearchlistitem, parent, false);
		  //Setup Views within layout within viewholder
		    ViewHolder viewHolder = new ViewHolder();
		    viewHolder.type = (TextView) rowView.findViewById(R.id.tcstvtype);
			viewHolder.standard = (TextView) rowView.findViewById(R.id.tcstvstandard);
			viewHolder.image = (ImageView) rowView.findViewById(R.id.tcsiv);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
	    
	    //Get thermocouple information from xml rescources
	    String[] thermoCouples = context.getResources().getStringArray(R.array.tctitles);
	    //Get TcSet + ids + Standard for layout
		List<com.southerntemp.thermocouples.TcSet> newlist = ThermocoupleSearch.filteredList;
	    com.southerntemp.thermocouples.TcSet tcInfo = newlist.get(position);
	    int tcid = tcInfo.getId();
	    String standard = tcInfo.getStd();
	    
	    //Fill views with info
	    holder.type.setText(thermoCouples[tcid]);
	       //set font
	    holder.standard.setText(standard);
	    int[] stda = returnStandardImageArray(standard);

	    final String imageKey = String.valueOf(stda[tcid]);
	    final Bitmap bitmap = getBitmapFromMemCache(imageKey);
	    if (bitmap != null) {
	        holder.image.setImageBitmap(bitmap);
	    } else {
	        BitmapWorkerTask task = new BitmapWorkerTask(holder.image);
	        task.execute(stda[tcid]);
	    }
	    
	    Animation anim = AnimationUtils.loadAnimation(context, R.anim.searchcardanimation);
	    rowView.setAnimation(anim);
	    
	    return rowView;
	  }
	public int[] returnStandardImageArray(String standardinput){
	    
	    if(standardinput.equals("IEC")){
	    	int[] stda = {0,R.drawable.iece,R.drawable.iecj,R.drawable.ieck,R.drawable.iecn,R.drawable.iecu,R.drawable.iecu,R.drawable.iect,R.drawable.iecu,R.drawable.iecv};
	    	return stda;
	    } else if (standardinput.equals("BS")){
	    	int[] stda = {0,R.drawable.bse,R.drawable.bsj,R.drawable.bsk,R.drawable.bsn,R.drawable.bsu,R.drawable.bsu,R.drawable.bst,R.drawable.bsu,R.drawable.bsv};
	    	return stda;
	    } else if (standardinput.equals("ANSI")){
	    	int[] stda = {R.drawable.ansib,R.drawable.ansie,R.drawable.ansij,R.drawable.ansik,R.drawable.ansin,R.drawable.ansiu,R.drawable.ansiu,R.drawable.ansit,R.drawable.ansiu,R.drawable.ansiv};
	    	return stda;
	    } else if (standardinput.equals("NFE")){
	    	int[] stda = {R.drawable.nfeb,R.drawable.nfee,R.drawable.nfej,R.drawable.nfek,0,R.drawable.nfeu,R.drawable.nfeu,R.drawable.nfet,R.drawable.nfeu,R.drawable.nfev};
	    	return stda;
	    } else if (standardinput.equals("DIN")){
	    	int[] stda = {R.drawable.dinb,R.drawable.dine,R.drawable.dinj,R.drawable.dink,0,R.drawable.dinu,R.drawable.dinu,R.drawable.dint,R.drawable.dinu,R.drawable.dinv};
	    	return stda;
	    } else{
	    	int[] stda = {R.drawable.jisb,R.drawable.jise,R.drawable.jisj,R.drawable.jisk,0,R.drawable.jisu,R.drawable.jisu,R.drawable.jist,R.drawable.jisu,R.drawable.jisv};
	    	return stda;
	    }
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	        TcHolder.mMemoryCache.put(key, bitmap);
	    }
	}

	public Bitmap getBitmapFromMemCache(String key) {
		Bitmap bitmap = TcHolder.mMemoryCache.get(key);
		if (bitmap == null){Log.i("Cache","Bitmpa null " + key);}
	    return bitmap;
	}
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and width
        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);

        // Choose the smallest ratio as inSampleSize value, this will guarantee
        // a final image with both dimensions larger than or equal to the
        // requested height and width.
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    return inSampleSize;
}
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
		 private final WeakReference<ImageView> imageViewReference;
		
	    public BitmapWorkerTask(ImageView specImg) {
	    	imageViewReference = new WeakReference<ImageView>(specImg);
		}

		// Decode image in background.
		@Override
	    protected Bitmap doInBackground(Integer... params) {
			int tcarray = params[0];
			String tctest = (String.valueOf(tcarray));
	        final Bitmap bitmap = decodeSampledBitmapFromResource(
	        		context.getResources(), tcarray, 100, 100);
	        addBitmapToMemoryCache(tctest, bitmap);
	        return bitmap;
		}
	    @Override
	    protected void onPostExecute(Bitmap bitmap) {
	        if (imageViewReference != null && bitmap != null) {
	            final ImageView imageView = imageViewReference.get();
	            int bitmapsize = bitmap.getHeight() * bitmap.getWidth() * 4;
	            String bms = (String.valueOf(bitmapsize));
	            Log.i("Bitmap Size", bms);
	            if (imageView != null && bitmap != null) {
	                imageView.setImageBitmap(bitmap);
	            }
	        }
	        else {
	        	return;
	        }
	        
	    }
	}
	


}
