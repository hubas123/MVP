package nz.co.udenbrothers.clockwork.app;

/**
 * Created by user on 28/08/2017.
 */

public class ApiLinks {
    public static final String SAVE_SHIFTS = AppConstants.API.BASE_URL+"v1/projects/shifts/save";
    public static final String SIGNUP = AppConstants.API.BASE_URL+"v1/authentication/create";
    public static final String UPGRADE_BUSS = AppConstants.API.BASE_URL+"v1/users/upgrade";
    public static String LINK_BUSS(String cid){return AppConstants.API.BASE_URL+"v1/users/" + cid + "/link";}
    public static final String FORGOT_PASS = AppConstants.API.BASE_URL+"v1/authentication/password/reset/request";
}
