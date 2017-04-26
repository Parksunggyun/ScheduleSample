package altong.mon.schedulesample;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.Vector;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GridView mScheduleGV;
    Vector<Schedule> mSchedules;
    BoomMenuButton bmb;

    ScheduleAdapter scheduleAdapter;
    int pos;
    OnBMClickListener builder = new OnBMClickListener() {
        @Override
        public void onBoomButtonClick(int index) {
            switch (index) {
                case 0:
                    mSchedules.add(pos+1,new Schedule(
                            mSchedules.get(pos).getName(),
                            mSchedules.get(pos).getType(),
                            mSchedules.get(pos).getWeekOrWeekend(),
                            mSchedules.get(pos).getStartEndTime(),
                            mSchedules.get(pos).getColor()));
                    break;
                case 1:
                    AddScheduleDialog dialog = new AddScheduleDialog();
                    dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light);
                    Bundle args = new Bundle();
                    args.putString("name", mSchedules.get(pos).getName());
                    args.putString("type", mSchedules.get(pos).getType());
                    args.putString("week", mSchedules.get(pos).getWeekOrWeekend());
                    args.putString("time", mSchedules.get(pos).getStartEndTime());
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager(), "addShift");
                    dialog.setDialogResult(new AddScheduleDialog.OnMyDialogResult() {
                        @Override
                        public void finish(String result) {
                            String[] results = result.split("/");
                            int r = Integer.parseInt(results[3]);
                            int g = Integer.parseInt(results[4]);
                            int b = Integer.parseInt(results[5]);
                            mSchedules.remove(pos);
                            mSchedules.add(pos,new Schedule("1",results[0], results[1], results[2], Color.rgb(r,g,b)));
                            mScheduleGV.setAdapter(new ScheduleAdapter(mSchedules, getLayoutInflater()));
                        }
                    });
                    break;
                case 2:
                    mSchedules.remove(pos);
                    break;
            }
            scheduleAdapter.notifyDataSetChanged();
            mScheduleGV.getChildAt(pos).setBackgroundResource(R.drawable.schedule_unborder);
            bmb.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView fab = (TextView) findViewById(R.id.fab);
        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_1);
        bmb.addBuilder(getHamButtonBuilder("복사하기","선택한 스케줄을 복사합니다.",R.drawable.ic_content_copy_black_24dp, Color.parseColor("#F3A530"),builder));
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_2);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_2);
        bmb.addBuilder(getHamButtonBuilder("수정하기","선택한 스케줄을 수정합니다.",R.drawable.ic_create_black_24dp, Color.parseColor("#4CB2D4"),builder));
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_3);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_3);
        bmb.addBuilder(getHamButtonBuilder("삭제하기","선택한 스케줄을 삭제합니다.",R.drawable.ic_delete_black_24dp, Color.parseColor("#30499B"),builder));
        /*for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_menu_camera);
            bmb.addBuilder(builder);
        }*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddScheduleDialog dialog = new AddScheduleDialog();
                dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light);
                dialog.show(getSupportFragmentManager(), "addShift");
                dialog.setDialogResult(new AddScheduleDialog.OnMyDialogResult() {
                    @Override
                    public void finish(String result) {
                        String[] results = result.split("/");
                        int r = Integer.parseInt(results[3]);
                        int g = Integer.parseInt(results[4]);
                        int b = Integer.parseInt(results[5]);
                        mSchedules.add(new Schedule("1",results[0], results[1], results[2], Color.rgb(r,g,b)));
                        mScheduleGV.setAdapter(new ScheduleAdapter(mSchedules, getLayoutInflater()));
                    }
                });

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mScheduleGV = (GridView) findViewById(R.id.scheduleGV);
        mSchedules = new Vector<>();
            int r = (int) ((Math.random() * 255) + 1);
            int g = (int) ((Math.random() * 255) + 1);
            int b = (int) ((Math.random() * 255) + 1);
        mSchedules.add(new Schedule("Schedule1","Open", "Week", "10:00 ~ 18:00",Color.rgb(r,g,b)));
        mSchedules.add(new Schedule("Schedule2","Middle", "Week", "16:00 ~ 24:00",Color.rgb(r,g,b)));
        mSchedules.add(new Schedule("Schedule3","Close", "Week", "18:00 ~ 24:00",Color.rgb(r,g,b)));
        mSchedules.add(new Schedule("Schedule4","AllDay", "Week", "09:00 ~ 24:00",Color.rgb(r,g,b)));
        mSchedules.add(new Schedule("Schedule5","Night", "Week", "22:00 ~ 04:00",Color.rgb(r,g,b)));
        mSchedules.add(new Schedule("Schedule6","OverNight", "Weekend", "24:00 ~ 08:00",Color.rgb(r,g,b)));
        mSchedules.add(new Schedule("Schedule7","Open3", "Week", "10:00 ~ 18:00",Color.rgb(r,g,b)));
        mSchedules.add(new Schedule("Schedule8","Close2", "Week", "18:00 ~ 24:00",Color.rgb(r,g,b)));
        mSchedules.add(new Schedule("Schedule9","Open2", "Weekend", "10:00 ~ 18:00",Color.rgb(r,g,b)));
        mSchedules.add(new Schedule("Schedule10","Open4", "Weekend", "10:00 ~ 18:00",Color.rgb(r,g,b)));

        scheduleAdapter = new ScheduleAdapter(mSchedules, getLayoutInflater());
        mScheduleGV.setAdapter(scheduleAdapter);
        mScheduleGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("position", position + "");
                if (bmb.getVisibility() == View.GONE) {
                    mScheduleGV.getChildAt(position).setBackgroundResource(R.drawable.schedule_border);
                    bmb.setVisibility(View.VISIBLE);
                    pos = position;
                } else {
                    mScheduleGV.getChildAt(pos).setBackgroundResource(R.drawable.schedule_unborder);
                    bmb.setVisibility(View.GONE);
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private HamButton.Builder getHamButtonBuilder(String text, String subText, int drawable, int color, OnBMClickListener builder) {
        return new HamButton.Builder()
                .normalImageRes(drawable)
                .imagePadding(new Rect(40,40,40,40))
                .normalText(text)
                .textSize(24)
                .subNormalText(subText)
                .subTextSize(16)
                .normalColor(color)
                .pieceColor(color)
                .listener(builder);
    }

}
