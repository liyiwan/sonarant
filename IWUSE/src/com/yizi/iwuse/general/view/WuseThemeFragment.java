package com.yizi.iwuse.general.view;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.yizi.iwuse.AppContext;
import com.yizi.iwuse.R;
import com.yizi.iwuse.common.utils.IWuseUtil;
import com.yizi.iwuse.common.widget.VideoWidget;
import com.yizi.iwuse.general.model.ThemeItem;
import com.yizi.iwuse.general.service.GeneralService;
import com.yizi.iwuse.general.service.events.ThemeEvent;

import de.greenrobot.event.EventBus;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 物色页面
 * 
 * @author hehaodong
 *
 */
public class WuseThemeFragment extends Fragment {

	private FirstItemMaxListView mListView;
	private FirstItemMaxAdapter mAdapter;
	private int maxHeight = 0;
	private int firstHeight = 0;
	private boolean isFisrt = true;
	private ViewGroup container;
	/***主题数据***/
	private ArrayList<ThemeItem> themeArray;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.container = container;
		View mView = inflater
				.inflate(R.layout.frg_wuse_theme, container, false);
		mListView = (FirstItemMaxListView) mView
				.findViewById(R.id.firstItemMaxListView);
		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		EventBus.getDefault().register(this);
		WindowManager wm = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		int mScreenHeight = wm.getDefaultDisplay().getHeight();
		System.out.println("MainHomeFragment.titleHeight"
				+ String.valueOf(MainHomeFragment.titleHeight));
		int statusHeight = IWuseUtil.getStatusBarHeight(getActivity());
		
		maxHeight = (mScreenHeight - MainHomeFragment.titleHeight - statusHeight) / 5 * 3;//列表项最大高度
		firstHeight = (mScreenHeight - MainHomeFragment.titleHeight - statusHeight) / 5;//列表项起始高度
		
		mAdapter = new FirstItemMaxAdapter();
		mListView.setAdapter(mAdapter);
		mListView.setItemHeight(firstHeight);
		mListView.setItemMaxHeight(maxHeight);
		GeneralService server = new GeneralService();
		server.doNetWork();
	}

	private class FirstItemMaxAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return (themeArray == null)?0:themeArray.size();
		}

		@Override
		public Object getItem(int i) {
			return themeArray.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int position, View view, ViewGroup viewGroup) {
			/*if (view == null) {
				view = LayoutInflater.from(getActivity()).inflate(
						R.layout.first_item_max_item, null);
				if(mDataSources.get(position).videoUrl != null && position == 0){
					String vdoPath = "android.resource://"+getActivity().getPackageName()+"/"+R.raw.demo2;
					view = new VideoWidget(getActivity(),view, vdoPath);
				}
			}*/
			
			ViewHolder viewHolder = new ViewHolder();
			ThemeItem item = themeArray.get(position);
			System.out.println("！！！！！！！！！！position = " +String.valueOf(position));
			
//			if (view == null) {
				view = LayoutInflater.from(getActivity()).inflate(
						R.layout.first_item_max_item, null);
//			}
			LinearLayout ll_video = (LinearLayout)view.findViewById(R.id.ll_video);
			if("视频".equals(item.property)){
				System.out.println("url不为空");
			}else{
				System.out.println("url为空！！！！！");
			}
			if("视频".equals(item.property)){
				if(view.getTag() != null){
					System.out.println("tag不为空");
					View videoView = (View)view.getTag();
					ll_video.removeAllViews();
					ll_video.addView(videoView);
					return view;
				}else{
					System.out.println("tag为空");
					String vdoPath = "android.resource://"+getActivity().getPackageName()+"/"+R.raw.demo3;
					View videoView = new VideoWidget(getActivity(),view, vdoPath);
					ll_video.removeAllViews();
					ll_video.addView(videoView);
					view.setTag(videoView);
				}
				viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
				viewHolder.tv_title.setText("标题标题标题标题");
				viewHolder.tv_kind = (TextView) view.findViewById(R.id.tv_kind);
				viewHolder.tv_kind.setText("分类类型");
				viewHolder.tv_property = (TextView) view.findViewById(R.id.tv_property);
				viewHolder.tv_property.setText("视频");
			}else{
				ll_video.setVisibility(View.GONE);
				viewHolder.cover = (ImageView) view.findViewById(R.id.cover);
				viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
				viewHolder.tv_title.setText("标题标题标题标题");
				viewHolder.tv_kind = (TextView) view.findViewById(R.id.tv_kind);
				viewHolder.tv_kind.setText("分类类型");
				viewHolder.tv_property = (TextView) view.findViewById(R.id.tv_property);
				viewHolder.tv_property.setText("图片");
				viewHolder.cover.setVisibility(View.VISIBLE);
				viewHolder.cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
				viewHolder.cover.setImageResource(item.themeUrl);
			}
			
			if (position == 0 && isFisrt) {
				isFisrt = false;
				view.setLayoutParams(new AbsListView.LayoutParams(
						AbsListView.LayoutParams.MATCH_PARENT, maxHeight));
			} else {
				view.setLayoutParams(new AbsListView.LayoutParams(
						AbsListView.LayoutParams.MATCH_PARENT, firstHeight));
			}
//			if(mDataSources.get(position).videoUrl != null){
//				String vdoPath = "android.resource://"+getActivity().getPackageName()+"/"+R.raw.demo;
//				view = new VideoWidget(getActivity(),view, vdoPath);
//				
//			}else{
			/*if(mDataSources.get(position).videoUrl == null){
				viewHolder.cover = (ImageView) view.findViewById(R.id.cover);
				viewHolder.cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
				viewHolder.cover.setImageResource(mDataSources.get(position).imgId);
			}*/
//			}
			return view;
		}

		private class ViewHolder {
			private ImageView cover;
			private SurfaceView surface;
			private TextView tv_title;
			private TextView tv_kind;
			private TextView tv_favor;
			private TextView tv_property;
		}
	}
	
	public void onEventMainThread(ThemeEvent event) {
		themeArray = event.getThemeArray();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

}
