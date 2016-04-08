package animation.meni.context.retrofit2tut;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    List<Github> curatorlistsynchronous = new ArrayList<>();
    Retrofit retrofit;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        Response<Github> responsse = null;
//        for (String login :
//                Data.githubList) {


//        }
        BackgroundTask task = new BackgroundTask();
        task.execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class BackgroundTask extends AsyncTask<Void, Void,
            List<Github>> {


        @Override
        protected void onPreExecute() {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        @Override
        protected List<Github> doInBackground(Void... params) {
            IApiMethods service = retrofit.create(IApiMethods.class);

            for (String login :
                    Data.githubList) {
                Call<Github> curators = service.getCurators(login);
                try {
                    Response<Github> responsse = curators.execute();
                    curatorlistsynchronous.add(responsse.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return curatorlistsynchronous;
        }

        @Override
        protected void onPostExecute(List<Github> curators) {
//            textView.setText(curators.title + "\n\n");
//            for (Curator.Dataset dataset : curators.dataset) {
//                textView.setText(textView.getText() + dataset.curator_title +
//                        " - " + dataset.curator_tagline + "\n");
//            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < curatorlistsynchronous.size(); i++) {
                builder.append(curatorlistsynchronous.get(i).getBlog());
                builder.append("\n");
            }
            String result = builder.toString();
            textView.setText(result);
        }
    }
}
