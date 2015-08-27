package mx.comunidadcodigo.comunidadcodigo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


import com.poliveira.apps.parallaxlistview.ParallaxScrollEvent;
import com.poliveira.apps.parallaxlistview.ParallaxScrollView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * Created by Jorge on 26/08/2015.
 */
public class PostDetail extends AppCompatActivity {

    private ActionBar mActionBar;
    private Context mContext;
    private ImageView imgTop;
    private LinearLayout linContent;
    private ArrayList<String> arrContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        mContext = this;
        mActionBar = getSupportActionBar();

        setActionBar();

        ParallaxScrollView mScrollView = (ParallaxScrollView) findViewById(R.id.view);
        View linTop = getLayoutInflater().inflate(R.layout.view_header, mScrollView, false);
        imgTop = (ImageView) linTop.findViewById(R.id.img_top_post_detail);
        linContent = (LinearLayout) findViewById(R.id.linear_content_post_detail);

        mScrollView.setParallaxView(linTop);

        arrContent = new ArrayList<String>();
        String content = getIntent().getStringExtra("Content");
        //content.replace("\"","'");

        System.out.println("Attachments" + getIntent().getStringExtra("Attachments"));

        boolean cont = true;
        boolean fusion = false;
        String fus = "";
        do{
            if(content.contains("<img")&&content.trim().length()>0){
                int iT = content.indexOf("<img");
                String tpm = content.substring(0,iT);

                content = content.substring(iT,content.length());

                String sTT = content.substring(0,content.indexOf("/>")+2);
                if(sTT.contains("class=\"wp-image")){//Es imagen
                    if(fusion){
                        arrContent.add(arrContent.size()-1,arrContent.get(arrContent.size()-1)+fus+tpm);
                    }else{
                        arrContent.add(tpm);
                    }
                    arrContent.add("IMG");
                    content = content.substring(content.indexOf("/>")+2,content.length());
                    fusion = false;
                }else{//Es Icono o una pendejada
                    fus = sTT;
                    arrContent.add(tpm);
                    content = content.substring(content.indexOf("/>")+2,content.length());
                    fusion = true;
                }

            }else{
                arrContent.add(content);
                cont = false;
            }
        }while(cont);

        for(int ii=0;ii<arrContent.size();ii++){
            //System.out.println(Html.fromHtml(arrContent.get(ii)).toString());
            //txtTemporal.setText(txtTemporal.getText().toString()+"\n\n***"+Html.fromHtml(arrContent.get(ii)).toString());

            if(!arrContent.get(ii).equals("IMG")){
                String htmlTemp = "<!DOCTYPE html><html><head><title>Page Title</title></head><body>"
                        +arrContent.get(ii)
                        +"</body></html";
                WebView webTemp = new WebView(mContext);
                webTemp.getSettings().setJavaScriptEnabled(true);
                WebSettings webSettings = webTemp.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webTemp.loadDataWithBaseURL("", htmlTemp, "text/html", "UTF-8", "");

                linContent.addView(webTemp);
            }else{
                ImageView imgTemp = new ImageView(mContext);
                LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                linLayoutParam.setMargins(20,20,20,20);
                imgTemp.setLayoutParams(linLayoutParam);
                imgTemp.setAdjustViewBounds(true);

                Picasso.with(mContext)
                        .load("http://comunidadcodigo.mx/wp-content/uploads/2015/08/Captura-de-pantalla-de-2015-08-19-093456.png")
                        .placeholder(R.color.white)
                        .error(R.color.white)
                        .into(imgTemp);

                linContent.addView(imgTemp);
            }

        }


        String featuredImage = getIntent().getStringExtra("FeaturedImage");
        if(featuredImage!=null){
            if(!featuredImage.trim().equals("")){
                try{
                    Picasso.with(mContext)
                            .load(featuredImage)
                            .placeholder(R.color.white)
                            .error(R.color.white)
                            .into(imgTop);
                }catch(IllegalArgumentException ex){
                    ex.printStackTrace();
                }
            }else{
                Picasso.with(mContext)
                        .load(R.mipmap.placeholder_img_list)
                        .placeholder(R.color.white)
                        .error(R.color.white)
                        .into(imgTop);
            }
        }else{
            Picasso.with(mContext)
                    .load(R.mipmap.placeholder_img_list)
                    .placeholder(R.color.white)
                    .error(R.color.white)
                    .into(imgTop);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setActionBar(){
        mActionBar.setTitle(getIntent().getStringExtra("Title"));
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }
}
