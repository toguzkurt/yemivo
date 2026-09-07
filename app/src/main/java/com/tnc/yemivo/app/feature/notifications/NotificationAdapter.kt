package com.tnc.yemivo.app.feature.notifications

import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.message
import com.tnc.yemivo.app.feature.common.relativeTime
import com.tnc.yemivo.app.feature.common.title
import com.tnc.yemivo.databinding.ItemNotificationBinding
import com.tnc.core.base.BaseAdapter
import com.tnc.domain.notification.model.AppNotification
import com.tnc.domain.notification.model.NotificationType

class NotificationAdapter(
    onItemClick: (AppNotification) -> Unit
) : BaseAdapter<AppNotification, ItemNotificationBinding>(
    bindingInflater = ItemNotificationBinding::inflate,
    diffCallback = object : DiffUtil.ItemCallback<AppNotification>() {

        override fun areItemsTheSame(
            oldItem: AppNotification,
            newItem: AppNotification
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: AppNotification,
            newItem: AppNotification
        ) = oldItem == newItem

    },
    onItemClick = onItemClick
) {

    override fun onBind(
        binding: ItemNotificationBinding,
        item: AppNotification,
        position: Int
    ) = with(binding) {

        tvTitle.text = item.title(root.context)
        tvMessage.text = item.message(root.context)
        tvTime.text = item.relativeTime(root.context)

        ivIcon.setImageResource(
            when (item.type) {
                NotificationType.DOWNLOAD_COMPLETE -> R.drawable.ic_download
                NotificationType.DAILY_RECIPE -> R.drawable.ic_bell
            }
        )

        ivUnreadDot.isVisible = !item.isRead

    }

}
