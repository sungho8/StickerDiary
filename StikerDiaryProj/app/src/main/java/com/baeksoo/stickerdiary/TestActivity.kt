package com.baeksoo.stickerdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.collections.ArrayList

class TestActivity : AppCompatActivity() {
    private val dateCalculator = DateCalculator()

    // ?˜ì´ì§€???¬ìš©?˜ëŠ” ë·? ?ìŠ¤??
    private var view_list = ArrayList<View>()
    private var month_list = ArrayList<String>()

    // ?„ì¬ ? ì§œ
    private val instance = Calendar.getInstance()
    private var year = instance.get(Calendar.YEAR)
    private var month = instance.get(Calendar.MONTH)

    // ?˜ì´ì§€ê°€ ë³´ì—¬ì£¼ëŠ” ? ì§œ
    private var pageYear = year;
    private var pageMonth = month;


    private val instance = Calendar.getInstance();
    private var year = instance.get(Calendar.YEAR).toInt()
    private var month = instance.get(Calendar.MONTH).toInt()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val preView = layoutInflater.inflate(R.layout.activity_main,null)
        val currentView  = layoutInflater.inflate(R.layout.activity_main,null)
        val nextView = layoutInflater.inflate(R.layout.activity_main,null)

        preView.recyclerView.adapter = CalendarAdapter(dateCalculator.setData(year,month-1))
        currentView.recyclerView.adapter = CalendarAdapter(dateCalculator.setData(year,month))
        nextView.recyclerView.adapter = CalendarAdapter(dateCalculator.setData(year,month+1))

        //êµ¬ë¶„??
        val dividerItemDecoration = DividerItemDecoration(currentView.recyclerView.context, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        currentView.recyclerView.addItemDecoration(dividerItemDecoration)

        val dividerItemDecoration2 = DividerItemDecoration(currentView.recyclerView.context, LinearLayoutManager.HORIZONTAL)
        dividerItemDecoration2.setDrawable(getDrawable(R.drawable.divider));
        currentView.recyclerView.addItemDecoration(dividerItemDecoration2)


        view_list.add(preView)
        view_list.add(currentView)
        view_list.add(nextView)

        pager.adapter = CustomAdapter()

        pager.setCurrentItem(1)

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            // ?¤í¬ë¡??íƒœê°€ ë³€ê²½ë˜?ˆì„ ??
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                tv1.text = "${year} ??${month + position} ??
            }

            // ?´ë¦­?ˆì„ ??
            override fun onPageSelected(position: Int) {

            }
        })
    }

    inner class CustomAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object` // 3. ë°›ì? ???ê°œê°€ ?¼ì¹˜? ë•Œë§?ë°˜í™˜.
        }

        override fun getCount(): Int {
            return view_list.size
        }

        // ??ª©??êµ¬ì„±?˜ê¸° ?„í•´???¸ì¶œ
        // ë³´ì—¬ì£¼ê³ ???˜ëŠ” ë·°ë? ?˜ì´?€ ê°ì²´??ì§‘ì–´ ?£ê³  ë°˜í™˜?˜ë©´ ?œë‹¤.
        override fun instantiateItem(container: ViewGroup, position: Int): Any { // position : ??ª©???¸ë±??
            pager.addView(view_list[position]) // 1. ?˜ê? isViewFromObject??viewë¡??¤ì–´?¤ê³ 

            return view_list[position] // 2. ?˜ê? isViewFromObject??`object`ë¡??¤ì–´?¨ë‹¤.
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            pager.removeView(`object` as View?)
        }
    }
}