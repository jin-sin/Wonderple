package kr.co.easterbunny.wonderple.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.etsy.android.grid.util.DynamicHeightTextView;

import java.util.ArrayList;
import java.util.Random;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.databinding.ListItemImageBinding;
import kr.co.easterbunny.wonderple.library.util.JSLog;

/**
 * Created by scona on 2017-01-23.
 */

public class CurationGridAdapter extends ArrayAdapter<String> {


    static class ViewHolder {
        DynamicHeightTextView txtLineOne;
    }


    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private final ArrayList<Integer> mBackgroundColors;

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();


    public CurationGridAdapter(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        mBackgroundColors = new ArrayList<Integer>();
        mBackgroundColors.add(R.color.orange);
        mBackgroundColors.add(R.color.green);
        mBackgroundColors.add(R.color.blue);
        mBackgroundColors.add(R.color.yellow);
        mBackgroundColors.add(R.color.grey);
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        ListItemImageBinding binding;

        if (convertView == null) {

            binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.list_item_image, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }
        else {
            binding = (ListItemImageBinding) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);
        int backgroundIndex = position >= mBackgroundColors.size() ?
                position % mBackgroundColors.size() : position;

        convertView.setBackgroundResource(mBackgroundColors.get(backgroundIndex));

        JSLog.D("getView position:" + position + " h:" + positionHeight, new Throwable());

        binding.postImage.setHeightRatio(positionHeight);
        binding.postImage.setText(getItem(position) + position);


        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            JSLog.D("getPositionRatio:" + position + " ratio:" + ratio, new Throwable());
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }
}
