package com.tkuLife.dorm.student.laundry.status.machineStatus

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tkuLife.dorm.databinding.FragmentBuilding1Binding
import com.tkuLife.dorm.student.laundry.status.MachineViewModel

class Building1(private val selectFloor : String, private val machineType:String, cont1xt:Context) : Fragment(){

    private lateinit var binding: FragmentBuilding1Binding
    private lateinit var viewModel: MachineViewModel
    private var viewAdapter = StatusAdapter(cont1xt)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentBuilding1Binding.inflate(layoutInflater)

        return binding.root
    }

//    設定recyclerView
    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recycleView1.apply {

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
        viewAdapter.floor = "一館-${selectFloor}F"
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
            val machineList = type["A-0${selectFloor}"] as ArrayList<HashMap<*,*>>
            upDateRecycler(machineList)
        })
    }



}