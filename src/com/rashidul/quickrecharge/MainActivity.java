package com.rashidul.quickrecharge;

import java.io.File;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	static final String DIGITS = null;
	String text;
	String text_temp;
	ImageView iv;
	TextView tv;
	Bitmap imageBitmap;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.button1);
        iv = (ImageView) findViewById(R.id.imageView1);
        tv = (TextView) findViewById(R.id.textView1);
        btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File file = new File(Environment.getExternalStorageDirectory()+File.separator + "img.jpg");
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			        startActivityForResult(takePictureIntent, 1);			    
				}
			});
     
    	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			
			if(requestCode == 1){
				File file = new File(Environment.getExternalStorageDirectory()+File.separator + "img.jpg");
				try {
					doCrop(Uri.fromFile(file));
				} catch(ActivityNotFoundException aNFE){
				    String errorMessage = "Sorry - your device doesn't support the crop action!";
				    Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
				    toast.show();
				}			
			}else if(requestCode == 2){
				Bundle extras = data.getExtras();
				imageBitmap = extras.getParcelable("data");
		        iv.setImageBitmap(imageBitmap);
		        
		        TessBaseAPI baseApi = new TessBaseAPI();
		        baseApi.init("/mnt/sdcard/tess_lang", "eng");
		        baseApi.setImage(imageBitmap);
		        text_temp = baseApi.getUTF8Text();
		        
		        text = text_temp.replaceAll("\\s+", "");
		        tv.setText(text);	     
		        Intent intent = new Intent(this, Second.class);
		        intent.putExtra(DIGITS, text);
		        startActivity(intent);
			}
	            
	    }
	}
	
	private void doCrop(Uri picUri){
				Intent cropIntent = new Intent("com.android.camera.action.CROP");
				cropIntent.setDataAndType(picUri, "image/*");
				cropIntent.putExtra("crop", "true");
				//cropIntent.putExtra("aspectX", 1);
				//cropIntent.putExtra("aspectY", 1);
				cropIntent.putExtra("outputX", 300);
				cropIntent.putExtra("outputY", 100);
				cropIntent.putExtra("return-data", true);
				startActivityForResult(cropIntent, 2);
	}
	
}