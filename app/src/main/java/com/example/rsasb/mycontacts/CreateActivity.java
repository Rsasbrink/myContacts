package com.example.rsasb.mycontacts;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity {

    private EditText mFirstName, mLastName, mPhone, mStreet, mHousenumber, mZipcode, mCity, mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mFirstName = findViewById(R.id.editText_firstName);
        mLastName = findViewById(R.id.editText_lastName);
        mPhone = findViewById(R.id.editText_phoneNumber);
        mStreet = findViewById(R.id.editText_street);
        mHousenumber = findViewById(R.id.editText_houseNumber);
        mZipcode = findViewById(R.id.editText_zipcode);
        mCity = findViewById(R.id.editText_city);
        mEmail = findViewById(R.id.editText_email);

        Button but = findViewById(R.id.button_saveContact);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = mFirstName.getText().toString();
                String lastName = mLastName.getText().toString();
                String phoneNumber = mPhone.getText().toString();
                String street = mStreet.getText().toString();
                String houseNumber = mHousenumber.getText().toString();
                String zipcode = mZipcode.getText().toString();
                String city = mCity.getText().toString();
                String email = mEmail.getText().toString();

                Contact newContact = new Contact(firstName, lastName, phoneNumber, street, houseNumber, zipcode, city, email);
                System.out.println(newContact.toString());

                Snackbar.make(view, newContact.getFirstName(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}
