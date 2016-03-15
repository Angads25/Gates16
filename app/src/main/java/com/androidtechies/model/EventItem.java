package com.androidtechies.model;

/**<p>
 * Created by Angad on 10/03/2016.
 * </p>
 */
public class EventItem {
    private String title, desc, imgUrl;
    private long time;
    private int event_id;

    public EventItem(String title,long time, String imgUrl,int event_id, String desc)
    {   this.title = title;
        this.imgUrl = imgUrl;
        this.time = time;
        this.event_id=event_id;
        this.desc=desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
}
