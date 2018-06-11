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

    //Local variables
    private ContactAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static List<Contact> mContacts = new ArrayList<>();
    static AppDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        // declaring database
        db = AppDatabase.getInstance(getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ContactAdapter(this, mContacts);
        mRecyclerView.setAdapter(mAdapter);
        new ContactAsyncTask().execute();

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
    // Start intent to the show activity, adding a contact.
    @Override
    public void ContactOnClick(int id) {
        Intent intent = new Intent(getActivity(), ShowContactActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact", mContacts.get(id));
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    // Class to retreive all our contacts which notifies our recyclerview
    public class ContactAsyncTask extends AsyncTask<Contact, Void, List> {
        @Override
        protected List doInBackground(Contact... contacts) {
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
