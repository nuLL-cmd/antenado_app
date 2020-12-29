package com.automatodev.antenado.activities;

import android.os.Bundle;

import com.automatodev.antenado.R;
import com.automatodev.antenado.adapters.TvMostPopularAdapter;
import com.automatodev.antenado.controllers.TvMostPopularController;
import com.automatodev.antenado.databinding.ActivityScrollingBinding;
import com.automatodev.antenado.models.TvMostPopular;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScrollingActivity extends AppCompatActivity {
    private List<TvMostPopular> tvMostPopulars = new ArrayList<>();
    private TvMostPopularAdapter adapter;
    private TvMostPopularController tvControler;
    private ActivityScrollingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout =  findViewById(R.id.toolbar_layout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        showData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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


    public void showData() {
        binding.includeContentScolling.swipeRefreshMain.setColorSchemeResources(R.color.primary);
        binding.includeContentScolling.swipeRefreshMain.setRefreshing(true);
        binding.includeContentScolling.recyclerItemMain.hasFixedSize();
        tvControler = new ViewModelProvider(this).get(TvMostPopularController.class);
        adapter = new TvMostPopularAdapter(tvMostPopulars);
        binding.includeContentScolling.recyclerItemMain.setAdapter(adapter);

        getAllTvMostPopular();
    }

    private void getAllTvMostPopular() {
        tvControler.getAllTvMostPopular(1).observe(this, tvDataSheet -> {
            if (tvDataSheet != null) {
                if (tvDataSheet.getTvMostPopulars() != null) {
                    tvMostPopulars.addAll(tvDataSheet.getTvMostPopulars());
                    adapter.notifyDataSetChanged();
                    binding.includeContentScolling.swipeRefreshMain.setRefreshing(false);
                    Snackbar.make(binding.getRoot(), "Itens disponiveis: "+tvMostPopulars.size(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}