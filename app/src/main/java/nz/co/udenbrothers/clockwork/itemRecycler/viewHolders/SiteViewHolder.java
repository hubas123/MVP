package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.activities.boss.BossActivity;
import nz.co.udenbrothers.clockwork.activities.boss.BossMyTeamActivity;
import nz.co.udenbrothers.clockwork.app.App;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.db.ProjectItem;
import nz.co.udenbrothers.clockwork.models.db.ShiftItem;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;


public class SiteViewHolder extends ItemHolder{

    private boolean isViewExpanded = false;
    private TextView title, yesterday, week, month, records;
    private View activeDot;

    public SiteViewHolder(CollectionView cv) {
        super(cv, R.layout.site_card_layout);

        title = findView(R.id.cardTitle);
        yesterday = findView(R.id.todayHour);
        week = findView(R.id.weekHour);
        month = findView(R.id.monthHour);
        records = findView(R.id.recordTxt);
        activeDot = findView(R.id.activeDot);

        clicked(card, ()->{
            if(isViewExpanded){
                expandView(Kit.dps(120));
                isViewExpanded = false;
            }
            else {
                expandView(Kit.dps(215));
                isViewExpanded = true;
            }
        });
    }

    @Override
    public void init(Item item){
        ProjectItem project = (ProjectItem) item.model;

        BossActivity bossActivity = (BossActivity) context;

        setHeight(Kit.dps(120));
        title.setText(project.getQrCodeIdentifier());
        List<ShiftItem> shifts = ((App)itemView.getContext().getApplicationContext()).getDaoSession().getShiftItemDao().loadAll();
        int counts = shifts.size();
        if(counts > 0){
            Date startdate = MyDate.strToDate(shifts.get(counts - 1).getShiftTimeStartOnUtc());
            Date endDate = MyDate.strToDate(shifts.get(counts - 1).getShiftTimeEndOnUtc());
            yesterday.setText(MyDate.dateToStr(startdate, "HH:mm"));
            week.setText(MyDate.dateToStr(endDate, "HH:mm"));
            month.setText(MyDate.gethourMin(endDate.getTime() - startdate.getTime()));
        }
        records.setText("0 staff working  in this project");

        if(Act.current() != null && Act.current().equals(project.getQrCodeIdentifier())){
            activeDot.setBackgroundResource( R.drawable.green_dot );
        }
        else {
            activeDot.setBackgroundResource( R.drawable.red_dot );
        }

        clicked(R.id.moreInfoButton, () -> {
           // pref.putStr("selectedProjectName", project.qrCodeIdentifier);
            bossActivity.navigate(BossMyTeamActivity.class);
        });

        clicked(R.id.deleteButton, () -> {
            ((App)itemView.getContext().getApplicationContext())
                    .getDaoSession().getProjectItemDao().delete(project);
            delete();
        });
    }

    @Override
    public void cleanUp() {}
}
