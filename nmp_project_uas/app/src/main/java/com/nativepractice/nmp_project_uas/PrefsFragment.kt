package com.nativepractice.nmp_project_uas

import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings.Global
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nativepractice.nmp_project_uas.databinding.FragmentPrefsBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var id_user =0 //bisa dihapus kalo ada global

/**
 * A simple [Fragment] subclass.
 * Use the [PrefsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PrefsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentPrefsBinding

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
        // Inflate the layout for this fragment
        binding = FragmentPrefsBinding.inflate(inflater,container,false)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        binding.switchMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.edit().putBoolean("darkMode",true).apply()
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferences.edit().putBoolean("darkMode", false).apply()

            }
        }

        val isDarkMode = sharedPreferences.getBoolean("darkMode",false)
        binding.switchMode.isChecked = isDarkMode

        val t = Volley.newRequestQueue(activity)
        val urlUser = "https://ubaya.me/native/160421125/users.php"
        var stringRequest = StringRequest(
            Request.Method.POST, urlUser,
            Response.Listener<String> {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val plyObj = data.getJSONObject(i)
                        if(plyObj.getInt("id")== id_user ){ //ganti Global.id_user
                            val url= plyObj.getString("url")
                            val username = plyObj.getString("username")
                            Picasso.get().load(url).into(binding.imageView2)
                            binding.textInputName.hint =username
                        }
                    }
                }

            },
            Response.ErrorListener {
                // Handle error here
                Log.e("apiresult", it.message.toString())
            }
        )
        t.add(stringRequest)
        binding.btnChangePass.setOnClickListener{
            val oldPass = binding.textInputOldPass.editText?.text.toString()
            val newPass = binding.textInputNewPass.editText?.text.toString()
            val rePass = binding.textInputRePass.editText?.text.toString()

            if(!oldPass.isEmpty() && newPass.isEmpty() && rePass.isEmpty()){
                if(newPass == rePass){
                    val t = Volley.newRequestQueue(activity)
                    val url = "https://ubaya.me/native/160421125/update_users.php"
                    val stringRequest = object : StringRequest(Request.Method.POST, url,
                        Response.Listener<String> {
                            Log.d("cekparams", it)
                            val obj = JSONObject(it)
                            if(obj.getString("result") == "OK"){
                                Toast.makeText(activity, obj.getString("message"), Toast.LENGTH_SHORT).show()
                                binding.textInputOldPass.editText?.setText("")
                                binding.textInputNewPass.editText?.setText("")
                                binding.textInputRePass.editText?.setText("")

                            }else{
                                Toast.makeText(activity, obj.getString("message"), Toast.LENGTH_SHORT).show()
                            }
                        },
                        Response.ErrorListener {
                            // Handle error here
                            Log.e("cekparams", it.message.toString())
                        })
                    {
                        override fun getParams(): MutableMap<String, String> {
                            val params = HashMap<String, String>()
                            params["id"] = id_user.toString() //ganti Global.id_users
                            params["oldpassword"]= oldPass
                            params["newpassword"] = newPass
                            return params
                        }
                    }
                    t.add(stringRequest)
                }else{
                    Toast.makeText(activity, "Password and Re-Password not same",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PrefsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PrefsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}