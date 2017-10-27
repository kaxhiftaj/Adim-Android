package com.adim.techease.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adim.techease.Adapter.GalleryAdapter;
import com.adim.techease.R;
import com.adim.techease.controllers.Gallery;
import com.adim.techease.utils.Configuration;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class GalleryFragment extends Fragment {

    RecyclerView recyclerView;
    List<Gallery> galleryList = new ArrayList<>();
    GalleryAdapter galleryAdapter;
    String test;
    RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_gallery,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestQueue = Volley.newRequestQueue(getActivity());
        galleryAdapter=new GalleryAdapter(getActivity(),galleryList);
        recyclerView.setAdapter(galleryAdapter);
        apicall();

        return view;
    }

    private void apicall() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#7DB3D2"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);
        pDialog.show();
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, Configuration.USER_URL+"App/getmedia",
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("zma respo", String.valueOf(response));

                        try{

                            JSONArray jsonArray = response.getJSONArray("user ");

                            for(int i=0; i<jsonArray.length(); i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Gallery gModel = new Gallery();
                                gModel.setType(jsonObject.getString("type"));
                                gModel.setLink(jsonObject.getString("link"));
                                gModel.setTitle(jsonObject.getString("title"));
                               // gModel.setThumbnail(jsonObject.getString("thumbnail"));
                                galleryList.add(gModel);
                                pDialog.dismiss();
                            }
                            galleryAdapter.notifyDataSetChanged();

                        }catch(JSONException e){e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", String.valueOf(error.getCause()));

                    }
                }
        );
        requestQueue.add(jor);

    }

    }


