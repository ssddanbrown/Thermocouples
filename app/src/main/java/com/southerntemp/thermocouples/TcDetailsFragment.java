package com.southerntemp.thermocouples;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class TcDetailsFragment extends Fragment {

    ImageView tcImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment, container, false);
        //Array Position is received here
        Bundle bundle=getArguments();
        int id = bundle.getInt("ID");

        //Thermocouple details Arrays
        String[] thermoCouples = getResources().getStringArray(R.array.tctitles);
        String[] positiveleg = getResources().getStringArray(R.array.positiveleg);
        String[] negativeleg = getResources().getStringArray(R.array.negativeleg);
        String[] temprange = getResources().getStringArray(R.array.temprange);
        String[] compensates = getResources().getStringArray(R.array.compcable);
        int[] ieccols = {0,R.drawable.iece,R.drawable.iecj,R.drawable.ieck,R.drawable.iecn,R.drawable.iecu,R.drawable.iecu,R.drawable.iect,R.drawable.iecu,R.drawable.iecv};
        int[] bscols = {0,R.drawable.bse,R.drawable.bsj,R.drawable.bsk,R.drawable.bsn,R.drawable.bsu,R.drawable.bsu,R.drawable.bst,R.drawable.bsu,R.drawable.bsv};
        int[] ansicols = {R.drawable.ansib,R.drawable.ansie,R.drawable.ansij,R.drawable.ansik,R.drawable.ansin,R.drawable.ansiu,R.drawable.ansiu,R.drawable.ansit,R.drawable.ansiu,R.drawable.ansiv};
        int[] nfecols = {R.drawable.nfeb,R.drawable.nfee,R.drawable.nfej,R.drawable.nfek,0,R.drawable.nfeu,R.drawable.nfeu,R.drawable.nfet,R.drawable.nfeu,R.drawable.nfev};
        int[] dincols = {R.drawable.dinb,R.drawable.dine,R.drawable.dinj,R.drawable.dink,0,R.drawable.dinu,R.drawable.dinu,R.drawable.dint,R.drawable.dinu,R.drawable.dinv};
        int[] jiscols = {R.drawable.jisb,R.drawable.jise,R.drawable.jisj,R.drawable.jisk,0,R.drawable.jisu,R.drawable.jisu,R.drawable.jist,R.drawable.jisu,R.drawable.jisv};
        int[][] drawableArray = {ieccols, bscols, ansicols, nfecols, dincols, jiscols};

        //Title is written to textview
        TextView detailTitle = (TextView)v.findViewById(R.id.tcTitle);
        detailTitle.setText(thermoCouples[id] + " Thermocouple");
        detailTitle.setTypeface(TcHolder.robotoSlab);

        //Tab setup
        RadioButton[] rbArray = {
                (RadioButton)v.findViewById(R.id.tcDetailRBiec),
                (RadioButton)v.findViewById(R.id.tcDetailRBbs),
                (RadioButton)v.findViewById(R.id.tcDetailRBansi),
                (RadioButton)v.findViewById(R.id.tcDetailRBnfe),
                (RadioButton)v.findViewById(R.id.tcDetailRBdin),
                (RadioButton)v.findViewById(R.id.tcDetailRBjis)};
        tcImage = (ImageView)v.findViewById(R.id.tcDetailImageView);
        boolean clicked = false;
        for (int i = 0; i<rbArray.length; i++){
            if (drawableArray[i][id] != 0){
                setupTab(rbArray[i], drawableArray[i][id]);
                if (!clicked)rbArray[i].performClick(); clicked=true;
            }
        }

        //Thermocouple text is written to views
        ((TextView)v.findViewById(R.id.tcPositiveLeg)).setText("+ Metal: " + positiveleg[id]);
        ((TextView)v.findViewById(R.id.tcNegativeLeg)).setText("- Metal: " + negativeleg[id]);
        ((TextView)v.findViewById(R.id.tcTempRange)).setText("Temp Range: " + temprange[id]);
        ((TextView)v.findViewById(R.id.compensates)).setText(compensates[id]);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    public void setupTab(RadioButton button, final int image){
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String imageKey = String.valueOf(image);
                final Bitmap bitmap = getBitmapFromMemCache(imageKey);
                if (bitmap != null) {
                    tcImage.setImageBitmap(bitmap);
                    addBitmapToMemoryCache(imageKey, bitmap);
                } else {
                    BitmapWorkerTask task = new BitmapWorkerTask(tcImage);
                    task.execute(image);
                }
            }
        });
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            TcHolder.mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return TcHolder.mMemoryCache.get(key);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
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
            Bitmap bitmap = decodeSampledBitmapFromResource(
                    getResources(), tcarray);
            addBitmapToCache(tctest, bitmap);
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null && bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
    public void addBitmapToCache(String key, Bitmap bitmap) {
        // Add to memory cache as before
        if (getBitmapFromMemCache(key) == null) {
            TcHolder.mMemoryCache.put(key, bitmap);
        }
    }

}
