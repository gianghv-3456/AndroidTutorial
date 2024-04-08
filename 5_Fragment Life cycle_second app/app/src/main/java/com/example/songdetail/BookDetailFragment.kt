package com.example.songdetail

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_BOOK_DETAIL = "book_detail_arg_key"
private const val ARG_BOOK_NAME = "book_name_arg_key"

/**
 * A simple [Fragment] subclass.
 * Use the [BookDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookDetailFragment : Fragment() {
    private var bookDetail: String? = null
    private var bookName: String? = null
    private var toolbar: androidx.appcompat.widget.Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bookDetail = it.getString(ARG_BOOK_DETAIL)
            bookName = it.getString(ARG_BOOK_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_book_detail, container, false)
        val bookNameTV = v.findViewById<TextView>(R.id.book_name_tv)
        val bookDetailTV = v.findViewById<TextView>(R.id.book_detail_tv)

        toolbar = v.findViewById(R.id.topAppBar)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            //setup back navigation button
            if (toolbar != null) {
                Log.d("HEHE", "PORTRAIT")
                toolbar?.setNavigationOnClickListener {
                    activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()

                }
            }
        }

        bookNameTV.text = bookName
        bookDetailTV.text = bookDetail

        return v
    }

    override fun onDetach() {
        super.onDetach()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    @SuppressLint("CommitTransaction")
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val newFragmentInstance =
            newInstance(bookName.toString(), bookDetail.toString())
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.detail_fragment_container, newFragmentInstance)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BookDetailFragment.
         */
        @JvmStatic
        fun newInstance(bookName: String, bookDetail: String) = BookDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_BOOK_NAME, bookName)
                putString(ARG_BOOK_DETAIL, bookDetail)
            }
        }
    }
}