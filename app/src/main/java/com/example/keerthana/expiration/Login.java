package com.example.keerthana.expiration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    private LinearLayout mPhoneLayout;
    private LinearLayout mCodeLayout;

    private ImageView mPhoneIcon;
    private ImageView mLockIcon;

    private EditText mPhoneText;
    private EditText mCodeText;

    private ProgressBar mPhoneBar;
    private ProgressBar mCodeBar;

    private Button mSendButton;

    private int btnType=0;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private TextView mErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mPhoneLayout=(LinearLayout)findViewById(R.id.phoneLayout);
        mCodeLayout=(LinearLayout)findViewById(R.id.codeLayout);

        mPhoneText=(EditText)findViewById(R.id.pText);
        mCodeText=(EditText)findViewById(R.id.codeEditText);

        mPhoneBar=findViewById(R.id.phoneProgress);
        mCodeBar=findViewById(R.id.codeProgress);

        mPhoneIcon=findViewById(R.id.phoneIcon);
        mLockIcon=findViewById(R.id.lockIcon);

        mSendButton=findViewById(R.id.sendBtn);
        mAuth= FirebaseAuth.getInstance();

        mErrorText=(TextView)findViewById(R.id.errorText);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(btnType==0){
                mPhoneBar.setVisibility(View.VISIBLE);
                mPhoneText.setEnabled(false);
                mSendButton.setEnabled(false);

                String phoneNumber = mPhoneText.getText().toString();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        Login.this,
                        mCallbacks// Activity (for callback binding)
                );}        // OnVerificationStateChangedCallbacks}

            else{
                mSendButton.setEnabled(false);
                mCodeBar.setVisibility(View.VISIBLE);

                String verificationCode=mCodeText.getText().toString();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,verificationCode);
                signInWithPhoneAuthCredential(credential);

            }
            }
        });
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential){

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e){
                mErrorText.setText("There was some error");
                mErrorText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                mVerificationId = verificationId;
                mResendToken = token;

                btnType=1;

                mPhoneBar.setVisibility(View.INVISIBLE);
                mCodeLayout.setVisibility(View.VISIBLE);
                mLockIcon.setVisibility(View.VISIBLE);

                mSendButton.setText("Verify Code");
                mSendButton.setEnabled(true);
            }
        };
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();
                            Intent i=new Intent(Login.this,retrieval.class);
                            startActivity(i);
                            finish();


                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            mErrorText.setVisibility(View.VISIBLE);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }


}
