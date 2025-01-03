package com.tkuLife.dorm.student.stdPackage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tkuLife.dorm.databinding.PackageItemBinding
import org.json.JSONArray
import org.json.JSONObject

class PackageAdapter(private var itemClickListener: OnItemClick) : RecyclerView.Adapter<PackageAdapter.ViewHolder>(){
    private lateinit var binding : PackageItemBinding

    var packageList = JSONArray()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    class ViewHolder(val view:PackageItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = PackageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pkg = packageList[(packageList.length()-1)-position] as JSONObject
        holder.view.textView33.text = "編號末三碼: ${pkg["pid"]}"
        holder.view.button18.setOnClickListener{
            itemClickListener.onItemClick(pkg["index"].toString().toInt(),pkg["pid"].toString())
        }
    }

    override fun getItemCount(): Int {
        return packageList.length()
    }
    interface OnItemClick{
        fun onItemClick(position: Int,pid:String)
    }
}