package nz.co.udenbrothers.clockwork.itemRecycler.items;

import android.support.annotation.NonNull;

import java.util.Date;

import nz.co.udenbrothers.clockwork.app.V;
import nz.co.udenbrothers.clockwork.models.db.ShiftItem;


public class ShiftRecyclerItem extends Item  implements Comparable<ShiftRecyclerItem>{

    public ShiftItem shift;
    public Date startDate;
    public Date endDate;

    public ShiftRecyclerItem(ShiftItem shift) {
        super(V.SHIFT);
        this.shift = shift;
    }

    @Override
    public int compareTo(@NonNull ShiftRecyclerItem item) {
        if (this.startDate == null || item.startDate == null) return 0;
        return item.startDate.compareTo(this.startDate);
    }
}
