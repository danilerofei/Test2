package com.test.test2.ui.bottomnav

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.test.test2.R
import com.test.test2.interfaces.IBubbleNavigation
import com.test.test2.interfaces.IBubbleNavigationChangeListener


class BubbleNavigationConstraintView : ConstraintLayout, View.OnClickListener, IBubbleNavigation {

    internal enum class DisplayMode {
        SPREAD, INSIDE, PACKED
    }

    //constants
    private val TAG = "BNLView"
    private val MIN_ITEMS = 2
    private val MAX_ITEMS = 5

    private var bubbleNavItems: ArrayList<BubbleToggleView>? = null
    private var navigationChangeListener: IBubbleNavigationChangeListener? = null

    private var currentActiveItemPosition = 0
    private var loadPreviousState = false

    //default display mode
    private var displayMode = DisplayMode.SPREAD

    private var currentTypeface: Typeface? = null

    private var pendingBadgeUpdate: SparseArray<String>? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        bundle.putInt("current_item", currentActiveItemPosition)
        bundle.putBoolean("load_prev_state", true)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var state = state
        if (state is Bundle) {
            val bundle = state
            currentActiveItemPosition = bundle.getInt("current_item")
            loadPreviousState = bundle.getBoolean("load_prev_state")
            state = bundle.getParcelable("superState")
        }
        super.onRestoreInstanceState(state)
    }

    /////////////////////////////////////////
    // PRIVATE METHODS
    /////////////////////////////////////////

    /////////////////////////////////////////
    // PRIVATE METHODS
    /////////////////////////////////////////
    /**
     * Initialize
     *
     * @param context current context
     * @param attrs   custom attributes
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        var mode = 0
        if (attrs != null) {
            val ta: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.BubbleNavigationConstraintView,
                0,
                0
            )
            mode = try {
                ta.getInteger(R.styleable.BubbleNavigationConstraintView_bnc_mode, mode)
            } finally {
                ta.recycle()
            }
        }

        //sets appropriate display node
        if (mode >= 0 && mode < DisplayMode.values().size) displayMode =
            DisplayMode.values()[mode]
        post { updateChildNavItems() }
    }

    /**
     * Get the chain type from the display mode
     *
     * @param mode display mode
     * @return the constraint chain mode
     */
    private fun getChainTypeFromMode(mode: DisplayMode): Int {
        return when (mode) {
            DisplayMode.SPREAD -> ConstraintSet.CHAIN_SPREAD
            DisplayMode.INSIDE -> ConstraintSet.CHAIN_SPREAD_INSIDE
            DisplayMode.PACKED -> ConstraintSet.CHAIN_PACKED
        }
    }

    /**
     * Finds Child Elements of type [BubbleToggleView] and adds them to [.bubbleNavItems]
     */
    private fun updateChildNavItems() {
        bubbleNavItems = ArrayList()
        for (index in 0 until childCount) {
            val view = getChildAt(index)
            if (view is BubbleToggleView) bubbleNavItems!!.add(view) else {
                Log.w(TAG, "Cannot have child bubbleNavItems other than BubbleToggleView")
                return
            }
        }
        if (bubbleNavItems!!.size < MIN_ITEMS) {
            Log.w(
                TAG,
                "The bubbleNavItems list should have at least 2 bubbleNavItems of BubbleToggleView"
            )
        } else if (bubbleNavItems!!.size > MAX_ITEMS) {
            Log.w(
                TAG,
                "The bubbleNavItems list should not have more than 5 bubbleNavItems of BubbleToggleView"
            )
        }
        setClickListenerForItems()
        setInitialActiveState()
        updateMeasurementForItems()
        createChains()
        if (currentTypeface != null) setTypeface(currentTypeface)

        //update the badge count
        if (pendingBadgeUpdate != null && bubbleNavItems != null) {
            for (i in 0 until pendingBadgeUpdate!!.size()) setBadgeValue(
                pendingBadgeUpdate!!.keyAt(
                    i
                ), pendingBadgeUpdate!!.valueAt(i)
            )
            pendingBadgeUpdate!!.clear()
        }
    }

    /**
     * Creates the chains to spread the [.bubbleNavItems] based on the [.displayMode]
     */
    private fun createChains() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        val chainIdsList = IntArray(bubbleNavItems!!.size)
        val chainWeightList = FloatArray(bubbleNavItems!!.size)
        for (i in 0 until bubbleNavItems!!.size) {
            val id: Int = bubbleNavItems!![i].id
            chainIdsList[i] = id
            chainWeightList[i] = 0.0f
            //set the top and bottom constraint for each items
            constraintSet.connect(
                id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                0
            )
            constraintSet.connect(
                id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                0
            )
        }

        //create an horizontal chain
        constraintSet.createHorizontalChain(
            id, ConstraintSet.LEFT,
            id, ConstraintSet.RIGHT,
            chainIdsList, chainWeightList,
            getChainTypeFromMode(displayMode)
        )

        //apply the constraint
        constraintSet.applyTo(this)
    }

    /**
     * Makes sure that ONLY ONE child [.bubbleNavItems] is active
     */
    private fun setInitialActiveState() {
        if (bubbleNavItems == null) return
        var foundActiveElement = false

        // find the initial state
        if (!loadPreviousState) {
            for (i in 0 until bubbleNavItems!!.size) {
                if (bubbleNavItems!![i].isActive && !foundActiveElement) {
                    foundActiveElement = true
                    currentActiveItemPosition = i
                } else {
                    bubbleNavItems!![i].setInitialState(false)
                }
            }
        } else {
            for (i in 0 until bubbleNavItems!!.size) {
                bubbleNavItems!![i].setInitialState(false)
            }
        }
        //set the active element
        if (!foundActiveElement) bubbleNavItems!![currentActiveItemPosition].setInitialState(true)
    }

    /**
     * Update the measurements of the child components [.bubbleNavItems]
     */
    private fun updateMeasurementForItems() {
        val numChildElements: Int = bubbleNavItems!!.size
        if (numChildElements > 0) {
            val calculatedEachItemWidth =
                (measuredWidth - (paddingRight + paddingLeft)) / numChildElements
            bubbleNavItems?.forEach { btv ->
                btv.updateMeasurements(calculatedEachItemWidth)
            }
        }
    }

    private fun setClickListenerForItems() {
        bubbleNavItems?.forEach { btv ->
            btv.setOnClickListener(this)
        }
    }

    /**
     * Gets the Position of the Child from [.bubbleNavItems] from its id
     *
     * @param id of view to be searched
     * @return position of the Item
     */
    private fun getItemPositionById(id: Int): Int {
        for (i in 0 until bubbleNavItems!!.size) if (id == bubbleNavItems!![i].id) return i
        return -1
    }

    ///////////////////////////////////////////
    // PUBLIC METHODS
    ///////////////////////////////////////////

    ///////////////////////////////////////////
    // PUBLIC METHODS
    ///////////////////////////////////////////
    /**
     * Set the navigation change listener [BubbleNavigationChangeListener]
     *
     * @param navigationChangeListener sets the passed parameters as listener
     */
    override fun setNavigationChangeListener(navigationChangeListener: IBubbleNavigationChangeListener) {
        this.navigationChangeListener = navigationChangeListener
    }

    /**
     * Set the [Typeface] for the Text Elements of the View
     *
     * @param typeface to be used
     */
    override fun setTypeface(typeface: Typeface?) {
        if (bubbleNavItems != null) {
            bubbleNavItems?.forEach { btv ->
                btv.setTitleTypeface(typeface)
            }
        } else {
            currentTypeface = typeface
        }
    }

    /**
     * Gets the current active position
     *
     * @return active item position
     */
    override fun getCurrentActiveItemPosition(): Int {
        return currentActiveItemPosition
    }

    /**
     * Sets the current active item
     *
     * @param position current position change
     */
    override fun setCurrentActiveItem(position: Int) {
        if (bubbleNavItems == null) {
            currentActiveItemPosition = position
            return
        }
        if (currentActiveItemPosition == position) return
        if (position < 0 || position >= bubbleNavItems!!.size) return
        val btv: BubbleToggleView = bubbleNavItems!![position]
        btv.performClick()
    }

    /**
     * Sets the badge value
     *
     * @param position current position change
     * @param value    value to be set in the badge
     */
    override fun setBadgeValue(position: Int, value: String?) {
        if (bubbleNavItems != null) {
            val btv: BubbleToggleView = bubbleNavItems!![position]
            btv.setBadgeText(value)
        } else {
            if (pendingBadgeUpdate == null) pendingBadgeUpdate = SparseArray()
            pendingBadgeUpdate!!.put(position, value)
        }
    }

    override fun onClick(v: View) {
        val changedPosition = getItemPositionById(v.id)
        if (changedPosition >= 0) {
            if (changedPosition == currentActiveItemPosition) {
                return
            }
            val currentActiveToggleView: BubbleToggleView =
                bubbleNavItems!![currentActiveItemPosition]
            val newActiveToggleView: BubbleToggleView = bubbleNavItems!![changedPosition]
            currentActiveToggleView.toggle()
            newActiveToggleView.toggle()

            //changed the current active position
            currentActiveItemPosition = changedPosition
            navigationChangeListener?.onNavigationChanged(v, currentActiveItemPosition)
        } else {
            Log.w(TAG, "Selected id not found! Cannot toggle")
        }
    }
}