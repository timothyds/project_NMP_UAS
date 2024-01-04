package com.nativepractice.nmp_project_uas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.nativepractice.nmp_project_uas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var fragments:ArrayList<Fragment> = arrayListOf()

    var title=""
    var desc=""
    var imgurl=""
    var genre=""
    var access=""
    var par=""
    var id=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fragments.add(HomeFragment())
        fragments.add(CreateFirstFragment())
        binding.viewPager.adapter = MyAdapter(this,fragments)

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    binding.bottomNav.selectedItemId = binding.bottomNav.menu.getItem(position).itemId
                }
            }
        )
//        val userId = intent.getStringExtra(SignIn.IDACCOUNT)
//        Toast.makeText(this,userId,Toast.LENGTH_SHORT).show()

        binding.bottomNav.setOnItemSelectedListener {
            binding.viewPager.currentItem = when(it.itemId){
                R.id.itemHome ->0
                //lanjutan fragment lain
                //R.id.itemFollowing -> 1
                R.id.itemCreate -> 1
                else -> 0
            }
            true
        }

    }
}