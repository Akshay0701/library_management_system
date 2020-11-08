package com.example.library_management_system.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.library_management_system.Model.Book;
import com.example.library_management_system.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Update_Book_Detail extends AppCompatActivity {

    EditText addBook_bookName,addBook_bookAuthor,addBook_availble,addBook_location;
    Button upDate_bookBtn;
    ImageView imageIv;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //image picked will be saved in this
    Uri image_rui=null;

    //permission constants
    private static final int CAMERA_REQUEST_CODE =100;
    private static final int STORAGE_REQUEST_CODE =200;


    //permission constants
    private static final int IMAGE_PICK_CAMERA_CODE =300;
    private static final int IMAGE_PICK_GALLERY_CODE=400;

    //permission array
    String[] cameraPermessions;
    String[] storagePermessions;

    //progresses bar
    ProgressDialog pd;

    Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__book__detail);
        addBook_bookName=findViewById(R.id.addBook_bookName);
        addBook_bookAuthor=findViewById(R.id.addBook_bookAuthor);
        addBook_availble=findViewById(R.id.addBook_availble);
        addBook_location=findViewById(R.id.addBook_location);
        upDate_bookBtn=findViewById(R.id.upDate_bookBtn);
        imageIv=findViewById(R.id.selected_img);

        pd= new ProgressDialog(this);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        assert bundle != null;
        book= (Book) bundle.getSerializable("BookObject");

        //set
        addBook_location.setText(book.getLocation());
        addBook_availble.setText(book.getAvailable());
        addBook_bookAuthor.setText(book.getAuthor());
        addBook_bookName.setText(book.getName());
//        bookId.setText(book.getbId());
        Picasso.get().load(book.getImageUrl()).into(imageIv);

        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Books");

        //init permissions
        cameraPermessions=new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermessions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //image
        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show image dialog
                //    Toast.makeText(AddPostActivity.this, "hey", Toast.LENGTH_SHORT).show();

                showImageDialog();
                //showDialogtoSelect();
            }
        });


        upDate_bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

    }
    void checkValidation(){
        String Name,Location,Author,Available;
        Name=addBook_bookName.getText().toString();
        Location=addBook_location.getText().toString();
        Author=addBook_bookAuthor.getText().toString();
        Available=addBook_availble.getText().toString();
        if(Name.isEmpty()){
            Toast.makeText(this, "Book name is empty", Toast.LENGTH_SHORT).show();
        }else if(Location.isEmpty()){
            Toast.makeText(this, "Location is empty", Toast.LENGTH_SHORT).show();
        }else if(Author.isEmpty()){
            Toast.makeText(this, "Author name is empty", Toast.LENGTH_SHORT).show();
        }else if(Available.isEmpty()){
            Toast.makeText(this, "Available must be Yes or No", Toast.LENGTH_SHORT).show();
        }else if(image_rui==null){
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
        }
        else{
            startUpdating(Name,Location,Author,Available);
        }
    }

    private void startUpdating(final String name, final String location, final String author, final String available) {
        pd.setMessage("Uploading Image");
        pd.show();
        StorageReference mPictureRef=FirebaseStorage.getInstance().getReferenceFromUrl(book.getImageUrl());
        mPictureRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        pd.setMessage("publishing post...");

                        pd.show();
                        final String timestamp= String.valueOf(System.currentTimeMillis());
                        String filePathName="Posts/"+"post_"+timestamp;

                        Bitmap bitmap=((BitmapDrawable)imageIv.getDrawable()).getBitmap();

                        ByteArrayOutputStream bout=new ByteArrayOutputStream();
                        //image compress
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,bout);
                        byte[] data=bout.toByteArray();

                        StorageReference ref= FirebaseStorage.getInstance().getReference().child(filePathName);


                        ref.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                                while(!uriTask.isSuccessful());

                                String downloadUri=uriTask.getResult().toString();

                                if(uriTask.isSuccessful()){
                                    //uri is received upload post to firebase database
                                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Books");

                                    Map<String, Object> hashMap=new HashMap<>();
                                    //put info
                                    hashMap.put("Name",name);
                                    hashMap.put("Location",location);
                                    hashMap.put("Author",author);
                                    hashMap.put("bId",book.getbId());
                                    //  hashMap.put("pId",timestamp);
                                    hashMap.put("Available",available);
                                    hashMap.put("ImageUrl",downloadUri);
                                    // hashMap.put("pTime",timestamp);

                                    ref.child(book.getbId()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            pd.dismiss();
                                            Toast.makeText(Update_Book_Detail.this, "Book Uploaded", Toast.LENGTH_SHORT).show();

                                            //reset view
                                            imageIv.setImageURI(null);
                                            image_rui=null;

                                            startActivity(new Intent(Update_Book_Detail.this,AdminDashBorad.class));

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(Update_Book_Detail.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            pd.dismiss();

                                        }
                                    });

                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }
    private void showImageDialog() {


        String[] options={"Camera","Gallery"};

        //dialog box
        AlertDialog.Builder builder=new AlertDialog.Builder(Update_Book_Detail.this);

        builder.setTitle("Choose Action");



        Toast.makeText(this, " reached", Toast.LENGTH_SHORT).show();
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0){
                    //camera clicked
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        pickFromCamera();
                    }
                }
                if(which==1){
                    //camera clicked

                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }


    private void pickFromCamera() {

        ContentValues cv=new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION,"Temp Descr");
        image_rui=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,cv);


        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_rui);
        startActivityForResult(intent,IMAGE_PICK_CAMERA_CODE);
    }

    private void pickFromGallery() {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }


    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermessions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

        return result&&result1;
    }


    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermessions,CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{

                if(grantResults.length>0){
                    boolean cameraAccepted=grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted=grantResults[1]== PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted&&storageAccepted){

                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this, "camera  & gallery both permission needed", Toast.LENGTH_SHORT).show();

                    }
                }
                else{

                }




            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean storageAccepted=grantResults[1]== PackageManager.PERMISSION_GRANTED;

                    if(storageAccepted){

                        pickFromGallery();
                    }
                    else {
                        Toast.makeText(this, "gallery both permission needed", Toast.LENGTH_SHORT).show();

                    }
                }
                else{

                }

            }
            break;
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==IMAGE_PICK_GALLERY_CODE){
                image_rui=data.getData();

                imageIv.setImageURI(image_rui);
            }
            else if(requestCode==IMAGE_PICK_CAMERA_CODE){

                imageIv.setImageURI(image_rui);

            }
        }
    }
}