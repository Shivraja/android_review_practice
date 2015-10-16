package com.example.shiv.list_view_practice;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String names[]={"Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja", "Kavi", "Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja"};
    String names2[]={"Ramu","rajaesd","radff","Pradeep", "Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja", "Kavi", "Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja"};
   // String name[]={"Pradeep", "Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja", "Kavi", "Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja"};
  //  String name2[]={"Raja","Ramu","rajaesd","radff","Pradeep", "Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja", "Kavi", "Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja"};
    int image1[] = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10,R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17};
    int image2[] = {R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10,R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17,R.drawable.a18};
    NotificationManager notificationManager;
    NotificationCompat.Builder notificationCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationCompat = new NotificationCompat.Builder(this);
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        ListView listView = (ListView) findViewById(R.id.list);
       // final ListView headView = (ListView) findViewById(R.id.headView);
        //CustomHeadAdapter headAdapter = new CustomHeadAdapter(name,name2,image1,this);
        CustomAdapter arrayAdapter = new CustomAdapter(names,names2,image2,this);
        listView.setAdapter(arrayAdapter);
        //headView.setAdapter(headAdapter);
        final ImageView head = (ImageView)findViewById(R.id.headimage);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                notificationCompat.setSmallIcon(android.R.drawable.sym_def_app_icon);
                notificationCompat.setContentTitle("List View Practice");
                notificationCompat.setContentText("The User Pressed " + i);
                notificationManager.notify(1, notificationCompat.build());
                Toast.makeText(getBaseContext(), i + " ", Toast.LENGTH_LONG).show();
            }
        });

         listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                Log.w("STATE CHANGED",i+"");
                Toast.makeText(getBaseContext(),"STATE CHANGED  "+i+"",Toast.LENGTH_SHORT);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                //headView.smoothScrollToPosition(i);
                head.setImageResource(image1[i]);
                Log.w("ON SCROLL",i+"  "+i1+"  "+i2);
                Toast.makeText(getBaseContext(), "ON SCROLL " + i+"  "+i1+"  "+i2, Toast.LENGTH_SHORT);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
