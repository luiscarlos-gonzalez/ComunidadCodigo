package mx.comunidadcodigo.comunidadcodigo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
    private ArrayList<String> arrContent,arrImg;
    private ShareActionProvider mShareActionProvider;


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
        arrImg = new ArrayList<String>();
        String content = getIntent().getStringExtra("Content");

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
                    int indexII = sTT.indexOf("src=")+5;
                    String sTTT= sTT.substring(indexII,sTT.length());
                    String sTTTT = sTTT.substring(0,sTTT.indexOf("\""));
                    arrContent.add("IMG");
                    arrImg.add(sTTTT);

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

        int actualPhoto = 0;
        for(int ii=0;ii<arrContent.size();ii++){
            //System.out.println(Html.fromHtml(arrContent.get(ii)).toString());
            //txtTemporal.setText(txtTemporal.getText().toString()+"\n\n***"+Html.fromHtml(arrContent.get(ii)).toString());

            if(!arrContent.get(ii).equals("IMG")){
                String htmlTemp = "<!DOCTYPE html><html><head>"+
                        "<link href='https://fonts.googleapis.com/css?family=Droid+Sans' rel='stylesheet' type='text/css'> <style type=\"text/css\"> body {font-family: 'Droid Sans', sans-serif; color: #FFF; background-color: #424242; padding: 10px; text-align: justify; } pre {font-family: 'Droid Sans', sans-serif; color: #FFF; background-color: #424242; } h1 {font-size: larger; } h2 {font-size: larger; } h3 {font-size: large; } a {color: #E0E0E0; } </style>"+
                        "</head><body>"
                        +arrContent.get(ii)
                        +"</body></html";
                WebView webTemp = new WebView(mContext);
                webTemp.setBackgroundColor(getResources().getColor(R.color.grey800));
                webTemp.getSettings().setJavaScriptEnabled(true);
                WebSettings webSettings = webTemp.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webTemp.loadDataWithBaseURL("", htmlTemp, "text/html", "UTF-8", "");

                linContent.addView(webTemp);
            }else{
                ImageView imgTemp = new ImageView(mContext);
                LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                linLayoutParam.setMargins(20, 20, 20, 20);
                imgTemp.setLayoutParams(linLayoutParam);
                imgTemp.setAdjustViewBounds(true);

                //"http://img1.wikia.nocookie.net/__cb20111008233253/pokemon/images/4/4c/Ash's_Pikachu.png"
                Picasso.with(mContext)
                        .load(arrImg.get(actualPhoto))
                        .placeholder(R.color.white)
                        .error(R.color.white)
                        .into(imgTemp);

                actualPhoto++;

                linContent.addView(imgTemp);
            }

        }


        String featuredImage = getIntent().getStringExtra("FeaturedImage");
        if(featuredImage!=null){
            if(!featuredImage.trim().equals("")){
                try{
                    Picasso.with(mContext)
                            .load(featuredImage)
                            .placeholder(R.color.grey800)
                            .error(R.color.grey800)
                            .into(imgTop);
                }catch(IllegalArgumentException ex){
                    ex.printStackTrace();
                }
            }else{
                Picasso.with(mContext)
                        .load(R.mipmap.placeholder_img_list)
                        .placeholder(R.color.grey800)
                        .error(R.color.grey800)
                        .into(imgTop);
            }
        }else{
            Picasso.with(mContext)
                    .load(R.mipmap.placeholder_img_list)
                    .placeholder(R.color.grey800)
                    .error(R.color.grey800)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_post_detail, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        mShareActionProvider.setShareIntent(createShareIntent());


        return super.onCreateOptionsMenu(menu);
    }

    private void setActionBar(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View header = inflater.inflate(R.layout.actionbar_custom_2, null);
        TextView titleA = (TextView) header.findViewById(R.id.text_title_actionbar);
        titleA.setText(getIntent().getStringExtra("Title"));

        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setCustomView(header);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("Title")+" - "+getIntent().getStringExtra("URL"));
        return shareIntent;
    }
}
