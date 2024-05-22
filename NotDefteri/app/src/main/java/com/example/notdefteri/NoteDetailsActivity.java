package com.example.notdefteri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.notdefteri.model.Not;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//20190305011 - ABDULHAMİT TUNÇ
public class NoteDetailsActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 99;
    private static final int SELECT_IMAGE = 100;

    String type=null;
    EditText inputTitle, inputNote;
    TextView textDate;
    Database db;

    ConstraintLayout imageContainer;
    ImageView imageNoteImage;
    ImageView imageNoteDelete;
    String imageUrl=null;
    Not selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        /*-------------------------------------*/
        type=getIntent().getStringExtra("type");
        /*-------------------------------------*/
        db=new Database(this); // girilen notları database'e yollamak için
        /*-------------------------------------*/
        inputTitle=findViewById(R.id.inputTitle);
        inputNote=findViewById(R.id.inputNote);
        textDate=findViewById(R.id.textDate);
        imageContainer=findViewById(R.id.imageNoteConteiner);
        imageNoteImage=findViewById(R.id.imageNoteImage);



        /*-------------------------------------*/
        if(type.equals("updateNote")){

            selectedNote= (Not) getIntent().getSerializableExtra("selectedNote");
            inputTitle.setText(selectedNote.getBaslik());
            inputNote.setText(selectedNote.getNotMetin());
            textDate.setText(selectedNote.getTarih());

            if (selectedNote.getImageUrl()!=null && selectedNote.getImageUrl().length()>4){
                imageContainer.setVisibility(View.VISIBLE);
                imageNoteImage.setImageBitmap(BitmapFactory.decodeFile(selectedNote.getWebUrl()));
            }
            //bak sonra
            if (selectedNote.getImageUrl()!=null && selectedNote.getImageUrl().length()>4){
                imageContainer.setVisibility(View.VISIBLE);
                imageNoteImage.setImageBitmap(BitmapFactory.decodeFile(selectedNote.getWebUrl()));
            }

        }else{
            //Date formatı: Cuma, 22 Aralık 2023 00:00
            textDate.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.getDefault()).format(new Date()));
        }
        /*-------------------------------------*/

        /*-------------------------------------*/
        findViewById(R.id.imageSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputTitle.getText().toString().trim().isEmpty()){
                    inputTitle.setError("Başlık giriniz");
                }else if (inputNote.getText().toString().trim().isEmpty()){
                    inputNote.setError("Lütfen not giriniz");
                }else{
                    if (type.equals("updateNote")){
                        db.NotGuncelle(new Not(selectedNote.getId(), inputTitle.getText().toString(),
                                inputNote.getText().toString(),null,
                                imageUrl, textDate.getText().toString(), null));
                    }else {
                        db.YeniNot(new Not(inputTitle.getText().toString(),
                                inputNote.getText().toString(),null,
                                imageUrl, textDate.getText().toString(), null));
                    }

                    finish();
                }

            }
        });

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onBackPressed();}
        });
        findViewById(R.id.imageAddImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        findViewById(R.id.imageNoteDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    db.NotSil(selectedNote.getId());
                    finish();

            }
        });

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                String[] permission={android.Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            }else {
                pickImage();
            }
        }else {
            pickImage();
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI); // fotoğrafı çekmek için kullanılır
        startActivityForResult(intent, SELECT_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if (requestCode==PERMISSION_CODE && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
           pickImage();
       }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==SELECT_IMAGE){
            if (data!=null){
                Uri uri = data.getData();
                try {
                    InputStream inputStream=getContentResolver().openInputStream(uri);
                    Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    imageNoteImage.setImageBitmap(bitmap);
                    imageContainer.setVisibility(View.VISIBLE);
                    imageUrl=getPath(uri);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private String getPath(Uri uri) {
        String filePath;
        Cursor cursor = getContentResolver().query(uri, null,null,null,null);
        if (cursor==null){
            filePath=uri.getPath();
        }else{
            cursor.moveToFirst();
            int index=cursor.getColumnIndex("_data");
            filePath=cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }
}