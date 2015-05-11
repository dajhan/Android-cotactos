package com.dahjana.contactos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    EditText NombreTxt,TelefonoTxt,CorreoTxt,DireccionTxt;
    List<Contact> Contacts = new ArrayList<com.dahjana.contactos.Contact>();
    ListView ContactListView;
    ImageView ContactImageImgView;
    Uri imageUri = Uri.parse("android.resource://org.intracode.contactmanager/drawable/no_user_logo.png");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NombreTxt =(EditText) findViewById(R.id.NombreTxt);
        TelefonoTxt =(EditText) findViewById(R.id.TelefonoTxt);
        CorreoTxt =(EditText) findViewById(R.id.CorreoTxt);
        DireccionTxt =(EditText) findViewById(R.id.DireccionTxt);
        ContactListView = (ListView) findViewById(R.id.listView);
        ContactImageImgView = (ImageView) findViewById(R.id.contactImage);


        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creador");
        tabSpec.setContent(R.id.tabCreador);
        tabSpec.setIndicator("Creador");
        tabHost.addTab(tabSpec);

        tabSpec =  tabHost.newTabSpec("lista");
        tabSpec.setContent(R.id.tabContactList);
        tabSpec.setIndicator("Lista");
        tabHost.addTab(tabSpec);

        final Button addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact(NombreTxt.getText().toString(), TelefonoTxt.getText().toString(),CorreoTxt.getText().toString(), DireccionTxt.getText().toString(),imageUri);
                populateListView();

                Toast.makeText(getApplicationContext(), String.valueOf(NombreTxt.getText()) + " Contacto agregado correctamente a la lista.", Toast.LENGTH_SHORT).show();
            }
        });


        NombreTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(String.valueOf(NombreTxt.getText()).trim().length() > 0);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ContactImageImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Gallery"), 1);
            }

        });

    }
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                ContactImageImgView.setImageURI(data.getData());
            }
        }
    }


    private void populateListView() {
        ArrayAdapter<Contact> adapter = new ContactListAdapter();
        ContactListView.setAdapter(adapter);
    }

private void addContact(String nombre, String telefono, String email, String direccion, Uri imageURI){
    Contacts.add(new Contact(nombre, telefono, email, direccion,imageURI));
}


    private class ContactListAdapter extends ArrayAdapter<Contact> {
        public ContactListAdapter() {
            super(MainActivity.this, R.layout.listview_item, Contacts);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Contact currentContact = Contacts.get(position);


            TextView nombre = (TextView) view.findViewById(R.id.contactNombre);
            nombre.setText(currentContact.getNombre());
            TextView telefono = (TextView) view.findViewById(R.id.contactTelefono);
            telefono.setText(currentContact.getTelefono());
            TextView email = (TextView) view.findViewById(R.id.contactCorreo);
            email.setText(currentContact.getEmail());
            TextView direccion = (TextView) view.findViewById(R.id.contactDireccion);
            direccion.setText(currentContact.getDireccion());
            ImageView imageURI = (ImageView) view.findViewById(R.id.contactImageView);
            imageURI.setImageURI(currentContact.getImageURI());

            return view;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
