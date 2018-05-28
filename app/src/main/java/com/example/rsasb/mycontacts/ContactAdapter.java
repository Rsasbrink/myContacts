package com.example.rsasb.mycontacts;

import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    final private ContactClickListener mContactClickListener;
    private List<Contact> mContacts;

    public ContactAdapter(ContactClickListener mContactClickListener, List<Contact> mContacts) {
        this.mContactClickListener = mContactClickListener;
        this.mContacts = mContacts;
    }

    public interface ContactClickListener{
        void ContactOnClick(int id);
    }
@Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, final int position) {
        Contact contact =  mContacts.get(position);
        holder.textView.setText(contact.getFirstName());

    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

   public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        android.content.Context context = parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(android.R.layout.simple_list_item_1, null);

        // Return a new holder instance
        ContactAdapter.ViewHolder viewHolder = new ContactAdapter.ViewHolder(view);
        return viewHolder;
    }
     public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        public TextView textView;

        public ViewHolder(View itemView) {

            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mContactClickListener.ContactOnClick(clickedPosition);
        }
    }

     public void swapList (List<Contact> newList) {

        mContacts = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

}
