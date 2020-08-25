package com.cjqb.caijiqianbao.utils;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cjqb.caijiqianbao.fragment.ProcessFragment;

/**
 * Created by cxf on 2018/9/29.
 */

public class ProcessResultUtil {

    protected ProcessFragment mFragment;


    public ProcessResultUtil(FragmentActivity activity) {
        mFragment = new ProcessFragment();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.add(mFragment, "ProcessFragment").commit();
    }

    public void requestPermissions(String[] permissions, Runnable runnable) {
        mFragment.requestPermissions(permissions, runnable);
    }


//    public void startActivityForResult(Intent intent, ActivityResultCallback callback){
//        mFragment.startActivityForResult(intent,callback);
//    }


    public void release(){
        if(mFragment!=null){
            mFragment.release();
        }
    }

}
