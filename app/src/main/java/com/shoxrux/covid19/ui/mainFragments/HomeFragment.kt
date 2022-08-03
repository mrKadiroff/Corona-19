package com.shoxrux.covid19.ui.mainFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.shoxrux.covid19.R
import com.shoxrux.covid19.databinding.FragmentHomeBinding
import com.shoxrux.covid19.models.Article
import com.shoxrux.covid19.ui.adapters.PreventAdapter
import com.shoxrux.covid19.utils.DataHandler
import com.shoxrux.covid19.viewmodel.OnlineViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    val viewModel: OnlineViewModel by viewModels()
    private val TAG = "HomeFragment"
    lateinit var binding: FragmentHomeBinding
    lateinit var preventAdapter: PreventAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        setRv()

        return binding.root
    }

    private fun setRv() {
        viewModel.topHeadlines.observe(viewLifecycleOwner, { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    Log.d(TAG, "onCreate: ${dataHandler.data?.articles}")
                    preventAdapter = PreventAdapter(dataHandler.data!!.articles,object:PreventAdapter.OnItemClickListener{
                        override fun onItemClick(malumotlar: Article) {

                        }

                    })
                    binding.rvPrev.adapter = preventAdapter

                }
                is DataHandler.ERROR -> {
                    Log.d(TAG, "onCreate: ${dataHandler.message}")
                }
                is DataHandler.LOADING -> {


                }
            }

        })
        viewModel.getTopHeadlines()
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).binding.toolbarMain.title = ""
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}