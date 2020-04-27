package net.laggedhero.doctorfinder.ui.doctor.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.doctorfinder.databinding.ViewDoctorListItemPageBinding
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListItem

class NextPageAdapterItem(
    private val listener: (DoctorListItem.NextPageItem) -> Unit
) : DoctorListAdapter.AdapterItem {
    override fun handles(item: DoctorListItem): Boolean {
        return item is DoctorListItem.NextPageItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ViewDoctorListItemPageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NextPageAdapterItemVH(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DoctorListItem) {
        if (!handles(item)) return
        if (item is DoctorListItem.NextPageItem) {
            listener.invoke(item)
        }
    }

    class NextPageAdapterItemVH(
        binding: ViewDoctorListItemPageBinding
    ) : RecyclerView.ViewHolder(binding.root)
}