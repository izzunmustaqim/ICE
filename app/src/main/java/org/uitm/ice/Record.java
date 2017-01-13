package org.uitm.ice;

//import org.uitm.ice.apps.Panic;
import org.uitm.ice.apps.Shout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
//import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Record extends AppCompatActivity implements OnClickListener  {
    SharedPreferences _sp;
    LinearLayout launchPanic;
    GridView launchGrid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        setContentView(R.layout.home);
        /*
        _sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(_sp.getBoolean("IsVirginUser", true)) {
        	launchWizard();
      	} */

        //launchPanic = (LinearLayout) findViewById(R.id.launchPanic);
        //launchPanic.setOnClickListener(this);

        launchGrid = (GridView)findViewById(R.id.launchGrid2);
        launchGrid.setAdapter(new ImageAdapter(this));
        launchGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(position == 0)
                    launchAllergies();
                else if(position == 1)
                    launchDiseases();
                else if(position == 2)
                    launchMedicines();
                else if(position == 3)
                	launchNotices();
            }
        });
    }

	private void launchMedicines() {
		Intent i = new Intent(this,Medicines.class);
		startActivity(i);
	}


	private void launchDiseases() {
		Intent i = new Intent(this,Diseases.class);
		startActivity(i);
	}

    private void launchAllergies() {
        Intent i = new Intent(this,Allergies.class);
        startActivity(i);
    }

	private void launchNotices() {
		Intent i = new Intent(this,Notices.class);
		startActivity(i);
	}
	/*
	private void launchPreferences() {
		Intent i = new Intent(this,ITCPreferences.class);
		startActivity(i);
	} */



    @Override
    public void onClick(View v) {
        //if(v == launchPanic)
        //	launchPanic();
    }

    public class ImageAdapter extends BaseAdapter
    {
        Context mContext;

        public ImageAdapter(Context c)
        {
            mContext = c;
        }

        @Override
        public int getCount()
        {
            return 4;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View MyView = convertView;

            if (convertView == null)
            {
                //Inflate the layout
                LayoutInflater li = getLayoutInflater();
                MyView = li.inflate(R.layout.grid_item, null);

                //Add image & text
                TextView tv = (TextView)MyView.findViewById(R.id.grid_item_text);
                ImageView iv = (ImageView)MyView.findViewById(R.id.grid_item_image);
                MyView.setId(position);

                switch(position){
                    case 0:
                        tv.setText("Allergies");
                        iv.setImageResource(R.drawable.allergies);
                        break;

                    case 1:
                        tv.setText("Diseases");
                        iv.setImageResource(R.drawable.pathologies);
                        break;
                    case 2:
                        tv.setText("Medications");
                        iv.setImageResource(R.drawable.medications);
                        break;
                    case 3:
                        tv.setText("Additional Data");
                        iv.setImageResource(R.drawable.notice);
                        break;
                }
            }
            return MyView;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO auto-generated method stub
            return 0;
        }
    }
}