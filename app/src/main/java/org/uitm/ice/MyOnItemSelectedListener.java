package org.uitm.ice;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class MyOnItemSelectedListener implements OnItemSelectedListener {

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		
		//Toast.makeText(parent.getContext(),	"Selected Country : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
		/*
		String s = String.valueOf(spinner1.getSelectedItem());
		if(s.equals("A-"))
			startActivity(new Intent(view.getContext(),Record.class));
		if(s.equals("B+"))
			startActivity(new Intent(view.getContext(),Record.class)); */
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

}
