package com.testcode.btyisu.darkmodedemotheme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    boolean isDark;
    static final String AFRAGMENT = "a";
    static final String BFRAGMENT = "b";
    Fragment mCurrentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO 설정에 따른 테마 분기처리가 필요함
        setTheme(R.style.AppTheme_Light);

        setContentView(R.layout.activity_main);

        replace(AFRAGMENT);

    }

    // 프래그먼트 교체
    public void replace(String tag){
        boolean isFrist = false;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if(fragment == null){
            fragment = getFragmentByTag(tag);
            isFrist = true;
        }

        if(mCurrentFragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(mCurrentFragment)
                    .commit();
        }


        if(isFrist){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_content, fragment, tag)
                    .commit();
        }else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(fragment)
                    .commit();
        }

        mCurrentFragment = fragment;


    }

    private Fragment getFragmentByTag(String tag){
        if(TextUtils.equals(tag, AFRAGMENT)){
            return new AFragment();
        }else if(TextUtils.equals(tag, BFRAGMENT)){
            return new BFragment();
        }

        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_mode){
            isDark = !isDark;

            if(isDark){
                setTheme(R.style.AppTheme_Dark);
            }else {
                setTheme(R.style.AppTheme_Light);
            }


            retach();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retach(){
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            getSupportFragmentManager()
                    .beginTransaction()
                    .detach(fragment)
                    .attach(fragment)
                    .commit();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
