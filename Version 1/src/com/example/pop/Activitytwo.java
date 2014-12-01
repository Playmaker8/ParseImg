package com.example.pop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activitytwo extends Activity implements OnClickListener {
	Button btnAccept;
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.two);
	    btnAccept = (Button) findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(this);
	  }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
	    case R.id.btnAccept:
	    	Intent intent = new Intent (this, Activitythree.class);
	    	startActivity(intent);
	    	break;
	    default:
	      break;
	
}
	}
	  
	  
}
