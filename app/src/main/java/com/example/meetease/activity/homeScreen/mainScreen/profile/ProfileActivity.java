package com.example.meetease.activity.homeScreen.mainScreen.profile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
import com.example.meetease.BaseClass;
import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.dataModel.EditUserResponse;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;

import java.io.ByteArrayOutputStream;
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

public class ProfileActivity extends BaseClass {

    ImageView ivBack, imgEdit;
    CircleImageView imgProfileImage;
    Tools tools;
    MultipartBody.Part imagePart = null;
    Context context = ProfileActivity.this;
    PreferenceManager preferenceManager;
    EditText etvFullName, etvPhoneNo, etvEmail;
    Button btnSave;
    String currentPhotoPath = "";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int CAMERA_PERMISSION_REQUEST = 101;
    Bitmap imageBitmap;
    RestCall restCall;
    String id, userPassword,profileImage;

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

        Tools.DisplayImage(this,imgProfileImage,preferenceManager.getKeyValueString(VariableBag.image,""));
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
                    if (checkAndRequestPermission(context)) {
                        openImageDialog(context);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }





    void editUser() {
        tools.showLoading();

        RequestBody tag = RequestBody.create(MediaType.parse("text/plain"), "UpdateUser");
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), preferenceManager.getKeyValueString(VariableBag.user_id, ""));
        RequestBody full_name = RequestBody.create(MediaType.parse("text/plain"), etvFullName.getText().toString());
        RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), etvPhoneNo.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etvEmail.getText().toString());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), userPassword);

        RequestBody imageRequestBody ;


        if (imageBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            imageRequestBody = RequestBody.create(MediaType.parse("image/*"), byteArray);
            imagePart = MultipartBody.Part.createFormData("profile_photo1", "image.jpg", imageRequestBody);
        }

        restCall.EditUser(tag, user_id, full_name, mobile, email, password,imagePart)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<EditUserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        tools.stopLoading();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Tools.showCustomToast(getApplicationContext(), "No Internet", findViewById(R.id.customToastLayout), getLayoutInflater());
                            }
                        });
                    }

                    @Override
                    public void onNext(EditUserResponse userResponse) {
                        tools.stopLoading();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (userResponse.getStatus().equals(VariableBag.SUCCESS_RESULT)) {

                                    Tools.DisplayImage(ProfileActivity.this,imgProfileImage,preferenceManager.getKeyValueString(VariableBag.image,""));
                                    preferenceManager.setKeyValueString(VariableBag.image,userResponse.getProfile_photo());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            assert selectedImage != null;
            profileImage = selectedImage.toString();
            Tools.DisplayImage(context,imgProfileImage,profileImage);

            // Convert the selected image URI to a Bitmap
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                imageBitmap = (Bitmap) extras.get("data");

                /*// Use Glide to load and display the Bitmap
                Glide.with(this).load(imageBitmap).into(b.ivProfileImage);*/

                // Convert the Bitmap to a URI
                assert imageBitmap != null;
                Uri imageUri = bitmapToUri(context, imageBitmap);
                profileImage = imageUri.toString();
                Glide.with(context).load(profileImage).into(imgProfileImage);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // For Android 13 and above, check both camera and media images permissions
                    boolean cameraPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean mediaImagesPermissionGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraPermissionGranted && mediaImagesPermissionGranted) {
                        // Both permissions granted, you can proceed
                        openImageDialog(context); // Or any other action you need
                    } else {
                        // Handle the case where one or both permissions were denied
                        if (!cameraPermissionGranted) {
                            // Permission denied for camera
                            Toast.makeText(context, "Please grant permission to access the camera.", Toast.LENGTH_SHORT).show();
                        }
                        if (!mediaImagesPermissionGranted) {
                            // Permission denied for media images
                            Toast.makeText(context, "Please grant permission to access media images.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // For Android versions less than or equal to Android 12, check media images and external storage permissions
                    boolean cameraPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean externalStoragePermissionGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraPermissionGranted && externalStoragePermissionGranted) {
                        // Both permissions granted, you can proceed
                        openImageDialog(context); // Or any other action you need
                    } else {
                        // Handle the case where one or both permissions were denied
                        if (!cameraPermissionGranted) {
                            // Permission denied for media images
                            Toast.makeText(context, "Please grant permission to access media images.", Toast.LENGTH_SHORT).show();
                        }
                        if (!externalStoragePermissionGranted) {
                            // Permission denied for external storage
                            Toast.makeText(context, "Please grant permission to access external storage.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}