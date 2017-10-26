package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nz.co.udenbrothers.clockwork.app.App;
import nz.co.udenbrothers.clockwork.itemRecycler.items.HeaderItem;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.ShiftRecyclerItem;
import nz.co.udenbrothers.clockwork.models.db.ShiftItemDao;
import nz.co.udenbrothers.clockwork.temps.TEMP;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.ShiftRecord;


public class HistoryItemMaker extends ItemMaker {

    private ShiftRecord shiftRecord;

    private List<Item> pack(List<ShiftRecyclerItem> shiftItems){
        List<Item> items = new ArrayList<>();
        String headerDate = "";
        String thisDate;
        HeaderItem curHeadItem = new HeaderItem();
        for (ShiftRecyclerItem shiftItem : shiftItems){
            thisDate = MyDate.dateToStr(shiftItem.startDate,"yyyy-MM-dd");
            if(!headerDate.equals(MyDate.dateToStr(shiftItem.startDate,"yyyy-MM-dd"))){
                headerDate = thisDate;
                curHeadItem = new HeaderItem();
                curHeadItem.des = headerDate;
                items.add(curHeadItem);
            }
            curHeadItem.total = curHeadItem.total + shiftItem.endDate.getTime() - shiftItem.startDate.getTime();
            items.add(shiftItem);
        }
        return items;
    }

    public HistoryItemMaker(Context context) {
        super(context);
    }

    public List<Item> fetch(int days) {
        ShiftItemDao dao = ((App)context.getApplicationContext()).getDaoSession().getShiftItemDao();
        if(TEMP.QR != null){
            shiftRecord = new ShiftRecord(dao.queryBuilder().where(ShiftItemDao.Properties.QrCodeIdentifier.eq(TEMP.QR)).list());
        }
        else if(TEMP.UID != null){
            shiftRecord = new ShiftRecord(dao.queryBuilder().where(ShiftItemDao.Properties.UserId.eq(TEMP.UID)).list());
        }
        else {
            shiftRecord = new ShiftRecord(dao.loadAll());
        }

        return pack(shiftRecord.getBefore(days));
    }

    public String getTotal(int days){
        return shiftRecord.beforeTotal(days);
    }

}
