package mr.li.dance.ui.activitys.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.MarkerOptions;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/28 0028
 * 描述:   高德地图
 * 修订历史:
 */
public class MapActivity extends BaseActivity implements AMap.OnMapLoadedListener {


    MapView mMapView = null;
    AMap aMap;
    private String title;
    private String x;
    private String y;

    @Override
    public void getIntentData() {
        super.getIntentData();
        title = mIntentExtras.getString("title");
        x = mIntentExtras.getString("x");
        y = mIntentExtras.getString("y");
    }

    @Override
    public int getContentViewId() {
        return R.layout.map_activity;
    }

    @Override
    public void initViews() {
        setTitle(title);

        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.setOnMapLoadedListener(this);
            addMarkersToMap();// 往地图上添加marker
        }

    }

    LatLng latlng;

    private void addMarkersToMap() {
        if (x.equals("0.0000000") && y.equals("0.0000000")) {
            latlng = new LatLng(39.913898, 116.397346);
        } else {
            Double l = Double.parseDouble(x);
            Double l1 = Double.parseDouble(y);
            latlng = new LatLng(l1, l);
        }

        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latlng)
                .draggable(true);
        aMap.addMarker(markerOption);
    }

    @Override
    public void initMap(Bundle savedInstanceState) {
        super.initMap(savedInstanceState);
        mMapView = (MapView) mDanceViewHolder.getView(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    public static void lunch(Context context, String title, String x, String y) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("x", x);
        intent.putExtra("y", y);
        context.startActivity(intent);
    }

    @Override
    public void onMapLoaded() {
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(latlng).build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));
    }
}
