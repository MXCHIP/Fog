package com.mxchip.ListViewAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mxchip.activities.DevCtrlActivity;
import com.mxchip.activities.DeviceEditActivity;
import com.mxchip.activities.R;
import com.mxchip.helper.CommonPara;
import com.mxchip.helper.ConstHelper;

import java.util.Vector;

/**
 * Created by Rocke on 2016/06/12.
 */
public class ListViewAdapter  extends BaseAdapter {

    private LayoutInflater mInflater;
    private Activity mContext;
    private ListView mListView;
    private Vector<BookModel> mModels = new Vector<BookModel>();
    SyncImageLoader syncImageLoader;

    public ListViewAdapter(Activity context, ListView listView) {
        mInflater = LayoutInflater.from(context);
        syncImageLoader = new SyncImageLoader();
        mContext = context;
        mListView = listView;

        mListView.setOnScrollListener(onScrollListener);
    }

    int i = 0;

    public void addBook(String book_name, String out_book_online, String out_book_pic, String out_book_deviceid, String out_book_devicepw) {
//        Log.d(TAG + "addbook", i++ + "");
        BookModel model = new BookModel();
        model.book_name = book_name;
        model.out_book_online = out_book_online;
        model.out_book_pic = out_book_pic;
        model.out_book_deviceid = out_book_deviceid;
        model.out_book_devicepw = out_book_devicepw;
        mModels.add(model);
    }

    public void clean() {
        mModels.clear();
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= getCount()) {
            return null;
        }
        return mModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.my_dev_list_item, null);
        }
        final BookModel model = mModels.get(position);
        convertView.setTag(position);

        LinearLayout detail = (LinearLayout) convertView.findViewById(R.id.dev_item_r_layid);
        TextView dev_item_isonline_tvid = (TextView) convertView.findViewById(R.id.dev_item_isonline_tvid);
        TextView dev_item_name_tvid = (TextView) convertView.findViewById(R.id.dev_item_name_tvid);

        dev_item_name_tvid.setText(model.book_name);
        dev_item_isonline_tvid.setText(model.out_book_online);

        syncImageLoader.loadImage(position, model, model.out_book_pic, imageLoadListener);

        //TODO 设备是否在线
        if ("offline".equals(model.out_book_online)) {
            dev_item_name_tvid.setTextColor(CommonPara.IS_OFFLINE_COLOR);
            dev_item_isonline_tvid.setTextColor(CommonPara.IS_OFFLINE_COLOR);

            ImageView dev_item_isonline_img = (ImageView) convertView.findViewById(R.id.dev_item_isonline_img);
            ImageView iv = (ImageView) convertView.findViewById(R.id.dev_item_imgid);
            dev_item_isonline_img.setVisibility(View.GONE);
            iv.setBackgroundResource(R.drawable.nydevice_icon_device_offline);
        }

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO 设备是否在线
                Intent intent = new Intent(mContext, DevCtrlActivity.class);
                intent.putExtra("deviceid", model.out_book_deviceid);
                intent.putExtra("devicepw", model.out_book_devicepw);
                intent.putExtra("devicename", model.book_name);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    SyncImageLoader.OnImageLoadListener imageLoadListener = new SyncImageLoader.OnImageLoadListener() {

        @Override
        public void onImageLoad(final BookModel model, Integer t, final Drawable drawable) {
            View view = mListView.findViewWithTag(t);
            if (view != null) {
                ImageView iv = (ImageView) view.findViewById(R.id.dev_item_imgid);
                Bitmap bitmap = ConstHelper.drawable2Bitmap(drawable);

                if("offline".equals(model.out_book_online))
                    bitmap = ConstHelper.getGrayBitmap(bitmap);

                final Bitmap nowBitMap = bitmap;
                iv.setImageBitmap(nowBitMap);

                // iv.setBackgroundDrawable(drawable);

                LinearLayout setimg = (LinearLayout) view.findViewById(R.id.mydev_setting_btn);
                setimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, DeviceEditActivity.class);
                        intent.putExtra("deviceid", model.out_book_deviceid);
                        intent.putExtra("devicepw", model.out_book_devicepw);
                        intent.putExtra("devicename", model.book_name);
                        intent.putExtra("deviceimg", ConstHelper.Bitmap2Bytes(nowBitMap));
                        mContext.startActivity(intent);
                    }
                });
            }
        }

        @Override
        public void onError(Integer t) {
            BookModel model = (BookModel) getItem(t);
            View view = mListView.findViewWithTag(model);
            if (view != null) {
                ImageView iv = (ImageView) view.findViewById(R.id.dev_item_imgid);
                iv.setBackgroundResource(R.drawable.mydevice_icon_device_online);
            }
        }
    };


    public void loadImage() {
        int start = mListView.getFirstVisiblePosition();
        int end = mListView.getLastVisiblePosition();
        if (end >= getCount()) {
            end = getCount() - 1;
        }
        syncImageLoader.setLoadLimit(start, end);
        syncImageLoader.unlock();
    }

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    syncImageLoader.lock();
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    loadImage();
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    syncImageLoader.lock();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    };
}