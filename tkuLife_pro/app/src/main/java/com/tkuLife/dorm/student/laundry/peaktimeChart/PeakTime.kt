package com.tkuLife.dorm.student.laundry.peaktimeChart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tkuLife.dorm.BarTool
import com.tkuLife.dorm.R
import com.tkuLife.dorm.databinding.PeakTimeTabBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.tabs.TabLayout
import kotlin.collections.ArrayList

class PeakTime : AppCompatActivity() {
    private lateinit var binding: PeakTimeTabBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= PeakTimeTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initView(){

//        設定BAR
        BarTool(this).setBundle("各時段使用狀況",R.color.barBlue)
//        初始化BarChart
        val res=BarChartModel.history[0]
        setBarChart(BarChartModel.countRate(res))



//        TAB點擊監聽
        binding.tabLayout.addOnTabSelectedListener(object:
            TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                取得某日資料集
                val res=BarChartModel.history[tab!!.position]
                Log.d("bar",BarChartModel.countRate(res).toString())
//                計算使用率: Xi/sum -> ArrayList<Float>
                setBarChart(BarChartModel.countRate(res))
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


    }



//    設定barChart
    private fun setBarChart(dataSet : ArrayList<Float>){
        val xLabel = arrayListOf("08-10","10-12","12-14","14-16","16-18","18-20","20-22","22-24","24-08")
        val entry = ArrayList<BarEntry>().also {
            for((i,e) in dataSet.withIndex()){
                it.add(BarEntry(e,i))
            }
        }
        val barDataset = BarDataSet(entry,"各時段使用次數/全天使用次數").apply {
            valueFormatter = PercentFormatter()
//            資料字體大小
            valueTextSize=13f
            highLightColor=Color.rgb(88,134,148)
            color=Color.rgb(110,176,195)

        }

//        binding.barChart.setBackgroundColor(R.color.barBlue)
//    長條圖顏色

        val data =BarData(xLabel,barDataset)
        binding.barChart.apply {
//            xLabel
            xAxis.apply {
                this.position = XAxis.XAxisPosition.BOTTOM
                textSize=11f
            }
            xAxis.labelRotationAngle=-65f
            xAxis.spaceBetweenLabels=2
            xAxis.setDrawGridLines(false)
//            yLabel
            axisLeft.axisLineWidth=0.9f
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawGridLines(false)
            barDataset.setDrawValues(true)
            //axisLeft.textSize=11f
            //axisRight.textSize=11f
//            加入資料集
            data.notifyDataChanged()
            this.data = data

//            設定背景色
//            setBackgroundColor(Color.WHITE)
//            圖表動畫
            animateXY(2000,2000)
//            圖表描述
            setDescription("")
//            描述字體大小
            setDescriptionTextSize(16f)
        }

    }
}

