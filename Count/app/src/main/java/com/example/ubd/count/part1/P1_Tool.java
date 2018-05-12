package com.example.ubd.count.part1;

import android.graphics.PointF;

import java.util.Vector;

import static java.lang.Math.cos;

public class P1_Tool {
    //圆周率
    private double PI = 3.1415926535;
    //起算数据
    Vector<Integer> angle;
    Vector<PointF> location;
    //测得数据
    Vector<Integer> angleBegin;
    Vector<Float> length;
    //计算得到的方位角数据
    Vector<Integer> azimuth;
    //坐标增量
    Vector<PointF> locationAdd;

    Count1 count1;

    public P1_Tool(Count1 count1) {
        this.count1 = count1;
    }

    public void changeToSecond(String string1,String string2,int sign1) {
        if (sign1 <= 2) {
            String[] stringsAngle = string1.split("//*");
            String[] stringsLocation = string2.split("//*");
            angle.add(Integer.getInteger(stringsAngle[0])*60*60+Integer.getInteger(stringsAngle[1])*60+Integer.getInteger(stringsAngle[2]));
            PointF pointF = new PointF(Float.parseFloat(stringsLocation[0]),Float.parseFloat(stringsLocation[1]));
            location.add(pointF);
        } else {
            String[] strings1 = string1.split("//*");
            angleBegin.add(Integer.getInteger(strings1[0])*60*60+Integer.getInteger(strings1[1])*60+Integer.getInteger(strings1[2]));
            if (string2 != null)
                length.add(Float.parseFloat(string2));
        }
    }

    public void stepAll(int signForStep) {
        switch (signForStep) {
            case 1:
                step1();
                break;
            case 2:
                step2();
                break;
            case 3:
                step3();
                break;
            case 4:
                step4();
                break;
            case 5:
                step5();
                break;
        }
    }

    private String changeToFormatString(int item) {
        int a = item%60;
        int temp = (item-a)%(60*60);
        int b = temp/60;
        int c = 0;
        if (item >= 60*60)
            c = (item-(temp+a))/3600;
        return Integer.toString(c)+"'"+Integer.toString(b)+"'"+Integer.toString(a)+"''";
    }

    //计算内角改正后的数值
    private void step1() {
        int angelAll = 0;
        int num = angleBegin.size();
        for (int i = 0; i < num; i++) {
            angelAll+=angleBegin.get(i);
        }
        int angleDeviation = (angelAll - count1.sign1*180*60*60) - (angle.get(1) - angle.get(0));
        int angleDeviationItem = angleDeviation/count1.sign1;
        for (int i = 0; i < num; i++) {
            angleBegin.set(i,angleBegin.get(i)-angleDeviationItem);
        }
        //然后展示在recyclerView中
        int agoNum = count1.displayAdapter.displayStrings.size();
        count1.displayAdapter.displayStrings.clear();
        for (int i = 0; i < num; i++) {
            displayStrings strings = new displayStrings(changeToFormatString(angleBegin.get(i)),"");
            count1.displayAdapter.displayStrings.add(strings);
        }
        count1.displayAdapter.notifyItemRangeChanged(0,agoNum);
        String string = "angel";
        count1.textView.setText(string);
    }

    //计算各方位角，左角姑且先减180
    private void step2() {
        int temp = angle.get(0);
        int num = angleBegin.size()-1;
        for (int i = 0; i < num; i++) {
            temp = temp + angleBegin.get(i) - 180*60*60;
            azimuth.add(temp);
        }
    }

    //计算改正前的坐标增量
    private void step3() {
        int num = azimuth.size();
        for (int i = 0; i < num; i++) {
            double angelForCount = azimuth.get(i)*PI/(180*60*60);
            //是否损失精度，有待考虑
            float addX = (float)(length.get(i)*cos(angelForCount));
            float addY = (float)(length.get(i)*cos(angelForCount));
            PointF pointF = new PointF(addX,addY);
            locationAdd.add(pointF);
        }
    }

    //计算改正后的坐标增量
    private void step4() {
        int num = locationAdd.size();
        int lenAll = 0;
        for (int i = 0; i < num; i++) {
            lenAll+=length.get(i);
        }
        float deviationXAll = 0;
        float deviationYAll = 0;
        for (int i = 0; i < num; i++) {
            deviationXAll+=locationAdd.get(i).x;
            deviationYAll+=locationAdd.get(i).y;
        }
        float deviationX = deviationXAll - (location.get(1).x - location.get(0).x);
        float deviationY = deviationYAll - (location.get(1).y - location.get(0).y);
        for (int i = 0; i < num; i++) {
            locationAdd.set(i,new PointF(locationAdd.get(i).x-deviationX*length.get(i)/lenAll,locationAdd.get(i).y-deviationY*length.get(i)/lenAll));
        }
    }

    //计算平差结果坐标
    private void step5() {
        int num = locationAdd.size();
        float x = location.get(0).x;
        float y = location.get(0).y;
        PointF pointF = new PointF(x,y);
        for (int i = 0; i < num; i++) {
            x+=locationAdd.get(i).x;
            y+=locationAdd.get(i).y;
            pointF.set(x,y);
            locationAdd.set(i,pointF);
        }
    }
}
