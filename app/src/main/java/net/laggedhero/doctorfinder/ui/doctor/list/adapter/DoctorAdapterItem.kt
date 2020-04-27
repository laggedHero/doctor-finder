package net.laggedhero.doctorfinder.ui.doctor.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.laggedhero.doctorfinder.R
import net.laggedhero.doctorfinder.databinding.ViewDoctorListItemDoctorBinding
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListItem

class DoctorAdapterItem(
    private val listener: (DoctorListItem.DoctorItem) -> Unit
) : DoctorListAdapter.AdapterItem {
    override fun handles(item: DoctorListItem): Boolean {
        return item is DoctorListItem.DoctorItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ViewDoctorListItemDoctorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DoctorAdapterItemVH(binding, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DoctorListItem) {
        if (!handles(item)) return
        if (item is DoctorListItem.DoctorItem) {
            (holder as? DoctorAdapterItemVH)?.onBind(item)
        }
    }

    class DoctorAdapterItemVH(
        private val binding: ViewDoctorListItemDoctorBinding,
        private val listener: (DoctorListItem.DoctorItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var doctorItem: DoctorListItem.DoctorItem? = null

        init {
            binding.root.setOnClickListener {
                doctorItem?.let { listener.invoke(it) }
            }
        }

        fun onBind(doctorItem: DoctorListItem.DoctorItem) {
            this.doctorItem = doctorItem
            val doctor = doctorItem.doctor
            binding.viewDoctorListItemName.text = doctor.name.value
            bindImage(doctor.photo?.value)
        }

        private fun bindImage(imageUrl: String?) {
            Glide.with(binding.root)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .circleCrop()
                .into(binding.viewDoctorListItemImage)
        }
    }
}