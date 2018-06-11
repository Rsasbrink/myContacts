package com.example.rsasb.mycontacts.UI;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rsasb.mycontacts.AppDatabase;
import com.example.rsasb.mycontacts.Contact;
import com.example.rsasb.mycontacts.R;

public class ShowActivity extends AppCompatActivity {
    public static final String EDIT_CONTACT = "";
    public static final int DELETED_CONTACT = 2;
    public static final int BACK_PRESSED = 1;

    private TextView mFirstName, mLastName, mPhone, mStreet, mHousenumber, mZipcode, mCity, mEmail;
    static AppDatabase db;
    private Button deleteButton, dialButton, ediButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        System.out.println("----------------------------------------------");

        db = AppDatabase.getInstance(this);

        Bundle bundle = getIntent().getExtras();

        final Contact contact = (Contact) bundle.get("contact");


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

                Intent intent = new Intent(ShowActivity.this, EditActivity.class);

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

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));

                if (ActivityCompat.checkSelfPermission(ShowActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

            }
        });
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(ShowActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(ShowActivity.this);
                }
                builder.setTitle("Contact verwijderen")
                        .setMessage("Weet u zeker dat u het contact wilt verwijderen?")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.contactDao().delete(contact);
                                Intent intent = new Intent(ShowActivity.this, ContactActivity.class);
                                startActivityForResult(intent, DELETED_CONTACT);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShowActivity.this, ContactActivity.class);
        startActivityForResult(intent, BACK_PRESSED);
    }
}
