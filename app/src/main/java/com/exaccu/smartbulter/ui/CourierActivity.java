package com.exaccu.smartbulter.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.exaccu.smartbulter.R;
import com.exaccu.smartbulter.adapter.CourierAdapter;
import com.exaccu.smartbulter.entity.CourierData;
import com.exaccu.smartbulter.utils.L;
import com.exaccu.smartbulter.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author:liuzhixiang
 * PackageName:com.exaccu.smartbulter.ui
 * Create by 17864 on 2018/8/24
 *
 * @ Description:
 */
public class CourierActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.btn_get_courier)
    Button btnGetCourier;
    @BindView(R.id.mListView)
    ListView mListView;
    private Unbinder unbinder;
    private List<CourierData> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_get_courier)
    public void onViewClicked() {
        String name = etName.getText().toString().trim();
        String number = etNumber.getText().toString().trim();
        //拼接我们的url
        String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIER_KEY
                + "&com=" + name + "&no=" + number;
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
            RxVolley.get(url, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    L.i("Courier:" + t);

                    parsingJson(t);

                }
            });
        } else {
            Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
        } 
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //解析数据
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);

                CourierData data = new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatetime(json.getString("datetime"));
                mList.add(data);
            }
            //倒序
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
