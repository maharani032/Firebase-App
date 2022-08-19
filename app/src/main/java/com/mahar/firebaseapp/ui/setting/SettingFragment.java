package com.mahar.firebaseapp.ui.setting;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mahar.firebaseapp.databinding.FragmentSettingBinding;
import com.mahar.firebaseapp.ui.activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SettingFragment extends Fragment {
    ActivityResultLauncher<Intent> activityResultLauncherForUploadImage;
    private FragmentSettingBinding binding;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    StorageReference storageReference;

    Uri selectedImage;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        storageReference = FirebaseStorage.getInstance().getReference(Objects.requireNonNull(user.getEmail()));
        RegisterActivityForUploadImage();
        SettingViewModel settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//?        final TextView textView = binding.textDashboard;
        final LinearLayout signOut=binding.logoutCard;
        final ImageView photoProfile= binding.photoProfil;
        final ProgressBar spinnerPhoto= binding.spinnerPhoto;
        spinnerPhoto.setVisibility(View.INVISIBLE);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),"Should be logout",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent i= new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });
        photoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoProfile.setClickable(false);
                spinnerPhoto.setVisibility(View.VISIBLE);
                if (ContextCompat.checkSelfPermission(getActivity()
                        , Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity()
                            ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }else{
//                    setStorageInfo();
                    choosePhoto();
//                    uploadPhoto();
                }
            }
        });
//?        settingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        return root;
    }
    public void uploadPhoto(){
        StorageReference reference=storageReference.child("user");
        reference.putFile(selectedImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        binding.spinnerPhoto.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(),"Upload successfully",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Upload has been failed",Toast.LENGTH_SHORT).show();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress=(100 * snapshot.getBytesTransferred()/ snapshot.getTotalByteCount());
                        binding.spinnerPhoto.setProgress((int)progress);
                    }
                });

    }
    public void choosePhoto() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncherForUploadImage.launch(i);
    }

    public void RegisterActivityForUploadImage(){
        activityResultLauncherForUploadImage= registerForActivityResult(new ActivityResultContracts
                .StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                int resultCode=result.getResultCode();
                Intent data=result.getData();

                if (resultCode==RESULT_OK && data!=null){

                    selectedImage = data.getData();
                    uploadPhoto();
                    Picasso.get().load(selectedImage).into(binding.photoProfil);
//                    uploadFile(selectedImage);
                }

            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}