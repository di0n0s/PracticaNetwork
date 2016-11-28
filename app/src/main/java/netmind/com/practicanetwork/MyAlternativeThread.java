package netmind.com.practicanetwork;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by A5Alumno on 11/28/2016.
 */

public class MyAlternativeThread extends AsyncTask <String, Void, String> {

    private static final String TAG = MyAlternativeThread.class.getSimpleName();
    private Context mContext;

    public MyAlternativeThread(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected @Nullable String doInBackground(String[] params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            //Starting HTTP query
            connection.connect();
            int respCode = connection.getResponseCode();
            Log.i(TAG, "Response Code: " + respCode);
            if (respCode == HttpURLConnection.HTTP_OK) {
                InputStream myStream = connection.getInputStream();
                XmlPullParser xmlPullParser = Xml.newPullParser();
                xmlPullParser.setInput(myStream, null);

                StringBuilder stringBuilder = new StringBuilder("");
                int event = xmlPullParser.nextTag();

                while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT){
                    switch (event){
                        case XmlPullParser.START_TAG:
                            if(xmlPullParser.getName().equals("item")){
                                xmlPullParser.nextTag();
                                xmlPullParser.next();
                                stringBuilder.append(xmlPullParser.getText()).append("\n");
                            }
                        break;
                    }
                    event = xmlPullParser.next();
                }
                myStream.close();
                Log.i(TAG, stringBuilder.toString());
                return stringBuilder.toString();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(@Nullable String returnString) {
        super.onPostExecute(returnString);

        if(returnString != null){
            Toast.makeText(this.mContext, returnString, Toast.LENGTH_SHORT).show();
        } else {

        }

    }
}