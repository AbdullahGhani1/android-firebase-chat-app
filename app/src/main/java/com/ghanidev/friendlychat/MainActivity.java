package com.ghanidev.friendlychat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String Anonymous = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private ListView mMessageListView;
    private MessageAdapter mMessageAdopter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;
    private String mUsername;
//EntryPoint app Access to Firebase Database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsername = Anonymous;

        mFirebaseDatabase =FirebaseDatabase.getInstance();
        mMessagesDatabaseReference =mFirebaseDatabase.getReference().child("messages");


//        Initialize reference to Views
        mMessageListView = findViewById(R.id.messageListView);
        mMessageEditText = findViewById(R.id.messageEditText);
        mPhotoPickerButton = findViewById(R.id.photoPickerButtton);
        mSendButton = findViewById(R.id.sendButton);
        mProgressBar =findViewById(R.id.progressBar);
//        Initialize message  ListView and its adapter
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdopter = new MessageAdapter(this,R.layout.item_message,friendlyMessages);
        mMessageListView.setAdapter(mMessageAdopter);
// Initialize Progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
// Image PickerButon shows an image to upload a image
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
if(cs.toString().trim().length()>0){
    mSendButton.setEnabled(true);
}
else {
    mSendButton.setEnabled(false);
}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
mSendButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mMessageEditText.setText("");
    }
});
    }
}