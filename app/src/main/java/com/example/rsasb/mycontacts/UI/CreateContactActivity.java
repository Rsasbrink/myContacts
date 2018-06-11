package com.example.rsasb.mycontacts.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rsasb.mycontacts.AppDatabase;
import com.example.rsasb.mycontacts.Contact;
import com.example.rsasb.mycontacts.R;

import java.util.List;


public class CreateContactActivity extends AppCompatActivity {
    public final static int TASK_GET_ALL_CONTACTS = 0;
    public final static int TASK_DELETE_CONTACT = 1;
    public final static int TASK_UPDATE_CONTACT = 2;
    public final static int TASK_INSERT_CONTACT = 3;
    private EditText mFirstName, mLastName, mPhone, mStreet, mHousenumber, mZipcode, mCity, mEmail;

    static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        db = AppDatabase.getInstance(this);

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

                if (firstName.trim().isEmpty() || firstName == null) {
                    Snackbar.make(view, R.string.warning_empty_first_name, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Contact newContact = new Contact(firstName, lastName, phoneNumber, street, houseNumber, zipcode, city, email);

                    // Insert new contact and retrieve the ID
                    long id = db.contactDao().insert(newContact);
                    // Get new made contact to pass it to the Contact show activity
                    Contact createdContact = db.contactDao().findById(id);
                    Intent intent = new Intent(CreateContactActivity.this, ShowContactActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("contact", createdContact);
                    intent.putExtras(bundle);

                    startActivityForResult(intent, 0);
                }
            }
        });
    }

}
