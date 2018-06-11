package com.example.rsasb.mycontacts.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rsasb.mycontacts.AppDatabase;
import com.example.rsasb.mycontacts.Contact;
import com.example.rsasb.mycontacts.R;

import java.util.List;


public class CreateActivity extends AppCompatActivity {
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

                Contact newContact = new Contact(firstName, lastName, phoneNumber, street, houseNumber, zipcode, city, email);


               long id =  db.contactDao().insert(newContact);
                Contact createdContact = db.contactDao().findById(id);
                Intent intent = new Intent(CreateActivity.this, ShowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", createdContact);
                intent.putExtras(bundle);

                startActivityForResult(intent, 0);
            }
        });
    }
    public class ContactAsyncTask  extends AsyncTask<Contact, Void, List> {

        private int taskCode;

        public ContactAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
              protected List doInBackground(Contact... contacts) {
            switch (taskCode){
                case TASK_DELETE_CONTACT:
                    db.contactDao().delete(contacts[0]);
                    break;
                case TASK_UPDATE_CONTACT:
                    db.contactDao().update(contacts[0]);
                    break;
                case TASK_INSERT_CONTACT:
                    db.contactDao().insert(contacts[0]);
                    break;
            }

            //To return a new list with the updated data, we get all the data from the database again.
            return db.contactDao().getAll();
        }

    }
}
