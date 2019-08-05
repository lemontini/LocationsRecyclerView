package com.montini.locationsrecyclerview;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.montini.locationsrecyclerview.model.Location;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AddLocationActivity extends AppCompatActivity {

    EditText name, address, maxCourts;
    Button btnSave;
    ImageView logo;
    Location cLocation;
    Toolbar toolbar;

    static final int GALLERY_REQUEST_CODE = 02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        logo = findViewById(R.id.choose_logo_Image);
        name = findViewById(R.id.editText1);
        address = findViewById(R.id.editText2);
        maxCourts = findViewById(R.id.editText3);
        btnSave = findViewById(R.id.buttonSave);

        Intent intent = getIntent();
        cLocation = (Location) intent.getParcelableExtra("locations");

        if (cLocation != null) {
            Picasso.with(this).load(cLocation.getLogo()).resize(480, 480).centerCrop().into(logo);
            name.setText(cLocation.getName());
            address.setText(cLocation.getAddress());
            maxCourts.setText(String.valueOf(cLocation.getMaxCourts()));
        }
    }

    public void btnSave_Click(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("name", name.getText().toString());
        returnIntent.putExtra("address", address.getText().toString());
        returnIntent.putExtra("maxCourts", maxCourts.getText().toString());
        returnIntent.putExtra("logo", logo.getTag().toString());
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void logo_Click(View v) {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_REQUEST_CODE:
                Uri selectedImage = data.getData();
                logo.setTag(Uri.parse(selectedImage.toString()).getPath());
                logo.setImageURI(selectedImage);
                // writeUsingOutputStream(selectedImage.toString());
                Picasso.with(this).load(selectedImage).resize(480, 480).centerCrop().into(logo);
                break;
        }
    }

    // void saveLogoToChache(String logoFileName) {
    //         FileWriter cache = new FileWriter("cache/" + logoFileName);
    // }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    // public void writeUsingOutputStream(String data) {
    //     OutputStream os = null;
    //     try {
    //         os = new FileOutputStream(new File(getFileName(data)));
    //         os.write(data.getBytes(), 0, data.length());
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }finally{
    //         try {
    //             os.close();
    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

}
