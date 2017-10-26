package nz.co.udenbrothers.clockwork.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 */

public class WaitingDialogHelper {
    private ProgressDialog dlg;
    public WaitingDialogHelper(Context context){
        dlg = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
    }
    public void show(int title,int msg,boolean cancelable){
        hideDialog();
        if (dlg!=null) {
            if (title!=0)
                dlg.setTitle(title);
            dlg.setMessage(dlg.getContext().getString(msg));
            dlg.setCancelable(cancelable);
            try {
                dlg.show();
            }catch (Exception ignore){}
        }
    }
    public void show(int msg){
        show(0,msg,false);
    }
    public void show(int title,int msg){
        show(title,msg,false);
    }

    public void hideDialog(){
        if (dlg!=null&&dlg.isShowing()) {
            try {
                dlg.dismiss();
            }catch (Exception ignore){}
        }
    }
    public void release(){
        hideDialog();
        dlg = null;
    }

    public void setMessage(int msgId) {
        if (dlg!=null) {
            try {
                dlg.setMessage(dlg.getContext().getString(msgId));
            }
            catch (Exception ignore){}
        }
    }
    public void setTitle(int msgId) {
        if (dlg!=null) {
            try {
                dlg.setTitle(msgId);
            }
            catch (Exception ignore){}
        }
    }
    public boolean isShowing(){
        return dlg!=null&&dlg.isShowing();
    }

}
