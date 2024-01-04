package com.nativepractice.nmp_project_uas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreateThirdFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_third, container, false)
        val genree = (activity as MainActivity).genre
        val titlee = (activity as MainActivity).title
        val userid = (activity as MainActivity).id
        val descc = (activity as MainActivity).desc
        val img = (activity as MainActivity).imgurl
        val dataAccess = (activity as MainActivity).access
        val paragraf = (activity as MainActivity).par

        view.findViewById<Button>(R.id.txtGenre2).text = genree
        val btnPublis = view.findViewById<Button>(R.id.btnPublish)
        btnPublis.setOnClickListener{
            val url = "https://ubaya.me/native/160421144/cerbung/add_cerbung.php"
            val request = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> { response ->
                    // Handle response dari server
                    Toast.makeText(activity, response, Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    // Handle error
                    Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
                }
            ){
                // Override metode untuk mengirim data ke server
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["judul"] = titlee
                    params["description"] = descc
                    params["user_id"] = userid.toString()
                    params["url_cerbung"] = img
                    params["akses"] = dataAccess
                    params["genre_id"] = genree
                    params["paragraf"] = paragraf
                    return params
                }

            }
            Volley.newRequestQueue(activity).add(request)
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}