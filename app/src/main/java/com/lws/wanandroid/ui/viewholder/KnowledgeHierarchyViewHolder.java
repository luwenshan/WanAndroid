package com.lws.wanandroid.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lws.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KnowledgeHierarchyViewHolder extends BaseViewHolder {
    @BindView(R.id.item_knowledge_hierarchy_title)
    public TextView mTitle;
    @BindView(R.id.item_knowledge_hierarchy_content)
    public TextView mContent;

    public KnowledgeHierarchyViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
