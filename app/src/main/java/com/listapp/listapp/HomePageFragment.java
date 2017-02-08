package com.listapp.listapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sonal on 2/7/2017.
 */
public class HomePageFragment extends Fragment {
    Context mContext;
    private List<ItemList> l_List;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    public RecyclerView recyclerView;
    private ListAdapter mAdapter;
    GridLayoutManager gridLayoutManager;

    private static final String url = "http://dev-lil-list.appspot.com/";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        view.setTag("RecyclerviewFragment");
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter( mAdapter );

        mContext = getActivity();
        ItemList l=null;
        //Set toolbar title
        collapsingToolbarLayout =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("List It Down");

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setNestedScrollingEnabled(false);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);



        try {
            JSONArray result = new NetworkCall().execute(url).get();
            Gson gson = new Gson();
            ItemList lists = gson.fromJson(result.toString(),ItemList.class);

            if(result!=null) {
                for(int i=0;i<result.length();i++) {
                    JSONObject list_obj= result.getJSONObject(i);
                 l= gson.fromJson(list_obj.toString(),ItemList.class);
                    l_List.add(l);
                }
            }

            mAdapter.notifyDataSetChanged();
            // specify an adapter
            mAdapter = new ListAdapter(l_List,getActivity());
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mAdapter.notifyDataSetChanged();



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}




    class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private String[] mDataset;
    public List<ItemList> l_list;
    Context context;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, list_item;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tv_title);
            list_item = (TextView) v.findViewById(R.id.tv_details);
        }
    }

    public ListAdapter(List<ItemList> l_list, Context context){
        this.l_list = l_list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemList list = l_list.get(position);
        holder.title.setText(list.getTitle());
        holder.list_item.setText(list.getItems().toString());

    }

    @Override
    public int getItemCount() {

        return l_list.size();
    }
}

