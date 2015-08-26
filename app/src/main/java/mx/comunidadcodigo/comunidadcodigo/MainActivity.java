package mx.comunidadcodigo.comunidadcodigo;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import mx.comunidadcodigo.comunidadcodigo.fragments.TitlesFragment;

public class MainActivity extends AppCompatActivity {

    ActionBar mActionBar;
    Context mContext;
    TitlesFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mActionBar = getSupportActionBar();

        setActionBar();

        fragment = new TitlesFragment();

        setFragment(fragment);
    }

    private void setFragment(TitlesFragment f){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, f)
                .commit();
    }

    private void setActionBar(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View header = inflater.inflate(R.layout.actionbar_custom, null);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setCustomView(header);
    }

}
