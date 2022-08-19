package com.mahar.firebaseapp.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.mahar.firebaseapp.ui.activity.MainActivity;
import com.mahar.firebaseapp.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingViewModel settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//?        final TextView textView = binding.textDashboard;
        final LinearLayout signOut=binding.logoutCard;
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),"Should be logout",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent i= new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });
//?        settingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}