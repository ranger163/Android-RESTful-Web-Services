package me.inassar.restful

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import me.inassar.restful.model.DataItem
import java.io.IOException

class DataItemAdapter(private val mContext: Context, private val mItems: List<DataItem>) :
    RecyclerView.Adapter<DataItemAdapter.ViewHolder>() {

    private var prefsListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val settings = PreferenceManager.getDefaultSharedPreferences(mContext)
        prefsListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            Log.i(
                "preferences",
                "onSharedPreferenceChanged: $key"
            )
        }
        settings.registerOnSharedPreferenceChangeListener(prefsListener)

        val grid = settings.getBoolean(
            mContext.getString(R.string.pref_display_grid), false
        )
        val layoutId = if (grid) R.layout.grid_item else R.layout.list_item

        val inflater = LayoutInflater.from(mContext)
        val itemView = inflater.inflate(layoutId, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]

        try {
            holder.tvName.text = item.itemName
            val imageFile = item.image
            val inputStream = mContext.assets.open(imageFile!!)
            val d = Drawable.createFromStream(inputStream, null)
            holder.imageView.setImageDrawable(d)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        holder.mView.setOnClickListener {
            //                Toast.makeText(mContext, "You selected " + item.getItemName(),
            //                        Toast.LENGTH_SHORT).show();
            //                String itemId = item.getItemId();
            mContext.startActivity(
                Intent(mContext, DetailActivity::class.java)
                    .putExtra(ITEM_KEY, item)
            )
        }

        holder.mView.setOnLongClickListener {
            mContext.toast("You long clicked ${item.itemName!!}")
            false
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(var mView: View) : RecyclerView.ViewHolder(mView) {

        var tvName: TextView
        var imageView: ImageView

        init {

            tvName = mView.findViewById<View>(R.id.itemNameText) as TextView
            imageView = mView.findViewById<View>(R.id.imageView) as ImageView
        }
    }

    companion object {

        val ITEM_ID_KEY = "item_id_key"
        val ITEM_KEY = "item_key"
    }
}