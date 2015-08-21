package mx.comunidadcodigo.comunidadcodigo.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import mx.comunidadcodigo.comunidadcodigo.R;
import mx.comunidadcodigo.comunidadcodigo.adapters.TitlesAdapter;
import mx.comunidadcodigo.comunidadcodigo.api.Wordpress;
import mx.comunidadcodigo.comunidadcodigo.api.models.PostsModel;
import retrofit.RestAdapter;

/**
 * Created by kentverger on 20/08/15.
 */
public class TitlesFragment extends Fragment {

    private TitlesAdapter adapter;
    private ListView list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetPosts().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_titles, container, false);

        list = (ListView) v.findViewById(R.id.listViewTitles);

        return v;
    }

    class GetPosts extends AsyncTask<Void, Void, PostsModel> {

        @Override
        protected PostsModel doInBackground(Void... voids) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://public-api.wordpress.com/rest/v1.1/sites/comunidadcodigo.mx/")
                    .build();

            Wordpress wordpressObject = restAdapter.create(Wordpress.class);

            PostsModel posts = wordpressObject.listPosts();

            return posts;
        }

        @Override
        protected void onPostExecute(PostsModel postsModel) {
            super.onPostExecute(postsModel);

            adapter = new TitlesAdapter(postsModel, getActivity().getApplicationContext());

            list.setAdapter(adapter);

        }
    }
}
