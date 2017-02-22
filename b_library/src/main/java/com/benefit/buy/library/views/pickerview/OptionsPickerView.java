package com.benefit.buy.library.views.pickerview;

import java.util.ArrayList;

import com.benefit.buy.library.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Sai on 15/11/22.
 */
public class OptionsPickerView<T> extends BasePickerView implements View.OnClickListener {

    WheelOptions wheelOptions;

    private final View btnSubmit, btnCancel;

    private final TextView tvTitle;

    private OnOptionsSelectListener optionsSelectListener;

    private static final String TAG_SUBMIT = "submit";

    private static final String TAG_CANCEL = "cancel";

    public OptionsPickerView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.pickerview_options, this.contentContainer);
        // -----确定和取消按钮
        this.btnSubmit = findViewById(R.id.btnSubmit);
        this.btnSubmit.setTag(TAG_SUBMIT);
        this.btnCancel = findViewById(R.id.btnCancel);
        this.btnCancel.setTag(TAG_CANCEL);
        this.btnSubmit.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
        //顶部标题
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----转轮
        final View optionspicker = findViewById(R.id.optionspicker);
        this.wheelOptions = new WheelOptions(optionspicker);
    }

    public void setPicker(ArrayList<T> optionsItems) {
        this.wheelOptions.setPicker(optionsItems, null, null, false);
    }

    public void setPicker(ArrayList<T> options1Items, ArrayList<ArrayList<T>> options2Items, boolean linkage) {
        this.wheelOptions.setPicker(options1Items, options2Items, null, linkage);
    }

    public void setPicker(ArrayList<T> options1Items, ArrayList<ArrayList<T>> options2Items,
            ArrayList<ArrayList<ArrayList<T>>> options3Items, boolean linkage) {
        this.wheelOptions.setPicker(options1Items, options2Items, options3Items, linkage);
    }

    /**
     * 设置选中的item位置
     * @param option1
     */
    public void setSelectOptions(int option1) {
        this.wheelOptions.setCurrentItems(option1, 0, 0);
    }

    /**
     * 设置选中的item位置
     * @param option1
     * @param option2
     */
    public void setSelectOptions(int option1, int option2) {
        this.wheelOptions.setCurrentItems(option1, option2, 0);
    }

    /**
     * 设置选中的item位置
     * @param option1
     * @param option2
     * @param option3
     */
    public void setSelectOptions(int option1, int option2, int option3) {
        this.wheelOptions.setCurrentItems(option1, option2, option3);
    }

    /**
     * 设置选项的单位
     * @param label1
     */
    public void setLabels(String label1) {
        this.wheelOptions.setLabels(label1, null, null);
    }

    /**
     * 设置选项的单位
     * @param label1
     * @param label2
     */
    public void setLabels(String label1, String label2) {
        this.wheelOptions.setLabels(label1, label2, null);
    }

    /**
     * 设置选项的单位
     * @param label1
     * @param label2
     * @param label3
     */
    public void setLabels(String label1, String label2, String label3) {
        this.wheelOptions.setLabels(label1, label2, label3);
    }

    /**
     * 设置是否循环滚动
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        this.wheelOptions.setCyclic(cyclic);
    }

    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        this.wheelOptions.setCyclic(cyclic1, cyclic2, cyclic3);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        }
        else {
            if (this.optionsSelectListener != null) {
                int[] optionsCurrentItems = this.wheelOptions.getCurrentItems();
                this.optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1],
                        optionsCurrentItems[2]);
            }
            dismiss();
            return;
        }
    }

    public interface OnOptionsSelectListener {

        public void onOptionsSelect(int options1, int option2, int options3);
    }

    public void setOnoptionsSelectListener(OnOptionsSelectListener optionsSelectListener) {
        this.optionsSelectListener = optionsSelectListener;
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }
}
