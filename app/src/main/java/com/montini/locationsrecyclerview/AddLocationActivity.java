package com.montini.locationsrecyclerview;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.montini.locationsrecyclerview.model.Location;
import com.squareup.picasso.Picasso;

public class AddLocationActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "AddLocationActivity";
    static final int IMAGE_REQUEST_CODE = 101;

    // vars
    private EditText name, address, maxCourts;
    private Button btnSave;
    private ImageView logo;
    private Location cLocation;
    private Toolbar toolbar;
    private int position;

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
        Log.d(TAG, "onCreate: has parcel? " + intent.hasExtra("location"));
        cLocation = intent.getParcelableExtra("location");
        position = intent.getIntExtra("position", -1);

        if (cLocation != null) {
            Picasso.with(this).setLoggingEnabled(true);
            Picasso.with(this).load(cLocation.getLogo()).error(R.drawable.placeholder_camera).resize(480, 480).centerCrop().into(logo);
            Log.d(TAG, "onCreate: path of image: " + cLocation.getLogo());
            name.setText(cLocation.getName());
            address.setText(cLocation.getAddress());
            maxCourts.setText(String.valueOf(cLocation.getMaxCourts()));
            Toast.makeText(this,cLocation.getLogo().toString(), Toast.LENGTH_LONG).show();
        } else cLocation = new Location();
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
        if (isContentChanged) {

            cLocation.setName(String.valueOf(name.getText()));
            cLocation.setAddress(String.valueOf(address.getText()));
            cLocation.setMaxCourts(Integer.parseInt(maxCourts.getText().toString()));

            returnIntent.putExtra("location", (Parcelable) cLocation);
            returnIntent.putExtra("position", position);
            setResult(RESULT_OK, returnIntent);

        } else setResult(RESULT_CANCELED, returnIntent);

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
            logo.setTag(selectedImage);
            cLocation.setLogo(selectedImage);
            logo.setImageURI(selectedImage);
            // writeUsingOutputStream(selectedImage.toString());
            Picasso.with(this).load(cLocation.getLogo()).resize(480, 480).centerInside().into(logo);
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
