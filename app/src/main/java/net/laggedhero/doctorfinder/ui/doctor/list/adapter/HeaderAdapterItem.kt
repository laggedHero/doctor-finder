package net.laggedhero.doctorfinder.ui.doctor.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.doctorfinder.databinding.ViewDoctorListItemHeaderBinding
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListItem

class HeaderAdapterItem : DoctorListAdapter.AdapterItem {
    override fun handles(item: DoctorListItem): Boolean {
        return item is DoctorListItem.HeaderItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ViewDoctorListItemHeaderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HeaderAdapterItemVH(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DoctorListItem) {
        if (!handles(item)) return
        if (item is DoctorListItem.HeaderItem) {
            (holder as? HeaderAdapterItemVH)?.onBind(item)
        }
    }

    class HeaderAdapterItemVH(
        private val binding: ViewDoctorListItemHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(headerItem: DoctorListItem.HeaderItem) {
            binding.viewDoctorListItemHeaderTitle.text = headerItem.title
        }
    }
}
