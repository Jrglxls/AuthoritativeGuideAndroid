package com.bignerdranch.android.criminalintent.Fragment;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.bignerdranch.android.criminalintent.Activity.CrimeActivity;
import com.bignerdranch.android.criminalintent.Activity.CrimePagerActivity;
import com.bignerdranch.android.criminalintent.Model.Crime;
import com.bignerdranch.android.criminalintent.Model.CrimeLab;
import com.bignerdranch.android.criminalintent.R;

public class CrimeListFragment extends ListFragment {
    //创建列表对象
    private ArrayList<Crime> mCrimes;
    //菜单项标题是否显示
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //菜单栏设置标题
        getActivity().setTitle(R.string.crimes_title);
        //先获取CrimeLab单例再获取其中的Crimes列表
        mCrimes = CrimeLab.get(getActivity()).getCrimes();

        /**
         * 方法一 默认
         */
        //创建一个默认的ArrayAdapter<T>类实现
        //第一个参数Context 第二个参数资源id，view对象，第三个参数数据集对象
//        ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(),R.layout.simple_list_item,crimes);
        //为内置的listview设置adapter
//        setListAdapter(adapter);

        /**
         * 方法二 自定义
         */
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);

        //通知FragmentManager接受调用指令
        setRetainInstance(true);
        //默认不显示
        mSubtitleVisible = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        //刷新列表
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    /**
     * 旋转保存菜单栏标题
     */
    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {   
            if (mSubtitleVisible) {
                getActivity().getActionBar().setSubtitle("something");
            }
        }

        //使用android.R.id.list资源ID获取ListFragment管理着的ListView
        ListView listView = (ListView) v.findViewById(android.R.id.list);
        //低配设备
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            //为上下文菜单登记ListView
            registerForContextMenu(listView);
        }else {
        //高配设备
            //列表多项选择设置
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            //列表多项选择监听
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                }

                //实例化上下文菜单资源，并显示在上下文操作栏上的任务完成的地方
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.crime_list_item_context,menu);
                    return true;
                }

                //需要刷新显示新数据时调用
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                //响应上下文菜单项操作的地方
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.menu_item_delete_crime:
                            CrimeAdapter crimeAdapter = (CrimeAdapter) getListAdapter();
                            CrimeLab crimeLab = CrimeLab.get(getActivity());
                            for (int i = crimeAdapter.getCount()-1;i >= 0 ;i--){
                                if (getListView().isItemChecked(i)){
                                    crimeLab.deleteCrime(crimeAdapter.getItem(i));
                                }
                            }
                            mode.finish();
                            crimeAdapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }

                //退出上下文操作模式或所选菜单项操作已被响应
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }

        return v;
    }

    /**
     * 实例化菜单资源，并用它填充上下文菜单
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //调用getMenuInfo方法，获取要删除crime对象的信息
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //获取要删除crime对象的位置
        int position = adapterContextMenuInfo.position;
        //创建crimeAdapter对象
        CrimeAdapter crimeAdapter = (CrimeAdapter) getListAdapter();
        //获取要删除crime对象
        Crime crime = crimeAdapter.getItem(position);

        switch (item.getItemId()){
            case R.id.menu_item_delete_crime:
                //删除crime
                CrimeLab.get(getActivity()).deleteCrime(crime);
                //更新列表
                crimeAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 列表点击事件
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
        //方法一 默认
//        Crime crime = (Crime) getListAdapter().getItem(position);
        //方法二 自定义
        Crime crime = ((CrimeAdapter)getListAdapter()).getItem(position);
        //跳转到CrimeActivity
//        Intent intent = new Intent(getActivity(),CrimeActivity.class);
        //跳转到CrimePagerActivity
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        //传递ID
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivityForResult(i, 0);
    }

    /**
     * 返回结果
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //刷新列表
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    /**
     * 设置菜单栏
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //添加布局
        inflater.inflate(R.menu.fragment_crime_list, menu);

        //查看菜单栏标题状态
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle("隐藏");
        }
    }

    @TargetApi(11)
    @Override
    /**
     * 菜单栏点击事件
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //新建
            case R.id.menu_item_new_crime:
                //新建Crime对象
                Crime crime = new Crime();
                //将新建的Crime添加到CrimeLab列表中
                CrimeLab.get(getActivity()).addCrime(crime);
                //跳转到详情页
                Intent i = new Intent(getActivity(), CrimeActivity.class);
                //传递Crime UUID
                i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
                startActivityForResult(i, 0);
                return true;
            //菜单项标题
            case R.id.menu_item_show_subtitle:
                //显示
            	if (getActivity().getActionBar().getSubtitle() == null) {
                    getActivity().getActionBar().setSubtitle("something");
                    mSubtitleVisible = true;
                    item.setTitle("隐藏");
                //未显示
            	}  else {
            		getActivity().getActionBar().setSubtitle(null);
            		 mSubtitleVisible = false;
            		item.setTitle("显示");
            	}
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } 
    }

    /**
     * 列表适配器
     */
    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(ArrayList<Crime> crimes) {
            //调用超类的构造方法来绑定Crime对象的数组列表
            //不使用预定义布局，传入0作为布局ID的参数
            super(getActivity(), android.R.layout.simple_list_item_1, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //自定义布局
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }

            //获取当前列表中的Crime对象
            Crime c = getItem(position);

            //标题
            TextView titleTextView = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());
            //时间
            TextView dateTextView = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(c.getDate().toString());
            //是否选择
            CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }
}

