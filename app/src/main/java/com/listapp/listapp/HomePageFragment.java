package com.listapp.listapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sonal on 2/7/2017.
 */
public class HomePageFragment extends Fragment {
    Context mContext;
    ItemList l;
    private List<ItemList> l_List= new ArrayList<>();
    private CollapsingToolbarLayout collapsingToolbarLayout;
    public RecyclerView recyclerView;
    private ListAdapter mAdapter;
    GridLayoutManager gridLayoutManager;

    private static final String url = "http://dev-lil-list.appspot.com/lists";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_main, container, false);
        view.setTag("RecyclerviewFragment");
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new AddNewListFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fl_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    Snackbar.make(view, "Make your own List.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });



        mContext = getActivity();
        //Set toolbar title
        collapsingToolbarLayout =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("List It Down");

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter( mAdapter );
        recyclerView.setNestedScrollingEnabled(false);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);



        try {
            JSONArray result = new NetworkCall().execute(url).get();
            Log.d("RezJSON",String.valueOf(result));
            Gson gson = new Gson();
            //ItemList lists = gson.fromJson(result.toString(),ItemList.class);

            if(result!=null) {
                for(int i=0;i<result.length();i++) {
                    JSONObject list_obj= result.getJSONObject(i);
                    Log.d("Bleh",list_obj.toString());
                 l= gson.fromJson(list_obj.toString(),ItemList.class);
                    Log.d("Boo",l.getId().toString());
                    l_List.add(l);
                }
                Log.d("gah",l_List.toString());
            }


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



    @Override
    public void onAttach(Context context) throws NullPointerException {
        super.onAttach(context);

    }
}




    class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private String[] mDataset;
    public List<ItemList> l_list = new ArrayList<>();
    public RelativeLayout rl;
        Context context;
        public int i;



        public ListAdapter(List<ItemList> l_list, Context context){
        this.l_list = l_list;
        Log.d("Meh",String.valueOf(l_list.
                size()));
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        rl = (RelativeLayout)v.findViewById(R.id.ly);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemList list = l_list.get(position);
        Log.d("goo",String.valueOf(position));
        holder.title.setText(list.getTitle());
       // LinearLayout ly= (LinearLayout)v.findViewById(R.id.ly);
        JSONArray array=new JSONArray(list.getItems());

        try {
            holder.list_item1.setText("• "+array.getString(i)+"\n"+"• "+array.getString(i+1)+"\n"+"• "+array.getString(i+2)+"\n"+"• "+array.getString(i+3));
            /*holder.list_item2.setText();
            holder.list_item3.setText(array.getString(i+2));
            holder.list_item4.setText();*/
        } catch (JSONException e) {
            e.printStackTrace();
        }



       // holder.list_item.setText(list.getItems().toString());



    }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView title, list_item1, list_item2,list_item3,list_item4,see_more;
            public ViewHolder(View v) {
                super(v);
                title = (TextView) v.findViewById(R.id.tv_title);
                list_item1 = (TextView) v.findViewById(R.id.tv_item1);
                /*list_item2 = (TextView) v.findViewById(R.id.tv_item2);
                list_item3 = (TextView) v.findViewById(R.id.tv_item3);
                list_item4 = (TextView) v.findViewById(R.id.tv_item4);*/
                see_more = (TextView) v.findViewById(R.id.see_more);
            }
        }

    @Override
    public int getItemCount() {
        if (l_list != null)
            return l_list.size();
        else
            return 0;

    }
}

