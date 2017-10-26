package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Parent class for recyclerview view holders
 * Has some additional helper methods
 * Created on 22.08.2017.
 */
public class BindableViewHolder extends RecyclerView.ViewHolder {
    private static final DateFormat DF_TIME = new SimpleDateFormat("HH:mm",Locale.getDefault());

    public BindableViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * Bind data for position to views
     * @param position position to show
     */
    public void bind(int position){}

    /**
     * Get data value for a clicked item
     * @param data list of adapters data
     * @param <T> type of data
     * @return data for clicked item or null
     */
    @Nullable
    protected <T> T getClickedItem(List<T> data){
        int position = getAdapterPosition();
        if (position!=RecyclerView.NO_POSITION&&data!=null&&position<data.size()){
            return data.get(position);
        }
        else
            return null;
    }

    @Nullable
    protected <T> T getClickedItem(T[] data){
        int position = getAdapterPosition();
        if (position!=RecyclerView.NO_POSITION&&data!=null&&position<data.length){
            return data[position];
        }
        else
            return null;
    }

    /**
     * Get context
     * @return activity context
     */
    protected Context getContext(){
        return itemView.getContext();
    }

    /**
     * Set string value to a view. It set "" if value is null
     * @param view text view to set value to
     * @param value value to set
     */
    protected void setStringValue(TextView view,String value){
        if (view==null)
            return;
        if (value==null)
            view.setText("");
        else
            view.setText(value);
    }

    /**
     * Set float value with formatting
     * @param view text view to set value to
     * @param value value to set
     * @param format string format
     * @param defForNull default value if value is null or NaN
     */
    protected void setFloatValue(TextView view, Float value, @NonNull String format, @NonNull String defForNull){
        if (view==null)
            return;
        if (value==null||Float.isNaN(value))
            view.setText(defForNull);
        else
            view.setText(String.format(Locale.getDefault(),format,value));
    }

    /**
     * Set integer value to a text view
     * @param view view to set value to
     * @param value integer value
     * @param defForNull    default value if value is null
     */
    protected void setIntegerValue(TextView view,Integer value,@NonNull String defForNull){
        if (view==null)
            return;
        if (value==null)
            view.setText(defForNull);
        else
            view.setText(String.valueOf(value));
    }
    protected void setTimeValue(TextView view, Date date) {
        if (view==null)
            return;
        if (date==null)
            view.setText("");
        else{
            view.setText(DF_TIME.format(date));
        }
    }

}
