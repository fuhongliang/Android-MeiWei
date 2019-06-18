package com.ifhu.meiwei.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class MenuAdapter extends BaseAdapter {
    List<MenuAdapter> menuAdapterList;
    Context mcontext;

    @Override
    public int getCount() {
        return menuAdapterList == null ? 0 : menuAdapterList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
