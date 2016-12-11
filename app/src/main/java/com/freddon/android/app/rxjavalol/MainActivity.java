package com.freddon.android.app.rxjavalol;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freddon.android.app.rxjavalol.model.Label;
import com.freddon.android.app.rxjavalol.ui.activities.BaseActivity;
import com.freddon.android.app.rxjavalol.ui.activities.ExcutorConsoleActivity_;
import com.freddon.android.app.rxjavalol.ui.adapter.RxLOLAdapter;
import com.freddon.android.app.rxjavalol.ui.adapter.RxLOLAdapter_;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements RxLOLAdapter.RxLOLLabelItemViewEvent {

    @ViewById(R.id.recycleView)
    RecyclerView recycleView;

    private RxLOLAdapter_ adapter;
    private List<Label> items;


    @AfterInject
    protected void init() {
        items = new ArrayList<>();
    }

    @ColorRes(R.color.dividerColor)
    int dividerColor;


    @AfterViews
    protected void onViewDidLoad() {
        try {
            InputStream in = getAssets().open("menu.json");
            InputStreamReader reader = new InputStreamReader(in);
            Gson gson = new Gson();
            Label[] arrays = gson.fromJson(reader, Label[].class);
            items = Arrays.asList(arrays);
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = RxLOLAdapter_.getInstance_(this);
        adapter.setItems(items);
        adapter.setRxLOLLabelItemViewEvent(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
//        recycleView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 5, dividerColor));
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(Label label, int position) {
        if (label.getChild() != null) {
            //浅拷贝即可
            stacks.add(position);
            adapter.setItems(label.getChild());
            adapter.notifyDataSetChanged();
            levels++;
        } else {
            ExcutorConsoleActivity_.intent(this).label(label).start();
        }
//        ModalTools.showToast(this, position + "::" + label.getText());
//        ModalTools.showToast(this, menu + "");
    }


    List<Integer> stacks = new ArrayList<>();
    int levels = 0;

    @Override
    public void onBackPressed() {
        if (stacks != null && stacks.size() > 0) {
            stacks.remove(stacks.size() - 1);
            List<Label> backStacks = items;
            for (int index = 0; index < stacks.size(); index++) {
                Integer pos = stacks.get(index);
                Label pre = backStacks.get(pos);
                backStacks = pre.getChild();
            }
            if (backStacks != null) {
                adapter.setItems(backStacks);
                adapter.notifyDataSetChanged();
            }
            levels--;
        } else {
            super.onBackPressed();
        }
    }
}
