package vn.viettel.browser;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.michael.easydialog.EasyDialog;

import java.util.ArrayList;

import acr.browser.lightning.R;
import vn.viettel.browser.Fragment.BaseFragment;
import vn.viettel.browser.listener.GesturesListener;
import vn.viettel.browser.models.GesturesAction;
import vn.viettel.browser.models.GesturesType;

/**
 * A placeholder fragment containing a simple view.
 */
public class GesturesFragment extends BaseFragment implements GestureOverlayView.OnGesturePerformedListener, View.OnClickListener{

    GestureLibrary mGLibrary;

    GesturesListener mListener;

    private View mView;
    private ImageView mSettingIv;

    public GesturesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_gestures, null, false);
        mGLibrary = GestureLibraries.fromRawResource(getActivity(), R.raw.gestures);
        if (!mGLibrary.load()) {
            return;
        }

        GestureOverlayView gOverlay =
                (GestureOverlayView) mView.findViewById(R.id.gOverlay);
        gOverlay.addOnGesturePerformedListener(this);
        mView.findViewById(R.id.button_close).setOnClickListener(this);
        mSettingIv = (ImageView)mView.findViewById(R.id.button_setting);
        mSettingIv.setOnClickListener(this);
        mView.findViewById(R.id.button_close_txt).setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //showTip();
    }

    public void showTip(){
        showTooltip(getActivity(), mSettingIv, "Click here to show list gestures", EasyDialog.GRAVITY_BOTTOM);
    }


    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
        ArrayList<Prediction> predictions =
                mGLibrary.recognize(gesture);

        if (predictions.size() > 0 && predictions.get(0).score > 2.0) {

            String action = predictions.get(0).name;
            boolean isTrue = true;
            if(action.contains("back")){
                mListener.onGesturesAction(new GesturesAction(GesturesType.BACK, ""));
            }else if(action.contains("close tab")){
                mListener.onGesturesAction(new GesturesAction(GesturesType.CLOSE_TAB, ""));
            }else if(action.contains("exit")){
                mListener.onGesturesAction(new GesturesAction(GesturesType.EXIT, ""));
            }else if(action.contains("refresh")){
                mListener.onGesturesAction(new GesturesAction(GesturesType.REFRESH, ""));
            }else if(action.contains("forward")){
                mListener.onGesturesAction(new GesturesAction(GesturesType.FORWORD, ""));
            }else if(action.contains("goto bottom")){
                mListener.onGesturesAction(new GesturesAction(GesturesType.GO_TO_BOTTOM, ""));
            }else if(action.contains("goto top")){
                mListener.onGesturesAction(new GesturesAction(GesturesType.GO_TO_TOP, ""));
            }else if(action.contains("new tab")){
                mListener.onGesturesAction(new GesturesAction(GesturesType.NEW_TAB, ""));
            }else if(action.contains("load")){
                String[] urls = action.split("url:");
                if(urls.length == 2)
                    mListener.onGesturesAction(new GesturesAction(GesturesType.GO_TO_URL, urls[1]));
            }else{
                isTrue = false;
            }
            if(isTrue){
                Toast.makeText(getActivity(), action, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setListener(GesturesListener listener){
        this.mListener = listener;
    }

    @Override
    public void onClick(View view) {
        if(mListener != null){
            mListener.onGesturesClick(view);
        }
    }
}
