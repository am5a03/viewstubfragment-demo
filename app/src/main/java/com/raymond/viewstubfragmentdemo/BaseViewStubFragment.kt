package com.raymond.viewstubfragmentdemo

import android.support.v4.app.Fragment
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.view.ViewStub
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar


abstract class BaseViewStubFragment : Fragment() {
    private var mSavedInstanceState: Bundle? = null
    private var hasInflated = false
    private var mViewStub: ViewStub? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_viewstub, container, false)
        mViewStub = view.findViewById(R.id.fragmentViewStub) as ViewStub
        mViewStub!!.layoutResource = getViewStubLayoutResource()
        mSavedInstanceState = savedInstanceState

        if (userVisibleHint && !hasInflated) {
            val inflatedView = mViewStub!!.inflate()
            onCreateViewAfterViewStubInflated(inflatedView, mSavedInstanceState)
            afterViewStubInflated(view)
        }

        return view
    }

    protected abstract fun onCreateViewAfterViewStubInflated(inflatedView: View, savedInstanceState: Bundle?)

    /**
     * The layout ID associated with this ViewStub
     * @see ViewStub.setLayoutResource
     * @return
     */
    @LayoutRes
    protected abstract fun getViewStubLayoutResource(): Int

    /**
     *
     * @param originalViewContainerWithViewStub
     */
    @CallSuper
    protected fun afterViewStubInflated(originalViewContainerWithViewStub: View?) {
        hasInflated = true
        if (originalViewContainerWithViewStub != null) {
            val pb = originalViewContainerWithViewStub.findViewById<ProgressBar>(R.id.inflateProgressbar)
            pb.visibility = View.GONE
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser && mViewStub != null && !hasInflated) {
            val inflatedView = mViewStub!!.inflate()
            onCreateViewAfterViewStubInflated(inflatedView, mSavedInstanceState)
            afterViewStubInflated(view)
        }
    }

    // Thanks to Noa Drach, this will fix the orientation change problem
    override fun onDetach() {
        super.onDetach()
        hasInflated = false
    }
}