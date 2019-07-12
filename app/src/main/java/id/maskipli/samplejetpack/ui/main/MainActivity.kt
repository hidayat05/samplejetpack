package id.maskipli.samplejetpack.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.maskipli.samplejetpack.utils.LineItemDecoration
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.maskipli.samplejetpack.R
import id.maskipli.samplejetpack.adapters.ArticleAdapter
import id.maskipli.samplejetpack.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var articleAdapter: ArticleAdapter
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil
                .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .also { viewBinding = it }
        lifecycle.addObserver(viewModel)
        initView()
        observeData()
    }

    private fun initView() {
        viewBinding.apply {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrBlank()) {
                        viewModel.search(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })

            articleAdapter = ArticleAdapter { article ->
                // todo open detail
            }
            recyclerViewContent.apply {
                adapter = articleAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(LineItemDecoration(this@MainActivity))
            }
        }
    }

    private fun observeData() {
        viewModel.apply {

            loading.observe(this@MainActivity, Observer {

            })

            errorMessages?.observe(this@MainActivity, Observer {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
            })

            listArticle.observe(this@MainActivity, Observer {
                articleAdapter.submitList(it)
                articleAdapter.notifyDataSetChanged()
            })
            requestState?.observe(this@MainActivity, Observer {
                articleAdapter.setRequestState(it)
            })
        }
    }
}
