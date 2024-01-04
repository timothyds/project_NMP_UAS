package com.nativepractice.nmp_project_uas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

import com.android.volley.toolbox.Volley
import com.nativepractice.nmp_project_uas.databinding.ActivitySignInBinding
import org.json.JSONObject

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    val account:ArrayList<Account> = ArrayList()
    companion object{
        val IDACCOUNT = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val t = Volley.newRequestQueue(this@SignIn)
        val url = "https://ubaya.me/native/160421144/cerbung/users.php"
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

        binding.btnSignIn.setOnClickListener{
            var username = binding.txtUsername.text.toString()
            var password = binding.txtPassword.text.toString()
            var status = false

            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"${username} Data cannot be empty", Toast.LENGTH_SHORT).show()

            }else{
                for(accounts in account){
                    if(accounts.username == username && accounts.password == password){
                        status = true
                        Toast.makeText(this, "${username} Sign In Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        var idAccount = accounts.id
                        intent.putExtra(IDACCOUNT, idAccount)
//                        Global.id_user = idAccount
                        startActivity(intent)
                        finish()
                        break
                    }
                    else{
                        status = false

                    }
                }
                if(!status){
                    Toast.makeText(this,"Username or Password is Incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.txtDonthaveacc.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}