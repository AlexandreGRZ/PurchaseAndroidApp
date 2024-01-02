package com.example.purchaseclientandroid.DataBinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.purchaseclientandroid.Models.Article;
import com.example.purchaseclientandroid.R;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {

    private LayoutInflater inflater;

    public ArticleAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_article, parent, false);
            holder = new ViewHolder();
            holder.textViewArticleName = convertView.findViewById(R.id.textViewArticleName);
            holder.textViewArticlePrice = convertView.findViewById(R.id.textViewArticlePrice);
            holder.textViewArticleQuantity = convertView.findViewById(R.id.textViewArticleQuantity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Article article = getItem(position);

        if (article != null) {
            holder.textViewArticleName.setText(article.getNom());
            holder.textViewArticlePrice. setText(String.valueOf(article.getPrix()));
            holder.textViewArticleQuantity. setText(String.valueOf(article.getQuantite()));
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewArticleName;
        TextView textViewArticlePrice;
        TextView textViewArticleQuantity;
    }
}