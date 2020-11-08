package com.example.library_management_system.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.library_management_system.Admin.adminLogin;
import com.example.library_management_system.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class userRegister extends AppCompatActivity {


    EditText user_login_name,user_login_email,user_login_password,user_login_PhoneNo,user_login_rollID;

    private FirebaseAuth mAuth;

    Button gotoLogin,registerBtn,gotoAdmin;

    //loading screen
    ScrollView scrollView;
    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        user_login_name=findViewById(R.id.user_login_name);
        user_login_email=findViewById(R.id.user_login_email);
        user_login_password=findViewById(R.id.user_login_password);
        user_login_PhoneNo=findViewById(R.id.user_login_PhoneNo);
        user_login_rollID=findViewById(R.id.user_login_rollID);

        gotoAdmin=findViewById(R.id.gotoAdmin);
        gotoLogin=findViewById(R.id.gotoLogin);
        registerBtn=findViewById(R.id.registerBtn);

        //screen loading
        loading=findViewById(R.id.loading);
        scrollView=findViewById(R.id.scrollable);
        loading.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        //init firebase
        mAuth = FirebaseAuth.getInstance();

        gotoLogin.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(userRegister.this, UserLogin.class));
            }
        });
        gotoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userRegister.this, adminLogin.class));
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user info
                String name,email,password,phone,rollId;
                email= user_login_email.getText().toString();
                name= user_login_name.getText().toString();
                password= user_login_password.getText().toString();
                phone= user_login_PhoneNo.getText().toString();
                rollId= user_login_rollID.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    user_login_email.setError("Invalided Email");
                    user_login_email.setFocusable(true);

                }
                else if(password.length()<6){
                    user_login_password.setError("Password length at least 6 characters");
                    user_login_password.setFocusable(true);
                }
                else if(name.isEmpty()){
                    user_login_name.setError("Name is empty");
                    user_login_name.setFocusable(true);
                }
                else if(phone.length()<10){
                    user_login_name.setError("PhoneNo length at least 10 characters");
                    user_login_name.setFocusable(true);
                }
                else if(rollId.length()<4){
                    user_login_name.setError("RollNo length at least 4 characters");
                    user_login_name.setFocusable(true);
                }
                else {
                    SharedPreferences.Editor editor;
                    editor= PreferenceManager.getDefaultSharedPreferences(userRegister.this).edit();
                    editor.putString("username", email.trim());
                    editor.putString("password", password.trim());
                    editor.apply();

                    registerUser(name,email,phone,password,rollId);

                }
            }
        });
    }

    private void registerUser(final String name, String email, final String phone, final String password, final String rollId) {
        loading.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // progressDialog.dismiss();
                            //back normal
                            loading.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);

                            FirebaseUser user = mAuth.getCurrentUser();

                            String email= user.getEmail();
                            String uid=user.getUid();
                            final HashMap<Object,String> hashMap=new HashMap<>();

                            //check if commander is allocated or  not
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Commanders");
                            hashMap.put("Name",name);
                            hashMap.put("Email",email);
                            hashMap.put("Phone",phone);
                            hashMap.put("Password",password);
                            hashMap.put("RollId",rollId);
                            hashMap.put("uid",uid);
                            final FirebaseDatabase database=FirebaseDatabase.getInstance();

                            DatabaseReference reference=database.getReference("Users");

                            reference.child(uid).setValue(hashMap);

                            //sucess
                            Toast.makeText(userRegister.this, "Registered with "+user.getEmail(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(userRegister.this, HomePage.class));
                            finish();

                        }
                        else {
                            //progressDialog.dismiss();
                            loading.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                            Toast.makeText(userRegister.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //progressDialog.dismiss();
                loading.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);

                Toast.makeText(userRegister.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            startActivity(new Intent(userRegister.this, HomePage.class));
            finish();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String username=prefs.getString("username","");
        String pass=prefs.getString("password","");

        if(username.equals("")&&pass.equals("")) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else {
            //     progressDialog.setMessage("Logging...");
            // progressDialog.show();
            loading.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            mAuth.signInWithEmailAndPassword(username, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // progressDialog.dismiss();

                                loading.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(userRegister.this, HomePage.class));
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                //progressDialog.dismiss();

                                loading.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);

                                Toast.makeText(userRegister.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //progressDialog.dismiss();

                    loading.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                    Toast.makeText(userRegister.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        super.onStart();
    }


    public boolean onSupportNavigateUp(){

        onBackPressed();//go baack

        return super.onSupportNavigateUp();
    }
}