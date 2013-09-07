package thomas.jonathan.calculator2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		/* Run the splash screen for one second then start Standard Calc */
		Thread timer = new Thread()
		{
			public void run()
			{
				try{
					sleep(200);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					try {
						startActivity(new Intent(MainActivity.this, Class.forName("thomas.jonathan.calculator2.StandardCalculator")));
						finish();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			};
			timer.start();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
