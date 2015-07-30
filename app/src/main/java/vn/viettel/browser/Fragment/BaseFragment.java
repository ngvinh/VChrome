package vn.viettel.browser.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.michael.easydialog.EasyDialog;

import acr.browser.lightning.R;


/**
 * Created by vinh on 7/28/15.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showTooltip(Context context, View view, String text, int gravity) {
        if(text == null || text.equals(""))
            return;
        View viewV = LayoutInflater.from(context).inflate(R.layout.layout_tooltip_text, null);
        TextView tv = (TextView) viewV.findViewById(R.id.content_tv);
        tv.setText(text);
        new EasyDialog(context)
                .setLayout(viewV)
                .setBackgroundColor(context.getResources().getColor(R.color.bg_tip))
                .setLocationByAttachedView(view)
                .setGravity(gravity)
                .setAnimationAlphaShow(600, 0.0f, 1.0f)
                .setAnimationAlphaDismiss(600, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(false)
                .setMarginLeftAndRight(10, 10)
                .setOutsideColor(context.getResources().getColor(android.R.color.transparent))
                .show();
    }
}
