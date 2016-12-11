package com.freddon.android.app.rxjavalol.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.freddon.android.app.rxjavalol.model.Label;
import com.freddon.android.app.rxjavalol.ui.views.LabelItemView;
import com.freddon.android.app.rxjavalol.ui.views.LabelItemView_;

import org.androidannotations.annotations.EBean;

/**
 * Created by fred on 2016/12/7.
 */

@EBean
public class RxLOLAdapter extends RxBaseAdapter<Label, LabelItemView> {

    Context context;

    public RxLOLAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected LabelItemView onCreateItemView(ViewGroup parent, int viewType) {
        return LabelItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<LabelItemView> holder, int position) {
        final int pos = position;
        LabelItemView view = holder.getView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rxLOLLabelItemViewEvent != null) {
                    rxLOLLabelItemViewEvent.onItemClick(items.get(pos), pos);
                }
            }
        });
        view.bind(items.get(position), position);
    }


    public void setRxLOLLabelItemViewEvent(RxLOLLabelItemViewEvent rxLOLLabelItemViewEvent) {
        this.rxLOLLabelItemViewEvent = rxLOLLabelItemViewEvent;
    }


    private RxLOLLabelItemViewEvent rxLOLLabelItemViewEvent;

    public interface RxLOLLabelItemViewEvent {
        void onItemClick(Label label, int position);
    }
}