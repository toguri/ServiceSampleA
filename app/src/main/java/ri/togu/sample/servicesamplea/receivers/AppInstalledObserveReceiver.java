package ri.togu.sample.servicesamplea.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Random;

import ri.togu.sample.servicesamplea.services.ShoutIntentService;

public class AppInstalledObserveReceiver extends BroadcastReceiver {

    private static final String TAG = AppInstalledObserveReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String packagePath = intent.getDataString();
        Log.d(TAG, "onReceive action:" + action + "  packagePath:" + packagePath + " PID" + android.os.Process.myPid());

        Random r = new Random();
        int n = r.nextInt(100);
        if (n % 2 == 0) {
            ShoutIntentService.startActionBaz(context, "Bazが", "ｷﾀ━━━ヽ(∀ﾟ )人(ﾟ∀ﾟ)人( ﾟ∀)人(∀ﾟ )人(ﾟ∀ﾟ)人( ﾟ∀)ノ━━━!!!!");
        } else {
            ShoutIntentService.startActionFoo(context, "Fooが", "ｷﾀﾜｧ*･゜ﾟ･*:.｡..｡.:*･゜(n‘∀‘)ηﾟ･*:.｡. .｡.:*･゜ﾟ･*!!!!");
        }
    }
}
