package com.ealpha.navigation;

import android.app.Fragment;
import android.app.FragmentManager;

import com.ealpha.R;
import com.ealpha.main.MainActivity;

/**
 * @author msahakyan
 */

public class FragmentNavigationManager implements NavigationManager {

	private static FragmentNavigationManager sInstance;

	private FragmentManager mFragmentManager;
	private MainActivity mActivity;

	public static FragmentNavigationManager obtain(MainActivity activity) {
		if (sInstance == null) {
			sInstance = new FragmentNavigationManager();
		}
		sInstance.configure(activity);
		return sInstance;
	}

	private void configure(MainActivity activity) {
		mActivity = activity;
		mFragmentManager = mActivity.getFragmentManager();
	}

	@Override
	public void showFragmentMen(String title) {
		// showFragment(FragmentComedy.newInstance(title), false);
	}

	@Override
	public void showFragmentWomen(String title) {
		// showFragment(FragmentDrama.newInstance(title), false);
	}

	@Override
	public void showFragmentKid(String title) {
		// showFragment(FragmentMusical.newInstance(title), false);
	}

	@Override
	public void showFragmentHealthCare(String title) {
		// showFragment(FragmentMusical.newInstance(title), false);
	}

	@Override
	public void showFragmentMobile_n_Tablets(String title) {
		// showFragment(FragmentThriller.newInstance(title), false);
	}

	@Override
	public void showFragmentElectronics(String title) {
		// showFragment(FragmentThriller.newInstance(title), false);
	}

	@Override
	public void showFragmentHomeDecor_n_furnishing(String title) {
		// showFragment(FragmentThriller.newInstance(title), false);
	}

	@Override
	public void showFragmentHome_Utility(String title) {
		// showFragment(FragmentThriller.newInstance(title), false);
	}

	@Override
	public void showFragmentSairandhri(String title) {
		// showFragment(FragmentThriller.newInstance(title), false);
	}

	@Override
	public void showFragmentSpiritual_Items(String title) {
		// showFragment(FragmentThriller.newInstance(title), false);
	}

	@SuppressWarnings("unused")
	private void showFragment(Fragment fragment, boolean allowStateLoss) {
		FragmentManager fm = mFragmentManager;

		// @SuppressLint("CommitTransaction")
		android.app.FragmentTransaction ft = fm.beginTransaction().replace(
				R.id.content_frame, fragment);

		ft.addToBackStack(null);

		if (allowStateLoss || !com.ealpha.BuildConfig.DEBUG) {
			ft.commitAllowingStateLoss();
		} else {
			ft.commit();
		}

		fm.executePendingTransactions();
	}

}
