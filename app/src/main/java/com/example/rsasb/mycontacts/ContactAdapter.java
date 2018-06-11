package com.example.rsasb.mycontacts;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    final private ContactClickListener mContactClickListener;
    private List<Contact> mContacts;

    public ContactAdapter(ContactClickListener mContactClickListener, List<Contact> mContacts) {
        this.mContactClickListener = mContactClickListener;
        this.mContacts = mContacts;
    }

    public interface ContactClickListener {
        void ContactOnClick(int id);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, final int position) {
        Contact contact = mContacts.get(position);

        holder.name.setText(contact.getFullName());
    }

    public void swapList(List<Contact> newList) {
        mContacts = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mContactClickListener.ContactOnClick(clickedPosition);
        }
    }
}
