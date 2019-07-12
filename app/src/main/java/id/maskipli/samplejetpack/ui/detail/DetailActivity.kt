package id.maskipli.samplejetpack.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import id.maskipli.samplejetpack.R
import id.maskipli.samplejetpack.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
    }
}
