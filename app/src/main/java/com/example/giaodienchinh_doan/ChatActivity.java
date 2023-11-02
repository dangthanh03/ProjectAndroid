package com.example.giaodienchinh_doan;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.giaodienchinh_doan.AdapterView.ChatAdapter;
import com.example.giaodienchinh_doan.Model.ChatMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RecyclerView ChatRec;
    String Id;
    String Email;
    String Name;
    TextView NameView;
    private ChatAdapter chatAdapter;
    private FirebaseFirestore database;
    private List<ChatMessage> chatMessages;
    FirebaseFirestore firestore;
    String sender;
    EditText MessInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ChatRec = findViewById(R.id.Chat_Recycler);
        sender =user.getUid().toString();
        Email = getIntent().getStringExtra("user_email");
        Name = getIntent().getStringExtra("user_name");
        Id = getIntent().getStringExtra("user_Id");
        NameView = findViewById(R.id.nameUserChat);
        NameView.setText(Name);



        Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        init(sender);
        loadMessages();
    }




    private void init(String SenderId) {
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages, SenderId,Id);
        RecyclerView recyclerView = findViewById(R.id.Chat_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);
        database = FirebaseFirestore.getInstance();
        listenForMessages();
    }


    public void sendMessage() {
        MessInput = findViewById(R.id.message_input);
        String mess = MessInput.getText().toString();



        HashMap<String, Object> message = new HashMap<>();
        message.put("senderId", sender);
        message.put("receiverId", Id);
        message.put("message", mess);
        message.put("dateTime", new Date());


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Chats")
                .add(message)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    MessInput.setText("");
                    // Tải lại tin nhắn sau khi gửi thành công
                    loadMessages();

                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }
    private void loadMessages() {
        database.collection("Chats")
                .whereEqualTo("senderId", sender)
                .whereEqualTo("receiverId", Id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        chatMessages.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                ChatMessage message = document.toObject(ChatMessage.class);
                                chatMessages.add(message);
                                Log.d("Message Loaded", message.message);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Load Messages", "Error converting document to object: " + e.getMessage());
                            }
                        }
                        // Sắp xếp danh sách tin nhắn theo thời gian
                        Collections.sort(chatMessages, (o1, o2) -> o1.dateTime.compareTo(o2.dateTime));
                        // In ra logcat
                        chatAdapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

        if (!sender.equals(Id)) {
            database.collection("Chats")
                    .whereEqualTo("senderId", Id)
                    .whereEqualTo("receiverId", sender)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    ChatMessage message = document.toObject(ChatMessage.class);
                                    chatMessages.add(message);
                                    Log.d("Message Loaded", message.message);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e("Errr", "Error converting document to object: " + e.getMessage());
                                }
                            }

                            Collections.sort(chatMessages, (o1, o2) -> o1.dateTime.compareTo(o2.dateTime));

                            chatAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("Error getting documents", "Error getting documents: ", task.getException());
                        }
                    });
        }
    }



    private void listenForMessages() {
        database.collection("Chats")
                .whereEqualTo("senderId", sender)
                .whereEqualTo("receiverId", Id)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.w(TAG, "Listen failed.", error);
                        return;
                    }

                    if (value != null) {
                        chatMessages.clear();
                        for (DocumentSnapshot doc : value.getDocuments()) {
                            ChatMessage message = doc.toObject(ChatMessage.class);
                            chatMessages.add(message);

                        }

                        chatAdapter.notifyDataSetChanged();
                    }
                });

    }






}