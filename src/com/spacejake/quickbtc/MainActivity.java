package com.spacejake.quickbtc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new RetreiveFeedTask().execute();
    
    }
    public boolean updateButtonClicked(MenuItem item) {
    	TextView display = (TextView) findViewById(R.id.textView1);
    	display.setText("...");
    	new RetreiveFeedTask().execute();
		return false;
    }
    
    public boolean infoButtonClicked(MenuItem item){
    	return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    protected void onResume () {
    	updateButtonClicked(null);
    	super.onResume();
    	return;
    }

    protected void onPause(){
    	if(new RetreiveFeedTask().isCancelled() == false){
    		new RetreiveFeedTask().cancel(true);
    	}
    	super.onPause();
    }
  
public class RetreiveFeedTask extends AsyncTask<String, Void, Void> {
	
	public String price = "0.00";
    	@Override
    	protected Void doInBackground(String... arg0) {
    		
    		URL url;
    		try {
    			// get URL content
    			url = new URL("http://blockchain.info/q/24hrprice");
    			URLConnection conn = url.openConnection();
     
    			// open the stream and put it into BufferedReader
    			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    			StringBuilder total = new StringBuilder();
    			String value;
    			while ((value = reader.readLine()) != null) {
    			    total.append(value);
    			}
    			//System.out.println(total.toString());
    			DecimalFormat twoDs = new DecimalFormat("#.##");
    			price = String.valueOf(twoDs.format(Double.valueOf(total.toString())));
    			
    		} catch (MalformedURLException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    			runOnUiThread(new Runnable() {
    				public void run() {
    					//TextView display = (TextView) findViewById(R.id.textView1);
    			          //display.setText(String.valueOf(price));
    				    Toast.makeText(MainActivity.this, "Error! Check your internet connection.", Toast.LENGTH_SHORT).show();
    				    }
    				});
    		}
    		return null;
    		
    		}
    	@Override
        protected void onPostExecute(Void result) {
    	  TextView display = (TextView) findViewById(R.id.textView1);
          display.setText(price);
          //System.out.println(price);
          
        }

    	
    }

    
    
}
