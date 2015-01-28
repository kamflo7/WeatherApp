package com.example.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

	public MyFragmentPageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		switch(i) {
		case 0: return new ViewFirst();
		case 1: return new ViewSecond();
		}
//		Fragment fragment = new DemoObjectFragment();
//        Bundle args = new Bundle();
//        // Our object is just an integer :-P
//        args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
//        fragment.setArguments(args);
//        return fragment;
		return null;
	}

	@Override
	public int getCount() {
		return 2;
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }

}


// Instances of this class are fragments representing a single
// object in our collection.
//public static class DemoObjectFragment extends Fragment {
//    public static final String ARG_OBJECT = "object";
//
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//            ViewGroup container, Bundle savedInstanceState) {
//        // The last two arguments ensure LayoutParams are inflated
//        // properly.
//        View rootView = inflater.inflate(
//                R.layout.fragment_collection_object, container, false);
//        Bundle args = getArguments();
//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));
//        return rootView;
//    }
//}