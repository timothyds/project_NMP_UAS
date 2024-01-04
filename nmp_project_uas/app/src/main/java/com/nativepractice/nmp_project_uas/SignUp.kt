package com.nativepractice.nmp_project_uas

import android.content.ContextParams
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nativepractice.nmp_project_uas.databinding.ActivitySignInBinding
import com.nativepractice.nmp_project_uas.databinding.ActivitySignUpBinding
import org.json.JSONObject
import java.util.Objects

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    val account: ArrayList<Account> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val t = Volley.newRequestQueue(this@SignUp)
        val url = "https://ubaya.me/native/160421125/users.php"
        var stringRequest = StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val plyObj = data.getJSONObject(i)
                        val users = Account(
                            plyObj.getInt("id"),
                            plyObj.getString("nama"),
                            plyObj.getString("username"),
                            plyObj.getString("img_url"),
                            plyObj.getString("password")

                            )
                        account.add(users)
                    }
                }
                Log.d("cekisiarray", account.toString())
            },
            Response.ErrorListener {
                // Handle error here
                Log.e("apiresult", it.message.toString())
            }
        )
        t.add(stringRequest)

        binding.btnSignUp.setOnClickListener {
            var name = binding.txtNama.text.toString()
            var username = binding.txtUsername.text.toString()
            var image = binding.txtUrl.text.toString()
            var password = binding.txtPassword.text.toString()
            var repassword = binding.txtRePassword.text.toString()
            var sameUser = false

            for (accounts in account) {
                if (accounts.username == username || accounts.nama == name) {
                    sameUser = true
                    break
                }
            }
            if (sameUser) {
                Toast.makeText(this, "Username Telah digunakan", Toast.LENGTH_SHORT).show()
            } else {
                if (!name.isEmpty() && !username.isEmpty() && !image.isEmpty() && !password.isEmpty() && !repassword.isEmpty()) {
                    if (password == repassword) {
                        val t = Volley.newRequestQueue(this@SignUp)
                        val url = "https://ubaya.me/native/160421125/new_users.php"
                        val stringRequest = object : StringRequest(Request.Method.POST, url,
                            Response.Listener<String> {
                                Log.d("cekparams", it)
                                val obj = JSONObject(it)
                            },
                            Response.ErrorListener {
                            // Handle error here
                            Log.e("cekparams", it.message.toString())
                            })
                        {
                            override fun getParams(): MutableMap<String, String> {
                                val params = HashMap<String, String>()
                                params["nama"] = name
                                params["username"]= username
                                params["img_url"] = image
                                params["password"] = password
                                return params
                            }
                        }
                        t.add(stringRequest)
                        Toast.makeText(this, "${username} Sign Up Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SignIn::class.java)
                        startActivity(intent)

                    }

                    }
                }
            }
        }
    }