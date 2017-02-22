package com.benefit.buy.library.utils.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 正在表达式验证工具类（验证身份证、车牌号等） 〈一句话功能简述〉 〈功能详细描述〉
 * @author qinyulun
 * @version HDMNV100R001, 2013-11-18
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ToolsValidate {

    /**
     * 验证str是否为正确的身份证格式
     * @param str
     * @return
     */
    public static boolean isIdentityCard(EditText view) {
        boolean flag = true;
        String licenc = view.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /*
         * { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古", 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
         * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南", 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
         * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃", 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" }
         */
        String provinces = "11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91";
        Pattern pattern = Pattern.compile("^[1-9]\\d{14}");
        Matcher matcher = pattern.matcher(licenc);
        Pattern pattern2 = Pattern.compile("^[1-9]\\d{16}[\\d,x,X]$");
        Matcher matcher2 = pattern2.matcher(licenc);
        // 粗略判断
        if (!matcher.find() && !matcher2.find()) {
            view.setError(fromHtml("身份证号必须为15或18位数字（最后一位可以为X）"));
            flag = false;
        }
        else {
            // 判断出生地
            if (provinces.indexOf(licenc.substring(0, 2)) == -1) {
                view.setError(fromHtml("身份证号前两位不正确！"));
                flag = false;
            }
            // 判断出生日期
            if (licenc.length() == 15) {
                String birth = "19" + licenc.substring(6, 8) + "-" + licenc.substring(8, 10) + "-"
                        + licenc.substring(10, 12);
                try {
                    Date birthday = sdf.parse(birth);
                    if (!sdf.format(birthday).equals(birth)) {
                        view.setError(fromHtml("出生日期非法！"));
                        flag = false;
                    }
                    if (birthday.after(new Date())) {
                        view.setError(fromHtml("出生日期不能在今天之后！"));
                        flag = false;
                    }
                }
                catch (ParseException e) {
                    view.setError(fromHtml("出生日期非法！"));
                    flag = false;
                }
            }
            else if (licenc.length() == 18) {
                String birth = licenc.substring(6, 10) + "-" + licenc.substring(10, 12) + "-"
                        + licenc.substring(12, 14);
                try {
                    Date birthday = sdf.parse(birth);
                    if (!sdf.format(birthday).equals(birth)) {
                        view.setError(fromHtml("出生日期非法！"));
                        flag = false;
                    }
                    if (birthday.after(new Date())) {
                        view.setError(fromHtml("出生日期不能在今天之后！"));
                        flag = false;
                    }
                }
                catch (ParseException e) {
                    view.setError(fromHtml("出生日期非法！"));
                    flag = false;
                }
            }
            else {
                view.setError(fromHtml("身份证号位数不正确，请确认！"));
                flag = false;
            }
        }
        if (!flag) {
            view.requestFocus();
        }
        return flag;
    }

    /**
     * 不为空时，验证str是否为正确的身份证格式
     * @param str
     * @return
     */
    public static boolean maybeIsIdentityCard(EditText view) {
        boolean flag = true;
        String licenc = view.getText().toString();
        if (!licenc.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            /*
             * { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古", 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
             * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南", 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
             * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃", 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"
             * }
             */
            String provinces = "11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91";
            Pattern pattern = Pattern.compile("^[1-9]\\d{14}");
            Matcher matcher = pattern.matcher(licenc);
            Pattern pattern2 = Pattern.compile("^[1-9]\\d{16}[\\d,x,X]$");
            Matcher matcher2 = pattern2.matcher(licenc);
            // 粗略判断
            if (!matcher.find() && !matcher2.find()) {
                view.setError(fromHtml("身份证号必须为15或18位数字（最后一位可以为X）"));
                flag = false;
            }
            else {
                // 判断出生地
                if (provinces.indexOf(licenc.substring(0, 2)) == -1) {
                    view.setError(fromHtml("身份证号前两位不正确！"));
                    flag = false;
                }
                // 判断出生日期
                if (licenc.length() == 15) {
                    String birth = "19" + licenc.substring(6, 8) + "-" + licenc.substring(8, 10) + "-"
                            + licenc.substring(10, 12);
                    try {
                        Date birthday = sdf.parse(birth);
                        if (!sdf.format(birthday).equals(birth)) {
                            view.setError(fromHtml("出生日期非法！"));
                            flag = false;
                        }
                        if (birthday.after(new Date())) {
                            view.setError(fromHtml("出生日期不能在今天之后！"));
                            flag = false;
                        }
                    }
                    catch (ParseException e) {
                        view.setError(fromHtml("出生日期非法！"));
                        flag = false;
                    }
                }
                else if (licenc.length() == 18) {
                    String birth = licenc.substring(6, 10) + "-" + licenc.substring(10, 12) + "-"
                            + licenc.substring(12, 14);
                    try {
                        Date birthday = sdf.parse(birth);
                        if (!sdf.format(birthday).equals(birth)) {
                            view.setError(fromHtml("出生日期非法！"));
                            flag = false;
                        }
                        if (birthday.after(new Date())) {
                            view.setError(fromHtml("出生日期不能在今天之后！"));
                            flag = false;
                        }
                    }
                    catch (ParseException e) {
                        view.setError(fromHtml("出生日期非法！"));
                        flag = false;
                    }
                }
                else {
                    view.setError(fromHtml("身份证号位数不正确，请确认！"));
                    flag = false;
                }
            }
            if (!flag) {
                view.requestFocus();
            }
        }
        return flag;
    }

    /**
     * 验证str是否为正确的身份证格式
     * @param str
     * @return
     */
    public static boolean isIdentityCard(String licenc) {
        boolean flag = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /*
         * { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古", 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
         * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南", 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
         * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃", 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" }
         */
        String provinces = "11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91";
        Pattern pattern = Pattern.compile("^[1-9]\\d{14}");
        Matcher matcher = pattern.matcher(licenc);
        Pattern pattern2 = Pattern.compile("^[1-9]\\d{16}[\\d,x,X]$");
        Matcher matcher2 = pattern2.matcher(licenc);
        // 粗略判断
        if (!matcher.find() && !matcher2.find()) {
            flag = false;
        }
        else {
            // 判断出生地
            if (provinces.indexOf(licenc.substring(0, 2)) == -1) {
                flag = false;
            }
            // 判断出生日期
            if (licenc.length() == 15) {
                String birth = "19" + licenc.substring(6, 8) + "-" + licenc.substring(8, 10) + "-"
                        + licenc.substring(10, 12);
                try {
                    Date birthday = sdf.parse(birth);
                    if (!sdf.format(birthday).equals(birth)) {
                        flag = false;
                    }
                    if (birthday.after(new Date())) {
                        flag = false;
                    }
                }
                catch (ParseException e) {
                    flag = false;
                }
            }
            else if (licenc.length() == 18) {
                String birth = licenc.substring(6, 10) + "-" + licenc.substring(10, 12) + "-"
                        + licenc.substring(12, 14);
                try {
                    Date birthday = sdf.parse(birth);
                    if (!sdf.format(birthday).equals(birth)) {
                        flag = false;
                    }
                    if (birthday.after(new Date())) {
                        flag = false;
                    }
                }
                catch (ParseException e) {
                    flag = false;
                }
            }
            else {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 验证str是否为正确的车牌号
     * @param str
     * @return
     */
    public static boolean isPlateNo(EditText view) {
        String no = view.getText().toString().trim();
        if ((no == null) || no.equals("")) {
            return false;
        }
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String[] str1 = {"京", "津", "冀", "晋", "蒙", "辽", "吉", "黑", "沪", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤",
                "桂", "琼", "渝", "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新", "农", "台", "中", "武", "WJ", "亥", "戌", "酉",
                "申", "未", "午", "巳", "辰", "卯", "寅", "丑", "子", "葵", "壬", "辛", "庚", "己", "戊", "丁", "丙", "乙", "甲", "河北",
                "山西", "北京", "北", "南", "兰", "沈", "济", "成", "广", "海", "空", "军", "京V", "使"};
        if (no.equals("新车")) {
            return true;
        }
        if (no.length() == 7) {
            int h = 0;
            for (int r = 0; r < no.length(); r++) {
                if (str.indexOf(no.charAt(r)) != -1) {
                    h++;
                }
            }
            if (h == 7) {
                return true;
            }
        }
        if (no.length() > 1) {
            String jq1 = no.substring(0, 1);
            String jq2 = no.substring(0, 2);
            for (int k = 0; k < str1.length; k++) {
                if (str1[k].equals(jq1)) {
                    if (no.length() <= 8) {
                        return true;
                    }
                }
                if (str1[k].equals(jq2)) {
                    if (no.length() <= 8) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isEmpty(TextView w, String displayStr) {
        if (ToolsKit.isEmpty(w.getText().toString())) {
            w.setError(fromHtml(displayStr + "不能为空！"));
            w.setFocusable(true);
            w.requestFocus();
            return true;
        }
        return false;
    }

    public static boolean isNum(TextView w, String displayStr) {
        if (!ToolsKit.isEmpty(w.getText().toString().trim())) {
            w.setError(fromHtml(displayStr + "必须为整数且大于0！"));
            w.setFocusable(true);
            w.requestFocus();
            return true;
        }
        return false;
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    public static boolean isDouble(TextView w, String displayStr) {
        if (!ToolsKit.decimal(w.getText().toString().trim())) {
            w.setError(fromHtml(displayStr + "必须为数字且大于0！"));
            w.setFocusable(true);
            w.requestFocus();
            return true;
        }
        return false;
    }

    public static boolean isNumber(TextView w, String displayStr) {
        String pass = w.getText().toString();
        if (!ToolsKit.isEmpty(pass) && (pass.length() < 6)) {
            w.setError(fromHtml(displayStr + "最少六位！"));
            w.setFocusable(true);
            w.requestFocus();
            w.setSelected(true);
            return true;
        }
        return false;
    }

    public static boolean isUserNum(TextView w, String displayStr) {
        String pass = w.getText().toString();
        if ((pass.length() > 6)) {
            w.setError(fromHtml(displayStr + "最多六位！"));
            w.setFocusable(true);
            w.requestFocus();
            w.setSelected(true);
            return true;
        }
        return false;
    }

    public static boolean isEquals(TextView w, TextView repeatText, String displayStr) {
        String str = w.getText().toString();
        String repeatStr = repeatText.getText().toString();
        if (!ToolsKit.isEmpty(str) && !str.equals(repeatStr)) {
            repeatText.setError(fromHtml(displayStr + "不相等！"));
            repeatText.setFocusable(true);
            repeatText.requestFocus();
            repeatText.setSelected(true);
            return true;
        }
        return false;
    }

    public static boolean isPwdEquals(TextView w, TextView repeatText) {
        String str = w.getText().toString();
        String repeatStr = repeatText.getText().toString();
        if (!ToolsKit.isEmpty(str) && !str.equals(repeatStr) && !str.equals(repeatStr)) {
            repeatText.setError(fromHtml("密码输入不匹配"));
            repeatText.setFocusable(true);
            repeatText.requestFocus();
            repeatText.setSelected(true);
            return true;
        }
        return false;
    }

    public static boolean isEqualsValue(TextView repeatText, String val, String displayStr) {
        String repeatStr = repeatText.getText().toString();
        if (!ToolsKit.isEmpty(repeatStr) && !repeatStr.equals(val)) {
            repeatText.setError(fromHtml(displayStr + "有误！"));
            repeatText.setFocusable(true);
            repeatText.requestFocus();
            repeatText.setSelected(true);
            return true;
        }
        return false;
    }

    // 可以空,非空时必须正确
    public static boolean maybeAccountNumberEmpty(TextView w) {
        if (!ToolsKit.isEmpty(w.getText().toString().trim())) {
            return isAccountNumber(w);
        }
        return true;
    }

    // 可以空,非空时必须正确
    public static boolean maybeMobileEmpty(TextView w) {
        if (!ToolsKit.isEmpty(w.getText().toString().trim())) {
            return isMobileNumber(w);
        }
        return true;
    }

    public static boolean isArea(TextView w) {
        if (!ToolsRegex.isArea(w.getText().toString().trim())) {
            w.setError(fromHtml("面积有非法字符！"));
            w.setFocusable(true);
            return false;
        }
        return true;
    }

    public static boolean isMobileNumber(TextView w) {
        if (!ToolsRegex.isMobileNumber(w.getText().toString().trim())) {
            w.setError(fromHtml("手机号码不正确！"));
            w.setFocusable(true);
            w.requestFocus();
            return false;
        }
        return true;
    }

    /** 银行帐号为16-21位的数字 */
    public static boolean isAccountNumber(TextView w) {
        if (!ToolsRegex.isAccountNumber(w.getText().toString().trim())) {
            w.setError(fromHtml("银行帐号必须为16-21位的数字！"));
            w.setFocusable(true);
            return false;
        }
        return true;
    }

    /**
     * 校验网络-如果没有网络就弹出设置,并返回true. false 提示联网状态〈一句话功能简述〉 〈功能详细描述〉
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static boolean validateInternet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        else {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 校验网络-如果没有网络就返回true. 〈一句话功能简述〉 〈功能详细描述〉
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static boolean hasInternetConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo network = manager.getActiveNetworkInfo();
            if ((network != null) && network.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断GPS是否已经开启. 〈一句话功能简述〉 〈功能详细描述〉
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static boolean hasLocationGPS(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断基站是否已经开启.
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static boolean hasLocationNetWork(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 设备 〈一句话功能简述〉 〈功能详细描述〉
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = tm.getDeviceId();
            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static CharSequence fromHtml(String msg) {
        CharSequence html = Html.fromHtml("<font color='red'>" + msg + "</font>"); // 任意长度内容均可
        return html;
    }
}
