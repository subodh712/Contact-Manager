package com.example.subodh.conatctmanager;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;


/**
 * Created by Taranpreet Singh on 20/09/2016.
 */
public class ContactsManager {

    String aNameFromContacts[];
    String aNumberFromContacts[];

    public ContactsManager(Context context)
    {
        Cursor contacts = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,"DISPLAY_NAME ASC");
        aNameFromContacts = new String[contacts.getCount()];
        aNumberFromContacts = new String[contacts.getCount()];
        int i = 0;

        int nameFieldColumnIndex = contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int numberFieldColumnIndex = contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        while(contacts.moveToNext()) {

            String contactName = contacts.getString(nameFieldColumnIndex);
            aNameFromContacts[i] =    contactName ;

            String number = contacts.getString(numberFieldColumnIndex);
            aNumberFromContacts[i] =    number ;
            i++;
        }

        contacts.close();
    }

    public String[] allName()
    {
        return aNameFromContacts;
    }
    public String[] allNumber()
    {
        return aNumberFromContacts;
    }

}
