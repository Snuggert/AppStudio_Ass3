package nl.mprog.scrooge;

/*
 * Abe Wiersma
 * 10433120
 * abe.wiersma@hotmail.nl
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nl.mprog.setup.scrooge.R;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<GridData> dataArray;

    /* Constructor for the ImageAdapter. */
    public ImageAdapter(Context c) {
        mContext = c;
        dataArray = new ArrayList<GridData>();
    }

    public void addItem(GridData newElement) {
        dataArray.add(newElement);
    }

    @Override
    public int getCount() {
        return dataArray.size();
    }

    @Override
    public GridData getItem(int position) {
        return  dataArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /* Create a new ImageView for each item referenced by the Adapter. */
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            gridView = inflater.inflate(R.layout.grid_item, null);

            TextView nameText = (TextView)
                    gridView.findViewById(R.id.company_name);
            nameText.setText(dataArray.get(position).Name);

            TextView priceText = (TextView)
                    gridView.findViewById(R.id.price);
            priceText.setText(dataArray.get(position).Price);


            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);
            String viewImage = "@drawable/" + dataArray.get(position).Name.toString();
            int imageResource = mContext.getResources().getIdentifier(
                    viewImage, null, mContext.getPackageName());

            Drawable res = mContext.getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        } else {
            gridView = (View) convertView;
        }
        return gridView;
    }
}