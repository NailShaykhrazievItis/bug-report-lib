package com.itis.testhelper.ui.bugreport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.testhelper.R
import com.itis.testhelper.model.bug.Step
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_step.*

class StepsAdapter(
        private var steps: ArrayList<Step>,
        private val removeStepLambda: (Int) -> Unit,
        private val itemClickLambda: (Int, Step) -> Unit
) : RecyclerView.Adapter<StepsAdapter.StepsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsHolder =
            StepsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_step, parent, false))

    override fun onBindViewHolder(holder: StepsHolder, position: Int) {
        holder.bind(steps[position], removeStepLambda, itemClickLambda)
    }

    override fun getItemCount(): Int = steps.size

    fun removeItem(position: Int) {
        steps.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, steps.size)
    }

    fun addItem(step: Step) {
        steps.add(step)
        notifyItemInserted(steps.size)
    }

    fun changeItem(position: Int, step: Step) {
        steps[position] = step
        notifyItemChanged(position)
    }

    class StepsHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(step: Step, removeStepLambda: (Int) -> Unit, itemClickLambda: (Int, Step) -> Unit) {
            tv_step.text = step.name
            tv_position.text = (adapterPosition + 1).toString().plus(")")
            iv_close.setOnClickListener { removeStepLambda(adapterPosition) }
            containerView.setOnClickListener { itemClickLambda(adapterPosition, step) }
        }
    }
}