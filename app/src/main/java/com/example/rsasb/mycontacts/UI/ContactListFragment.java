package com.example.rsasb.mycontacts.UI;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rsasb.mycontacts.AppDatabase;
import com.example.rsasb.mycontacts.Contact;
import com.example.rsasb.mycontacts.ContactAdapter;
import com.example.rsasb.mycontacts.ContactAdapter.ContactClickListener;
import com.example.rsasb.mycontacts.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment implements ContactClickListener {

    public final static int TASK_GET_ALL_CONTACTS = 0;
    public final static int TASK_DELETE_CONTACT = 1;
    public final static int TASK_UPDATE_CONTACT = 2;
    public final static int TASK_INSERT_CONTACT = 3;
    public static final int DELETED_CONTACT = 2;
    public static final int BACK_PRESSED = 1;

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

    public ContactListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        // Inflate the layout for this fragment
        db = AppDatabase.getInstance(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        new ContactAsyncTask(TASK_GET_ALL_CONTACTS).execute();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ContactAdapter(this, mContacts);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setAdapter(mAdapter);
        ((RecyclerView) view.findViewById(R.id.recyclerView)).setAdapter(mAdapter);


        return view;
    }
    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new ContactAdapter(this, mContacts);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mContacts);
        }
    }

    public void onContactDbUpdated(List list) {
        mContacts = list;
        updateUI();
    }

    @Override
    public void ContactOnClick(int id) {
        Intent intent = new Intent(getActivity(), ShowContactActivity.class);
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
