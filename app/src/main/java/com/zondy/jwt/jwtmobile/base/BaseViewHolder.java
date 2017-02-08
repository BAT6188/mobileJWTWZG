package com.zondy.jwt.jwtmobile.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class BaseViewHolder {
	private final SparseArray<View> mViews;
	private int mPosition;

	public int getmPosition() {
		return mPosition;
	}

	private View mConvertView;
	private Context context;

	private BaseViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		this.context = context;
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * 拿到ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static BaseViewHolder get(Context context, View convertView,
									 ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new BaseViewHolder(context, parent, layoutId, position);
		}
		BaseViewHolder holder = (BaseViewHolder) convertView.getTag();
		holder.setPosition(position);

		return holder;
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对应的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 为TextView设置 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public BaseViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public BaseViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);
		return this;
	}
	
	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawable
	 * @return
	 */
	public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
		ImageView view = getView(viewId);
		view.setImageDrawable(drawable);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param bm
	 * @return
	 */
	public BaseViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}


	public int getPosition() {
		return mPosition;
	}

	public void setPosition(int position) {
		this.mPosition = position;
	}

}
