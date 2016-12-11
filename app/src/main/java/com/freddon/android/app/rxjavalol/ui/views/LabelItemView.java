package com.freddon.android.app.rxjavalol.ui.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freddon.android.app.rxjavalol.R;
import com.freddon.android.app.rxjavalol.model.Label;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by fred on 2016/12/7.
 */
@EViewGroup(R.layout.label_item)
public class LabelItemView extends LinearLayout {

    @ViewById(R.id.text)
    TextView text;

    @ViewById(R.id.desc)
    TextView desc;


    public LabelItemView(Context context) {
        super(context);
    }

    public void bind(Label label, int position) {
        if (label == null) return;
        text.setText(label.getText());
        desc.setText(label.getDesc() + "");
    }


}
