/*
 * Tint Browser for Android
 * 
 * Copyright (C) 2012 - to infinity and beyond J. Devauchelle and contributors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package vn.viettel.browser.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import acr.browser.lightning.R;
import acr.browser.lightning.constant.HistoryPage;
import acr.browser.lightning.database.BookmarkManager;
import acr.browser.lightning.database.HistoryItem;
import vn.viettel.browser.provider.BookmarksProvider;

public class BookmarksAdapter extends BaseAdapter {

	private Context mContext;
	private List<HistoryItem> mData;
	public BookmarksAdapter(Context context) {
		this.mContext = context;
		mData = BookmarkManager.getInstance(context).getBookmarks(true);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mData.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View superView = LayoutInflater.from(mContext).inflate(R.layout.start_page_row, parent, false);
				
		ImageView thumbnailView = (ImageView) superView.findViewById(R.id.BookmarkRow_Thumbnail);
		

			byte[] thumbnail = null;
			if (thumbnail != null) {
				thumbnailView.setImageBitmap(BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length));
			} else {
				//thumbnailView.setImageResource(mDefaultThumbnailId);
//				thumbnailView.setImageResource(mContext.getResources().getColor(getColor(position)));
				thumbnailView.setBackgroundColor(Color.parseColor(mContext.getResources().getString(getString(position))));
//				thumbnailView.setImageResource(mContext.getResources().getColor(getColor(position)));
			}
		return superView;
	}

	private int getColor(int p){
		int po = (p % 7) + 1;
		switch (po){
			case 1:
				return R.color.home_1;
			case 2:
				return R.color.home_1;
			case 3:
				return R.color.home_3;
			case 4:
				return R.color.home_4;
			case 5:
				return R.color.home_5;
			case 6:
				return R.color.home_6;
			case 7:
				return R.color.home_7;
			default:
				return R.color.home_1;
		}
	}

	private int getColorHex(int p){
		int po = (p % 7) + 1;
		switch (po){
			case 1:
				return 0x14b609;
			case 2:
				return 0xf1645b;
			case 3:
				return 0xffd300;
			case 4:
				return 0x0075fd;
			case 5:
				return 0xfe6e00;
			case 6:
				return 0x16d8f3;
			case 7:
				return 0x860102;
			default:
				return 0x14b609;
		}
	}

	private int getString(int p){
		int po = (p % 7) + 1;
		switch (po){
			case 1:
				return R.string.home_1;
			case 2:
				return R.string.home_1;
			case 3:
				return R.string.home_3;
			case 4:
				return R.string.home_4;
			case 5:
				return R.string.home_5;
			case 6:
				return R.string.home_6;
			case 7:
				return R.string.home_7;
			default:
				return R.string.home_1;
		}
	}

}
