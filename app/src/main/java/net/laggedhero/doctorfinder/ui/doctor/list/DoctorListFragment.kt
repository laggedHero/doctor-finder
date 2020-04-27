package net.laggedhero.doctorfinder.ui.doctor.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.Disposable
import net.laggedhero.doctorfinder.databinding.FragmentDoctorListBinding
import net.laggedhero.doctorfinder.domain.Doctor
import net.laggedhero.doctorfinder.domain.DoctorPageKey
import net.laggedhero.doctorfinder.extensions.toBundle
import net.laggedhero.doctorfinder.provider.SchedulerProvider
import net.laggedhero.doctorfinder.ui.doctor.list.adapter.DoctorAdapterItem
import net.laggedhero.doctorfinder.ui.doctor.list.adapter.DoctorListAdapter
import net.laggedhero.doctorfinder.ui.doctor.list.adapter.HeaderAdapterItem
import net.laggedhero.doctorfinder.ui.doctor.list.adapter.NextPageAdapterItem
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListAction
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListDestination
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListState

class DoctorListFragment(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val schedulerProvider: SchedulerProvider
) : Fragment() {

    private val viewModel by viewModels<DoctorListViewModel> { viewModelFactory }

    private var _binding: FragmentDoctorListBinding? = null
    private val binding get() = _binding!!

    private var disposable: Disposable? = null

    private val doctorListAdapter = DoctorListAdapter(
        listOf(
            HeaderAdapterItem(),
            DoctorAdapterItem {
                viewModel.onAction(
                    DoctorListAction.OpenDetails(it.doctor)
                )
            },
            NextPageAdapterItem {
                viewModel.onAction(
                    DoctorListAction.Load(it.pageKey)
                )
            }
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onAction(DoctorListAction.Load(DoctorPageKey.Initial))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoctorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeState()
    }

    private fun setupUi() {
        binding.fragmentDoctorListRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = doctorListAdapter
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun observeState() {
        disposable = viewModel.state
            .observeOn(schedulerProvider.ui())
            .subscribe { state -> onState(state) }
    }

    private fun onState(state: DoctorListState) {
        binding.fragmentDoctorListLoader.isVisible = state.loading
        doctorListAdapter.submitList(state.doctorListItems)
        state.error?.get()?.let { showError(it) }
        state.destination?.get()?.let { navigateTo(it) }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateTo(action: DoctorListDestination) {
        when (action) {
            is DoctorListDestination.DoctorDetails -> navigateTo(action.doctor)
        }
    }

    private fun navigateTo(doctor: Doctor) {
        val action = DoctorListFragmentDirections.actionDoctorListFragmentToDoctorDetailFragment(
            doctor.toBundle()
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        disposable?.dispose()
        super.onDestroyView()
    }
}