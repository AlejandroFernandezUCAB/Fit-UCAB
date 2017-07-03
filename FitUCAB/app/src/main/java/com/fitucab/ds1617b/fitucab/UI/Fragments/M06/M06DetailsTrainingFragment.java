package com.fitucab.ds1617b.fitucab.UI.Fragments.M06;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitucab.ds1617b.fitucab.Helper.OnFragmentSwap;
import com.fitucab.ds1617b.fitucab.Model.Activit;
import com.fitucab.ds1617b.fitucab.Model.Training;
import com.fitucab.ds1617b.fitucab.R;

public class M06DetailsTrainingFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView _title;
    private View _view;
    private Training _Training;
    private OnFragmentSwap _callBack;

    public M06DetailsTrainingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mBundle = this.getArguments();
        _Training = mBundle.getParcelable("training");

    }


    //En el callBack guardo la instancia de la aplicacion
    /**
     * Una vez la activity llama a un fragment se ejecuta este metodo
     * @param activity recibe la activity que llamo o instancio al fragment
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {

            _callBack = (OnFragmentSwap) activity;

        }
        catch (ClassCastException e) {

            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _view =  inflater.inflate(R.layout.fragment_m06_details_training, container, false);
        setHasOptionsMenu(true);
        setupViewValues();
        manageRecyclerView();
        return _view;
    }


    /**
     * Method type: @Override Void
     * Method Name: onCreateOptionsMenu
     * Method @Params Menu menu, MenuInflater inflater
     * Method Description: this method makes a copnfiguration to MenuOptions
     * **/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_m06_training, menu);
        super.onCreateOptionsMenu(menu,inflater);
        setHasOptionsMenu(true);

    }

    /**
     * Method type: @Override Void
     * Method Name: onOptionsItemSelected
     * Method @Params Menu menu, MenuInflater inflater
     * Method Description: this method drive the click on the menuIcon
     * **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.m06_edit_training:
                // do stuff
                ///TODO GO TO EDIT FRAGMENT
                //_callBack.onSwap();
                return true;

            case R.id.m06_active_training:
                // do stuff
                ///TODO CALL ACTIVE TRAINING
                //_callBack.onSwap();
                return true;

            case R.id.m06_delete_training:
                // do stuff
                ///TODO GO TO DELETE TRAINING
                //_callBack.onSwap();
                return true;

            case R.id.m06_share_training:
                // do stuff
                ///TODO GO TO SHARE
                System.out.println("hola");
                //_callBack.onSwap();
                return true;
        }



        return false;
    }

    private void setupViewValues(){

        _title = (TextView) _view.findViewById(R.id.tv_m06_training_name);
        //_title.setText();
        getActivity().setTitle(_Training.get_trainingName());


    }

    private void manageRecyclerView(){

        ActivitiesAdapter mAdapter = new ActivitiesAdapter(_Training.get_activities());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) _view.findViewById(R.id.m06_activities_recycler) ;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }



}
