package com.drivewell.drivewell.ui.ranking;


import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.adapter.AdapterRankingLeaderBoard;
import com.drivewell.drivewell.constants.BaseActivity;
import com.drivewell.drivewell.model.DriverModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriverRankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverRankingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mLeaderBoardRecyclerView;
    private AdapterRankingLeaderBoard mAdapterRankingLeaderBoard;
    private Context context;
    private List<DriverModel> driverList;


    private IDriverRankingPresenter iDriverRankingPresenter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DriverRankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DriverRankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriverRankingFragment newInstance(String param1, String param2) {
        DriverRankingFragment fragment = new DriverRankingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context=getActivity().getApplicationContext();
        iDriverRankingPresenter=new DriverRankingPresenter();


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView= inflater.inflate(R.layout.fragment_driver_ranking, container, false);
        driverList=new ArrayList<>();

        driverList=iDriverRankingPresenter.getUpdatedRanking();


        mLeaderBoardRecyclerView=mView.findViewById(R.id.rcvRankingBoardRecylerView);
        mAdapterRankingLeaderBoard=new AdapterRankingLeaderBoard(context,driverList);
        mLeaderBoardRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mLeaderBoardRecyclerView.setAdapter(mAdapterRankingLeaderBoard);


         //milliseconds
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                driverList=new ArrayList<>();
                driverList=iDriverRankingPresenter.getUpdatedRanking();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLeaderBoardRecyclerView.setAdapter(new AdapterRankingLeaderBoard(context,driverList));
                    }
                });
            }
        }, 0, 5000);

        return mView;
    }

}
