package com.cinemo.metarbrowser.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cinemo.metarbrowser.MetarApp;
import com.cinemo.metarbrowser.adapter.InfoListAdapter;
import com.cinemo.metarbrowser.adapter.InfoListAdapterPaged;
import com.cinemo.metarbrowser.db.entity.Info;
import com.cinemo.metarbrowser.util.DiffCallbackItem;
import com.cinemo.metarbrowser.util.ViewModelFactory;
import com.cinemo.metarbrowser.R;
import com.cinemo.metarbrowser.util.ClickListener;
import com.cinemo.metarbrowser.util.Constants;
import com.cinemo.metarbrowser.util.Utils;
import com.cinemo.metarbrowser.vm.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        ClickListener {

    private TextView placeHolder;
    private InfoListAdapterPaged adapter;
    private MainViewModel viewModel;
    private SwipeRefreshLayout swipeRefresh;
    private TextView lastUpdatedAt;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = (new ViewModelFactory(MetarApp.get())).create(MainViewModel.class);
        init();
        subscribe();
    }

    private void subscribe() {
        viewModel.getFilteredPaged().observe(this, data -> {
            if (data.size() == 1) data.get(0).isExpanded = true;
            if (data.size() == 0) placeHolder.setVisibility(View.VISIBLE);
            else placeHolder.setVisibility(View.GONE);
            adapter.submitList(data);
        });

        viewModel.setLastUpdatedAtLiveData(sharedPreferences.getString("lastUpdatedAt", "unknown"));
        viewModel.getLastUpdatedAtLiveData().observe(this, s -> lastUpdatedAt.setText("Last updated at: " + s));

        swipeRefresh.setOnRefreshListener(() -> MetarApp.get().getExecutors().networkIO().execute(() -> {
            List<Info> list = MetarApp.get().getNetworkRequest().fetchList(Constants.STATIONS);
            MetarApp.get().getExecutors().diskIO().execute(() -> MetarApp.get().getInfoDao().insertInfoList(list));
            MetarApp.get().getExecutors().mainThread().execute(() -> swipeRefresh.setRefreshing(false));
        }));
    }

    private void init() {
        placeHolder = findViewById(R.id.placeHolder);
        lastUpdatedAt = findViewById(R.id.lastUpdatedAt);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        EditText input = findViewById(R.id.input);
        RecyclerView infoList = findViewById(R.id.infoList);
        infoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InfoListAdapterPaged(new DiffCallbackItem(), this);
        infoList.setAdapter(adapter);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.setFilterTerm(editable.toString());
            }
        });

        sharedPreferences = getSharedPreferences("WORKER", Context.MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        viewModel.setLastUpdatedAtLiveData(sharedPreferences.getString(s, ""));
    }

    @Override
    public void onItemClick(int position, Info data) {
        MetarApp.get().getExecutors().networkIO().execute(() -> {
            if (Utils.isNetworkAvailable()) {
                String decode = MetarApp.get().getNetworkRequest().fetchSingle(Constants.DECODED + data.getId() + ".TXT");
                String raw = MetarApp.get().getNetworkRequest().fetchSingle(Constants.STATIONS + data.getId() + ".TXT");
                data.setRaw(raw);
                data.setDecode(decode);
                data.setLastUpdated(raw.split("\n")[0]);
                MetarApp.get().getExecutors().diskIO().execute(() -> MetarApp.get().getInfoDao().updateInfo(data));
                MetarApp.get().getExecutors().mainThread().execute(() -> adapter.notifyItemChanged(position));
            }
        });
    }
}