package thomas.jonathan.calculator2;

import java.text.DecimalFormat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.slidingmenu.lib.app.SlidingActivity;

public class TipCalculator extends SlidingActivity implements OnSeekBarChangeListener {
	
	double tipPercent, tA, newTotal;
	EditText billAmount, tipAmount, total;
	TextView tip;
	SeekBar slider;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tip_calc);

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

		lv.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {

				//For sliding menu
				try {
					 Class c;
		    		 if(menu[position].equals("Standard Calculator"))
		    			 c = Class.forName("thomas.jonathan.calculator2.StandardCalculator");
		    		 else if (menu[position].equals("Tip Calculator"))
		    			 c = Class.forName("thomas.jonathan.calculator2.TipCalculator");
		    		 else c = Class.forName("thomas.jonathan.calculator2."+menu[position]);
		    		 
					Intent i = new Intent(TipCalculator.this, c);
					startActivity(i);
					finish();

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				   }
			}
		});
		
		//font throughout is Roboto
		final Typeface mFont = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF"); 
		final ViewGroup mContainer = (ViewGroup) findViewById(android.R.id.content).getRootView();
		BMI.setAppFont(mContainer, mFont);	
				
		tipPercent=0.15;
		billAmount = (EditText) findViewById(R.id.billAmount);
		billAmount.requestFocus();
		tipAmount = (EditText) findViewById(R.id.tipAmount);
		total = (EditText) findViewById(R.id.total);
		tip = (TextView) findViewById(R.id.tip);
		slider = (SeekBar) findViewById(R.id.seekBar1);
		
		slider.setOnSeekBarChangeListener(this);
		
		billAmount.addTextChangedListener(textWatcher);
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

	//as you move the slider, the tip will calculate
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if(seekBar==slider && fromUser)
		{
			tipPercent = seekBar.getProgress();
			tipPercent /= 100;
			tip.setText(progress + "%"); // shows tip percent as you move the slider
			
			calculate();
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		tipAmount.setText(String.format( "$%.2f", tA ));
		total.setText(String.format( "$%.2f", newTotal ));
	}
	
	private TextWatcher textWatcher = new TextWatcher() 
	{
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			calculate();
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
		}
	};
	
	// calculate the total bill
	private void calculate()
	{
		if(!billAmount.getText().toString().equals(""))
		{
			double bA = Double.parseDouble(billAmount.getText().toString());
			tA = bA * tipPercent;
			newTotal = bA + tA;
			tipAmount.setText(String.format( "$%.2f", tA ));
			total.setText(String.format( "$%.2f", newTotal ));
		}
		
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
