package com.southerntemp.thermocouples;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class TcArrayAdapter extends ArrayAdapter<TcColor> {

    private final Context context;
    private List<com.southerntemp.thermocouples.TcColor > objects;
    private int lastPosition;

    public TcArrayAdapter(Context context, int resource,
                          int textViewResourceId, List<TcColor> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        lastPosition = 0;
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
        if (rowView == null) {
            // Inflate layout to view
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.spinner_item_search_result, parent, false);

            // Setup Views within layout within ViewHolder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.type = rowView.findViewById(R.id.tcstvtype);
            viewHolder.standard = rowView.findViewById(R.id.tcstvstandard);
            viewHolder.image = rowView.findViewById(R.id.tcsiv);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();

        // Get TcSet + ids + Standard for layout
        TcColor tcInfo = objects.get(position);

        // Fill views with info
        holder.type.setText(tcInfo.thermocouple.getTypeFormatted());
        holder.standard.setText(tcInfo.standard);

        final String imageKey = String.valueOf(tcInfo.drawable);
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap);
        } else {
            BitmapWorkerTask task = new BitmapWorkerTask(holder.image);
            task.execute(tcInfo.drawable);
        }

        if (position >= lastPosition) {
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.searchcardanimation);
            rowView.setAnimation(anim);
        }

        lastPosition = position;
        return rowView;
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            DetailsFragment.mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        Bitmap bitmap = DetailsFragment.mMemoryCache.get(key);
        if (bitmap == null) {
            Log.i("Cache", "Bitmap null " + key);
        }
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
            inSampleSize = Math.min(heightRatio, widthRatio);
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
            final ImageView imageView = imageViewReference.get();
            int bitmapsize = bitmap.getHeight() * bitmap.getWidth() * 4;
            String bms = (String.valueOf(bitmapsize));
            Log.i("Bitmap Size", bms);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }


}
