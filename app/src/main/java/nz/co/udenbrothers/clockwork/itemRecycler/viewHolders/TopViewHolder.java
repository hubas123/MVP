package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.app.App;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.adapters.CurrentDayAdapter;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.db.ProjectItem;
import nz.co.udenbrothers.clockwork.models.db.ShiftItem;
import nz.co.udenbrothers.clockwork.serverices.UploadService;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.Popup;

public class TopViewHolder extends ItemHolder {

    private boolean isViewExpanded = false;
    private TextView started, worked, title;
    private Date startDate;
    private Handler handler;
    private Item item;
    private ViewGroup expandLayout;
    private ViewGroup collapseLayout;
    private RecyclerView dayItems;

    public TopViewHolder(CollectionView cv) {
        super(cv, R.layout.top_card_layout);

        expandLayout = findView(R.id.expandLayout);
        collapseLayout = findView(R.id.collapseLayout);

        started = findView(R.id.startedHour);
        worked = findView(R.id.workedHour);
        title = findView(R.id.cardTitle);
        dayItems = findView(R.id.dayItems);

        handler = new Handler();

        clicked(card, ()->{
            isViewExpanded = !isViewExpanded;
            showView();
            if(!isViewExpanded){
                expandView(collapseLayout,expandLayout);
                //collapseLayout.setVisibility(View.VISIBLE);
                //expandLayout.setVisibility(View.GONE);
            }
            else {
                expandView(expandLayout,collapseLayout);
                //collapseLayout.setVisibility(View.GONE);
                //expandLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void countDownStart() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                Date curDate = new Date();
                long diff = curDate.getTime() - startDate.getTime();
                long days = diff / (24 * 60 * 60 * 1000);
                diff -= days * (24 * 60 * 60 * 1000);
                long hours = diff / (60 * 60 * 1000);
                diff -= hours * (60 * 60 * 1000);
                long minutes = diff / (60 * 1000);
                diff -= minutes * (60 * 1000);
                long seconds = diff / 1000;
                worked.setText(String.format(Locale.ENGLISH,"%02d", hours) + ":" + String.format(Locale.ENGLISH,"%02d", minutes) + ":" + String.format(Locale.ENGLISH,"%02d", seconds));
            }
        };
        handler.postDelayed(runnable, 0);
    }

    @Override
    public void init(Item item){
        this.item = item;
        showView();
        if (isViewExpanded)
            collapseLayout.setVisibility(View.GONE);
        else
            expandLayout.setVisibility(View.GONE);
    }

    @Override
    public void cleanUp() {
        handler.removeCallbacksAndMessages(null);
    }

    private void showView() {
        if (isViewExpanded)
            showExpandedView();
        else
            showCollapsedView();
    }

    private void showExpandedView() {
        title.setText(R.string.your_day_today);

        List<ProjectItem> projects = ((App)itemView.getContext().getApplicationContext()).getDaoSession().getProjectItemDao().loadAll();
        List<ShiftItem> shiftsTemp = ((App)itemView.getContext().getApplicationContext()).getDaoSession().getShiftItemDao().loadAll();
        List<ShiftItem> shifts = new ArrayList<>();
        for (ShiftItem shift:shiftsTemp){
            Date startDate1 = shift.getStartDate();
            Date endDate = shift.getEndDate();
            if (endDate==null)
                shifts.add(shift);
            else if (DateUtils.isToday(endDate.getTime()))
                shifts.add(shift);
            else if (startDate1 !=null&&DateUtils.isToday(startDate1.getTime()))
                shifts.add(shift);
        }
        Map<String,String> prj = new HashMap<>();
        for (ProjectItem p:projects){
            prj.put(p.getQrCodeIdentifier(),p.getProjectName());
        }
        List<CurrentDayAdapter.DataToShow> data = new ArrayList<>();
        for (ShiftItem shift:shifts){
            data.add(new CurrentDayAdapter.DataToShow(prj.get(shift.getQrCodeIdentifier()),shift.getStartDate(),shift.getEndDate()));
        }
        if (Act.current()!=null)
            data.add(new CurrentDayAdapter.DataToShow(item.des,MyDate.strToDate(Act.startTime()),null));
        CurrentDayAdapter adapter = new CurrentDayAdapter(data);
        dayItems.setLayoutManager(new LinearLayoutManager(context));
        dayItems.setAdapter(adapter);
    }

    private void showCollapsedView(){
        title.setText(context.getString(R.string.working_now,item.des));

        startDate = MyDate.strToDate(Act.startTime());
        started.setText(MyDate.dateToStr(startDate,"HH:mm"));
        countDownStart();

        clicked(R.id.stopSessionButton, ()-> {

            ShiftItem shift = new ShiftItem(item.des, Act.startTime(), MyDate.dateToStr(new Date()), Profile.userID());
            shift.setComment(context.getString(R.string.comment_force_stop));
            shift.setManual(true);
            shift.setStopped(1);
            ((App)itemView.getContext().getApplicationContext()).getDaoSession().getShiftItemDao().insert(shift);
            String current = Act.current();
            Act.current(null);
            delete();
            getParentView().projectUpdated(current);
            Calendar rightNow = Calendar.getInstance();
            Popup popup = new Popup(context, R.layout.time_comment_layout);
            TimePicker timePicker = popup.getView(R.id.stopTimePicker);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(rightNow.get(Calendar.HOUR_OF_DAY));
            }
            else {
                timePicker.setCurrentHour(rightNow.get(Calendar.HOUR_OF_DAY));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setMinute(rightNow.get(Calendar.MINUTE));
            }
            else {
                timePicker.setCurrentMinute(rightNow.get(Calendar.MINUTE));
            }
            EditText commentBox = popup.getView(R.id.commentBox);

            popup.clicked(R.id.saveButton, ()->{
                shift.setComment( shift.getComment() + commentBox.getText().toString().trim());
                ((App)itemView.getContext().getApplicationContext()).getDaoSession().getShiftItemDao().update(shift);
                context.startService(new Intent(context, UploadService.class));
                popup.dismiss();
                getParentView().projectUpdated(current);
            });
            popup.show();

        });


    }
}
