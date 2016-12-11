package com.freddon.android.app.rxjavalol.ui.activities;


import android.text.TextUtils;
import android.widget.TextView;

import com.freddon.android.app.rxjavalol.ModalTools;
import com.freddon.android.app.rxjavalol.R;
import com.freddon.android.app.rxjavalol.model.Label;
import com.freddon.android.app.rxjavalol.presentor.OperatorCREATEExcutorPresentor;
import com.freddon.android.app.rxjavalol.presentor.OperatorConcatMAPExcutorPresentor;
import com.freddon.android.app.rxjavalol.presentor.OperatorConcatWithExcutorPresentor;
import com.freddon.android.app.rxjavalol.presentor.OperatorFILTERExcutorPresentor;
import com.freddon.android.app.rxjavalol.presentor.OperatorFLATMAPExcutorPresentor;
import com.freddon.android.app.rxjavalol.presentor.OperatorFROMExcutorPresentor;
import com.freddon.android.app.rxjavalol.presentor.OperatorIntervalExcutorPresentor;
import com.freddon.android.app.rxjavalol.presentor.OperatorJUSTExcutorPresentor;
import com.freddon.android.app.rxjavalol.presentor.OperatorMAPExcutorPresentor;
import com.freddon.android.app.rxjavalol.presentor.OperatorMERGEExcutorPresentor;
import com.freddon.android.app.rxjavalol.presentor.OperatorTIMERExcutorPresentor;
import com.freddon.android.app.rxjavalol.presentor.connector.IExcutorPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by fred on 2016/12/7.
 */
@EActivity(R.layout.activity_excutor)
public class ExcutorConsoleActivity extends BaseActivity<IExcutorPresenter.Presenter> implements IExcutorPresenter.View {


    private static final String INTENT_RXJAVA_LABEL = "INTENT_RXJAVA_LABEL";


    private static final int FLAG_O_JUST = 0;
    private static final int FLAG_O_TIMER = 1;
    private static final int FLAG_O_CREATE = 2;
    private static final int FLAG_O_MAP = 3;
    private static final int FLAG_O_FROM = 4;
    private static final int FLAG_O_FILTER = 6;
    private static final int FLAG_O_INTERVAL = 5;
    private static final int FLAG_O_MEARGE = 7;
    private static final int FLAG_O_FLATMAP = 8;
    private static final int FLAG_O_CONCATMAP = 9;
    private static final int FLAG_O_CONCATWITH = 10;


    @ViewById(R.id.result)
    TextView result;

    @ViewById(R.id.sourceCode)
    TextView sourceCodeTv;

    private IExcutorPresenter.Presenter mPresenter;


    @Click(R.id.go)
    void onClick() {
        if (mPresenter == null) return;
        result.setText("");
        mPresenter.excute();
    }

    @Click(R.id.sourceCode)
    void onViewInHtml() {
        CodeActivity_.intent(this).html(mPresenter.getCode()).start();
    }

    @Extra(INTENT_RXJAVA_LABEL)
    Label label;


    @AfterViews
    void initViews() {
        setPresenter();
    }


    public void setPresenter() {
        if (label == null) return;
        IExcutorPresenter.Presenter presenter2 = presenterByExtras(label.getFlag());
        if (presenter2 != null) setPresenter(presenter2);

    }

    private IExcutorPresenter.Presenter presenterByExtras(int rxJava_ex_flag) {
        IExcutorPresenter.Presenter presenter = null;
        switch (rxJava_ex_flag) {
            case FLAG_O_JUST:
                presenter = new OperatorJUSTExcutorPresentor(this, label);
                break;
            case FLAG_O_TIMER:
                presenter = new OperatorTIMERExcutorPresentor(this, label);
                break;
            case FLAG_O_CREATE:
                presenter = new OperatorCREATEExcutorPresentor(this, label);
                break;
            case FLAG_O_MAP:
                presenter = new OperatorMAPExcutorPresentor(this, label);
                break;
            case FLAG_O_FROM:
                presenter = new OperatorFROMExcutorPresentor(this, label);
                break;
            case FLAG_O_FILTER:
                presenter = new OperatorFILTERExcutorPresentor(this, label);
                break;
            case FLAG_O_INTERVAL:
                presenter = new OperatorIntervalExcutorPresentor(this, label);
                break;
            case FLAG_O_MEARGE:
                presenter = new OperatorMERGEExcutorPresentor(this, label);
                break;
            case FLAG_O_FLATMAP:
                presenter = new OperatorFLATMAPExcutorPresentor(this, label);
                break;
            case FLAG_O_CONCATMAP:
                presenter = new OperatorConcatMAPExcutorPresentor(this, label);
                break;
            case FLAG_O_CONCATWITH:
                presenter = new OperatorConcatWithExcutorPresentor(this, label);
                break;
        }
        return presenter;
    }

    @Override
    public void onShow(CharSequence content) {
        result.append("\n>>> " + content);
    }

    @Override
    public void alert(String content) {
        if (TextUtils.isEmpty(content)) return;
        ModalTools.showToast(this, content);
    }

    @Override
    public void setSourceCode(CharSequence sourceCode) {
        sourceCodeTv.setText(sourceCode);
    }

    @Override
    public void setRxLabelTitle(String content) {
        setTitle(content);
    }


    @Override
    public void setPresenter(IExcutorPresenter.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }

    }
}
