package netmind.com.practicanetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "In Main Activity";

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        final ImageButton downloadImage_Btn = (ImageButton) this.findViewById(R.id.imgBtnDownload);
        downloadImage_Btn.setOnClickListener(this);

        this.mImageView = (ImageView) this.findViewById(R.id.imageViewMain);

        final Button readFeed_Btn = (Button) this.findViewById(R.id.btnReadFeed);
        readFeed_Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ConnectivityManager mConnectionManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectionManager.getActiveNetworkInfo();

        if (mNetworkInfo!=null && mNetworkInfo.isConnected()){
            if(view.getId()==R.id.imgBtnDownload){
                final String urlString = "https://www.tutorialspoint.com/green/images/logo.png";
                Picasso.with(this).load(urlString).into(mImageView);


            }else if(view.getId()==R.id.btnReadFeed){
                final String urlFeed = "https://www.theguardian.com/international/rss";

                new MyAlternativeThread(this).execute(urlFeed);




            }
        } else {
            Log.i(MainActivity.TAG, "Connecion not available");
            Toast.makeText(this, "Connection unavailable", Toast.LENGTH_SHORT).show();
        }



    }


}
