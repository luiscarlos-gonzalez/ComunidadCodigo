package mx.comunidadcodigo.comunidadcodigo.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import mx.comunidadcodigo.comunidadcodigo.R;
import mx.comunidadcodigo.comunidadcodigo.api.models.PostModel;
import mx.comunidadcodigo.comunidadcodigo.api.models.PostsModel;

/**
 * Created by kentverger on 20/08/15.
 */
public class TitlesAdapter extends BaseAdapter{

    private PostsModel posts;
    private Context context;

    public TitlesAdapter(PostsModel p, Context c) {
        posts = p;
        context = c;
    }

    @Override
    public int getCount() {
        return posts.found;
    }

    @Override
    public Object getItem(int i) {
        return posts.posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return posts.posts.get(i).ID;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.item_post_title, viewGroup, false);

        PostModel post = posts.posts.get(i);

        TextView txtTitle = (TextView) v.findViewById(R.id.textViewTitle);
        TextView txtDate = (TextView) v.findViewById(R.id.textViewDate);
        TextView txtExcerpt = (TextView) v.findViewById(R.id.textViewExcerpt);
        ImageView imgFeatured = (ImageView) v.findViewById(R.id.img_placeholder_post_title_item);

        String dateS = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        SimpleDateFormat format2 = new SimpleDateFormat("EEEE dd MMMM yyyy");
        try {
            Date date = format.parse(post.date);
            dateS = format2.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        txtTitle.setText(post.title);
        txtDate.setText(dateS);
        txtExcerpt.setText(Html.fromHtml(post.excerpt).toString());


        if(post.featured_image!=null){
            if(!post.featured_image.trim().equals("")){
                try{
                    Picasso.with(context)
                            .load(post.featured_image)
                            .resize(900, 300)
                            .centerCrop()
                            .placeholder(R.mipmap.placeholder_img_list)
                            .error(R.mipmap.placeholder_img_list)
                            .transform(new BlurTransformation(context, 5, 4))
                            .into(imgFeatured);
                }catch(IllegalArgumentException ex){
                    ex.printStackTrace();
                }
            }else{
                Picasso.with(context)
                        .load(R.mipmap.placeholder_img_list)
                        .resize(900, 300)
                        .centerCrop()
                        .placeholder(R.mipmap.placeholder_img_list)
                        .error(R.mipmap.placeholder_img_list)
                        .transform(new BlurTransformation(context, 5, 4))
                        .into(imgFeatured);
            }
        }else{
            Picasso.with(context)
                    .load(R.mipmap.placeholder_img_list)
                    .resize(900, 300)
                    .centerCrop()
                    .placeholder(R.mipmap.placeholder_img_list)
                    .error(R.mipmap.placeholder_img_list)
                    .transform(new BlurTransformation(context, 5, 4))
                    .into(imgFeatured);
        }

        ;

        return v;
    }
}
