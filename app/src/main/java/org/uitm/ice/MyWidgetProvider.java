package org.uitm.ice;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class MyWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		//super.onUpdate(context, appWidgetManager, appWidgetIds);

		for (int i=0; i<appWidgetIds.length; i++) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_nice);
			Intent openApp = new Intent(context, InTheClear.class);
			openApp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//openApp.setAction(InTheClear.UPDATEMOOD)
			PendingIntent pIntent = PendingIntent.getActivity(context, 0, openApp, 0);
			remoteViews.setOnClickPendingIntent(R.id.buttonStartWL, pIntent);
		/*
		remoteViews.setOnClickPendingIntent(R.id.btnOpen, buildButtonPendingIntent(context));
		*/
			//pushWidgetUpdate(context, remoteViews);
			appWidgetManager.updateAppWidget(appWidgetIds[i],remoteViews);
		}
	}
	/*
	public static PendingIntent buildButtonPendingIntent(Context context) {
		Intent intent = new Intent();
	    intent.setAction("pl.looksok.intent.action.CHANGE_PICTURE");
	    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
		ComponentName myWidget = new ComponentName(context, InTheClear.class);
	    AppWidgetManager manager = AppWidgetManager.getInstance(context);
	    manager.updateAppWidget(myWidget, remoteViews);		
	}
	*/
}
