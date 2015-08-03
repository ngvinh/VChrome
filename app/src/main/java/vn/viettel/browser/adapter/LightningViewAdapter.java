package vn.viettel.browser.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import acr.browser.lightning.R;
import acr.browser.lightning.activity.BrowserActivity;
import acr.browser.lightning.view.LightningView;
import vn.viettel.browser.listener.CloseTabListener;

/**
 * Created by vinh on 7/31/15.
 */
public class LightningViewAdapter extends ArrayAdapter<LightningView> {

    final BrowserActivity context;
    ColorMatrix colorMatrix;
    ColorMatrixColorFilter filter;
    Paint paint;
    final int layoutResourceId;
    List<LightningView> data = null;
    final CloseTabListener mExitListener;

    private int mImageWidth;
    private int mImageHeight;

    private int mFaviconSize;
    LightningViewHolder holder;

    public LightningViewAdapter(BrowserActivity activity, int layoutResourceId, List<LightningView> data, CloseTabListener listener) {
        super(activity, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = activity;
        this.data = data;
        this.mExitListener = listener;
        float density = context.getResources().getDisplayMetrics().density;

        mImageWidth = (int) (200 * density);
        mImageHeight = (int) (120 * density);
        mFaviconSize = (int) (32 * density);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new LightningViewHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.title);
            holder.exit = (ImageView) row.findViewById(R.id.closetab);
            holder.exit.setTag(position);
            holder.thumbIv = (ImageView) row.findViewById(R.id.tab_view);
            row.setTag(holder);
        } else {
            holder = (LightningViewHolder) row.getTag();
        }

        holder.exit.setTag(position);
        holder.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExitListener.onClick(v);
            }
        });

        ViewCompat.jumpDrawablesToCurrentState(holder.exit);

        LightningView web = data.get(position);
        holder.txtTitle.setText(web.getTitle());
        if (web.isForegroundTab()) {
            holder.txtTitle.setTextAppearance(context, R.style.boldText);
            holder.txtTitle.setTextColor(R.color.green_btn);
        } else {
            holder.txtTitle.setTextAppearance(context, R.style.normalText);
            holder.txtTitle.setTextColor(R.color.white);
        }

        Bitmap favicon = web.getFavicon();
        if (web.isForegroundTab()) {
            setFavicon(favicon);
            if (!context.isIncognito() && context.getColorMode())
                context.changeToolbarBackground(favicon);
        } else {
            Bitmap grayscaleBitmap = Bitmap.createBitmap(favicon.getWidth(),
                    favicon.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas c = new Canvas(grayscaleBitmap);
            if (colorMatrix == null || filter == null || paint == null) {
                paint = new Paint();
                colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(0);
                filter = new ColorMatrixColorFilter(colorMatrix);
                paint.setColorFilter(filter);
            }

            c.drawBitmap(favicon, 0, 0, paint);
            setFavicon(grayscaleBitmap);
        }

        setImage(web.getWebView().capturePicture());
        return row;
    }

    public void setImage(Picture picture) {
        if(picture == null) return;
        Bitmap bm = Bitmap.createBitmap(mImageWidth, mImageHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFFFFFFFF);
        canvas.drawRect(0, 0, mImageWidth, mImageHeight, p);

        if (picture != null) {
            float scale = mImageWidth / (float) picture.getWidth();
            canvas.scale(scale, scale);

            picture.draw(canvas);
        }

        holder.thumbIv.setImageBitmap(bm);
    }

    public void setFavicon(Bitmap icon) {
        BitmapDrawable bd;
        if (icon != null) {
            bd = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(icon, mFaviconSize, mFaviconSize, false));
        } else {
            bd = null;
        }

        holder.txtTitle.setCompoundDrawablesWithIntrinsicBounds(bd, null, null, null);
    }

    class LightningViewHolder {
        TextView txtTitle;
        ImageView exit;
        ImageView thumbIv;
    }
}
