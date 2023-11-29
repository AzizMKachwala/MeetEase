package com.example.meetease.homeScreen;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivBack, imgEdit;
    CircleImageView imgProfileImage;
    Tools tools;
    PreferenceManager preferenceManager;
    EditText etvFullName, etvPhoneNo, etvEmail;
    Button btnSave;
    String currentPhotoPath = "";
    int REQUEST_CAMERA_PERMISSION = 101;
    ActivityResultLauncher<Intent> cameraLauncher;
    File currentPhotoFile;
    RestCall restCall;
    String id, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tools = new Tools(this);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        ivBack = findViewById(R.id.ivBack);
        imgEdit = findViewById(R.id.imgEdit);
        btnSave = findViewById(R.id.btnSave);
        etvFullName = findViewById(R.id.etvFullName);
        etvPhoneNo = findViewById(R.id.etvPhoneNo);
        etvEmail = findViewById(R.id.etvEmail);
        imgProfileImage = findViewById(R.id.imgProfileImage);

        preferenceManager = new PreferenceManager(this);
        id = preferenceManager.getKeyValueString(VariableBag.user_id, "");
        etvFullName.setText(preferenceManager.getKeyValueString(VariableBag.full_name, ""));
        etvEmail.setText(preferenceManager.getKeyValueString(VariableBag.email, ""));
        etvPhoneNo.setText(preferenceManager.getKeyValueString(VariableBag.mobile, ""));
        userPassword = preferenceManager.getKeyValueString(VariableBag.password, "");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etvFullName.getText().toString().isEmpty()) {
                    etvFullName.setError("Enter Name");
                    etvFullName.requestFocus();
                } else if (etvFullName.getText().toString().length() < 2) {
                    etvFullName.setError("Enter Valid Name");
                    etvFullName.requestFocus();
                } else if (etvPhoneNo.getText().toString().isEmpty()) {
                    etvPhoneNo.setError("Enter Mobile Number");
                    etvPhoneNo.requestFocus();
                } else if (etvPhoneNo.length() != 10) {
                    etvPhoneNo.setError("Enter Mobile Number with 10 Digits");
                    etvPhoneNo.requestFocus();
                } else if (etvEmail.getText().toString().isEmpty()) {
                    etvEmail.setError("Email Address cannot be Empty");
                    etvEmail.requestFocus();
                } else if (!Tools.isValidEmail(etvEmail.getText().toString())) {
                    etvEmail.setError("Email Address must contain @ and .com in it");
                    etvEmail.requestFocus();
                } else {
                    editUser();
                }
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    currentPhotoPath = "";
                    if (checkCameraPermission()) {
                        openCamera();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                // Camera capture was successful, handle the result.
                Tools.DisplayImage(ProfileActivity.this, imgProfileImage, currentPhotoPath);
            } else {
                Toast.makeText(ProfileActivity.this, "Error Loading Photo. Please Click Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(ProfileActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(ProfileActivity.this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(ProfileActivity.this, "com.example.meetease", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraLauncher.launch(takePictureIntent);
            }
        }

    }


    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = ProfileActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoFile = image;
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    void editUser() {
        tools.showLoading();
        RequestBody tag = RequestBody.create(MediaType.parse("text/plain"), "UpdateUser");
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody full_name = RequestBody.create(MediaType.parse("text/plain"), etvFullName.getText().toString());
        RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), etvPhoneNo.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etvEmail.getText().toString());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), userPassword);
        MultipartBody.Part fileToUploadfile = null;
        if (fileToUploadfile == null && currentPhotoPath != null) {
            try {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                File file = new File(currentPhotoPath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                fileToUploadfile = MultipartBody.Part.createFormData("product_image", file.getName(), requestBody);
            } catch (Exception e) {
                Toast.makeText(this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        restCall.EditUser(tag, user_id, full_name, mobile, email, password, fileToUploadfile)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        tools.stopLoading();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProfileActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        tools.stopLoading();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(ProfileActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (userResponse.getStatus().equals(VariableBag.SUCCESS_RESULT)) {
                                    preferenceManager.setKeyValueString(VariableBag.full_name, etvFullName.getText().toString());
                                    preferenceManager.setKeyValueString(VariableBag.mobile, etvPhoneNo.getText().toString());
                                    preferenceManager.setKeyValueString(VariableBag.email, etvEmail.getText().toString());
                                    finish();
                                }
                            }
                        });
                    }
                });

    }
}