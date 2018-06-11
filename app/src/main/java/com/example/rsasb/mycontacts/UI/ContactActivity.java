package com.example.rsasb.mycontacts.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.rsasb.mycontacts.AppDatabase;
import com.example.rsasb.mycontacts.Contact;
import com.example.rsasb.mycontacts.ContactAdapter;
import com.example.rsasb.mycontacts.R;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements ContactAdapter.ContactClickListener {

    public final static int TASK_GET_ALL_CONTACTS = 0;
    public final static int TASK_DELETE_CONTACT = 1;
    public final static int TASK_UPDATE_CONTACT = 2;
    public final static int TASK_INSERT_CONTACT = 3;

    //Local variables
    private ContactAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private static List<Contact> mContacts = new ArrayList<>();

    //Database related local variables

    static AppDatabase db;

    public static final String VIEW_CONTACT = "";
    public static final int REQUESTCODE = 1234;
    private int mModifyPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getInstance(this);

        mRecyclerView = findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ContactAdapter(this, mContacts);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        new ContactAsyncTask(TASK_GET_ALL_CONTACTS).execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, CreateActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });

    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new ContactAdapter(this, mContacts);
            mRecyclerView.setAdapter(mAdapter);
        } else {

            mAdapter.swapList(mContacts);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.All_contacts) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUESTCODE) {
            if (resultCode == RESULT_OK) {

//                //Because the updatedReminder object is a new object with a different id, we just change the text of the current one
//                //So the database can recognize the Reminder by id and update it
//                Contact updatedReminder = data.getParcelableExtra(MainActivity.EXTRA_CONTACT);
//                mContacts.get(mModifyPosition).setReminderText(updatedReminder.getReminderText());
//
////                new ReminderAsyncTask(TASK_UPDATE_CONTACT).execute(mContacts.get(mModifyPosition));
            }
        }
    }

    public void onContactDbUpdated(List list) {
        mContacts = list;
        updateUI();
    }

    @Override
    public void ContactOnClick(int id) {
        Intent intent = new Intent(ContactActivity.this, ShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact", mContacts.get(id));
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUESTCODE);
    }

    public class ContactAsyncTask extends AsyncTask<Contact, Void, List> {

        private int taskCode;

        public ContactAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
        protected List doInBackground(Contact... contacts) {
            switch (taskCode) {
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

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onContactDbUpdated(list);
            mAdapter.notifyDataSetChanged();
        }
    }
}



