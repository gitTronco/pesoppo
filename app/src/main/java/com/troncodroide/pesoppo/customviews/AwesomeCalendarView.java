package com.troncodroide.pesoppo.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.CalendarView;

import java.util.List;

/**
 * Created by Tronco on 07/06/2015.
 */
public class AwesomeCalendarView extends CalendarView {

    List<Evento> events;

    public AwesomeCalendarView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //super.drawBackground(Canvas canvas);

    }

    public static class Evento{
        private String date;
        private int Color;
        private int type;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getColor() {
            return Color;
        }

        public void setColor(int color) {
            Color = color;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
