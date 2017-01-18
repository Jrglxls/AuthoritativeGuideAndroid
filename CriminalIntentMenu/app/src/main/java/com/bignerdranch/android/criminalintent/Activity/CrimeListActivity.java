package com.bignerdranch.android.criminalintent.Activity;

import android.support.v4.app.Fragment;

import com.bignerdranch.android.criminalintent.Fragment.CrimeListFragment;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        //返回新的CrimeListFragment实例
        return new CrimeListFragment();
    }
}
