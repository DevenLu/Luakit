package luakit.com.weathertest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.common.luakit.Demo;
import com.common.luakit.ILuaCallback;
import com.common.luakit.LuaHelper;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private  MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LuaHelper.startLuaKit(this);

        Object[] ret = Demo.getWeather();
        ILuaCallback callback = new ILuaCallback() {
            @Override
            public void onResult(Object o) {
                Object[] ret = (Object[])o;
                adapter.source = ret;
                adapter.notifyDataSetChanged();
            }
        };
        Demo.loadWeather(callback);
        ListView lv=(ListView) findViewById(R.id.lv);
        adapter = new MyAdapter(this);
        adapter.source = ret;
        lv.setAdapter(adapter);
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public  Object[] source;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (source != null){
                return source.length;
            }
            return  0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item, null);
            }
            HashMap<String,Object> map = (HashMap<String,Object>)source[position];
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView subTitle = (TextView) convertView.findViewById(R.id.subTitle);
            String titleText = map.get("city") + " " + map.get("date");
            String subTitleText = "最高温度:" + " " + map.get("high") + " 最低温度:" + " " + map.get("low") + " " + map.get("wind") ;
            title.setText(titleText);
            subTitle.setText(subTitleText);
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

    }
}
