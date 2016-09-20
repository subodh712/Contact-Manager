package com.example.subodh.conatctmanager;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;


import java.util.ArrayList;

/**
 * Created by Taranpreet Singh on 20/09/2016.
 */
public class ContactsManager extends Activity {

    public String ContactName = "";
    public String ContactTelefonNr = "";
    public String ContactTimesContact = "";
    public int Contact_ID=0;

    public void getAllAndroidContacts(){
        ArrayList<ContactsManager> contacts = new ArrayList<>();

        //Default Contacts Cursor
        Cursor D_Contacs = null;

        ContentResolver contentResolver = getContentResolver();

        try{
            D_Contacs = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        }catch(Exception e){
            Log.e("Holy Crap ERROR ! :", e.getMessage());
        }

        if(D_Contacs.getCount() > 0){
            while(D_Contacs.moveToNext()){
                ContactsManager contactsManager = new ContactsManager();

                String Contact_ID = D_Contacs.getString(D_Contacs.getColumnIndex(ContactsContract.Contacts._ID));
                String Contacts_DisplayName = D_Contacs.getString(D_Contacs.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String Contacts_Number =  D_Contacs.getString(D_Contacs.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String Contacts_TimesContacted = D_Contacs.getString(D_Contacs.getColumnIndex(ContactsContract.Contacts.TIMES_CONTACTED));

                contactsManager.ContactName = Contacts_DisplayName;

               int hasPhoneNumber = Integer.parseInt(D_Contacs.getString(D_Contacs.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if(hasPhoneNumber > 0){
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{Contact_ID}, null);

                    while(phoneCursor.moveToNext()){
                        String phoneNumber =  phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        contactsManager.ContactTelefonNr = phoneNumber;
                    }
                    phoneCursor.close();
                }
                contacts.add(contactsManager);
            }
        }
    }
}
