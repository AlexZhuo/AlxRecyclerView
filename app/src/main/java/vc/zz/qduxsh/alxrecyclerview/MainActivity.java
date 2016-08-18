package vc.zz.qduxsh.alxrecyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AlxRefreshLoadMoreRecyclerView alxRecyclerView = (AlxRefreshLoadMoreRecyclerView) findViewById(R.id.alx_recyclerView);
        String[] names = {"张三","李四","王五","哈哈","格格","咳咳","发发","宝宝","嘎嘎","数控刀具","孙菲菲","郭芳芳","王欣欣","郭晓小","刘莎莎","郑哈哈","新飞飞","林丹丹","宋彤彤","李花花"};
        final List<String> nameList = new ArrayList<>();
        for(String s:names)nameList.add(s);

        final AlxRecyclerViewAdapter adapter = new AlxRecyclerViewAdapter(nameList,R.layout.recyclerview_item,true);
        adapter.setPullLoadMoreEnable(true);
        alxRecyclerView.setPullLoadEnable(true);
        alxRecyclerView.setAdapter(adapter);
        alxRecyclerView.setLoadMoreListener(new AlxRefreshLoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.i("Alex","现在开始加载下一页");
                new Handler().postDelayed(new Runnable() {//模拟两秒网络延迟
                    @Override
                    public void run() {
                        String[] moreNames = {"新加的名字1","新加的名字2","新加的名字3","新加的名字4","新加的名字5","新加的名字6","新加的名字7","新加的名字8"};
                        List<String> dataList = adapter.getDataList();
                        for(String s:moreNames)dataList.add(s);
                        adapter.notifyItemInserted(nameList.size() - moreNames.length + 1);
                        alxRecyclerView.stopLoadMore();
                    }
                },2000);

            }
        });
        alxRecyclerView.setOnRefreshListener(new AlxRefreshLoadMoreRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("Alex","现在开始刷新");
                new Handler().postDelayed(new Runnable() {//模拟两秒网络延迟
                    @Override
                    public void run() {
                        String[] moreNames = {"刷新的名字1","刷新的名字2","刷新的名字3","刷新的名字4","刷新的名字5","刷新的名字6","刷新的名字7","刷新的名字8","刷新的名字9","刷新的名字10","刷新的名字11","刷新的名字12"};
                        final List<String> nameList2 = new ArrayList<String>();
                        for(String s:moreNames)nameList2.add(s);
                        adapter.setDataList(nameList2);//重设数据
                        adapter.notifyDataSetChanged();
                        alxRecyclerView.stopRefresh();
                    }
                },2000);
            }
        });
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alxRecyclerView.forceRefresh();//不用下拉，强制刷新
            }
        });
    }

    class AlxRecyclerViewAdapter extends AlxRefreshLoadMoreRecyclerView.AlxDragRecyclerViewAdapter<String>{

        public AlxRecyclerViewAdapter(List<String> dataList, int itemLayout, boolean pullEnable) {
            super(dataList, itemLayout, pullEnable);
        }

        @Override
        public RecyclerView.ViewHolder setItemViewHolder(View itemView) {
            return new AlxRecyclerViewHolder(itemView);
        }

        @Override
        public void initItemView(RecyclerView.ViewHolder itemHolder, int posion, String entity) {
            AlxRecyclerViewHolder holder = (AlxRecyclerViewHolder)itemHolder;
            holder.tv1.setText(getDataList().get(posion-1));
        }
    }

    class AlxRecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView iv1;
        TextView tv1;
        public AlxRecyclerViewHolder(View itemView) {
            super(itemView);
            iv1 = (ImageView) itemView.findViewById(R.id.iv1);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
        }
    }
}
