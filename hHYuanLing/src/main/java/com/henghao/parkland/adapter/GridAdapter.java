package com.henghao.parkland.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.henghao.parkland.R;
import com.henghao.parkland.utils.Bimp;

@SuppressLint("HandlerLeak")
public class GridAdapter extends BaseAdapter {
	private final LayoutInflater inflater;
	private int selectedPosition = -1;
	private boolean shape;

	private final Context mContext;

	public boolean isShape() {
		return this.shape;
	}

	public void setShape(boolean shape) {
		this.shape = shape;
	}

	public GridAdapter(Context context) {
		this.mContext = context;
		this.inflater = LayoutInflater.from(context);
	}

	public void update() {
		loading();
	}

	@Override
	public int getCount() {
		if (Bimp.tempSelectBitmap.size() == 6) {
			return 6;
		}
		return (Bimp.tempSelectBitmap.size() + 1);
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	public void setSelectedPosition(int position) {
		this.selectedPosition = position;
	}

	public int getSelectedPosition() {
		return this.selectedPosition;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = this.inflater.inflate(R.layout.item_published_grida, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position == Bimp.tempSelectBitmap.size()) {
			holder.image.setImageBitmap(BitmapFactory.decodeResource(this.mContext.getResources(),
			        R.drawable.icon_addpic_unfocused));
			if (position == 6) {
				holder.image.setVisibility(View.GONE);
			}
		}
		else {
			holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
		}

		return convertView;
	}

	public class ViewHolder {
		public ImageView image;
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					notifyDataSetChanged();
					break;
			}
			super.handleMessage(msg);
		}
	};

	public void loading() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (Bimp.max == Bimp.tempSelectBitmap.size()) {
						Message message = new Message();
						message.what = 1;
						GridAdapter.this.handler.sendMessage(message);
						break;
					}
					else {
						Bimp.max += 1;
						Message message = new Message();
						message.what = 1;
						GridAdapter.this.handler.sendMessage(message);
					}
				}
			}
		}).start();
	}
}
