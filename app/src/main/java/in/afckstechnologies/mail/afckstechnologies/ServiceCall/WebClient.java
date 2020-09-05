package in.afckstechnologies.mail.afckstechnologies.ServiceCall;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

/**
 * Created by IT TEAM on 12/10/2014.
 */
@SuppressWarnings({ "deprecation", "deprecation" })
public class WebClient {
    Context context;

        String TAG = "ServiceAccess";
        String response ="";
        String baseURL = "";
        @SuppressWarnings({ "deprecation", "resource" })
		public String SendHttpPost(String URL, JSONObject jsonObjSend) {

            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(URL);
               // HttpGet post = new HttpGet(URL);
                post.setHeader("Content-type", "application/json; charset=UTF-8");
                post.setHeader("Accept", "application/json");
                post.setEntity(new StringEntity(jsonObjSend.toString(), "UTF-8"));
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10*1000); 
                HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),10*1000); 
                HttpResponse response = client.execute(post);
                Log.i(TAG,"resoponse"+response);
                HttpEntity entity = response.getEntity();

                return EntityUtils.toString(entity);

            }catch (Exception e) {
                // TODO: handle exception
                Log.i(TAG, "exception" + e);
            }
            Log.i(TAG, "response" + response);
            return response;
        }


    public String doGet(String url) {

        HttpResponse response = null;
        String resultantResponse = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            response = httpclient.execute(httpget);
        }

        catch (Exception e) {
            // TODO: handle exception
            Log.i(TAG, "exception" + e);
        }
        return response.toString();

    }

    @SuppressWarnings("resource")
	public String accessGetMethod(String parameter)
    {
        String resultantResponse = "";
        HttpClient httpClient = new DefaultHttpClient();
        try
        {
            String url = parameter;

            HttpGet method = new HttpGet( new URI(url) );
            HttpResponse response = httpClient.execute(method);
            if ( response != null )
            {
                Log.i( "login", "received " + getResponse(response.getEntity()) );
                resultantResponse = getResponse(response.getEntity());

//        if(method.compareTo("login")==0) {
//            resultantResponse = "{\"authToken\":\"Adjneownfsf3253\",\"status\":\"Success\",\"message\":\"User logged in successfully.\",\"section\":[\"section1\",\"section2\",\"section3\",\"section4\"],\"session\":[\"session1\",\"session2\",\"session3\",\"session4\"]}";
//        }else if (method.compareTo("student")==0) {
//            resultantResponse = "{\"status\":\"Success\",\"message\":\"Students list for section1.\",\"studentsList\":[{\"studentsName\":\"Raju Shrivastava\",\"studentsRollNo\":\"1\",\"studentsPhoto\":\"http://api.androidhive.info/music/images/adele.png\"},{\"studentsName\":\"Raju Shrivastava\",\"studentsRollNo\":\"2\",\"studentsPhoto\":\"http://api.androidhive.info/music/images/eminem.png\"},{\"studentsName\":\"Raj Shri\",\"studentsRollNo\":\"3\",\"studentsPhoto\":\"http://api.androidhive.info/music/images/mj.png\"},{\"studentsName\":\"Ramu Shah\",\"studentsRollNo\":\"4\",\"studentsPhoto\":\"http://api.androidhive.info/music/images/rihanna.png\"},{\"studentsName\":\"Ramu Shah\",\"studentsRollNo\":\"5\",\"studentsPhoto\":\"http://api.androidhive.info/music/images/rihanna.png\"},{\"studentsName\":\"Ramu Shah\",\"studentsRollNo\":\"4\",\"studentsPhoto\":\"http://api.androidhive.info/music/images/rihanna.png\"},{\"studentsName\":\"Ramu Shah\",\"studentsRollNo\":\"4\",\"studentsPhoto\":\"http://api.androidhive.info/music/images/rihanna.png\"}]}";
//        }else
//        {
//            resultantResponse = "{\"status\":\"Success\",\"message\":\"Students list for section1.\",\"studentsList\":[{\"studentsName\":\"Raju Shrivastava\",\"studentsRollNo\":\"1\",\"studentsPhoto\":\"fasfsdAASfasf\"},{\"studentsName\":\"Raju Shrivastava\",\"studentsRollNo\":\"1\",\"studentsPhoto\":\"fasfsdAASfasf\"},{\"studentsName\":\"Raju Shrivastava\",\"studentsRollNo\":\"1\",\"studentsPhoto\":\"fasfsdAASfasf\"},{\"studentsName\":\"Raju Shrivastava\",\"studentsRollNo\":\"1\",\"studentsPhoto\":\"fasfsdAASfasf\"}]}";
//        }
            }
            else
            {
                Log.i( "login", "got a null response" );
            }
        } catch (IOException e) {
            Log.e( "error", e.getMessage() );
        } catch (URISyntaxException e) {
            Log.e( "error", e.getMessage() );
        }
        return resultantResponse;
    }

    private String getResponse( HttpEntity entity )
    {
        String response = "";

        try
        {

            int length = ( int ) entity.getContentLength();

            StringBuffer sb = new StringBuffer( length );
            InputStreamReader isr = new InputStreamReader( entity.getContent(), "UTF-8" );
            char buff[] = new char[length];
            int cnt;
            while ( ( cnt = isr.read( buff, 0, length - 1 ) ) > 0 )
            {
                sb.append( buff, 0, cnt );
            }

            response = sb.toString();
            isr.close();
        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }

        return response;
    }




    public String getAddressFromLatLong(Double lat,Double lng){
        String addressFromLatLong = null;

        String address = String.format(Locale.ENGLISH,"http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$f,%2$f&sensor=true&language="+Locale.getDefault().getCountry(), lat, lng);
        HttpGet httpGet = new HttpGet(address);

        HttpClient client = new DefaultHttpClient();

        HttpResponse response;

        StringBuilder stringBuilder = new StringBuilder();


        try {

            response = client.execute(httpGet);

            HttpEntity entity = response.getEntity();

            InputStream stream = entity.getContent();

            int b;

            while ((b = stream.read()) != -1) {

                stringBuilder.append((char) b);

            }


            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            System.out.println("jsonObject-- ioutil->"+jsonObject.toString());
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject1 = results.getJSONObject(0);

                addressFromLatLong = jsonObject1.getString("formatted_address");

            }

        }catch (Exception ex) {
        }






        return addressFromLatLong;

    }


    public String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    }


