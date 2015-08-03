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

package vn.viettel.browser.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import acr.browser.lightning.R;
import acr.browser.lightning.constant.HistoryPage;
import vn.viettel.browser.adapter.BookmarksAdapter;
import vn.viettel.browser.provider.BookmarksProvider;
import vn.viettel.browser.utils.Constants;

public abstract class HomePageStartPageFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Object> {

	@Override
	public Loader<Object> onCreateLoader(int id, Bundle args) {
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Object> loader, Object data) {

	}

	@Override
	public void onLoaderReset(Loader<Object> loader) {

	}

	public interface OnStartPageItemClickedListener {
		public void onStartPageItemClicked(String url);
	}
	
	private View mParentView = null;
	
	private GridView mGrid;	
	private BookmarksAdapter mAdapter;
	
	private OnStartPageItemClickedListener mListener = null;
	
	private OnSharedPreferenceChangeListener mPreferenceChangeListener;
	
	protected Activity mActivity;
	
	private boolean mInitialized;
	
	private boolean mListShown = true;

	private Context mContext;

	public HomePageStartPageFragment() {
		mInitialized = false;
	}

	public static HomePageStartPageFragment newInstance(final int id, Context context){
		HomePageStartPageFragment fragment = new HomePageStartPageFragment() {
			@Override
			protected int getStartPageFragmentLayout() {
				return id;
			}
		};
		fragment.mInitialized = false;
		fragment.mContext = context;
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if (!mInitialized) {
			mActivity = activity;
			mInitialized = true;
		}		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mParentView == null) {		
			mParentView = inflater.inflate(getStartPageFragmentLayout(), container, false);
			mGrid = (GridView) mParentView.findViewById(R.id.StartPageFragmentGrid);
			
			String[] from = new String[] { BookmarksProvider.Columns.TITLE, BookmarksProvider.Columns.URL };
			int[] to = new int[] { R.id.StartPageRowTitle, R.id.StartPageRowUrl };
			
			mAdapter = new BookmarksAdapter(mContext);
			
			mGrid.setAdapter(mAdapter);
			
			mGrid.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					if (mListener != null) {
						mListener.onStartPageItemClicked(HistoryPage.getWebHistory(getActivity()).get(position).getUrl());
					}
				}
			});
			
//			mGrid.setOnTouchListener(mUIManager);
			
			mPreferenceChangeListener = new OnSharedPreferenceChangeListener() {
				@Override
				public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
					if (Constants.PREFERENCE_START_PAGE_LIMIT.equals(key)) {
						getLoaderManager().restartLoader(0, null, HomePageStartPageFragment.this);
					}
				}			
			};

			PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(mPreferenceChangeListener);
		}
		
		return mParentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setListShown(false);
		
		mParentView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if (isAdded()) {
					getLoaderManager().initLoader(0, null, HomePageStartPageFragment.this);
				}
			}
		}, 100);
	}	

	@Override
	public void onDestroy() {
		PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(mPreferenceChangeListener);
		super.onDestroy();
	}

//	@Override
//	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//		int limit;
//		try {
//			limit = Integer.parseInt(
//					PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(
//							Constants.PREFERENCE_START_PAGE_LIMIT,
//							Integer.toString(getResources().getInteger(R.integer.default_start_page_items_number))));
//		} catch (Exception e) {
//			limit = getResources().getInteger(R.integer.default_start_page_items_number);
//		}
//
//		return BookmarksWrapper.getCursorLoaderForStartPage(getActivity(), limit);
//	}
//
//	@Override
//	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//		mAdapter.swapCursor(data);
//		setListShown(true);
//	}
//
//	@Override
//	public void onLoaderReset(Loader<Cursor> loader) {
//		mAdapter.swapCursor(null);
//	}



	public void setOnStartPageItemClickedListener(OnStartPageItemClickedListener listener) {
		mListener = listener;
	}
	
	protected abstract int getStartPageFragmentLayout();
	
	private void setListShown(boolean shown) {
		
		if (mListShown == shown) {
			return;
		}
		
		mListShown = shown;
		
		if (shown) {
			mGrid.setVisibility(View.VISIBLE);
		} else {
			mGrid.setVisibility(View.GONE);
		}
	}

}
