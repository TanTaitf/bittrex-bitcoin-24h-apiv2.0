package Model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VP-T on 6/19/2017.
 */

public class Video implements Serializable {
    String title, thumnail,dercption, id, chanelTitle, dayupl;

    public String getDercption() {
        return dercption;
    }

    public String getTitle() {
        return title;
    }

    public Video(String id, String title, String dercption,String thumnail, String chanelTitle) {
        this.title = title;
        this.thumnail = thumnail;
        this.id = id;
        this.dercption = dercption;
        this.chanelTitle = chanelTitle;
    }
    public Video(String id, String title, String dercption,String thumnail, String chanelTitle, String dayupl) {
        this.title = title;
        this.thumnail = thumnail;
        this.id = id;
        this.dercption = dercption;
        this.chanelTitle = chanelTitle;
        this.dayupl = dayupl;
    }

    public Video(String title, String thumnail, String chanelTitle,String id) {
        this.id = id;
        this.title = title;
        this.thumnail = thumnail;
        this.chanelTitle = chanelTitle;
    }

    public String getThumnail() {
        return thumnail;
    }

    public String getId() {
        return id;
    }

    public String getDayupl() {
        dayupl = dayupl.substring(0,10);

        //dayupl = formateDateFromstring("yyyy-MM-dd", "dd-MM-yyyy", dayupl);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());

        Date date = null, dateend = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dayupl);
            dateend = format.parse(currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "ffff";
    }
    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            //LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getChanelTitle() {
        return chanelTitle;
    }
}
