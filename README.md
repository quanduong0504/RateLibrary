# RateLibrary
Base Module Tutorial:
1. ADS
  - Sử dụng lại AdsApplication
  - Sử dụng phương thức ((AdsApplication) getApplicationContext()).getAdsManager().forceShowInterstitial(Activity.this, AdsUnitString, () -> { }
 
          <com.mmgsoft.modules.libs.widgets.BannerAds
            android:id="@+id/banner_ads"
            android:layout_alignParentBottom="true"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:ba_adsUnitId="@string/banner_ad_unit_id"
            app:ba_autoLoad="true"/>
            
  - ba_autoLoad:
    - true: view sẽ tự load banner
    - false: phải gọi vào view 1 func để load banner ads
2. PurchaseView

        <com.mmgsoft.modules.libs.widgets.PurchaseView
          android:id="@+id/purchaseView"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          android:background="#BFBFBF"
          app:pv_layoutManager="list"
          app:pv_orientation="vertical" />

  - pv_layoutManager (list/grid)
  - pv_orientation (vertical/horizontal)
  - pv_spanCount (Khi ở dạng grid mới chấp nhận trường này!)
