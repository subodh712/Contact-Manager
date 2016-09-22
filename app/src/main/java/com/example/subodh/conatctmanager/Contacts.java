package com.example.subodh.conatctmanager;

import android.app.SearchManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class Contacts extends AppCompatActivity {

    SearchView searchView;
    ListView contact_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ContactsManager contactsManager = new ContactsManager(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String firstName[];// = {"Udit", "Taran", "Subodh", "Sharan", "Rahul", "Baba", "Burger", "Tikki"};
        String numbers[] ;//= {"1234", "12345", "123456", "12331", "313132", "23232", "13231", "23323"};


        firstName=contactsManager.allName();
        numbers=contactsManager.allNumber();

        contact_list_view = (ListView) findViewById(R.id.contact_list_view);
        CustomAdapter contactAdapter = new CustomAdapter(getApplicationContext(), R.layout.contact_list_item,firstName, numbers);
        contact_list_view.setAdapter(contactAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));




       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String[] aNameFromContacts;
                String[] aNumberFromContacts;

                Cursor contacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,"DISPLAY_NAME ASC");
                aNameFromContacts = new String[contacts.getCount()];
                aNumberFromContacts = new String[contacts.getCount()];

                int nameFieldColumnIndex = contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int numberFieldColumnIndex = contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int l=0;
                while(contacts.moveToNext()) {

                    String contactName = contacts.getString(nameFieldColumnIndex);
                    String contactNameTemp = contactName.toLowerCase();
                    newText=newText.toLowerCase();
                    if(contactNameTemp.contains(newText))
                    {
                        aNameFromContacts[l] =    contactName ;

                        String number = contacts.getString(numberFieldColumnIndex);
                        aNumberFromContacts[l] =    number ;
                        l++;
                    }

                }

                String[] name=new String[l];
                String[] phone = new String[l];
                for(int k=0;k<l;k++)
                {
                    name[k]=aNameFromContacts[k];
                    phone[k]=aNumberFromContacts[k];
                }
                contacts.close();

                contact_list_view.setAdapter(null);
                CustomAdapter contactAdapter = new CustomAdapter(getApplicationContext(), R.layout.contact_list_item,name, phone);
                contact_list_view.setAdapter(contactAdapter);
                return false;


            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
