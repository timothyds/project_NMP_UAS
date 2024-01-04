package com.nativepractice.nmp_project_uas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.versionedparcelable.ParcelField
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nativepractice.nmp_project_uas.databinding.FragmentCerbungReadBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

private const val ARG_EVENT="cerbungs"

class CerbungReadFragment : Fragment() {
    private lateinit var binding: FragmentCerbungReadBinding
    private var cerbungs:Cerbung? = null
    private lateinit var recycler: RecyclerView
    var paragrafs:ArrayList<Paragraf> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            cerbungs = it.getParcelable(ARG_EVENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cerbung_read, container, false)
        val id_detail = arguments?.getString("index_cerbung").toString()
        val judul_detail = arguments?.getString("judul_cerbung").toString()
        val description_detail = arguments?.getString("description_cerbung").toString()
        val num_likes_detail = arguments?.getString("num_likes_cerbung").toString()
        val user_id_detail = arguments?.getString("user_id_cerbung").toString()
        val image_url_detail = arguments?.getString("image_url_cerbung").toString()
        val genre_id_detail = arguments?.getString("genre_id_cerbung").toString()
        val username_detail = arguments?.getString("username_cerbung").toString()

        view.findViewById<TextView>(R.id.txtTitle).text = judul_detail
        val urlView: ImageView = view.findViewById(R.id.imgCerbung)
        Picasso.get().load(image_url_detail).into(urlView)
        view.findViewById<TextView>(R.id.txtGenre).text = genre_id_detail
        view.findViewById<TextView>(R.id.txtAuthor).text = username_detail

        var q = Volley.newRequestQueue(activity)
        var url = "https://ubaya.me/native/160421144/cerbung/read_cerbung.php?idcerbung="+id_detail
        val stringRequest = StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("apisuccess", it.toString())

                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Cerbung>>() {}.type
                    paragrafs = Gson().fromJson(data.toString(), sType) as ArrayList<Paragraf>
                    Log.d("apiresult", paragrafs.toString())

                    updateList()
                }
            },
            {
                Log.d("apierror", it.message.toString())
            }
        )
        q.add(stringRequest)
        return view
    }

    fun updateList(){
        val lm = LinearLayoutManager(activity)
        with(binding.ReadRecyclerView){
            recycler.layoutManager = lm
            recycler.setHasFixedSize(true)
            recycler.adapter = ParagrafAdapter(paragrafs)
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CerbungReadFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}