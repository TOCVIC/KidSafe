package com.mansourappdevelopment.androidapp.kidsafe.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mansourappdevelopment.androidapp.kidsafe.R;
import com.mansourappdevelopment.androidapp.kidsafe.interfaces.OnContactClickListener;
import com.mansourappdevelopment.androidapp.kidsafe.models.Contact;
import com.mansourappdevelopment.androidapp.kidsafe.utils.BackgroundGenerator;
import com.mansourappdevelopment.androidapp.kidsafe.utils.Constant;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsAdapterViewHolder> {
    private Context context;
    private ArrayList<Contact> contacts;
    private OnContactClickListener onContactClickListener;

    public void setOnContactClickListener(OnContactClickListener onContactClickListener) {
        this.onContactClickListener = onContactClickListener;
    }

    public ContactsAdapter(ArrayList<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    public class ContactsAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView txtContactName;
        private TextView txtContactNumber;
        private ImageButton imgBtnCall;
        private ImageButton imgBtnMessage;
        private TextView txtContactBackground;

        public ContactsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContactName = (TextView) itemView.findViewById(R.id.txtContactName);
            txtContactNumber = (TextView) itemView.findViewById(R.id.txtContactNumber);
            txtContactBackground = (TextView) itemView.findViewById(R.id.txtContactBackground);
            imgBtnCall = (ImageButton) itemView.findViewById(R.id.imgBtnCall);
            imgBtnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onContactClickListener.onCallClick(contacts.get(getAdapterPosition()).getContactNumber());
                }
            });

            imgBtnMessage = (ImageButton) itemView.findViewById(R.id.imgBtnMessage);
            imgBtnMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onContactClickListener.onMessageClick(contacts.get(getAdapterPosition()).getContactNumber());
                }
            });

        }
    }


    @NonNull
    @Override
    public ContactsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_contact, viewGroup, false);
        return new ContactsAdapter.ContactsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapterViewHolder contactsAdapterViewHolder, int i) {
        Contact contact = contacts.get(i);
        contactsAdapterViewHolder.txtContactName.setText(contact.getContactName());
        contactsAdapterViewHolder.txtContactNumber.setText(contact.getContactNumber());
        contactsAdapterViewHolder.txtContactBackground.setText(BackgroundGenerator.getFirstCharacters(contact.getContactName()));
        contactsAdapterViewHolder.txtContactBackground.setBackground(BackgroundGenerator.getBackground(context));
    }

    @Override
    public int getItemCount() {
        return contacts.size();

    }


}
