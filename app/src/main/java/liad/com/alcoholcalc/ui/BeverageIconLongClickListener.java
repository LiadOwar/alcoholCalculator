package liad.com.alcoholcalc.ui;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import liad.com.alcoholcalc.R;

/**
 * Created by liad on 07/05/2020.
 */

public class BeverageIconLongClickListener implements android.view.View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{

    @Override
    public boolean onLongClick(View v) {
        showBeverageIconMenu(v);
        return false;
    }

    private void showBeverageIconMenu(View v) {
        PopupMenu beverageMenu = new PopupMenu(v.getContext(), v);
        beverageMenu.setOnMenuItemClickListener(this);
        beverageMenu.inflate(R.menu.beverage_icon_menu);
        beverageMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        View actionView = item.getActionView();
        ImageView imageView = new ImageView(actionView.getContext());
        Context context = imageView.getContext();
        LinearLayout viewById = (LinearLayout)imageView.findViewById(R.id.linear_layout);

        return false;
    }
}
