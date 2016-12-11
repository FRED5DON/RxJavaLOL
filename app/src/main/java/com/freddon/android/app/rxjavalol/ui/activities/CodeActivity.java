package com.freddon.android.app.rxjavalol.ui.activities;

import com.freddon.android.app.markdownwebview.MarkDownWebView;
import com.freddon.android.app.rxjavalol.ModalTools;
import com.freddon.android.app.rxjavalol.R;
import com.freddon.android.app.rxjavalol.presentor.CodePrensenter;
import com.freddon.android.app.rxjavalol.presentor.connector.ICodePresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by fred on 2016/12/9.
 */

@EActivity(R.layout.activity_code)
public class CodeActivity extends BaseActivity<ICodePresenter.Presenter> implements ICodePresenter.View {


    private static final String INTENT_RXJAVA_HTML = "INTENT_RXJAVA_LABEL";
    @ViewById(R.id.markdown)
    MarkDownWebView markDownWebView;


    @Extra(INTENT_RXJAVA_HTML)
    String html;

    @AfterViews
    void init() {
        mPresenter = new CodePrensenter(this, html);
        mPresenter.excute();
    }


    @Override
    public void onShow(CharSequence content) {
        markDownWebView.setContent("```\n" + content + "\n```");
    }

    @Override
    public void alert(String content) {
        ModalTools.showToast(this, content);
    }

    @Override
    public void setRxLabelTitle(String title) {
        setTitle(title);
    }

    @Override
    public void setPresenter(ICodePresenter.Presenter presenter) {
    }

    @Override
    public void showError(String msg) {
        ModalTools.showToast(this, msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }
}
