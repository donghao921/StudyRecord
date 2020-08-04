package com.dongh.funplus.view.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongh.baselib.glide.GlideHelper;
import com.dongh.funplus.R;
import com.dongh.funplus.service.bean.ArticleBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import org.apache.commons.lang3.StringEscapeUtils;

public class ArticleListAdapt extends RecyclerView.Adapter<ArticleListAdapt.ViewHolder> {
    private List<ArticleBean> articleBeans;
    private Context mContext;
    private OnItemViewClick onItemViewClick;

    public ArticleListAdapt(Context mContext, List<ArticleBean> articleBeans) {
        this.mContext = mContext;
        this.articleBeans = articleBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final ArticleBean articleBean = articleBeans.get(position);
        if (!articleBean.isFresh()){
            viewHolder.tvNew.setVisibility(View.GONE);
        } else {
            viewHolder.tvNew.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(articleBean.getShareUser())) {
            viewHolder.tvShareUser.setVisibility(View.GONE);
        } else {
            viewHolder.tvShareUser.setVisibility(View.VISIBLE);
            viewHolder.tvShareUser.setText(articleBean.getShareUser());
        }
        if (articleBean.getTags().size() > 0) {
            if (TextUtils.isEmpty(articleBean.getTags().get(0).getName())) {
                viewHolder.tvTagName.setVisibility(View.GONE);
            } else {
                viewHolder.tvTagName.setVisibility(View.VISIBLE);
                viewHolder.tvTagName.setText(articleBean.getTags().get(0).getName());
            }
        } else {
            viewHolder.tvTagName.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(articleBean.getEnvelopePic())) {
            viewHolder.articleImage.setVisibility(View.GONE);
        } else {
            viewHolder.articleImage.setVisibility(View.VISIBLE);
            GlideHelper.loadSquareImage(mContext, articleBean.getEnvelopePic(), viewHolder.articleImage);
        }

        if (TextUtils.isEmpty(articleBean.getTitle())) {
            viewHolder.tvArticleTitle.setVisibility(View.GONE);
        } else {
            viewHolder.tvArticleTitle.setVisibility(View.VISIBLE);
            viewHolder.tvArticleTitle.setText(StringEscapeUtils.unescapeHtml4(articleBean.getTitle()));
        }

        if (TextUtils.isEmpty(articleBean.getSuperChapterName())) {
            viewHolder.tvChapterName.setVisibility(View.GONE);
        } else {
            viewHolder.tvChapterName.setVisibility(View.VISIBLE);
            viewHolder.tvChapterName.setText(articleBean.getSuperChapterName());
        }
        if (articleBean.isCollect()) {
            viewHolder.ivCollect.setBackground(mContext.getDrawable(R.mipmap.like));
        } else {
            viewHolder.ivCollect.setBackground(mContext.getDrawable(R.mipmap.unlike));
        }
        viewHolder.ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemViewClick != null) {
                    onItemViewClick.onCollectClick(articleBean);
                }
            }
        });
        viewHolder.rlArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemViewClick != null) {
                    onItemViewClick.onLinkClick(articleBean.getLink());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleBeans.size() > 0 ? articleBeans.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_new)
        TextView tvNew;
        @BindView(R.id.tv_shareuser)
        TextView tvShareUser;
        @BindView(R.id.tv_tag_name)
        TextView tvTagName;
        @BindView(R.id.article_title)
        TextView tvArticleTitle;
        @BindView(R.id.tv_chapter_name)
        TextView tvChapterName;
        @BindView(R.id.article_image)
        ImageView articleImage;
        @BindView(R.id.iv_collect)
        ImageView ivCollect;
        @BindView(R.id.rl_img_title)
        RelativeLayout rlArticle;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClick(OnItemViewClick onItemViewClick) {
        this.onItemViewClick = onItemViewClick;
    }

    public interface OnItemViewClick{
        void onCollectClick(ArticleBean articleBean);

        void onLinkClick(String url);
    }

    public void updataList(ArticleBean articleBean) {
        for (int i = 0; i < articleBeans.size(); i++ ) {
            if (articleBeans.get(i).getId() == articleBean.getId()) {
                articleBeans.set(i, articleBean);
            }

        }
    }


}
