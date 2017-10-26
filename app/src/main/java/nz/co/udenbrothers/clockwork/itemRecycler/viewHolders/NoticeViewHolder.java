package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.widget.TextView;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.db.NoticeItem;


public class NoticeViewHolder extends ItemHolder {

    private TextView title, message;

    public NoticeViewHolder(CollectionView cv) {
        super(cv, R.layout.notice_card_layout);

        title = (TextView) findView(R.id.title);
        message = (TextView) findView(R.id.message);
    }

    @Override
    public void init(Item item) {
        NoticeItem notice = (NoticeItem) item.model;
        title.setText(notice.getTitle());
        message.setText(notice.getMessage());
    }

    @Override
    public void cleanUp() {}
}
