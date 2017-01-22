package victor.crvit.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import victor.crvit.MainActivity;
import victor.crvit.R;
import victor.crvit.repositories.Location;

/**
 * Created by Viktor on 1/22/2017.
 */

public class LocationAdapter extends ArrayAdapter<Location> {

    private LayoutInflater mInflater;
    Location[] list;
    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Location getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.item, parent, false);
        }

        Location p = getItem(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.lad)).setText(Double.toString(p.Latitude));
        ((TextView) view.findViewById(R.id.log)).setText(Double.toString(p.Latitude));
        ((TextView) view.findViewById(R.id.time)).setText(p.time.toString());
        ((TextView) view.findViewById(R.id.dateAdd)).setText(p.createdDate.toString());

        return view;
    }

    public LocationAdapter(Location[] list, Context context) {
        super(context, R.layout.item,  list);
        this.list = list;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
