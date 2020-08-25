package com.cjqb.caijiqianbao.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.activity.ContactcenterActivity;
import com.cjqb.caijiqianbao.activity.MainActivity;
import com.cjqb.caijiqianbao.activity.WebViewActivity;
import com.cjqb.caijiqianbao.activity.WebViewNewActivity;
import com.cjqb.caijiqianbao.bean.userBean.ServiceList;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.cjqb.caijiqianbao.utils.DialogUtil;
import com.cjqb.caijiqianbao.utils.LocationUtils;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.StringUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;

import java.util.List;


public class MainSelfAdapter extends RecyclerView.Adapter<MainSelfAdapter.Vh> {

    private final List<ServiceList> mList;

    private Context mContext;
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;
    private View mPopuView;
    private PopupWindow popupWindow;

    public MainSelfAdapter(Context context, List<ServiceList> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    public List<ServiceList> getList() {
        return mList;
    }

    public void setOnItemClickListener(AdapterView.OnClickListener onItemClickListener) {
        mOnClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.main_self_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position) {
        vh.setData(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Vh extends RecyclerView.ViewHolder {

        ImageView mThumb;
        TextView mName;
        View view;

        public Vh(View itemView) {
            super(itemView);
            mThumb = (ImageView) itemView.findViewById(R.id.thumb);
            mName = (TextView) itemView.findViewById(R.id.name);
            view = itemView.findViewById(R.id.view);
        }

        void setData(final ServiceList bean, final int position) {
//            itemView.setTag(bean);
            ImgLoader.display(mContext, bean.getService_icon(), mThumb);
            mName.setText(bean.getService_name());

            if (position == getList().size() - 1) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getType() == 1) {
                        String contact = StringUtil.contact(bean.getContent(), "?", SpUtil.UID, "=", SpUtil.getInstance().getStringValue(SpUtil.UID), "&&", SpUtil.TOKEN, "=", SpUtil.getInstance().getStringValue(SpUtil.TOKEN));
                        WebViewActivity.forward(mContext, contact,bean.getService_name());
                    } else if (bean.getType() == 3) {

//                        ?parentid=1234&mobile=1234&lat=30.25318714&long=118.21287663
//                        String contact = StringUtil.contact(bean.getContent(), "?", "parentid", "=", "2ddc8bfc00b748418f646813d715fe01", "&&", "mobile", "=", SpUtil.getInstance().getStringValue(SpUtil.PHONE), "&&", "lat", "=", "39.913766", "&&", "long", "=", "116.398035");
//                        Intent intent = new Intent();
//                        intent.setAction("android.intent.action.VIEW");
//                        Uri content_url = Uri.parse(contact);
//                        intent.setData(content_url);
//                        mContext.startActivity(intent);
//                        init(bean);
//                        show();
                        String contact = bean.getContent();
//                        Intent intent = new Intent();
//                        intent.setAction("android.intent.action.VIEW");
//                        Uri content_url = Uri.parse(contact);
//                        intent.setData(content_url);
//                        mContext.startActivity(intent);
                        WebViewActivity.forward(mContext, contact,bean.getService_name());
                    } else if (bean.getType() == 4) {
                        //TODO 联系客服
                        ContactcenterActivity.forward(mContext);
//                        WebViewNewActivity.forward(mContext, bean.getContent());
                    } else {
                        showSimpleDialog(mContext, bean.getService_name(), bean.getContent(), true);
                    }
                }
            });
        }
    }

    public void showSimpleDialog(Context context, String title, String content, boolean cancelable) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setCancelable(cancelable)
                .show();
    }

    private void init(final ServiceList bean) {
        if (mPopuView == null) {
            mPopuView = mInflater.inflate(R.layout.popu_quanyi, null);
//        mTvPopuAmount = mPopuView.findViewById(R.id.tv_popu_amount);
            popupWindow = DialogUtil.showPopuWindow(mPopuView);
            mPopuView.findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
//                    String lat = SpUtil.getInstance().getStringValue(SpUtil.LOCATION_LAT);
//                    String lon = SpUtil.getInstance().getStringValue(SpUtil.LOCATION_LNG);
                    String lat =LocationUtils.latitude+"";
                    String lon = LocationUtils.longitude+"";
                    String contact = StringUtil.contact(bean.getContent(), "?", "parentid", "=", "2ddc8bfc00b748418f646813d715fe01", "&&", "mobile", "=", SpUtil.getInstance().getStringValue(SpUtil.PHONE), "&&", "lat", "=", lat, "&&", "long", "=", lon);
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(contact);
                    intent.setData(content_url);
                    mContext.startActivity(intent);
                }
            });
            mPopuView.findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    String contact = StringUtil.contact(bean.getCost(), "?", "parentid", "=", "2ddc8bfc00b748418f646813d715fe01", "&&", "mobile", "=", SpUtil.getInstance().getStringValue(SpUtil.PHONE));
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(contact);
                    intent.setData(content_url);
                    mContext.startActivity(intent);
                }
            });
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }


    }

    public void show() {
        ToastUtil.show("密码默认当前登录手机号后6位");
        popupWindow.showAtLocation(((MainActivity) mContext).fl, Gravity.BOTTOM, 0, 0);
    }
}
