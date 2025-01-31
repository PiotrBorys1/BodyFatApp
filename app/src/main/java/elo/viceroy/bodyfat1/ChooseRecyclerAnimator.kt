package elo.viceroy.bodyfat1

import android.content.Context
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView

class ChooseRecyclerAnimator(val context:Context): RecyclerView.ItemAnimator() {
    override fun animateDisappearance(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo?
    ): Boolean {

        return false
    }

    override fun animateAppearance(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo?,
        postLayoutInfo: ItemHolderInfo
    ): Boolean {
        val animation= AnimationUtils.loadAnimation(context,R.anim.anim_fade_in)
        animation.duration=(viewHolder.adapterPosition*700+500).toLong()
        if(viewHolder is adapterForChoseButtons.myViewHolder) {
            viewHolder.button.startAnimation(animation)
        }
        return true
    }

    override fun animatePersistence(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo
    ): Boolean {
        return true
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo
    ): Boolean {
        return true
    }

    override fun runPendingAnimations() {

    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {

    }

    override fun endAnimations() {

    }

    override fun isRunning(): Boolean {
        return true
    }
}