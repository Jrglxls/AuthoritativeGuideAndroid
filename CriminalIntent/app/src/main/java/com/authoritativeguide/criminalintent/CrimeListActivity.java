package com.authoritativeguide.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Jrglxls on 2016/12/22.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        //返回新的CrimeListFragment实例
        return new CrimeListFragment();
    }
}
