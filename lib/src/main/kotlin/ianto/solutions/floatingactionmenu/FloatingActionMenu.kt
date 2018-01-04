package ianto.solutions.floatingactionmenu

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.TextView


class FloatingActionMenu(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private val menuItems = mutableListOf<View>()
    private val mainFab: FloatingActionButton
    private val animationDuration: Long = 3000
    private var isExpanded = false

    init {
        val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, 0, 0)
        val mainFabDrawableRes: Int
        try {
            mainFabDrawableRes = attr.getInteger(R.styleable.FloatingActionMenu_srcMainFab, R.drawable.ic_add)
        } finally {
            attr.recycle()
        }
        val view = View.inflate(context, R.layout.main_layout, this)

        mainFab = view.findViewById(R.id.main_fab)
        mainFab.setImageDrawable(ContextCompat.getDrawable(context, mainFabDrawableRes))
        mainFab.setOnClickListener({ if (isExpanded) collapseMenu() else expandMenu() })
    }

    fun addMenuItem(@DrawableRes iconDrawableRes: Int? = null,
                    @StringRes iconLabelRes: Int? = null,
                    iconDrawable: Drawable? = null,
                    iconLabel: String? = null,
                    onItemSelected: (view: View) -> Unit): FloatingActionMenu {
        val menuDrawable: Drawable? = iconDrawable ?: iconDrawableRes?.let { ContextCompat.getDrawable(context, it) }
        val menuLabelText: String? = iconLabel ?: iconLabelRes?.let { resources.getString(it) }
        val menuItem = View.inflate(context, R.layout.menu_item_layout, null)
        val menuItemFab = menuItem.findViewById<FloatingActionButton>(R.id.menu_item_fab)
        val menuItemLabel = menuItem.findViewById<TextView>(R.id.menu_item_label)

        menuItemLabel.text = menuLabelText ?: "".also { menuItemLabel.visibility = View.GONE }

        if (menuLabelText == null) {
            menuItemLabel.text = menuLabelText
        } else {
            menuItemLabel.visibility = View.GONE
        }


        menuItem.setOnClickListener(onItemSelected)

        menuItem.alpha = 0f

        addView(menuItem)
        menuItems.add(menuItem)
        mainFab.post {
            positionOnMainFAB(menuItem)
        }
        return this
    }

    private fun positionOnMainFAB(menuItem: View) {
        menuItem.y = (mainFab.y - (mainFab.height / 2)) + ((menuItem.height) / 2)
        menuItem.x = (mainFab.x)// + (mainFab.width / 2)) - ((menuItem.width) / 2)
    }

    /**
     * */
    fun expandMenu() {
        if (menuItems.size == 0) {
            return
        }
        val expansionSpacer = 20f
        val baseLineY: Float = mainFab.y
        for (i in 0 until menuItems.size) {
            menuItems[i].animate().setDuration(animationDuration).y(baseLineY - ((menuItems[i].height + expansionSpacer) * (i + 1))).alpha(1f).start()
        }
        isExpanded = true
    }

    fun collapseMenu() {
        if (menuItems.size == 0) {
            return
        }
        val baseLineY: Float = (mainFab.y - (mainFab.height / 2)) + ((menuItems[0].height) / 2)
        for (i in 0 until menuItems.size) {
            menuItems[i].animate().setDuration(animationDuration).y(baseLineY).alpha(0f).start()
        }
        isExpanded = false
    }

}