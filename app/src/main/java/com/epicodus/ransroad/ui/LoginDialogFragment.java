package com.epicodus.ransroad.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.epicodus.ransroad.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginDialogFragment extends DialogFragment {
    @Bind(R.id.emailDialogEditText) EditText mEmailDialogEditText;
    @Bind(R.id.passwordDialogEditText) EditText mPasswordDialogEditText;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_login_dialog, null);
        ButterKnife.bind(this, view);

        mAuth = FirebaseAuth.getInstance();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    addAuthStateToSharedPreferences(user.getDisplayName());
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.fragment_login_dialog, null))
                .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String email = mEmailDialogEditText.getText().toString();
                        String password = mPasswordDialogEditText.getText().toString();

                        if (email.equals("")) {
                            Toast.makeText(getActivity(), "Email address was blank, try again!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (password.equals("")) {
                            Toast.makeText(getActivity(), "Password was blank, woops!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        loginWithPassword(email, password);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginDialogFragment.this.getDialog().cancel();
                    }
                }).setView(view);
        return builder.create();
    }

    private void loginWithPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password);
    }

    private void addAuthStateToSharedPreferences(String name) {
        mEditor.putString(Constants.PREFERENCES_AUTHENTICATED, name);
        mEditor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
