package mx.comunidadcodigo.comunidadcodigo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.item_post_title, viewGroup, false);

        PostModel post = posts.posts.get(i);

        TextView title = (TextView) v.findViewById(R.id.textViewTitle);
        TextView date = (TextView) v.findViewById(R.id.textViewDate);

        title.setText(post.title);
        date.setText(post.date);

        return v;
    }
}
