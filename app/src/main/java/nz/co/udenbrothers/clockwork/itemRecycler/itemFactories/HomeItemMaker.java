package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.app.App;
import nz.co.udenbrothers.clockwork.app.V;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.db.ProjectItem;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.temps.Profile;


public class HomeItemMaker extends ItemMaker {

    private List<ProjectItem> projects;

    public HomeItemMaker(Context context) {
        super(context);
    }

    public List<Item> fetch() {

        projects = ((App)context.getApplicationContext()).getDaoSession().getProjectItemDao().loadAll();

        List<Item> items = new ArrayList<>();
        if(search(Act.current())){
            items.add(newItem(V.TOP, Act.current()));
        }
        else {
            Act.current(null);
        }

        if(Profile.role() == 1){
            for (ProjectItem model : projects) items.add(newItem(V.PROJECT, model));
        }
        else if (Profile.role() == 2){
            for (ProjectItem model : projects) items.add(newItem(V.SITE, model));
        }
        else {
            throw new ArithmeticException("Fatal");
        }

        items.add(newItem(V.TOTAL,context.getString(R.string.total)));
        return items;
    }

    public boolean search(String name) {
        if (name!=null){
            for (ProjectItem item:projects){
                if (name.equals(item.getProjectName()))
                    return true;
            }
        }
        return false;
    }

}
