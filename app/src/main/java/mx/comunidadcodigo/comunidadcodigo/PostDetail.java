package mx.comunidadcodigo.comunidadcodigo;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.poliveira.apps.parallaxlistview.ParallaxScrollView;

/**
 * Created by Jorge on 26/08/2015.
 */
public class PostDetail extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);



        ParallaxScrollView mScrollView = (ParallaxScrollView) findViewById(R.id.view);

        View linTop = getLayoutInflater().inflate(R.layout.view_header, mScrollView, false);



        mScrollView.setParallaxView(linTop);
    }
}
