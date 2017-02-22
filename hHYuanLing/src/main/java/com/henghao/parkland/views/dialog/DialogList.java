package com.henghao.parkland.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.benefit.buy.library.views.NoScrollListView;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.YanghuStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class DialogList extends Dialog {
    private NoScrollListView dialog_ok;
    private final DialogAlertListener listener;

    private String[] state_array = {"施肥", "浇水", "除草", "除虫", "修枝", "防风防汛", "防寒防冻", "防日灼", "扶正", "补栽"};//养护状态选项
    private YanghuStateAdapter state_Adapter;
    private String str_state;//养护状态

    private Context mcontext;

    public interface DialogAlertListener {
        public void onDialogCreate(Dialog dlg);

        public void onDialogOk(Dialog dlg, String par);

        public void onDialogCancel(Dialog dlg);
    }

    public DialogList(Context context, DialogAlertListener listener) {
        super(context, R.style.dialog_alert);
        mcontext = context;
        this.listener = listener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yanghu_listview);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        this.dialog_ok = (NoScrollListView) findViewById(R.id.common_list);
        TextView txtTitle = (TextView) findViewById(R.id.dialog_title);
        /**
         * 初始化养护状态下拉列表框
         */
        List<String> mlist=new ArrayList<>();
        for (int i=0;i<state_array.length;i++){
            mlist.add(state_array[i]);
        }
        state_Adapter = new YanghuStateAdapter(mcontext,mlist);
        dialog_ok.setAdapter(state_Adapter);
        if (this.listener != null) {
            this.listener.onDialogCreate(this);
        }
        dialog_ok.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                str_state = state_array[position];
                onBtnOk();
            }
        });
    }


    private void onBtnOk() {
        // cancel();
        if (this.listener != null) {
            this.listener.onDialogOk(this, str_state);
            cancel();
        }
    }

    private void onBtnCancel() {
        cancel();
        if (this.listener != null) {
            this.listener.onDialogCancel(this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBtnCancel();
        }
        return super.onKeyDown(keyCode, event);
    }
}
