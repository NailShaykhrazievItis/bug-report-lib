package com.itis.testhelper.ui.bugreport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.testhelper.R
import com.itis.testhelper.model.Step
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_step.*

class StepsAdapter(
        private var steps: ArrayList<Step>,
        private val removeStepLambda: (Int) -> Unit
) : RecyclerView.Adapter<StepsAdapter.StepsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsHolder =
            StepsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_step, parent, false))

    override fun onBindViewHolder(holder: StepsHolder, position: Int) {
        holder.bind(steps[position], removeStepLambda)
    }

    override fun getItemCount(): Int = steps.size

    fun removeItem(position: Int) {
        steps.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, steps.size)
    }

    class StepsHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(step: Step, removeStepLambda: (Int) -> Unit) {
            tv_step.text = step.name
            tv_position.text = (adapterPosition + 1).toString()
            iv_close.setOnClickListener { removeStepLambda(adapterPosition) }
        }
    }
}