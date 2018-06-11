package com.example.rsasb.mycontacts.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rsasb.mycontacts.Contact;
import com.example.rsasb.mycontacts.R;

public class ShowActivity extends AppCompatActivity {
    public static final String EDIT_CONTACT = "";
    public static final int REQUESTCODE = 1234;
    private TextView mFirstName, mLastName, mPhone, mStreet, mHousenumber, mZipcode, mCity, mEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        System.out.println("----------------------------------------------");

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
        Button editButton =  findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowActivity.this, EditActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", contact);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUESTCODE);

            }
        });
    }
}
