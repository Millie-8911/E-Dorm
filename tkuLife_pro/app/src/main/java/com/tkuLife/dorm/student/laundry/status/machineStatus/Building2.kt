package com.tkuLife.dorm.student.laundry.status.machineStatus

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tkuLife.dorm.databinding.FragmentBuilding2Binding
import com.tkuLife.dorm.student.laundry.status.MachineViewModel

class Building2(private val selectFloor : String, private val machineType:String, cont1xt:Context) : Fragment() {

    private lateinit var binding: FragmentBuilding2Binding
    private lateinit var viewModel: MachineViewModel
    private var viewAdapter = StatusAdapter(cont1xt)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentBuilding2Binding.inflate(layoutInflater)

        return binding.root
    }

//    設定recyclerView
    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recycleView2.apply {

            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            setHasFixedSize(true)
            setLayoutManager(layoutManager)

            adapter = viewAdapter //只建立一次FloorAdapter
        }
    }
    private fun upDateRecycler(adapterData:ArrayList<HashMap<*,*>>){
        viewAdapter.floor = "二館-${selectFloor}F"
        viewAdapter.machineData = adapterData
        viewAdapter.machineType = machineType
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
//        取得viewModel
        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)
//        viewModel資料監聽
        viewModel.getRealtimeData().observe(viewLifecycleOwner,{ data->
            val type=data[machineType] as HashMap<*,*>
            val machineList = type["B-0${selectFloor}"] as ArrayList<HashMap<*,*>>
            upDateRecycler(machineList)

        })
    }

}