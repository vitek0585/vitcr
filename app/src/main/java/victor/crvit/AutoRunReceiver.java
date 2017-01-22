package victor.crvit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import victor.crvit.service.DoSomething;

public class AutoRunReceiver extends BroadcastReceiver {
    public AutoRunReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, DoSomething.class));
    }
}
