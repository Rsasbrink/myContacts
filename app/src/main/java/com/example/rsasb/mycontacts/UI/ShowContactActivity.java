package com.example.rsasb.mycontacts.UI;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rsasb.mycontacts.AppDatabase;
import com.example.rsasb.mycontacts.Contact;
import com.example.rsasb.mycontacts.R;

public class ShowContactActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static final String EDIT_CONTACT = "";
    public static final int DELETED_CONTACT = 2;
    public static final int BACK_PRESSED = 1;

    private TextView mFirstName, mLastName, mPhone, mStreet, mHousenumber, mZipcode, mCity, mEmail;
    static AppDatabase db;
    private Button deleteButton, dialButton, editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        final Contact contact = (Contact) bundle.get("contact");

        setTitle(contact.getFullName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);

        db = AppDatabase.getInstance(this);

        mFirstName = findViewById(R.id.TextView_firstName);
        mLastName = findViewById(R.id.TextView_lastName);
        mPhone = findViewById(R.id.TextView_phoneNumber);
        mStreet = findViewById(R.id.TextView_street);
        mHousenumber = findViewById(R.id.TextView_houseNumber);
        mZipcode = findViewById(R.id.TextView_zipcode);
        mCity = findViewById(R.id.TextView_city);
        mEmail = findViewById(R.id.TextView_email);

        mFirstName.setText(contact.getFirstName());
        mLastName.setText(contact.getLastName());
        mPhone.setText(contact.getPhoneNumber());
        mStreet.setText(contact.getStreet());
        mHousenumber.setText(contact.getHouseNumber());
        mZipcode.setText(contact.getZipCode());
        mCity.setText(contact.getCity());
        mEmail.setText(contact.getEmailAddress());
        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowContactActivity.this, EditContactActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", contact);
                intent.putExtras(bundle);
                startActivityForResult(intent, DELETED_CONTACT);

            }
        });
        dialButton = findViewById(R.id.dialContact);
        dialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!contact.getPhoneNumber().trim().isEmpty()) {
                    //Check whether the app is installed on Android 6.0 or higher//
                    if (Build.VERSION.SDK_INT >= 23) {
                        //Check whether your app has access to the READ permission//
                        if (checkPermission()) {
                            //If your app has access to the device’s storage, then print the following message to Android Studio’s Logcat//
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
                            startActivity(callIntent);
                        } else {
                            //If your app doesn’t have permission to access external storage, then call requestPermission//
                            requestPermission();
                        }
                    }
                } else {
                    Snackbar.make(view, R.string.warning_cant_call, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(ShowContactActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(ShowContactActivity.this);
                }
                builder.setTitle(R.string.alert_delete_contact_title)
                        .setMessage(R.string.alert_delete_contact_content)
                        .setPositiveButton(R.string.answer_yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.contactDao().delete(contact);
                                Intent intent = new Intent(ShowContactActivity.this, ContactActivity.class);
                                startActivityForResult(intent, DELETED_CONTACT);
                            }
                        })
                        .setNegativeButton(R.string.answer_no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {
        //Check for READ_EXTERNAL_STORAGE access, using ContextCompat.checkSelfPermission()//
        int result = ContextCompat.checkSelfPermission(ShowContactActivity.this, Manifest.permission.CALL_PHONE);

        //If the app does have this permission, then return true//
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            //If the app doesn’t have this permission, then return false//
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShowContactActivity.this, ContactActivity.class);
        startActivityForResult(intent, BACK_PRESSED);
    }
}
