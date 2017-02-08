package com.zondy.jwt.jwtmobile.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;

public abstract class BaseCommonAdapter<T> extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	protected final int mItemLayoutId;
	// 用来控制item的选中状态
	private HashMap<Integer, Boolean> isSelected;
	public BaseCommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
		isSelected = new HashMap<Integer, Boolean>();
		initSelectData();
	}

	public void setDatas(List<T> datas) {
		if (datas != null) {

			this.mDatas = datas;
		}
		this.notifyDataSetChanged();
	}

	// 初始化isSelected的数量
	public void initSelectData() {
		getIsSelected().clear();
		for (int i = 0; i < mDatas.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	public HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final BaseViewHolder viewHolder = getViewHolder(position, convertView,
				parent);
		convert(viewHolder, getItem(position));
		return viewHolder.getConvertView();
	}

	public abstract void convert(BaseViewHolder holder, T item);

	private BaseViewHolder getViewHolder(int position, View convertView,
										 ViewGroup parent) {
		return BaseViewHolder.get(mContext, convertView, parent, mItemLayoutId,
				position);
	}

	/**
	 * 刷新adapter
	 * 
	 * @param items
	 */
	public void updateAdapter(List<T> items) {
		mDatas.clear();
		mDatas.addAll(items);
		initSelectData();
		this.notifyDataSetChanged();
	}

}
