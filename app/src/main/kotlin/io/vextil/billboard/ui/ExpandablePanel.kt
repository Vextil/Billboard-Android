package io.vextil.billboard.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout

import io.vextil.billboard.R

class ExpandablePanel
/**
 * The constructor simply validates the arguments being passed in and
 * sets the global variables accordingly. Required attributes are
 * 'handle' and 'content'
 */
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    private val mHandleId: Int
    private val mContentId: Int

    // Contains references to the handle and content views
    private var mHandle: View? = null
    private var mContent: View? = null

    // Does the panel start expanded?
    private var mExpanded = false
    // The height of the content when collapsed
    private var mCollapsedHeight = 0
    // The full expanded height of the content (calculated)
    private var mContentHeight = 0
    // How long the expand animation takes
    private var mAnimationDuration = 0

    // Listener that gets fired onExpand and onCollapse
    private var mListener: OnExpandListener? = null

    init {
        mListener = DefaultOnExpandListener()

        val a = context.obtainStyledAttributes(
                attrs, R.styleable.ExpandablePanel, 0, 0)

        // How high the content should be in "collapsed" state
        mCollapsedHeight = a.getDimension(
                R.styleable.ExpandablePanel_collapsedHeight, 0.0f).toInt()

        // How long the animation should take
        mAnimationDuration = a.getInteger(
                R.styleable.ExpandablePanel_animationDuration, 500)

        val handleId = a.getResourceId(
                R.styleable.ExpandablePanel_handle, 0)

        if (handleId == 0) {
            throw IllegalArgumentException(
                    "The handle attribute is required and must refer " + "to a valid child.")
        }

        val contentId = a.getResourceId(
                R.styleable.ExpandablePanel_content, 0)
        if (contentId == 0) {
            throw IllegalArgumentException(
                    "The content attribute is required and must " + "refer to a valid child.")
        }

        mHandleId = handleId
        mContentId = contentId

        a.recycle()
    }

    // Some public setters for manipulating the
    // ExpandablePanel programmatically
    fun setOnExpandListener(listener: OnExpandListener) {
        mListener = listener
    }

    fun setCollapsedHeight(collapsedHeight: Int) {
        mCollapsedHeight = collapsedHeight
    }

    fun setAnimationDuration(animationDuration: Int) {
        mAnimationDuration = animationDuration
    }

    /**
     * This method gets called when the View is physically
     * visible to the user
     */
    override fun onFinishInflate() {
        super.onFinishInflate()

        mHandle = findViewById(mHandleId)
        if (mHandle == null) {
            throw IllegalArgumentException(
                    "The handle attribute is must refer to an" + " existing child.")
        }

        mContent = findViewById(mContentId)
        if (mContent == null) {
            throw IllegalArgumentException(
                    "The content attribute must refer to an" + " existing child.")
        }

        // This changes the height of the content such that it
        // starts off collapsed
        val lp = mContent!!.layoutParams
        lp.height = mCollapsedHeight
        mContent!!.layoutParams = lp

        // Set the OnClickListener of the handle view
        mHandle!!.setOnClickListener(PanelToggler())
    }

    /**
     * This is where the magic happens for measuring the actual
     * (un-expanded) height of the content. If the actual height
     * is less than the collapsedHeight, the handle will be hidden.
     */
    override fun onMeasure(widthMeasureSpec: Int,
                           heightMeasureSpec: Int) {
        // First, measure how high content wants to be
        mContent!!.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED)
        mContentHeight = mContent!!.measuredHeight
        Log.v("cHeight", mContentHeight.toString())
        Log.v("cCollapseHeight", mCollapsedHeight.toString())

        if (mContentHeight < mCollapsedHeight) {
            mHandle!!.visibility = View.GONE
        } else {
            mHandle!!.visibility = View.VISIBLE
        }

        // Then let the usual thing happen
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * This is the on click listener for the handle.
     * It basically just creates a new animation instance and fires
     * animation.
     */
    private inner class PanelToggler : OnClickListener {
        override fun onClick(v: View) {
            val a: Animation
            if (mExpanded) {
                a = ExpandAnimation(mContentHeight, mCollapsedHeight)
                mListener!!.onCollapse(mHandle, mContent)
            } else {
                a = ExpandAnimation(mCollapsedHeight, mContentHeight)
                mListener!!.onExpand(mHandle, mContent)
            }
            a.duration = mAnimationDuration.toLong()
            mContent!!.startAnimation(a)
            mExpanded = !mExpanded
        }
    }

    /**
     * This is a private animation class that handles the expand/collapse
     * animations. It uses the animationDuration attribute for the length
     * of time it takes.
     */
    private inner class ExpandAnimation(private val mStartHeight: Int, endHeight: Int) : Animation() {
        private val mDeltaHeight: Int

        init {
            mDeltaHeight = endHeight - mStartHeight
        }

        override fun applyTransformation(interpolatedTime: Float,
                                         t: Transformation) {
            val lp = mContent!!.layoutParams
            lp.height = (mStartHeight + mDeltaHeight * interpolatedTime).toInt()
            mContent!!.layoutParams = lp
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    /**
     * Simple OnExpandListener interface
     */
    interface OnExpandListener {
        fun onExpand(handle: View?, content: View?)
        fun onCollapse(handle: View?, content: View?)
    }

    private inner class DefaultOnExpandListener : OnExpandListener {
        override fun onCollapse(handle: View?, content: View?) {
        }

        override fun onExpand(handle: View?, content: View?) {
        }
    }

}