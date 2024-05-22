package com.example.notdefteri.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notdefteri.NoteListener;
import com.example.notdefteri.R;
import com.example.notdefteri.model.Not;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    ArrayList<Not> notListesi;
    Activity activity;
    NoteListener listener;

    //const. oluştur
    public NoteAdapter(ArrayList<Not> notListesi, Activity activity) {
        this.notListesi = notListesi;
        listener = (NoteListener) activity;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.SetNote(notListesi.get(position));
    }

    @Override
    public int getItemCount() {
        return notListesi.size();
    }


    class NoteViewHolder extends RecyclerView.ViewHolder{

        CardView cardViewConteiner;
        TextView textTitle, textNote, textDate;
        ImageView imageNoteImage;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            cardViewConteiner=itemView.findViewById(R.id.cardViewContainer);
            textTitle=itemView.findViewById(R.id.textTitle);
            textNote=itemView.findViewById(R.id.textNote);
            textDate=itemView.findViewById(R.id.textDate);
            imageNoteImage=itemView.findViewById(R.id.imageNoteImage);

        }
        void SetNote(Not not){
            textTitle.setText(not.getBaslik());
            textNote.setText(not.getNotMetin());
            textDate.setText(not.getTarih());
            cardViewConteiner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.NoteClick(not);
                }
            });
            if (!not.getImageUrl().isEmpty() && not.getImageUrl().length()>0){
                imageNoteImage.setVisibility(View.VISIBLE);
                imageNoteImage.setImageBitmap(BitmapFactory.decodeFile(not.getImageUrl()));
            }

        }
    }
}
