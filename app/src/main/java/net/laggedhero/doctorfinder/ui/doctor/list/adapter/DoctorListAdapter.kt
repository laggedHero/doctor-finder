package net.laggedhero.doctorfinder.ui.doctor.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListItem

class DoctorListAdapter(
    private val adapterItems: List<AdapterItem>
) : ListAdapter<DoctorListItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return adapterItems.indexOfFirst { it.handles(item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapterItems[viewType].onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        adapterItems.find { it.handles(item) }?.onBindViewHolder(holder, item)
    }

    interface AdapterItem {
        fun handles(item: DoctorListItem): Boolean
        fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
        fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DoctorListItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DoctorListItem>() {
            override fun areItemsTheSame(
                oldItem: DoctorListItem,
                newItem: DoctorListItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DoctorListItem,
                newItem: DoctorListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}