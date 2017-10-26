package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Checkable;
import android.widget.TextView;

import java.util.Date;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.abstractions.Cmd;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;

public abstract class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final String TAG = ItemHolder.class.getSimpleName();
    protected ViewGroup card;
    private SparseArray<Cmd> cmds = new SparseArray<>();
    protected Context context;
    private CollectionView collectionView;
    private Animation a;

    public ItemHolder(CollectionView cv, int Rid) {
        super(LayoutInflater.from(cv.tempVG.getContext()).inflate(Rid, cv.tempVG, false));
        card =  (ViewGroup) findView(R.id.card);
        context = cv.context;
        collectionView = cv;
    }

    protected <T extends View & Checkable> T findView(int Rid){
        return itemView.findViewById(Rid);
    }

    protected void delete(){
        collectionView.delete(getAdapterPosition());
    }

    protected void setHeight(int h){
        ViewGroup.LayoutParams layoutParams = card.getLayoutParams();
        layoutParams.height = h;
        card.setLayoutParams(layoutParams);
    }

    protected void clicked(View v, Cmd cd){
        v.setOnClickListener(this);
        cmds.put(v.getId(),cd);
    }

    protected void clicked(int id, Cmd cd){
        findView(id).setOnClickListener(this);
        cmds.put(id,cd);
    }

    protected void expandView(int height) {
        ValueAnimator anim = ValueAnimator.ofInt(card.getMeasuredHeightAndState(), height);
        anim.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue();
            setHeight(val);
        });
        anim.start();
    }

    public abstract void init(Item item);

    public abstract void cleanUp();

    @Override
    public void onClick(View v) {
        cmds.get(v.getId()).exec();
    }

    protected void expandView(final View viewToShow,final View viewToHide) {
        Log.d(TAG, "expandView:\n\t"+viewToShow+"\n\t"+viewToHide);
        viewToShow.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        final int targetHeight = viewToShow.getMeasuredHeight();
        viewToHide.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        final int srcHeight = viewToHide.getMeasuredHeight();

        /*viewToShow.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = viewToShow.getMeasuredHeight();
        viewToHide.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int srcHeight = viewToHide.getMeasuredHeight();
        */
        Log.d(TAG, "expandView: "+srcHeight+", "+targetHeight);
        if (a!=null)
            a.cancel();
        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                viewToShow.getLayoutParams().height = (int)(srcHeight+(targetHeight-srcHeight) * interpolatedTime);
                viewToHide.getLayoutParams().height = (int)(srcHeight+(targetHeight-srcHeight) * interpolatedTime);
                viewToShow.requestLayout();
                viewToHide.requestLayout();
                viewToHide.setAlpha(1-interpolatedTime);
                viewToShow.setAlpha(interpolatedTime);
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewToShow.setVisibility(View.VISIBLE);
                viewToShow.bringToFront();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewToHide.setVisibility(View.GONE);
                viewToShow.requestLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // 1dp/ms
        a.setDuration((int)(targetHeight / viewToShow.getContext().getResources().getDisplayMetrics().density*2));
        viewToShow.getLayoutParams().height = srcHeight;
        viewToShow.requestLayout();
        viewToShow.setVisibility(View.VISIBLE);
        viewToShow.startAnimation(a);
    }


    protected CollectionView getParentView(){
        return collectionView;
    }

    protected void setDurationValue(TextView view, Date startDate, Date endDate) {
        if (view==null)
            return;
        if (startDate==null||endDate==null||endDate.before(startDate))
            view.setText("");
        else{
            setDurationValue(view,endDate.getTime()-startDate.getTime());
        }
    }
    protected void setDurationValue(TextView view, long ms) {
        if (view==null)
            return;
        ms/=1000*60;
        int h = (int)(ms/60);
        int m = (int)(ms-h*60);
        view.setText(context.getString(R.string.time_format,h,m));
    }

}
