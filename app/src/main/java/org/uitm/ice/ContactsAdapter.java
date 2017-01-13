package org.uitm.ice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
/*
public class ContactsAdapter
  extends BaseAdapter
{
  ArrayList<Contact> contacts;
  LayoutInflater inflater;
  
  public ContactsAdapter(Context paramContext, ArrayList<Contact> paramArrayList)
  {
    this.inflater = LayoutInflater.from(paramContext);
    this.contacts = paramArrayList;
  }
  
  public int getCount()
  {
    return this.contacts.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.contacts.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return ((Contact)this.contacts.get(paramInt)).getId();
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewHolder localViewHolder;
    if (paramView == null)
    {
      localViewHolder = new ViewHolder();
      paramView = this.inflater.inflate(2130903047, null);
      localViewHolder.textViewName = ((TextView)paramView.findViewById(2131165217));
      localViewHolder.textViewPhone = ((TextView)paramView.findViewById(2131165216));
      localViewHolder.textViewStatus = ((TextView)paramView.findViewById(2131165218));
      paramView.setTag(localViewHolder);
    }
    for (;;)
    {
      localViewHolder.textViewName.setText(((Contact)this.contacts.get(paramInt)).getName());
      localViewHolder.textViewPhone.setText(((Contact)this.contacts.get(paramInt)).getPhone());
      localViewHolder.textViewStatus.setText(((Contact)this.contacts.get(paramInt)).getStatus());
      paramView.setOnLongClickListener(new View.OnLongClickListener()
      {
        public boolean onLongClick(View paramAnonymousView)
        {
          return false;
        }
      });
      return paramView;
      localViewHolder = (ViewHolder)paramView.getTag();
    }
  }
  
  public class ViewHolder
  {
    TextView textViewName;
    TextView textViewPhone;
    TextView textViewStatus;
    
    public ViewHolder() {}
  }
} */
