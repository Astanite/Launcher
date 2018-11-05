package com.astanite.launcher;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends ListActivity {
    private List<ContactInfo> contactsList;
    public List<ContactInfo> selectedContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactsList = new ArrayList<ContactInfo>();
        selectedContacts = new ArrayList<ContactInfo>();
    }

    public void getContactList(View view) {
        Log.i("MYINFO", "Button clicked");
        ListView listView = getListView();
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);

        //--	text filtering
        listView.setTextFilterEnabled(true);

        ContentResolver cr = view.getContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, "DISPLAY_NAME ASC");

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        this.contactsList.add(new ContactInfo(name, phoneNo));
                        // Log.i(TAG, "Name: " + name);
                        // Log.i(TAG, "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }

        setListAdapter(new ArrayAdapter<ContactInfo>(this, android.R.layout.simple_list_item_checked, contactsList));
    }

    public void onListItemClick(ListView parent, View v, int position,long id){
        Log.i("MY_INFO", "List Item Clicked");
        ContactInfo ci = contactsList.get(position);

        if (selectedContacts.indexOf(ci) != -1) {
            selectedContacts.remove(ci);
        } else {
            selectedContacts.add(ci);
        }
    }

    public List<ContactInfo> getImportantContacts(View view) {
        Log.i("MY_INFO", "Length of contacts: " + selectedContacts.size());
        return selectedContacts;
    }
}
