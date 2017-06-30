package com.example.kh.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.txt)
    TextView txt;
    private int READ_CONTACT=  20;
    private String[] columnInjection = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };
    private String selectionContact = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY+" April";
    private String[] selectArgument = new String[]{"April"};
    private String orderby  = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkPermission();
        ContentResolver contentResolver =  getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, columnInjection,null, null,null);
        if(cursor!=null && cursor.getCount()>0){
            StringBuilder stringBuilder = new StringBuilder("");
            while(cursor.moveToNext()){
                stringBuilder.append(cursor.getString(0)+"...."+cursor.getString(1)+"....."+cursor.getString(2));
            }
            txt.setText(stringBuilder.toString());
        }else{
            txt.setText("no contact");
        }
    }

    public void checkPermission(){
        int permission  = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if(permission!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)){

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACT);
            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Log.i(TAG, "onRequestPermissionsResult: ");
        }else{
            Log.i(TAG, "fail: ");
        }
    }
}
