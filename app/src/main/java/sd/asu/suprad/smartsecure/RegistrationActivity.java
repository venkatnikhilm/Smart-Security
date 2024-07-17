package sd.asu.suprad.smartsecure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPaswordEditText;
    private Button createAccountButton;

    private FirebaseAuth mAuth;
    private TextView backToLoginTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        emailEditText = findViewById(R.id.email_registration);
        passwordEditText = findViewById(R.id.password_registration);
        confirmPaswordEditText = findViewById(R.id.confirm_password_registration);
        createAccountButton = findViewById(R.id.registration_create_account);
        backToLoginTextView=findViewById(R.id.back_to_login);

        mAuth = FirebaseAuth.getInstance();

        createAccountButton.setOnClickListener(view -> {
            String email= emailEditText.getText().toString();
            String password= passwordEditText.getText().toString();
            String confirmPassword= confirmPaswordEditText.getText().toString();

            if (email.isEmpty()){
                emailEditText.setError("Email cannot be empty");
                emailEditText.requestFocus();
                return;
            }
            if (password.isEmpty()){
                passwordEditText.setError("Password cannot be empty");
                passwordEditText.requestFocus();
                return;
            }
            if (confirmPassword.isEmpty()){
                confirmPaswordEditText.setError("Password cannot be empty");
                confirmPaswordEditText.requestFocus();
                return;
            }
            if (!password.equals(confirmPassword)){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                passwordEditText.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            //TODO: Go to home page
                            startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                        } else {
                            Toast.makeText(this, "Error during Registration", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Couldn't Register User", Toast.LENGTH_SHORT).show();
                    });
        });

        backToLoginTextView.setOnClickListener(view -> {
            Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(intent);
        });
    }

}