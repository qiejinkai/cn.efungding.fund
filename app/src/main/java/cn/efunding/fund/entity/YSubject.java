package cn.efunding.fund.entity;

import java.util.List;

import cn.efunding.fund.common.Value;

/**
 * Created by qiejinkai on 16/4/7.
 */
public class YSubject {

    private long objectId;

    private String name;

    private long yield;

    private long yieldTop;

    private long yieldBottom;

    private int count;

    private int targetCount;

    private long itemValue;

    private int closedDays;

    private int uCount;

    private int interestDays;

    private int status ;

    public YSubject() {
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getYield() {
        return yield;
    }

    public void setYield(long yield) {
        this.yield = yield;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(int targetCount) {
        this.targetCount = targetCount;
    }

    public long getItemValue() {
        return itemValue;
    }

    public void setItemValue(long itemValue) {
        this.itemValue = itemValue;
    }

    public int getClosedDays() {
        return closedDays;
    }

    public void setClosedDays(int closedDays) {
        this.closedDays = closedDays;
    }

    public int getuCount() {
        return uCount;
    }

    public void setuCount(int uCount) {
        this.uCount = uCount;
    }

    public int getInterestDays() {
        return interestDays;
    }

    public void setInterestDays(int interestDays) {
        this.interestDays = interestDays;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getYieldTop() {
        return yieldTop;
    }

    public void setYieldTop(long yieldTop) {
        this.yieldTop = yieldTop;
    }

    public long getYieldBottom() {
        return yieldBottom;
    }

    public void setYieldBottom(long yieldBottom) {
        this.yieldBottom = yieldBottom;
    }

    public YSubject(Object ysubject) {

        if(ysubject != null) {
            List<Object> attributes = (List<Object> )Value.objectValueForKey(ysubject,"attributes",null);
            this.objectId = Value.longValueForKey(ysubject, "objectId", 0);
            this.name = Value.stringValueForKey(ysubject, "name", "");
            this.yield = Value.longValueForKey(ysubject, "yield", 0);
            this.count = Value.intValueForKey(ysubject, "count", 0);
            this.targetCount = Value.intValueForKey(ysubject, "targetCount", 0);
            this.itemValue = Value.longValueForKey(ysubject, "itemValue", 0);
            this.closedDays = Value.intValueForKey(ysubject, "closedDays", 0);
            this.uCount = Value.intValueForKey(ysubject, "uCount", 0);
            this.interestDays = Value.intValueForKey(ysubject, "interestDays", 0);
            this.status = Value.intValueForKey(ysubject,"status",0);

            if(attributes != null && attributes.size() >0){

                for(Object obj : attributes){
                    String name = Value.stringValueForKey(obj,"name",null);
                    if("yieldTop".equals(name)){
                        this.yieldTop = Value.longValueForKey(obj,"value",0);
                        continue;
                    }

                    if("yieldBottom".equals(name)){
                        this.yieldBottom = Value.longValueForKey(obj,"value",0);
                        continue;
                    }
                }

            }
        }
    }
}
