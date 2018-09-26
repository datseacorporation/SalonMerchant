package com.datseacorporation.salonmerchant;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    private Button signoutBtn;
    private Button editbusinessprofileBtn;


    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_more, container, false);
        editbusinessprofileBtn = view.findViewById(R.id.editbusinessprofileBtn);
        editbusinessprofileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), EditBusinessActivity.class));
            }
        });
        signoutBtn = view.findViewById(R.id.signoutBtn);
        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        firebaseAuth.signOut();
                        startActivity(new Intent(view.getContext(), LoginActivity.class));
                    }
                });
            }
        });

        return view;
    }

}
