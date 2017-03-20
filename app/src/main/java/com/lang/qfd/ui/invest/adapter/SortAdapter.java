package com.lang.qfd.ui.invest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;
import com.lang.R;
import com.lang.widgets.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.List;

public class SortAdapter extends BaseAdapter implements SectionIndexer,PinnedHeaderListView.PinnedHeaderAdapter,OnScrollListener{
    private List<ContactBean> list = null; //这里是你listview里面要用到的内容的list 可以自定义
    private Context mContext;  
    private ArrayList<String> sections=new ArrayList<String>(); //这里是分组的名字也是在外面自定义，然后传进来就是，这和普通的Listview是一样的  
    public SortAdapter(Context mContext, List<ContactBean> list) {
        this.mContext = mContext;  
        this.list = list;  
        this.setSections(list);  //分组 list
    }

    /**
     * 这个函数是我自己把list和section的数据绑定了某种关系。当然你们大可不必
     *  你们可以在外面填充好一个list内容，和一个分组内容就行，当然list里面需要有个
     *  字段，来区分你是哪个分组
     * */

    public void setSections(List<ContactBean> list)  
    {  
        for(ContactBean c:list)
        {
          if(!sections.contains(c.getSortKey()))
            sections.add(c.getSortKey());
        }
    }


    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final ContactBean mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_main_contact_list, null);
            viewHolder.imgSortKey=(ImageView) view.findViewById(R.id.img_sort_key);
            viewHolder.tvKey=(TextView) view.findViewById(R.id.sort_key);
            viewHolder.sortKeyLayout = (LinearLayout) view.findViewById(R.id.sort_key_layout);
            viewHolder.lyDetail = (LinearLayout) view.findViewById(R.id.ly_detail);
            viewHolder.flMore = (FrameLayout) view.findViewById(R.id.fl_more);

            viewHolder.tvMore = (TextView) view.findViewById(R.id.tv_more);


            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //根据position获取分类的首字母的Char ascii值
        //int section = getSectionForPosition(position);
//        viewHolder.tvTitle.setText(mContent.getDisplayName());
//        viewHolder.tvPhone.setText(mContent.getPhoneNum());
        //if (position == getPositionForSection(section)) {
        if(position == 0||
                position-1>=0
            && !list.get(position-1).getSortKey().equals(list.get(position).getSortKey())){

            String typeName = getTypeNameFromPositon(position);
            if("活期".equals(typeName)){
                viewHolder.imgSortKey.setImageResource(R.drawable.product_list_current);
            }else if("短期".equals(typeName)){
                viewHolder.imgSortKey.setImageResource(R.drawable.product_list_short);
            }else {
                viewHolder.imgSortKey.setImageResource(R.drawable.product_list_long);
            }

            //viewHolder.tvKey.setText(mContent.getSortKey());
            viewHolder.sortKeyLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.sortKeyLayout.setVisibility(View.GONE);
        }

        //显示更多
        if(position == list.size()-1
                || getSectionForPosition(position)!= getSectionForPosition(position+1)){
            viewHolder.flMore.setVisibility(View.VISIBLE);
        }else {
            viewHolder.flMore.setVisibility(View.GONE);
        }


        viewHolder.lyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"detail"+position,Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.flMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"more",Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    final static class ViewHolder {
        ImageView imgSortKey;
        TextView tvKey;
        TextView tvTitle;
        TextView tvPhone;
        LinearLayout sortKeyLayout,lyDetail;
        FrameLayout flMore;
        TextView tvMore;
    }

    public int getNextSection(int section)
    {
        for(int i=0,len=sections.size();i<len;i++)
        {
            if(sections.get(i).charAt(0)==section &&i+1<len)
                return  sections.get(i+1).charAt(0);
        }
        return -1;
    }
    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        if(list.size()==0)
            return -1;
        return list.get(position).getSortKey().charAt(0);
    }

    public String getTypeNameFromPositon(int position){
        if(list.size()==0)
            return "";
        return list.get(position).getSortKey();
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortKey();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int arg2, int arg3) {
        if (view instanceof PinnedHeaderListView) {
            ((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getPinnedHeaderState(int position) {
        int realPosition = position;
        if (realPosition < 0)
        {
            return PINNED_HEADER_GONE;
        }
        int section = getSectionForPosition(realPosition);// 得到此item所在的分组位置
        int nextSectionPosition = getPositionForSection(getNextSection(section));// 得到下一个分组的位置
        //当前position正好是当前分组的最后一个position，也就是下一个分组的第一个position的前面一个
        if (nextSectionPosition != -1
                && realPosition == nextSectionPosition - 1) {
            return PINNED_HEADER_PUSHED_UP;
        }
        return PINNED_HEADER_VISIBLE;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {
        int realPosition = position;
        int section = getSectionForPosition(realPosition);
        String typeName = getTypeNameFromPositon(realPosition);
        if("活期".equals(typeName)){
            ((ImageView)header.findViewById(R.id.img_sort)).setImageResource(R.drawable.product_list_current);
        }else if("短期".equals(typeName)){
            ((ImageView)header.findViewById(R.id.img_sort)).setImageResource(R.drawable.product_list_short);
        }else {
            ((ImageView)header.findViewById(R.id.img_sort)).setImageResource(R.drawable.product_list_long);
        }
//        String title = ""+(char)section;
//        ((TextView) header.findViewById(R.id.sort_key_title)).setText(list.get(position).getSortKey());

    }
}