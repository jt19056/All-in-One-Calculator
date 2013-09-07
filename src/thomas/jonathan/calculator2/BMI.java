package thomas.jonathan.calculator2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.slidingmenu.lib.app.SlidingActivity;

public class BMI extends SlidingActivity implements OnClickListener, OnItemSelectedListener {

	Spinner spinnerA, spinnerW, spinnerH;
	EditText etA, etW, etH, bmiResult;
	TextView minMax;
	Button calc;
	String [] age = {"Male", "Female"}, 
			  weight = {"lbs", "kg"}, 
			  height = {"in", "cm"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bmi);

		// for the sliding menu. set it behind and set the margin to 100
		setBehindContentView(R.layout.activity_menu);
		getSlidingMenu().setBehindOffset(100);
		getActionBar().setDisplayHomeAsUpEnabled(true); // so the icon in top left can toggle the menu
		setSlidingActionBarEnabled(false);  //don't slide the actionbar
		
		final String[] menu = getResources().getStringArray(R.array.Menu);
		ListView lv = (ListView) findViewById(R.id.listView1);
		
		// set the font type in the menu
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu)
		{
			public View getView(int position, View convertView, ViewGroup parent)
			{
			View v = super.getView(position, convertView, parent);

			Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
			((TextView) v).setTypeface(externalFont);

			return v;
			}
							
		};
						
		lv.setAdapter(ad);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				try {
					Class c;
		    		 if(menu[position].equals("Standard Calculator"))
		    			 c = Class.forName("thomas.jonathan.calculator2.StandardCalculator");
		    		 else if (menu[position].equals("Tip Calculator"))
							c = Class.forName("thomas.jonathan.calculator2.TipCalculator");
		    		 else c = Class.forName("thomas.jonathan.calculator2."+menu[position]);
		    		 
					Intent i = new Intent(BMI.this, c);
					startActivity(i);
					finish();

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		
		ArrayAdapter <String> adapter_a = new ArrayAdapter<String>(this, R.layout.spinner, age)
		{
			public View getView(int position, View convertView, ViewGroup parent)
			{
                View v = super.getView(position, convertView, parent);

                Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                ((TextView) v).setTypeface(externalFont);

                return v;
			}
		};
		ArrayAdapter <String> adapter_w = new ArrayAdapter<String>(this, R.layout.spinner, weight)
		{
			public View getView(int position, View convertView, ViewGroup parent)
			{
                View v = super.getView(position, convertView, parent);

                Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                ((TextView) v).setTypeface(externalFont);

                return v;
			}
		};
		ArrayAdapter <String> adapter_h = new ArrayAdapter<String>(this, R.layout.spinner, height)
		{
			public View getView(int position, View convertView, ViewGroup parent)
			{
                View v = super.getView(position, convertView, parent);

                Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                ((TextView) v).setTypeface(externalFont);

                return v;
			}			
		};
		
		spinnerA = (Spinner) findViewById(R.id.spinnerAge);
		spinnerW = (Spinner) findViewById(R.id.spinnerWeight);
		spinnerH = (Spinner) findViewById(R.id.spinnerHeight);
		spinnerA.setAdapter(adapter_a);
		spinnerW.setAdapter(adapter_w);
		spinnerH.setAdapter(adapter_h);
		
		calc = (Button) findViewById(R.id.bmiCalc);
		etA = (EditText) findViewById(R.id.editTextAge);
		etW = (EditText) findViewById(R.id.editTextWeight);
		etH = (EditText) findViewById(R.id.editTextHeight);
		minMax = (TextView) findViewById(R.id.editTextMinMax);
		bmiResult = (EditText) findViewById(R.id.bmiResult);
		
		calc.setOnClickListener(this);
		
		//font throughout is Roboto
		final Typeface mFont = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF"); 
		final ViewGroup mContainer = (ViewGroup) findViewById(android.R.id.content).getRootView();
		BMI.setAppFont(mContainer, mFont);				
		
	}
	
	// so the icon in top left can toggle the menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return onOptionsItemSelected(item);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.bmiCalc)
		{
			double userA, userW, userH, result=0, min=0, max=0;
			
			//if the user pressed calculate but a value hasn't been entered in every box
			if(etA.getText().toString().equals("")|| etW.getText().toString().equals("") || etH.getText().toString().equals(""))
				Toast.makeText(getApplicationContext(), "Please enter a value for every entry.", Toast.LENGTH_SHORT).show();
			
			else
			{
				if( Double.parseDouble(etA.getText().toString()) <= 20 )
					Toast.makeText(getApplicationContext(), "Please an age over 20.", Toast.LENGTH_SHORT).show();
				else //over the age of 20, so cacluate the bmi
				{
					InputMethodManager imm = 
						    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(bmiResult.getWindowToken(), 0);
					
					userA = Double.parseDouble(etA.getText().toString());
					userW = Double.parseDouble(etW.getText().toString());
					userH = Double.parseDouble(etH.getText().toString());
					String aUnit = spinnerA.getSelectedItem().toString();
					String wUnit = spinnerW.getSelectedItem().toString();
					String hUnit = spinnerH.getSelectedItem().toString();
					
					if(wUnit.equals("lbs") && hUnit.equals("in"))
					{
						result = (userW / (userH*userH))*703;
						min = (18.5*userH*userH)/703;
						max = (25*userH*userH)/703;
						
						minMax.setText("Your ideal weight is between "+ Math.round(min) +" and "
								+ Math.round(max) +" lbs.");
					}
					else if(wUnit.equals("kg") && hUnit.equals("cm"))
					{
						result = userW / ((userH/100)*(userH/100));
						min = (18.5*(userH/100)*(userH/100));
						max = (25*(userH/100)*(userH/100));
						minMax.setText("Your ideal weight is between "+ Math.round(min) +" and "
								+ Math.round(max) +" kg.");
					}
					else Toast.makeText(getApplicationContext(), "Please make sure the units are the same.", Toast.LENGTH_SHORT).show();
				}
			}
			
			if(result<18.5)
			{
				bmiResult.setBackgroundColor( Color.parseColor("#FFBB33"));
				bmiResult.setText(String.format( "%.1f (Underweight)", result ));
			}
			else if(result>=18.5 && result<25)
			{
				bmiResult.setBackgroundColor( Color.parseColor("#33B5E5"));
				bmiResult.setText(String.format( "%.1f (Normal)", result ));
			}
			else if(result>=25 && result<30)
			{
				bmiResult.setBackgroundColor( Color.parseColor("#FF4444"));
				bmiResult.setText(String.format( "%.1f (Overweight)", result ));
			}
			else
			{
				bmiResult.setBackgroundColor( Color.parseColor("#CC0000"));
				bmiResult.setText(String.format( "%.1f (Obese)", result ));
				//Toast.makeText(getApplicationContext(), "Lose weight fatty.", Toast.LENGTH_SHORT).show();

			}				
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Recursively sets a {@link Typeface} to all
	 * {@link TextView}s in a {@link ViewGroup}.
	 */
	public static final void setAppFont(ViewGroup mContainer, Typeface mFont)
	{
	    if (mContainer == null || mFont == null) return;

	    final int mCount = mContainer.getChildCount();

	    // Loop through all of the children.
	    for (int i = 0; i < mCount; ++i)
	    {
	        final View mChild = mContainer.getChildAt(i);
	        if (mChild instanceof TextView)
	        {
	            // Set the font if it is a TextView.
	            ((TextView) mChild).setTypeface(mFont);
	        }
	        else if (mChild instanceof ViewGroup)
	        {
	            // Recursively attempt another ViewGroup.
	            setAppFont((ViewGroup) mChild, mFont);
	        }
	    }
	}
	
	

}


