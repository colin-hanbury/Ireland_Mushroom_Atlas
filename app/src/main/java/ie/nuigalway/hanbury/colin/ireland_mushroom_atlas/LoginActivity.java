package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText emailText;
    private EditText passwordText;
    private Button signInButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(emailText.getText().toString(), passwordText.getText().toString());
            }
        });

        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(emailText.getText().toString(), passwordText.getText().toString());
            }
        });
    }

    public void createAccount(String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        toast("Authentication failed");
                        updateUI(null);
                    }
                }
            });
    }


    public void signIn(String email, String password){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
    }

    private void updateUI(FirebaseUser user) {
        emailText.setText(null);
        passwordText.setText(null);
        if (user != null) {
            Intent menu = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(menu);
        }
    }

    public void toast(String message){
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }
}

