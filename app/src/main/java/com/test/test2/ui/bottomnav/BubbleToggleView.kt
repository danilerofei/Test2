package com.test.test2.ui.bottomnav

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.test.test2.R
import com.test.test2.data.BubbleToggleItem
import com.test.test2.utils.ViewUtils


class BubbleToggleView : RelativeLayout {
    private var bubbleToggleItem: BubbleToggleItem? = null

    /**
     * Get the current state of the view
     *
     * @return the current state
     */
    var isActive = false
        private set
    private var iconView: ImageView? = null
    private var titleView: TextView? = null
    private var badgeView: TextView? = null
    private var animationDuration = 0
    private var showShapeAlways = false
    private var maxTitleWidth = 0f
    private var measuredTitleWidth = 0f

    /**
     * Constructors
     */
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

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }
    /////////////////////////////////////
    // PRIVATE METHODS
    /////////////////////////////////////
    /**
     * Initialize
     *
     * @param context current context
     * @param attrs   custom attributes
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        //initialize default component
        var title = "Title"
        var icon: Drawable? = null
        var shape: Drawable? = null
        var shapeColor = Int.MIN_VALUE

        var colorActive: Int = ViewUtils.getThemeAccentColor(context)
        var colorInactive = ContextCompat.getColor(context, R.color.default_inactive_color)
        var titleSize: Float =
            context.resources.getDimension(R.dimen.default_nav_item_text_size)
        maxTitleWidth =
            context.resources.getDimension(R.dimen.default_nav_item_title_max_width)
        var iconWidth: Float = context.resources.getDimension(R.dimen.default_icon_size)
        var iconHeight: Float = context.resources.getDimension(R.dimen.default_icon_size)
        var internalPadding =
            context.resources.getDimension(R.dimen.default_nav_item_padding).toInt()
        var titlePadding =
            context.resources.getDimension(R.dimen.default_nav_item_text_padding).toInt()
        var badgeTextSize =
            context.resources.getDimension(R.dimen.default_nav_item_badge_text_size).toInt()
        var badgeBackgroundColor =
            ContextCompat.getColor(context, R.color.default_badge_background_color)
        var badgeTextColor = ContextCompat.getColor(context, R.color.default_badge_text_color)
        var badgeText: String? = null
        if (attrs != null) {
            val ta: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.BubbleToggleView, 0, 0)
            try {
                icon = ta.getDrawable(R.styleable.BubbleToggleView_bt_icon)
                iconWidth = ta.getDimension(R.styleable.BubbleToggleView_bt_iconWidth, iconWidth)
                iconHeight = ta.getDimension(R.styleable.BubbleToggleView_bt_iconHeight, iconHeight)
                shape = ta.getDrawable(R.styleable.BubbleToggleView_bt_shape)
                shapeColor = ta.getColor(R.styleable.BubbleToggleView_bt_shapeColor, shapeColor)
                showShapeAlways =
                    ta.getBoolean(R.styleable.BubbleToggleView_bt_showShapeAlways, false)
                title = ta.getString(R.styleable.BubbleToggleView_bt_title) ?: ""
                titleSize = ta.getDimension(R.styleable.BubbleToggleView_bt_titleSize, titleSize)
                colorActive = ta.getColor(R.styleable.BubbleToggleView_bt_colorActive, colorActive)
                colorInactive =
                    ta.getColor(R.styleable.BubbleToggleView_bt_colorInactive, colorInactive)
                isActive = ta.getBoolean(R.styleable.BubbleToggleView_bt_active, false)
                animationDuration =
                    ta.getInteger(R.styleable.BubbleToggleView_bt_duration, DEFAULT_ANIM_DURATION)
                internalPadding = ta.getDimension(
                    R.styleable.BubbleToggleView_bt_padding,
                    internalPadding.toFloat()
                ).toInt()
                titlePadding = ta.getDimension(
                    R.styleable.BubbleToggleView_bt_titlePadding,
                    titlePadding.toFloat()
                ).toInt()
                badgeTextSize = ta.getDimension(
                    R.styleable.BubbleToggleView_bt_badgeTextSize,
                    badgeTextSize.toFloat()
                ).toInt()
                badgeBackgroundColor = ta.getColor(
                    R.styleable.BubbleToggleView_bt_badgeBackgroundColor,
                    badgeBackgroundColor
                )
                badgeTextColor =
                    ta.getColor(R.styleable.BubbleToggleView_bt_badgeTextColor, badgeTextColor)
                badgeText = ta.getString(R.styleable.BubbleToggleView_bt_badgeText)
            } finally {
                ta.recycle()
            }
        }

        //set the default icon
        if (icon == null) icon = ContextCompat.getDrawable(context, R.drawable.default_icon)

        //set the default shape
        if (shape == null) shape =
            ContextCompat.getDrawable(context, R.drawable.transition_background_drawable)

        //create a default bubble item
        bubbleToggleItem = BubbleToggleItem(
            icon = icon,
            shape = shape,
            title = title,
            colorActive = colorActive,
            colorInactive = colorInactive,
            shapeColor = shapeColor,
            badgeText = badgeText,
            badgeTextColor = badgeTextColor,
            badgeBackgroundColor = badgeBackgroundColor,
            titleSize = titleSize,
            titlePadding = titlePadding,
            iconWidth = iconWidth,
            iconHeight = iconHeight,
            internalPadding = internalPadding,
            badgeTextSize = badgeTextSize.toFloat()
        )

        //set the gravity
        gravity = Gravity.CENTER

        //set the internal padding
        setPadding(
            bubbleToggleItem!!.internalPadding,
            bubbleToggleItem!!.internalPadding,
            bubbleToggleItem!!.internalPadding,
            bubbleToggleItem!!.internalPadding
        )
        post { //make sure the padding is added
            setPadding(
                bubbleToggleItem!!.internalPadding,
                bubbleToggleItem!!.internalPadding,
                bubbleToggleItem!!.internalPadding,
                bubbleToggleItem!!.internalPadding
            )
        }
        createBubbleItemView(context)
        setInitialState(isActive)
    }

    /**
     * Create the components of the bubble item view [.iconView] and [.titleView]
     *
     * @param context current context
     */
    private fun createBubbleItemView(context: Context) {

        //create the nav icon
        iconView = ImageView(context)
        iconView!!.id = ViewCompat.generateViewId()
        val lpIcon = LayoutParams(
            bubbleToggleItem!!.iconWidth.toInt(),
            bubbleToggleItem!!.iconHeight.toInt()
        )
        lpIcon.addRule(CENTER_VERTICAL, TRUE)
        iconView!!.layoutParams = lpIcon
        iconView!!.setImageDrawable(bubbleToggleItem!!.icon)

        //create the nav title
        titleView = TextView(context)
        val lpTitle = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        lpTitle.addRule(CENTER_VERTICAL, TRUE)
        lpTitle.addRule(
            END_OF,
            iconView!!.id
        )
        titleView!!.layoutParams = lpTitle
        titleView!!.isSingleLine = true
        titleView!!.setTextColor(bubbleToggleItem!!.colorActive)
        titleView!!.setText(bubbleToggleItem!!.title)
        titleView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, bubbleToggleItem!!.titleSize)
        //get the current measured title width
        titleView!!.visibility = VISIBLE
        //update the margin of the text view
        titleView!!.setPadding(
            bubbleToggleItem!!.titlePadding,
            0,
            bubbleToggleItem!!.titlePadding,
            0
        )
        //measure the content width
        titleView!!.measure(0, 0) //must call measure!
        measuredTitleWidth = titleView!!.measuredWidth.toFloat() //get width
        //limit measured width, based on the max width
        if (measuredTitleWidth > maxTitleWidth) measuredTitleWidth = maxTitleWidth

        //change the visibility
        titleView!!.visibility = GONE
        addView(iconView)
        addView(titleView)
        updateBadge(context)

        //set the initial state
        setInitialState(isActive)
    }

    /**
     * Adds or removes the badge
     */
    private fun updateBadge(context: Context) {
        //remove the previous badge view
        if (badgeView != null) removeView(badgeView)
        if (bubbleToggleItem!!.badgeText == null) return

        //create badge
        badgeView = TextView(context)
        val lpBadge = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        iconView?.let {
            lpBadge.addRule(ALIGN_TOP, it.id)
            lpBadge.addRule(ALIGN_END, it.id)
        }
        badgeView!!.layoutParams = lpBadge
        badgeView!!.isSingleLine = true
        badgeView!!.setTextColor(bubbleToggleItem!!.badgeTextColor)
        badgeView!!.setText(bubbleToggleItem!!.badgeText)
        badgeView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, bubbleToggleItem!!.badgeTextSize)
        badgeView!!.gravity = Gravity.CENTER
        val drawable = ContextCompat.getDrawable(context, R.drawable.badge_background_white)
        drawable?.let {
            ViewUtils.updateDrawableColor(it, bubbleToggleItem!!.badgeBackgroundColor)
        }
        badgeView!!.background = drawable
        val badgePadding =
            context.resources.getDimension(R.dimen.default_nav_item_badge_padding).toInt()
        //update the margin of the text view
        badgeView!!.setPadding(badgePadding, 0, badgePadding, 0)
        //measure the content width
        badgeView!!.measure(0, 0)
        if (badgeView!!.measuredWidth < badgeView!!.measuredHeight) badgeView!!.width =
            badgeView!!.measuredHeight
        addView(badgeView)
    }
    /////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////
    /**
     * Updates the Initial State
     *
     * @param isActive current state
     */
    fun setInitialState(isActive: Boolean) {
        //set the background
        background = bubbleToggleItem!!.shape
        if (isActive) {
            ViewUtils.updateDrawableColor(iconView!!.drawable, bubbleToggleItem!!.colorActive)
            this.isActive = true
            titleView!!.visibility = VISIBLE
            if (background is TransitionDrawable) {
                val trans = background as TransitionDrawable
                trans.startTransition(0)
            } else {
                if (!showShapeAlways && bubbleToggleItem!!.shapeColor != Int.MIN_VALUE) {
                    ViewUtils.updateDrawableColor(
                        bubbleToggleItem!!.shape!!,
                        bubbleToggleItem!!.shapeColor
                    )
                }
            }
        } else {
            ViewUtils.updateDrawableColor(iconView!!.drawable, bubbleToggleItem!!.colorInactive)
            this.isActive = false
            titleView!!.visibility = GONE
            if (!showShapeAlways) {
                if (background !is TransitionDrawable) {
                    background = null
                } else {
                    val trans = background as TransitionDrawable
                    trans.resetTransition()
                }
            }
        }
    }

    /**
     * Toggles between Active and Inactive state
     */
    fun toggle() {
        if (!isActive) activate() else deactivate()
    }

    /**
     * Set Active state
     */
    fun activate() {
        ViewUtils.updateDrawableColor(iconView!!.drawable, bubbleToggleItem!!.colorActive)
        isActive = true
        titleView!!.visibility = VISIBLE
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = animationDuration.toLong()
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            titleView!!.width = (measuredTitleWidth * value).toInt()
            //end of animation
            if (value >= 1.0f) {
                //do something
            }
        }
        animator.start()
        if (background is TransitionDrawable) {
            val trans = background as TransitionDrawable
            trans.startTransition(animationDuration)
        } else {
            //if not showing Shape Always and valid shape color present, use that as tint
            if (!showShapeAlways && bubbleToggleItem!!.shapeColor != Int.MIN_VALUE) {
                ViewUtils.updateDrawableColor(
                    bubbleToggleItem!!.shape!!,
                    bubbleToggleItem!!.shapeColor
                )
            }
            background = bubbleToggleItem!!.shape
        }
    }

    /**
     * Set Inactive State
     */
    fun deactivate() {
        ViewUtils.updateDrawableColor(iconView!!.drawable, bubbleToggleItem!!.colorInactive)
        isActive = false
        val animator = ValueAnimator.ofFloat(1f, 0f)
        animator.duration = animationDuration.toLong()
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            titleView!!.width = (measuredTitleWidth * value).toInt()
            //end of animation
            if (value <= 0.0f) titleView!!.visibility = GONE
        }
        animator.start()
        if (background is TransitionDrawable) {
            val trans = background as TransitionDrawable
            trans.reverseTransition(animationDuration)
        } else {
            if (!showShapeAlways) background = null
        }
    }

    /**
     * Sets the [Typeface] of the [.titleView]
     *
     * @param typeface to be used
     */
    fun setTitleTypeface(typeface: Typeface?) {
        titleView!!.typeface = typeface
    }

    /**
     * Updates the measurements and fits the view
     *
     * @param maxWidth in pixels
     */
    fun updateMeasurements(maxWidth: Int) {
        var marginLeft = 0
        var marginRight = 0
        val titleViewLayoutParams = titleView!!.layoutParams
        if (titleViewLayoutParams is LayoutParams) {
            marginLeft = titleViewLayoutParams.rightMargin
            marginRight = titleViewLayoutParams.leftMargin
        }
        val newTitleWidth = ((maxWidth
                - (paddingRight + paddingLeft)
                - (marginLeft + marginRight)
                - bubbleToggleItem!!.iconWidth.toInt())
                + titleView!!.paddingRight + titleView!!.paddingLeft)

        //if the new calculate title width is less than current one, update the titleView specs
        if (newTitleWidth > 0 && newTitleWidth < measuredTitleWidth) {
            measuredTitleWidth = titleView!!.measuredWidth.toFloat()
        }
    }

    /**
     * Set value to the Badge's
     *
     * @param value as String, null to hide
     */
    fun setBadgeText(value: String?) {
        bubbleToggleItem?.badgeText = value
        updateBadge(context)
    }

    companion object {
        private const val TAG = "BNI_View"
        private const val DEFAULT_ANIM_DURATION = 300
    }
}