package ccc.ioslockscreen;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Notif {

	public static int notifId = 654654;

	public static Notification getNotification(Context context) {

		Notification n = new Notification(R.drawable.ic_launcher,
				"Applock Secured", System.currentTimeMillis());

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				new Intent(context, StartActivity.class), 0);
		n.setLatestEventInfo(context,
				context.getResources().getString(R.string.app_name),
				"Tap to change settings", pendingIntent);

		return n;
	}

	public static void cancel(Context context) {
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		nm.cancel(notifId);
	}

}
