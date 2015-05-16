package com.troncodroide.pesoppo.fragments;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.troncodroide.pesoppo.R;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MultiNotificationsFragment extends Fragment {

    private LinearLayout content;
    private Timer timer = new Timer();
    private List<Message> mapNotification = new LinkedList<Message>();
    private LayoutInflater inflater;

    public static class Message implements Serializable {
        private static final long serialVersionUID = 1L;
        public final long id;
        public View view;
        public String text;
        public int type;
        public boolean persistence;
        public int seconds;

        public Message(String text, int type, boolean persistence, int seconds) {
            super();
            id = System.currentTimeMillis();
            this.text = text;
            this.type = type;
            this.persistence = persistence;
            this.seconds = seconds;
        }

        public void closeMessage() {
            this.type = Notification_Close;
        }
    }

    public static final String data = "dataObject";
    public static final String intentFilter = "com.troncodroide.pesoppo.notifications";
    public static final int Notification_Error = 0;
    public static final int Notification_Ok = 1;
    public static final int Notification_Loading = 2;
    public static final int Notification_Warning = 3;
    public static final int Notification_Close = 4;

    BroadcastReceiver notificationsReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Message message = (Message) intent.getSerializableExtra(data);

            View v = typeProcedure(message);
            if (message.type == Notification_Close) {
                for (Message msg : mapNotification) {
                    if (msg.id == message.id) {
                        removeViewNotification(msg.view);
                        mapNotification.remove(msg);
                    }
                }
            } else {
                if (!message.persistence) {

                    timer.schedule(new CustomTimerTask(message) {
                        @Override
                        public void run() {
                            if (getActivity() != null)
                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Message msg = getMsg();
                                        removeViewNotification(msg.view);
                                        mapNotification.remove(msg);
                                    }
                                });
                            // handler.sendEmptyMessage(Notification_Close);
                        }
                    }, message.seconds * 1000);
                }
                message.view = v;
                mapNotification.add(message);
                addViewNotification(v);
            }
        }
    };

    @SuppressLint("InflateParams")
    private View typeProcedure(Message msg) {
        int tipo = msg.type;

        View v = inflater.inflate(R.layout.notification_layout, null);
        try {
            ProgressBar loading = (ProgressBar) v.findViewById(R.id.progressBarNotificationLayout);
            loading.setVisibility(ProgressBar.GONE);
            TextView text = (TextView) v
                    .findViewById(R.id.textViewNotificationLayout);
            text.setText(msg.text);
            switch (tipo) {
                case Notification_Error:
                    v.setBackgroundColor(getResources().getColor(
                            R.color.rojo_claro));
                    break;
                case Notification_Ok:
                    v.setBackgroundColor(getResources().getColor(
                            R.color.verde_claro));
                    break;
                case Notification_Loading:
                    loading.setVisibility(ProgressBar.VISIBLE);
                    v.setBackgroundColor(getResources().getColor(
                            R.color.azul_claro));
                    break;
                case Notification_Warning:
                    v.setBackgroundColor(getResources().getColor(
                            R.color.naranja_claro));
                    break;
                case Notification_Close:

                    break;

                default:
                    break;
            }

        } catch (Exception ex) {

        }
        return v;

    }

    public void addViewNotification(View v) {
        content.addView(v);
        v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadeinnotification));
    }

    public synchronized void removeViewNotification(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout));
        v.getAnimation().setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                content.requestLayout();

            }
        });
        content.removeViewInLayout(v);
        //content.removeView(v);
    }

    public void onCreate(Bundle savedInstanceState) {
        IntentFilter mainFilter = new IntentFilter(
                MultiNotificationsFragment.intentFilter);
        getActivity().registerReceiver(notificationsReceiver, mainFilter);
        Log.i("NotificationFragment", "Create");
        super.onCreate(savedInstanceState);
    }

    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("NotificationFragment", "CreateView");
        View view = inflater.inflate(R.layout.multi_notifications_fragment_layout,
                container, false);
        this.inflater = inflater;

        content = (LinearLayout) view
                .findViewById(R.id.viewNotificationsContainer);
        content.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onDestroy() {
        Log.i("NotificationFragment", "Destroy");
        getActivity().unregisterReceiver(notificationsReceiver);
        super.onDestroy();
    }

    private class CustomTimerTask extends TimerTask {
        private Message msg;

        public CustomTimerTask(Message msg) {
            this.msg = msg;
        }

        public Message getMsg() {
            return msg;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub

        }

    }

}
