package com.example.deepakgarg.capstoneproject;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.id;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private NewsAdapter newsAdapter;
    boolean mTwoPane;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> description = new ArrayList<String>();
    ArrayList<String> newsurl = new ArrayList<String>();
    ArrayList<String> image = new ArrayList<String>();
    StaggeredGridLayoutManager llm;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //FragmentManager fm = getActivity().getSupportFragmentManager();
        mTwoPane = getResources().getBoolean(R.bool.isTablet);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.newsrecyclerview);
        mRecyclerView.setHasFixedSize(true);
        if(!mTwoPane) {

            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                llm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            } else {
                llm = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
            }
        }
        else{
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                llm = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
            } else {
                llm = new StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL);
            }
        }
        mRecyclerView.setLayoutManager(llm);

        newsAdapter = new NewsAdapter(getActivity(), id, name, description, newsurl, image, mTwoPane, fm);
        mRecyclerView.setAdapter(newsAdapter);
        data();

        return rootView;

    }


    public void data() {
        try {
            final String BASE_URL = getString(R.string.sources_url);

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    BASE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String syncresponse = object.getString("sources");
                                JSONArray a1obj = new JSONArray(syncresponse);
                                for (int j = 0; j < a1obj.length(); j++) {
                                    JSONObject obj = a1obj.getJSONObject(j);
                                    id.add(obj.getString("id"));
                                    name.add(obj.getString("name"));
                                    description.add(obj.getString("description"));
                                    newsurl.add(obj.getString("url"));
                                    String s = obj.getString("urlsToLogos");
                                    JSONObject obj2 = new JSONObject(s);
                                    image.add(obj2.getString("small"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }newsAdapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }
            });/* {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

            };*/
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}