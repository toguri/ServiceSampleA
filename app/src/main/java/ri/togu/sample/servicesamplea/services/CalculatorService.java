package ri.togu.sample.servicesamplea.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class CalculatorService extends Service {
    private static final String TAG = CalculatorService.class.getSimpleName();
    private RemoteCallbackList<ICalculatorCallback> mCallbacks = new RemoteCallbackList<ICalculatorCallback>();
    private ICalculatorService.Stub mStub =
            new ICalculatorService.Stub() {
                @Override
                public void registerCallback(ICalculatorCallback callback) throws RemoteException {
                    Log.d(TAG, "registerCallback");
                    mCallbacks.register(callback);
                }

                @Override
                public void unregisterCallback(ICalculatorCallback callback) throws RemoteException {
                    Log.d(TAG, "unregisterCallback");
                    mCallbacks.unregister(callback);
                }

                @Override
                public int add(int lhs, int rhs) {
                    return lhs + rhs;
                }

            };

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mStub;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        boolean result = super.onUnbind(intent);
        Log.d(TAG, "onUnbind:" + result);
        return result;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }
}
