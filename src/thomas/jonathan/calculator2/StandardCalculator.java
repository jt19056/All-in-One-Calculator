package thomas.jonathan.calculator2;

import java.text.DecimalFormat;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.slidingmenu.lib.app.SlidingActivity;

public class StandardCalculator extends SlidingActivity {
	
	Button zero,one,two,three,four,five,six,seven,eight,nine,plus,minus,mult,div,eq,dot,clear;
	EditText display;
	int op = 0;
	double result=0, val=0;
	boolean opflag=false;
	
	DecimalFormat df = new DecimalFormat("#.#####");
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.standard_calc);

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
				
				//For sliding menu
				try {
					Class c;
					if (menu[position].equals("Hello World"))
						c = Class.forName("thomas.jonathan.calculator2.HelloWorld");
					else if (menu[position].equals("Standard Calculator"))
						c = Class.forName("thomas.jonathan.calculator2.StandardCalculator");
					else if (menu[position].equals("Tip Calculator"))
						c = Class.forName("thomas.jonathan.calculator2.TipCalculator");
					else
						c = Class.forName("thomas.jonathan.calculator2." + menu[position]);

					Intent i = new Intent(StandardCalculator.this, c);
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
		
		/* For the calculator */
		zero = (Button) findViewById(R.id.button0);
		one = (Button) findViewById(R.id.button1);
		two = (Button) findViewById(R.id.button2);
		three = (Button) findViewById(R.id.button3);
		four = (Button) findViewById(R.id.button4);
		five = (Button) findViewById(R.id.button5);
		six = (Button) findViewById(R.id.button6);
		seven = (Button) findViewById(R.id.button7);
		eight = (Button) findViewById(R.id.button8);
		nine = (Button) findViewById(R.id.button9);
		plus = (Button) findViewById(R.id.buttonPlus);
		minus = (Button) findViewById(R.id.buttonMinus);
		mult = (Button) findViewById(R.id.buttonMult);
		div = (Button) findViewById(R.id.buttonDiv);
		eq = (Button) findViewById(R.id.buttonEquals);
		dot = (Button) findViewById(R.id.buttonDot);
		clear = (Button) findViewById(R.id.buttonClear);
		
		display = (EditText) findViewById(R.id.display);
		display.setText("");
		
		zero.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(opflag) display.append("0");
				else display.setText("0");
				opflag=true;
			}
		});
		one.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(opflag) display.append("1");
				else display.setText("1");
				opflag=true;
			}
		});
		two.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(opflag) display.append("2");
				else display.setText("2");
				opflag=true;
			}
		});
		three.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(opflag) display.append("3");
				else display.setText("3");
				opflag=true;
			}
		});
		four.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(opflag) display.append("4");
				else display.setText("4");
				opflag=true;
			}
		});
		five.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(opflag) display.append("5");
				else display.setText("5");
				opflag=true;
			}
		});
		six.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(opflag) display.append("6");
				else display.setText("6");
				opflag=true;
			}
		});
		seven.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(opflag) display.append("7");
				else display.setText("7");
				opflag=true;
			}
		});
		eight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(opflag) display.append("8");
				else display.setText("8");
				opflag=true;
			}
		});
		nine.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(opflag) display.append("9");
				else display.setText("9");
				opflag=true;
			}
		});
		plus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				
				val = Double.parseDouble(display.getText().toString());
				
				if(op==0 || op==5) result=val;
				else if(op==1)      result+=val;
				else if(op==2) result-=val;
				else if(op==3) result*=val;
				else if(op==4) result/=val;
				
				display.setText(df.format(result));
				opflag=false;
				op=1;
			}
		});
		minus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				val = Double.parseDouble(display.getText().toString());
			
				if(op==0 || op==5) result=val;
				else if(op==1)      result+=val;
				else if(op==2) result-=val;
				else if(op==3) result*=val;
				else if(op==4) result/=val;
				
				display.setText(df.format(result));
				opflag=false;
				op=2;
			}
		});
		mult.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				val = Double.parseDouble(display.getText().toString());
			
				if(op==0 || op==5) result=val;
				else if(op==1)      result+=val;
				else if(op==2) result-=val;
				else if(op==3) result*=val;
				else if(op==4) result/=val;
				
				display.setText(df.format(result));
				opflag=false;
				op=3;
			}
		});
		div.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				val = Double.parseDouble(display.getText().toString());
			
				if(op==0 || op==5) result=val;
				else if(op==1)      result+=val;
				else if(op==2) result-=val;
				else if(op==3) result*=val;
				else if(op==4) result/=val;
				
				display.setText(df.format(result));
				opflag=false;
				op=4;
			}
		});
		dot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(opflag) display.append(".");
				else display.setText("0.");
				opflag=true;
			}
		});
		clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				display.setText("");
				op=0;
				result=0;
				val=0;
				opflag=false;
			}
		});
		eq.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				val = Double.parseDouble(display.getText().toString());
				
				if(op==0) result=val;
				else if(op==1)      result+=val;
				else if(op==2) result-=val;
				else if(op==3) result*=val;
				else if(op==4) result/=val;
				
				display.setText(df.format(result));
				
				op=5;
				opflag=false;
			}
		});
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
}
