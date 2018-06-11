package com.example.rsasb.mycontacts.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.rsasb.mycontacts.AppDatabase;
import com.example.rsasb.mycontacts.Contact;
import com.example.rsasb.mycontacts.R;

public class EditContactActivity extends AppCompatActivity {

    //Local variables
    private EditText mFirstName, mLastName, mPhone, mStreet, mHousenumber, mZipcode, mCity, mEmail;
    static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getInstance(this);

        Bundle bundle = getIntent().getExtras();
        final Contact contact = (Contact) bundle.get("contact");

        mFirstName = findViewById(R.id.editText_firstName);
        mLastName = findViewById(R.id.editText_lastName);
        mPhone = findViewById(R.id.editText_phoneNumber);
        mStreet = findViewById(R.id.editText_street);
        mHousenumber = findViewById(R.id.editText_houseNumber);
        mZipcode = findViewById(R.id.editText_zipcode);
        mCity = findViewById(R.id.editText_city);
        mEmail = findViewById(R.id.editText_email);

        mFirstName.setText(contact.getFirstName());
        mLastName.setText(contact.getLastName());
        mPhone.setText(contact.getPhoneNumber());
        mStreet.setText(contact.getStreet());
        mHousenumber.setText(contact.getHouseNumber());
        mZipcode.setText(contact.getZipCode());
        mCity.setText(contact.getCity());
        mEmail.setText(contact.getEmailAddress());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contact.setFirstName(mFirstName.getText().toString());
                contact.setLastName(mLastName.getText().toString());
                contact.setPhoneNumber(mPhone.getText().toString());
                contact.setStreet(mStreet.getText().toString());
                contact.setHouseNumber(mHousenumber.getText().toString());
                contact.setZipCode(mZipcode.getText().toString());
                contact.setCity(mCity.getText().toString());
                contact.setEmailAddress(mEmail.getText().toString());

                db.contactDao().update(contact);

                Intent intent = new Intent(EditContactActivity.this, ShowContactActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", contact);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1234);
            }
        });
    }

    public void onBackPressed() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(R.string.alert_discard_change_title)
                .setMessage(R.string.alert_discard_change_content)
                .setPositiveButton(R.string.answer_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.answer_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();

    }
}
