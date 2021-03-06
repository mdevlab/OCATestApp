package io.mdevlab.ocatraining.notification;

import android.content.Context;

import io.mdevlab.ocatraining.util.Helper;
import io.mdevlab.ocatraining.util.UtilSharedPreferences;

/**
 * Created by husaynhakeem on 5/5/17.
 */

public class NotificationsManager {


    /**
     * Method that handles turning on/off notifications
     */
    public static void handleNotifications(Context context) {
        if (!UtilSharedPreferences.with(context).notificationsAreEnabled()) {
            turnOffNotifications();
            return;
        }
        turnOnNotifications();
    }


    private static void turnOffNotifications() {
        JobScheduler.cancelScheduledJobs();
    }


    public static void turnOnNotifications() {
        scheduleFirstNotification();
        schedulePeriodicJobsScheduler();
    }


    private static void scheduleFirstNotification() {
        JobScheduler.scheduleJob(Helper.timeUntilFirstNotification(), false, JobScheduler.FIRST_NOTIFICATION_TAG);
    }


    private static void schedulePeriodicJobsScheduler() {
        JobScheduler.scheduleJob(Helper.timeUntilFirstNotification(), false, JobScheduler.PERIODIC_NOTIFICATIONS_SCHEDULER_TAG);
    }


    public static void schedulePeriodicNotifications(Context context) {
        JobScheduler.scheduleJob(Helper.frequencyInMillis(UtilSharedPreferences.with(context).getNotificationsFrequency()),
                true,
                JobScheduler.PERIODIC_NOTIFICATION_TAG
        );
    }
}
