package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.widget.TextView;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.app.App;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.tools.ShiftRecord;

public class TotalViewHolder extends ItemHolder {

    private TextView title, yesterday, week, month;

    public TotalViewHolder(CollectionView cv) {
        super(cv, R.layout.total_card_layout);

        title =  (TextView) findView(R.id.cardTitle);
        yesterday =  (TextView) findView(R.id.todayHour);
        week =  (TextView) findView(R.id.weekHour);
        month =  (TextView) findView(R.id.monthHour);
    }

    @Override
    public void init(Item item){
        title.setText(item.des);
        ShiftRecord shiftRecord = new ShiftRecord(((App)itemView.getContext().getApplicationContext()).getDaoSession().getShiftItemDao().loadAll());
        yesterday.setText(shiftRecord.beforeTotal(1));
        week.setText(shiftRecord.beforeTotal(7));
        month.setText(shiftRecord.beforeTotal(30));
    }

    @Override
    public void cleanUp() {}
}
