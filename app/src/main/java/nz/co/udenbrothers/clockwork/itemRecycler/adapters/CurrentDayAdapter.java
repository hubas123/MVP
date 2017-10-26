package nz.co.udenbrothers.clockwork.itemRecycler.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.BindableViewHolder;

/**
 *
 */

public class CurrentDayAdapter extends RecyclerView.Adapter<BindableViewHolder> {
    private final List<DataToShow> data;

    public CurrentDayAdapter(List<DataToShow> data) {
        this.data = data;
    }

    public static class DataToShow {
        private String name;
        private Date startDate;
        private Date endDate;
        private boolean showTitle;

        public DataToShow(String name, Date startDate, Date endDate) {
            this.name = name;
            this.startDate = startDate;
            this.endDate = endDate;
            this.showTitle = true;
        }
        public DataToShow(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.showTitle = false;
        }

    }

    @Override
    public BindableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_top_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BindableViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class ProjectViewHolder extends BindableViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.startTime)
        TextView startTime;
        @BindView(R.id.endTime)
        TextView endTime;
        @BindView(R.id.duration)
        TextView duration;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(int position) {
            DataToShow item = data.get(position);
            if (item.showTitle) {
                setStringValue(name, item.name);
                name.setVisibility(View.VISIBLE);
            }
            else{
                name.setVisibility(View.GONE);
            }
            setTimeValue(startTime,item.startDate);
            setTimeValue(endTime,item.endDate);
            setDurationValue(duration,item.startDate,item.endDate);
        }

        private void setDurationValue(TextView view, Date startDate, Date endDate) {
            if (view==null)
                return;
            if (startDate==null||endDate==null||endDate.before(startDate))
                view.setText("");
            else{
                long d = endDate.getTime()-startDate.getTime();
                d/=1000*60;
                int h = (int)(d/60);
                int m = (int)(d-h*60);
                view.setText(getContext().getString(R.string.time_format,h,m));
            }
        }

    }
}
