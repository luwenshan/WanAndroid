package com.lws.wanandroid.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lws.wanandroid.R;
import com.lws.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.lws.wanandroid.ui.viewholder.KnowledgeHierarchyViewHolder;
import com.lws.wanandroid.utils.CommonUtils;

import java.util.List;

public class KnowledgeHierarchyAdapter extends BaseQuickAdapter<KnowledgeHierarchyData, KnowledgeHierarchyViewHolder> {

    public KnowledgeHierarchyAdapter(@Nullable List<KnowledgeHierarchyData> data) {
        super(R.layout.item_knowledge_hierarchy, data);
    }

    @Override
    protected void convert(KnowledgeHierarchyViewHolder helper, KnowledgeHierarchyData item) {
        if (item.getName() == null) {
            return;
        }
        helper.setText(R.id.item_knowledge_hierarchy_title, item.getName());
        helper.setTextColor(R.id.item_knowledge_hierarchy_title, CommonUtils.randomColor());
        if (item.getChildren() == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (KnowledgeHierarchyData data : item.getChildren()) {
            sb.append(data.getName()).append("   ");
        }
        helper.setText(R.id.item_knowledge_hierarchy_content, sb.toString());
    }
}
