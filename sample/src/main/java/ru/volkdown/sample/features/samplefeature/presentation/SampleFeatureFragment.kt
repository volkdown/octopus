package ru.volkdown.sample.features.samplefeature.presentation

import ru.volkdown.sample.R
import ru.volkdown.sample.base.BaseFragment

class SampleFeatureFragment : BaseFragment<SampleFeaturePresenter>(){

    override val layoutResId: Int = R.layout.fragment_sample_feature

    override val presenter: SampleFeaturePresenter?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun providePresenter(): SampleFeaturePresenter? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object{

        fun newInstance() = SampleFeatureFragment()
    }
}