package com.ifhu.meiwei.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.base.BaseFragment;


/**
 * 所有订单
 * @author fuhongliang
 */
public class AllOrderFragment extends BaseFragment {

    public static AllOrderFragment newInstance() {
        return new AllOrderFragment();
    }

    public AllOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_order, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    public void getData() {

    }

}
