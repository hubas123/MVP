package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.activities.staff.StaffActivity;
import nz.co.udenbrothers.clockwork.activities.staff.StaffHistoryActivity;
import nz.co.udenbrothers.clockwork.app.App;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.adapters.CurrentDayAdapter;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.db.ProjectItem;
import nz.co.udenbrothers.clockwork.models.db.ShiftItem;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.temps.TEMP;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.util.MyDateUtils;

import static nz.co.udenbrothers.clockwork.R.id.shifts;


public class ProjectViewHolder extends ItemHolder{

    private boolean isViewExpanded = false;
    private TextView title, today, week, month, records;
    private TextView todayE, weekE, monthE;
    private ViewGroup collapseLayout;
    private ViewGroup expandLayout;
    private ViewGroup noToday;
    private ViewGroup todayShifts;
    private RecyclerView shiftsView;
    private View activeDot;
    private ProjectItem project;

    private boolean isTodayExists = false;
    public ProjectViewHolder(CollectionView cv) {
        super(cv, R.layout.site_card_layout);

        title = findView(R.id.cardTitle);
        today = findView(R.id.todayDuration);
        week = findView(R.id.weekDuration);
        month = findView(R.id.monthDuration);
        todayE = findView(R.id.todayDurationE);
        weekE = findView(R.id.weekDurationE);
        monthE = findView(R.id.monthDurationE);

        records = findView(R.id.recordTxt);
        activeDot = findView(R.id.activeDot);

        collapseLayout = findView(R.id.collapseLayout);
        expandLayout = findView(R.id.expandLayout);
        noToday = findView(R.id.noToday);
        todayShifts = findView(R.id.todayShifts);
        shiftsView = findView(shifts);

        clicked(card, ()->{
            isViewExpanded = !isViewExpanded;
            showView();
            if(!isViewExpanded){
                expandView(collapseLayout,expandLayout);
            }
            else {
                expandView(expandLayout,collapseLayout);
            }
        });
    }

    private void showView() {
        if (isViewExpanded) {
            if (isTodayExists){
                noToday.setVisibility(View.GONE);
                todayShifts.setVisibility(View.VISIBLE);
            }
            else{
                noToday.setVisibility(View.VISIBLE);
                todayShifts.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void init(Item item){

        project = (ProjectItem) item.model;
        StaffActivity staffActivity = (StaffActivity) context;

        title.setText(project.getQrCodeIdentifier());
        List<ShiftItem> shiftsAll = ((App)itemView.getContext().getApplicationContext()).getDaoSession().getShiftItemDao().loadAll();
        List<ShiftItem> shifts = new ArrayList<>();
        for (ShiftItem shift:shiftsAll){
            if (!shift.getQrCodeIdentifier().equals(project.getQrCodeIdentifier()))
                continue;
            Date endDate = shift.getEndDate();
            if (endDate==null||MyDateUtils.isThisMonth(endDate.getTime())) {
                shifts.add(shift);
            }
        }
        int counts = shifts.size();
        isTodayExists = false;
        if(counts > 0){
            long todayD = 0;
            long weekD = 0;
            long monthD = 0;
            List<ShiftItem> todayShifts = new ArrayList<>();
            for (ShiftItem shift:shifts){
                if (shift.isToday()) {
                    todayD += shift.getDuration();
                    todayShifts.add(shift);
                }
                else if (shift.isThisWeek())
                    weekD+=shift.getDuration();
                else if (shift.isThisMonth())
                    monthD+=shift.getDuration();
            }
            isTodayExists = todayShifts.size()>0;
            createTodayAdapter(todayShifts);
            weekD+=todayD;
            monthD+=weekD;
            setDurationValue(today,todayD);
            setDurationValue(week,weekD);
            setDurationValue(month,monthD);
            setDurationValue(todayE,todayD);
            setDurationValue(weekE,weekD);
            setDurationValue(monthE,monthD);
        }
        else{
            setDurationValue(today,0);
            setDurationValue(week,0);
            setDurationValue(month,0);
            setDurationValue(todayE,0);
            setDurationValue(weekE,0);
            setDurationValue(monthE,0);
        }
        records.setText(context.getString(R.string.records_template,counts));

        if(Act.current() != null && Act.current().equals(project.getQrCodeIdentifier())){
            activeDot.setBackgroundResource( R.drawable.green_dot );
        }
        else {
            activeDot.setBackgroundResource( R.drawable.red_dot );
        }

        clicked(R.id.moreInfoButton, () -> {
            TEMP.QR = project.getQrCodeIdentifier();
            staffActivity.navigate(StaffHistoryActivity.class);
        });

        clicked(R.id.deleteButton, () -> {
            ((App)itemView.getContext().getApplicationContext()).getDaoSession().getProjectItemDao().delete(project);
            delete();
        });
        if (isViewExpanded){
            collapseLayout.setVisibility(View.GONE);
            expandLayout.setVisibility(View.VISIBLE);
        }
        else{
            collapseLayout.setVisibility(View.VISIBLE);
            expandLayout.setVisibility(View.GONE);
        }
    }

    private void createTodayAdapter(List<ShiftItem> shifts) {
        List<CurrentDayAdapter.DataToShow> data = new ArrayList<>();
        for (ShiftItem shift:shifts){
            data.add(new CurrentDayAdapter.DataToShow(shift.getStartDate(),shift.getEndDate()));
        }
        if (Act.current()!=null&&Act.current().equals(project.getQrCodeIdentifier()))
            data.add(new CurrentDayAdapter.DataToShow(MyDate.strToDate(Act.startTime()),null));
        CurrentDayAdapter adapter = new CurrentDayAdapter(data);
        shiftsView.setLayoutManager(new LinearLayoutManager(context));
        shiftsView.setAdapter(adapter);

    }

    @Override
    public void cleanUp() {}
}
