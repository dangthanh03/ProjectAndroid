package com.example.giaodienchinh_doan.Activity;
//package com.example.giaodienchinh_doan.AnotherNav;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.example.giaodienchinh_doan.Login;
//import com.example.giaodienchinh_doan.R;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.giaodienchinh_doan.User;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.ValueEventListener;
//
//public class EditProfileActivity extends Fragment {
//    private static final String TAG = "Update email"; // Declare the TAG constant at the class level
//
//    FirebaseAuth auth;
//    Button button;
//    TextView textView;
//    Button Save;
//    FirebaseUser user;
//
//
//    public EditProfileActivity() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        EditText name = view.findViewById(R.id.username);
//        EditText email = view.findViewById(R.id.email);
//        EditText phone = view.findViewById(R.id.phone);
//        email.setEnabled(false);
//        email.setFocusable(false);
//        email.setClickable(false);
//
//
//        // Tìm kiếm nút logout trong view được inflate
//        Button buttonLogout = view.findViewById(R.id.LogOut);
//        Save = view.findViewById(R.id.EditProfile);
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        email.setText(user.getEmail());
//        if (user != null) {
//            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
//            usersRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()) {
//                        User userProfile = snapshot.getValue(User.class);
//                        if (userProfile != null) {
//                            name.setText(userProfile.displayName);
//                            phone.setText(userProfile.phoneNumber);
//                            email.setText(user.getEmail());
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Log.e("TAG", "Error retrieving user information: " + error.getMessage());
//                }
//            });
//        }
//        Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                auth = FirebaseAuth.getInstance();
//                FirebaseUser user = auth.getCurrentUser();
//                String uid = user.getUid();
//                String mail = email.getText().toString();
//
//                String displayName = name.getText().toString();
//                String phoneNumber = phone.getText().toString();
//
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference usersRef = database.getReference("users");
//
//                User newUser = new User(mail, displayName, phoneNumber);
//
//                usersRef.child(uid).setValue(newUser)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d("TAG", "User information saved successfully");
//                                new AlertDialog.Builder(requireContext())
//                                        .setTitle("Success")
//                                        .setMessage("Data has been saved successfully.")
//                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                                                fragmentTransaction.detach(EditProfileActivity.this);
//                                                fragmentTransaction.attach(EditProfileActivity.this);
//                                                fragmentTransaction.commit();
//                                            }
//                                        })
//                                        .show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.e("TAG", "Failed to save user information: " + e.getMessage());
//                            }
//                        });
//            }
//        });
//
//        buttonLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(requireContext(), Login.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });
//
//        return view;
//    }
//}
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.giaodienchinh_doan.Activity.Login;
import com.example.giaodienchinh_doan.R;
import com.example.giaodienchinh_doan.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "Update email";
    FirebaseAuth auth;
    Button button;
    TextView textView;
    Button Save;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText name = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText phone = findViewById(R.id.phone);
        email.setEnabled(false);
        email.setFocusable(false);
        email.setClickable(false);

        Button buttonLogout = findViewById(R.id.LogOut);
        Save = findViewById(R.id.EditProfile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email.setText(user.getEmail());

        if (user != null) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        User userProfile = snapshot.getValue(User.class);
                        if (userProfile != null) {
                            name.setText(userProfile.displayName);
                            phone.setText(userProfile.phoneNumber);
                            email.setText(user.getEmail());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.e("TAG", "Error retrieving user information: " + error.getMessage());
                }
            });
        }

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khởi tạo Firebase Authentication
                auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                String uid = user.getUid();
                String mail = email.getText().toString();
                String displayName = name.getText().toString();
                String phoneNumber = phone.getText().toString();

                // Khởi tạo Firebase Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("users");

                // Tạo một đối tượng User mới
                User newUser = new User(mail, displayName, phoneNumber);

                // Lưu thông tin người dùng vào cơ sở dữ liệu Firebase
                usersRef.child(uid).setValue(newUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "User information saved successfully");

                                // Hiển thị thông báo thành công
                                new AlertDialog.Builder(EditProfileActivity.this)
                                        .setTitle("Success")
                                        .setMessage("Data has been saved successfully.")
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                // Cập nhật giao diện nếu cần
                                            }
                                        })
                                        .show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TAG", "Failed to save user information: " + e.getMessage());
                            }
                        });
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(EditProfileActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
