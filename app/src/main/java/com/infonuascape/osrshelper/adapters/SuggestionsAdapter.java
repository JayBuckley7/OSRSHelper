package com.infonuascape.osrshelper.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.infonuascape.osrshelper.R;
import com.infonuascape.osrshelper.db.DBController;
import com.infonuascape.osrshelper.enums.AccountType;
import com.infonuascape.osrshelper.models.Account;
import com.infonuascape.osrshelper.utils.Utils;

import androidx.cursoradapter.widget.CursorAdapter;

public class SuggestionsAdapter extends CursorAdapter {

	public SuggestionsAdapter(Context context, Cursor c) {
		super(context, c);
	}

	class ViewHolder {
		TextView username;
		ImageView image;
		View delete;
	}

	@Override
	public View newView(final Context context, Cursor cursor, ViewGroup viewGroup) {
		final View result;
		ViewHolder holder;
		result = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.username_list_item, viewGroup, false);
		holder = new ViewHolder();
		holder.username = result.findViewById(R.id.username);
		holder.image = result.findViewById(R.id.icon);
		holder.delete = result.findViewById(R.id.delete_btn);
		result.setTag(holder);

		return result;
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		final Account account = DBController.createAccountFromCursor(cursor);

		ViewHolder holder = (ViewHolder) view.getTag();
		if(account != null) {
			holder.username.setText(account.username);
			holder.image.setImageResource(Utils.getAccountTypeResource(account.type));
			holder.delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (context != null) {
						new AlertDialog.Builder(context)
								.setTitle(R.string.delete)
								.setMessage(R.string.delete_username)
								.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialogInterface, int i) {
										DBController.deleteAccount(context, account);
										notifyDataSetChanged();
									}
								})
								.setNegativeButton(R.string.dialog_no, null)
								.create().show();
					}
				}
			});
		} else {
			holder.username.setText(R.string.unknown_account);
			holder.image.setImageResource(Utils.getAccountTypeResource(AccountType.REGULAR));
			holder.delete.setOnClickListener(null);
		}
	}
}