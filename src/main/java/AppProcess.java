import java.util.HashMap;

/**
 * Created by usrc on 5/27/2016.
 */
public class AppProcess extends DailyProcess {
    AppRules appRules = new AppRules();
    HashMap<String, AppStats> appStatsMap = new HashMap();

    public AppProcess() {
        headLine = "imsi,use_date,seving_mcc,fqdn_host,filtered_host,cmi_app_id,cmti_app,app_use_freq,content_length,duration,app_user_agent\n";

    }

    public void process(XdrHttp xdrHttp) {
        String appKey = xdrHttp.getImsi() + "," + xdrHttp.getReadableDate() + "," + xdrHttp.getServingMcc() + "," + xdrHttp.getHost() + "," +
                xdrHttp.getFilteredHost() + "," + appRules.getCMIApp(xdrHttp.getHost()) + "," +
                appRules.getUSRCApp(xdrHttp.getHost() + xdrHttp.getUserAgent());
        if (!appStatsMap.containsKey(appKey)) {
            appStatsMap.put(appKey, new AppStats());
        }
        appStatsMap.get(appKey).addAppUsage(xdrHttp);
    }

    @Override
    public void writeOut(DailyWriter dailyWriter, boolean print_to_screan) {
        String output;
        for (String key : appStatsMap.keySet()
                ) {
            output = key + "," +
                    appStatsMap.get(key) + "\n";
            dailyWriter.write(output, print_to_screan);
        }
    }
}
