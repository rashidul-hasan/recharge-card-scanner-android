package com.rashidul.quickrecharge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Second extends ActionBarActivity implements OnItemSelectedListener{

	String[] operators;
	Spinner sp;
	String operator = "";
	String num = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		TextView tv = (TextView) findViewById(R.id.textView1);
		TextView code = (TextView) findViewById(R.id.code);
		
		Intent intent = getIntent();
		num = intent.getStringExtra(MainActivity.DIGITS);
		code.setText(num);
		
		sp = (Spinner) findViewById(R.id.spinner1);
		operators = getResources().getStringArray(R.array.operators);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,operators);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(this);
		
		Button btn = (Button)findViewById(R.id.btnRecharge);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean okay = validateDigits(num);
				if(okay){
					String uri = "tel:"+operator+num+"%23";
					Intent i = new Intent(Intent.ACTION_DIAL);
					i.setData(Uri.parse(uri));
					startActivity(i);
				}else{
					Toast.makeText(Second.this, "the number is invalid,please scan again", Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	private boolean validateDigits(String num) {
		String number = num;
		if(number.length()==16 && number.matches("[0-9]+")){
			return true;
		}
		else
			return false;
	}
	
	//}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
		case 0:
			operator = "*555*";
			break;
		case 1:
			operator = "*111*";
			break;
		case 2:
			operator = "*123*";
			break;
		case 3:
			operator = "*787*";
			break;
		case 4:
			operator = "*888*";
			break;	

		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		operator = "*555*";
		
	}
	
}