package ri.togu.sample.servicesamplea;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import ri.togu.sample.servicesamplea.services.ICalculatorCallback;
import ri.togu.sample.servicesamplea.services.ICalculatorService;

public class ItemListActivity extends FragmentActivity
        implements ItemListFragment.Callbacks {

    private static final String TAG = ItemListActivity.class.getSimpleName();

    private boolean mTwoPane;
    private ICalculatorService mService;
    private Handler mHandler;

    // サービスからコールバックしたい時に使用する
    private ICalculatorCallback mCallback =
            new ICalculatorCallback.Stub() {
                @Override
                public void resultSum(final int value) {
                    mHandler.post(new Runnable() {
                        public void run() {
                            Log.d(TAG, "resultSum value:" + value);
                        }
                    });
                }
            };

    private ServiceConnection mServiceConnection =
            new ServiceConnection() {
                @Override
                public void onServiceDisconnected(ComponentName name) {
                    mService = null;
                }

                @Override
                public void onServiceConnected(
                        ComponentName name, IBinder service) {
                    mService = ICalculatorService.Stub.asInterface(service);
                    try {
                        mService.registerCallback(mCallback);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }

        if (mService == null) {
            mHandler = new Handler();
            bindService(new Intent(ICalculatorService.class.getName()),
                    mServiceConnection, BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConnection);
        }
    }

    @Override
    public void onItemSelected(String id) {

        int result = 0;
        try {
            result = mService.add(1, 2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onItemSelected result:" + result);

        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
