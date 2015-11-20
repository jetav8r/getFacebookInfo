package com.waynejohnson.getfacebookinfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.ProfilePictureView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CircleImageView circleImageView;
    private ImageView standardImageView;

    private String userId;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(this);

        userId = "10152831009457616";

        ProfilePictureView profilePictureView;
        profilePictureView = (ProfilePictureView) findViewById(R.id.facebookPictureView);
        profilePictureView.setProfileId(userId);

        //standardImageView = (ImageView) findViewById(R.id.imageView);
        //circleImageView = (CircleImageView) findViewById(R.id.profImage);

        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute("https://graph.facebook.com/" + userId + "/picture?type=large");

        new DownloadImageTask((CircleImageView) findViewById(R.id.profImage))
                .execute("https://graph.facebook.com/" + userId + "/picture?type=large");

        /*AsyncTask<Void, Void, Bitmap> t = new AsyncTask<Void, Void, Bitmap>() {
            protected Bitmap doInBackground(Void... p) {
                Bitmap bm = null;
                try {
                    URL aURL = new URL("http://graph.facebook.com/" + userId + "/picture?type=large");
                    URLConnection conn = aURL.openConnection();
                    conn.setUseCaches(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bm;
            }

            protected void onPostExecute(Bitmap bm) {

                standardImageView.setImageBitmap(bm);
                circleImageView.setImageBitmap(bm);
            }
        };
        t.execute();*/
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("MyApp", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}

