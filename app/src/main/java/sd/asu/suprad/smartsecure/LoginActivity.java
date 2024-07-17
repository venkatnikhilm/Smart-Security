package sd.asu.suprad.smartsecure;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.app.Activity;
import android.content.Intent;
import androidx.biometric.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailEditText;
    private EditText passwordEditText;

    private TextView forgotPasswordTextView;
    private Button signInButton, createAccountButton;
    private SignInButton googleSignInButton;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), o -> {
                if (o.getResultCode() == RESULT_OK) {
                    Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(o.getData());
                    try {
                        GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                        mAuth.signInWithCredential(credential)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        authenticateUsingBiometrics();
//                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    } else {
                                        Toast.makeText(this, "An error occurred while Signing in using Google", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "An error occurred while Signing in using Google", Toast.LENGTH_SHORT).show();
                                });
                    } catch (ApiException e) {
                        throw new RuntimeException(e);
                    }
                }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        GoogleSignInClient mGoogleSignInClient;

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        forgotPasswordTextView = findViewById(R.id.forgot);
        signInButton = findViewById(R.id.sign_in);
        googleSignInButton = findViewById(R.id.google_sign_in);
        createAccountButton = findViewById(R.id.create_account);

        signInButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty()) {
                emailEditText.setError("Email cannot be empty");
                emailEditText.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                passwordEditText.setError("Password cannot be empty");
                passwordEditText.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            authenticateUsingBiometrics();
//                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);
        googleSignInButton.setOnClickListener(view -> {
            Intent intent = mGoogleSignInClient.getSignInIntent();
            activityResultLauncher.launch(intent);
        });

        createAccountButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        forgotPasswordTextView.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            if (email.isEmpty()) {
                emailEditText.setError("Email cannot be empty");
                emailEditText.requestFocus();
                return;
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Password Reset email sent.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    public void authenticateUsingBiometrics() {
        executor = ContextCompat.getMainExecutor(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            executor = ContextCompat.getMainExecutor(this);
            biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode,
                                                  @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(getApplicationContext(), "Couldn't verify Fingerprint", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }

                @Override
                public void onAuthenticationSucceeded(
                        @NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            });
            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Verification")
                    .setSubtitle("Verify using your biometric credential")
                    .setAllowedAuthenticators(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)
                    .build();
            biometricPrompt.authenticate(promptInfo);
        }
    }
}