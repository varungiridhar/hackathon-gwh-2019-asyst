package com.example.asystfinal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

        private static final String TAG = "RecyclerViewAdapter";

        private ArrayList<String> mPhoneNumbers = new ArrayList<>();
        private Context mcontext;


        public RecyclerViewAdapter(ArrayList<String> mPhoneNumbers, Context mcontext) {
            this.mPhoneNumbers = mPhoneNumbers;
            this.mcontext = mcontext;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            Log.d(TAG, "onBindViewHolder: called.");

            holder.phone_number.setText(mPhoneNumbers.get(position));

            holder.phone_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getCallingView(mPhoneNumbers.get(position));
                }
            });
            holder.mapActivityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: button has been pressed");
                    getMapView();
                }
            });







        }

        @Override
        public int getItemCount() {
            return mPhoneNumbers.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView phone_number;
            Button mapActivityButton;
            RelativeLayout parent_layout;
            TextView makeProfileButton;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                phone_number = itemView.findViewById(R.id.phone_number);
                parent_layout = itemView.findViewById(R.id.parent_layout);
                mapActivityButton = itemView.findViewById(R.id.mapActivityButton);

            }
        }
        private void getCallingView(String phoneNum){
            Intent i = new Intent(Intent.ACTION_DIAL);
            String p = "tel:" + phoneNum;
            i.setData(Uri.parse(p));
            mcontext.startActivity(i);
        }

        private void getMapView(){


            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference().child("+918861958661");


            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //validation check for deleted values shoud occur.
                    //$When value is changed on database, the app crashes$

                    String datasnaphotStr = dataSnapshot.getValue().toString();
                    Log.d(TAG, "onDataChange: "+dataSnapshot.getValue().toString());
                    String cleanStr = datasnaphotStr.replace("{lat=", "").replace("long=", "").replace("}", "");
                    String latlang[] = cleanStr.split(", ");
                    String lat = latlang[0];
                    Log.d(TAG, "latitude: "+lat);
                    String lang = latlang[1];

                    //onDataChange: {lat=12.8989521, long=77.7525264}



                    Uri mapUri = Uri.parse("geo:0,0?q=" + lat + ","+ lang);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    mcontext.startActivity(mapIntent);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });







        }

}
