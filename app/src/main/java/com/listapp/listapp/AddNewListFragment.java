package com.listapp.listapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sonal on 2/10/2017.
 */
public class AddNewListFragment extends Fragment {
  public EditText title,items;
    public Button submit,cancel;
    public ItemList itemList;
    String Title,L_Item;
    NetCall netCall;
    //final List<String> itemList1= new ArrayList<>();
    PostCall ps;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_new_list, container,
                false);
        title=(EditText)view.findViewById(R.id.et_title);
        items=(EditText)view.findViewById(R.id.et_items);
        submit = (Button)view.findViewById(R.id.btn_submit);
        cancel =(Button)view.findViewById(R.id.btn_cancel);
        Title =title.getText().toString();
        L_Item =items.getText().toString();


        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


            netCall.execute();
            }

        });
       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Fragment fragment = new HomePageFragment();
               FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.fl_container, fragment);
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();
           }
           });

        return view;


    }


}

