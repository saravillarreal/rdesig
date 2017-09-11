package rdesign.android.fragments;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

import rdesign.android.R;
import rdesign.android.adapters.HitsAdapter;
import rdesign.android.bean.Hit;
import rdesign.android.bean.ObjectResponse;
import rdesign.android.databinding.MainFragmentBinding;
import rdesign.android.util.AppUtil;
import rdesign.android.util.SessionUtil;
import rdesign.android.web.UrlBase;
import rdesign.android.web.UrlHit;

/**
 * Created by saravillarreal on 9/4/17.
 */

public class MainFragment extends BaseFragment  {
    public final static String TAG = "MainFragment";


    private MainFragmentBinding mainFragmentBinding;
    private int page = 1;
    private LinearLayoutManager mLinearLayoutManager;

    private HitsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public String query = "";
    private ArrayList<Hit> mHitsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false );


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mainFragmentBinding.recyclerView.setLayoutManager(mLayoutManager);




        if (SessionUtil.getArrayListItems() == null){
            mHitsList = SessionUtil.getPreferencesItems(getActivity(), getActivity().getString(R.string.items_arraylist));
            if (mHitsList.size() > 0){
                mAdapter = new HitsAdapter(mHitsList, getActivity());
                mainFragmentBinding.recyclerView.setAdapter(mAdapter);
            }
            else{
                getHits();
            }

        }

        else {
            mHitsList = SessionUtil.getPreferencesItems(getActivity(), getActivity().getString(R.string.items_arraylist));
        }
        getHits();

        mainFragmentBinding.flickrPhotoSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshFunctionality();
            }
        });
        //getPhotosFromService(Boolean.FALSE);

        return mainFragmentBinding.getRoot();

    }

    @Override
    protected void setViewsEvents() {
        if(getArguments()!= null){

            mainFragmentBinding.flickrPhotoSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pullToRefreshFunctionality();
                }
            });
            getHits();
        }

        mainFragmentBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastItemPosition != RecyclerView.NO_POSITION && lastItemPosition == (mHitsList.size() - 1)) {
                    page = page + 1;
                    getHits();
                }
            }
        });
    }

    private void pullToRefreshFunctionality() {
        if (AppUtil.isNetworkAvailable(getActivity())) {
            page = 1;
            getHits();
            mainFragmentBinding.flickrPhotoSwipeRefresh.setRefreshing(false);
        } else {
            AppUtil.showSnackbar(getContext(), R.string.network_not_available, mainFragmentBinding.flickrPhotoSwipeRefresh);
        }
    }



    public void getHits() {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getActivity());
        final String[] data = {""};
        String url = UrlBase.createUrl(UrlHit.GET_ELEMENTS_URL);

        // Casts results into the TextView found within the main layout XML with id jsonData


        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    // Consuming remote method
                    String strJson = response.toString();
                    ObjectResponse objectResponse = new Gson().fromJson(strJson, ObjectResponse.class);

                    mHitsList = new ArrayList<>();
                    mHitsList = SessionUtil.getPreferencesItems(getActivity(), getActivity().getString(R.string.items_arraylist));
                    for (int i= 0; i< objectResponse.getHits().size(); i++){
                        mHitsList.add(objectResponse.getHits().get(i));
                    }


                    SessionUtil.setArrayListItems(mHitsList);
                    SessionUtil.savePreferencesItemFlickr(getActivity(), mHitsList);
                    mAdapter = new HitsAdapter(mHitsList, getActivity());
                    mainFragmentBinding.recyclerView.setAdapter(mAdapter);


                } catch (Exception e) {
                     e.printStackTrace();
                }


                mHitsList = new ArrayList<>();
                mHitsList = SessionUtil.getPreferencesItems(getActivity(), getActivity().getString(R.string.items_arraylist));

                SessionUtil.setArrayListItems(mHitsList);
                SessionUtil.savePreferencesItemFlickr(getActivity(), mHitsList);
                mAdapter = new HitsAdapter(mHitsList, getActivity());
                mainFragmentBinding.recyclerView.setAdapter(mAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.toString());
            }
        });



        requestQueue.add(stringRequest);

    }

    private void getPhotosService(Boolean isPaging){
        if (AppUtil.isNetworkAvailable(getContext())) {
            getHits();
        } else {
            AppUtil.showSnackbar(getContext(), R.string.network_not_available, mainFragmentBinding.flickrPhotoSwipeRefresh);
        }
    }




    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                mainFragmentBinding.getRoot().getWindowToken(), 0);
    }

}
