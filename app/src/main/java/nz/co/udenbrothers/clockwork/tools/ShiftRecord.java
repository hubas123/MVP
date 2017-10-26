package nz.co.udenbrothers.clockwork.tools;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import nz.co.udenbrothers.clockwork.itemRecycler.items.ShiftRecyclerItem;
import nz.co.udenbrothers.clockwork.models.db.ShiftItem;


public class ShiftRecord {

    private List<ShiftRecyclerItem> shiftItems;

    public ShiftRecord( List<ShiftItem> shifts){
        shiftItems = new ArrayList<>();
        for (ShiftItem shift : shifts){
            ShiftRecyclerItem shiftItem = new ShiftRecyclerItem(shift);
            shiftItem.startDate = MyDate.strToDate(shift.getShiftTimeStartOnUtc());
            shiftItem.endDate = MyDate.strToDate(shift.getShiftTimeEndOnUtc());
            shiftItems.add(shiftItem);
        }
        Collections.sort(shiftItems);
    }

    public List<ShiftRecyclerItem> getBefore(int days){
        if(days <= 0) return shiftItems;
        List<ShiftRecyclerItem> newShifts = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1*days);
        for (ShiftRecyclerItem shiftItem: shiftItems){
            if(c.getTime().compareTo(shiftItem.startDate) < 0){
                newShifts.add(shiftItem);
            }
        }
        return newShifts;
    }

    public String beforeTotal(int days){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        if(days > 0) c.add(Calendar.DATE, -1*days);
        long total = 0;
        for (ShiftRecyclerItem shiftItem: shiftItems){
            if(c.getTime().compareTo(shiftItem.startDate) < 0){
                total  = total + shiftItem.endDate.getTime() - shiftItem.startDate.getTime();
            }
        }
        return MyDate.gethourMin(total);
    }
}
