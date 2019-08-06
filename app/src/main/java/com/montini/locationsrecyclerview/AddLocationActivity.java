package com.montini.locationsrecyclerview;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.montini.locationsrecyclerview.model.Location;
import com.squareup.picasso.Picasso;

public class AddLocationActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "AddLocationActivity";
    static final int IMAGE_REQUEST_CODE = 02;

    // vars
    EditText name, address, maxCourts;
    Button btnSave;
    ImageView logo;
    Location cLocation;
    Toolbar toolbar;

    private boolean isContentChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        logo = findViewById(R.id.choose_logo_Image);
        name = findViewById(R.id.editText1);
        name.addTextChangedListener(textWatcher);
        address = findViewById(R.id.editText2);
        address.addTextChangedListener(textWatcher);
        maxCourts = findViewById(R.id.editText3);
        maxCourts.addTextChangedListener(textWatcher);
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

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            isContentChanged = true;
        }
    };

    public void btnSave_Click(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("name", name.getText().toString());
        returnIntent.putExtra("address", address.getText().toString());
        returnIntent.putExtra("maxCourts", maxCourts.getText().toString());
        returnIntent.putExtra("logo", logo.getTag().toString());
        setResult((isContentChanged ? RESULT_OK : RESULT_CANCELED), returnIntent);
        finish();
    }

    public void logo_Click(View v) {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == IMAGE_REQUEST_CODE) && (resultCode == RESULT_OK)) {

            Uri selectedImage = data.getData();
            // logo.setTag(Uri.parse(selectedImage.toString()).getPath());
            logo.setTag(selectedImage);
            logo.setImageURI(selectedImage); // TODO: check if this is necessary
            // writeUsingOutputStream(selectedImage.toString());
            Picasso.with(this).load(selectedImage).resize(480, 480).centerCrop().into(logo);
            isContentChanged = true;
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
