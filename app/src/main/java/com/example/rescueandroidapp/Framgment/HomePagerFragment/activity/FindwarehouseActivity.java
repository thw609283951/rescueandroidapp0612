package com.example.rescueandroidapp.Framgment.HomePagerFragment.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rescueandroidapp.R;
import com.rescueandroid.data.BaseDataService;
import com.rescueandroid.exception.NetConnectionException;
import com.rescueandroid.utils.JsonUtils;
import com.rescueandroid.utils.SharedPreferencesUtils;
import com.rescueandroid.utils.ui.ProgressDialogEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by 冯志强 on 2017/6/5 0005.
 * 找库
 */

public class FindwarehouseActivity extends Activity implements View.OnClickListener{

    private ProgressDialogEx progressDlgEx;
    protected Handler mHandler = new Handler();
    private LayoutInflater factory;
    private ImageView findrount_back;
    private LinearLayout findwarehouse_layout;
    private TextView findroute_name,findroute_tv1 ,findroute_tv2,findroute_time,findroute_cartype,findroute_carline,
            findroute_tel,findroute_route,find_price;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.findwarehouse);

        progressDlgEx = new ProgressDialogEx(FindwarehouseActivity.this, mHandler);
        factory = LayoutInflater.from(this);

        findrount_back = (ImageView) findViewById(R.id.findrount_back);
        findrount_back.setOnClickListener(this);
        findwarehouse_layout = (LinearLayout) findViewById(R.id.findwarehouse_layout);

        show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.findrount_back:

                finish();

                break;

        }

    }

    private void show(){
        final String username = SharedPreferencesUtils.getString(this, "userName", "");// 获取用户名
        final String userPass = SharedPreferencesUtils.getString(this, "userPass", "");// 获取密码
        // TODO Auto-generated method stub
        new Thread(new Runnable() {
            public void run() {
                try {
                    progressDlgEx.simpleModeShowHandleThread();
                    final JSONObject jsobj = BaseDataService.coldstore(username,userPass);
                    String code = jsobj.getString("status");
                    if (code.equals("1")) {
                        JSONArray results = jsobj.getJSONArray("data");
                        final List listt = JsonUtils.parseJsonArray(results);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub

                                findwarehouse_layout.removeAllViews();
                                for (int q = 0; q < listt.size(); q++) {
                                    Map map = (Map) listt.get(q);
                                    LinearLayout convertView = (LinearLayout) factory.inflate(R.layout.findwarehouse_layout, null);


                                    findroute_tv1 = (TextView)  convertView.findViewById(R.id.findroute_tv1);

                                    findroute_time = (TextView)  convertView.findViewById(R.id.findroute_time);

                                    findroute_cartype = (TextView)  convertView.findViewById(R.id.findroute_cartype);

                                    findroute_carline = (TextView)  convertView.findViewById(R.id.findroute_carline);

                                    find_price = (TextView)  convertView.findViewById(R.id.find_price);

                                    findroute_tel = (TextView)  convertView.findViewById(R.id.findroute_tel);

                                    findroute_route = (TextView)  convertView.findViewById(R.id.findroute_route);

                                    final int id = new Integer(map.get("id").toString());
                                    String province = map.get("province").toString();
                                    String city = map.get("city").toString();
                                    String district = map.get("district").toString();
                                    String PutDate = map.get("createon").toString();

                                    //1.阴凉库2.冷藏库3.常温库
                                    final int type = new Integer(map.get("type").toString());
                                    if(type == 1){
                                        findroute_cartype.setText("阴凉库");
                                    }
                                    if(type == 2){
                                        findroute_cartype.setText("冷藏库");
                                    }
                                    if(type == 3){
                                        findroute_cartype.setText("常温库");
                                    }

                                    int capacity = new Integer(map.get("capacity").toString());

                                    // 截取时间
                                    String[] starTimestr = PutDate.split("T");
                                    String startcreateDate = starTimestr[0];

                                    final String cartel = map.get("linktel").toString();

                                    findroute_tv1.setText(province+city+district);
                                    findroute_time.setText(startcreateDate);
                                    findroute_carline.setText(capacity+"平方");

                                    findwarehouse_layout.addView(convertView);
                                    //拨打电话
                                    findroute_tel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builde  = new AlertDialog.Builder(FindwarehouseActivity.this);
                                            builde.setMessage(cartel) ;
                                            builde.setPositiveButton("取消",new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            builde.setNegativeButton("呼叫", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //用intent启动拨打电话
                                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ cartel));
                                                    startActivity(intent);
                                                }
                                            });
                                            builde.show();
                                        }
                                    });

                                    findroute_route.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent();
                                            intent.putExtra("id", id);
                                            intent.setClass(FindwarehouseActivity.this, MyhouseAcitivity.class);
                                            startActivity(intent);
                                        }
                                    });

                                    convertView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent();
                                            intent.putExtra("id", id);
                                            intent.setClass(FindwarehouseActivity.this, FindwarehouseDetails.class);
                                            FindwarehouseActivity.this.startActivity(intent);
                                        }
                                    });

                                }
                            }
                        });
                    }
                } catch (NetConnectionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    progressDlgEx.closeHandleThread();
                }
            }
        }).start();

    }

}
