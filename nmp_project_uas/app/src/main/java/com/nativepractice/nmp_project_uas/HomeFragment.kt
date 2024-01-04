package com.nativepractice.nmp_project_uas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nativepractice.nmp_project_uas.databinding.FragmentHomeBinding
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private var cerbungs:ArrayList<Cerbung> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var q = Volley.newRequestQueue(activity)
        var url = "https://ubaya.me/native/160421144/cerbung/get_cerbung.php"
        val stringRequest = StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("apisuccess",it.toString())

                val obj = JSONObject(it)
                if(obj.getString("result")=="OK"){
                    val data = obj.getJSONArray("data")
                    val sType = object: TypeToken<List<Cerbung>>(){}.type
                    cerbungs = Gson().fromJson(data.toString(),sType) as ArrayList<Cerbung>
                    Log.d("apiresult",cerbungs.toString())

                    updateList()
                }
            },
            {
                Log.d("apierror",it.message.toString())
            }
        )
        q.add(stringRequest)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun updateList(){
        val lm = LinearLayoutManager(activity)
        with(binding.homeRecyclerView){
            layoutManager = lm
            setHasFixedSize(true)
            adapter = CerbungAdapter(cerbungs,this.context)
        }
//        binding.homeRecyclerView.layoutManager = LinearLayoutManager(activity)
//        binding.homeRecyclerView.setHasFixedSize(true)
//        binding.homeRecyclerView.adapter = CerbungAdapter(cerbungs,this.context)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}