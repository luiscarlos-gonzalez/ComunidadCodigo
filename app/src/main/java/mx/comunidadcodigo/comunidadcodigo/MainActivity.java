package mx.comunidadcodigo.comunidadcodigo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.comunidadcodigo.comunidadcodigo.fragments.TitlesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TitlesFragment fragment = new TitlesFragment();

        setFragment(fragment);
    }

    private void setFragment(TitlesFragment f){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, f)
                .commit();
    }

}
