package com.ghf.email.listner;

import com.ghf.email.dao.NotificationDao;
import com.ghf.email.exceptions.GHFException;
import com.ghf.email.model.EmailData;
import com.ghf.email.model.EmailInfo;
import com.ghf.email.model.Notification;
import com.ghf.email.repository.EmailInfoRepository;
import com.ghf.email.services.impl.EmailProcessService;
import com.ghf.email.util.Constants;
import com.ghf.email.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
public class EmailNotificationListner {

    @Autowired
    private EmailProcessService jewelleryShopService;
	@Autowired
	private EmailInfoRepository emailInfoRepository;

	@RabbitListener(queues = "${email.rabbitmq.queue}")
	@Retryable(maxAttempts=4, value= GHFException.class)
	public void onMessage(final Message message) {
		try {
			Notification notification = Utilities.mapEmailNotification(message);
			log.debug("Email notification is mapped with message data : {} ", Utilities.convertObjectToRequestString(notification));
			EmailData emailData = new EmailData();
			NotificationDao notificationDao = new NotificationDao(emailInfoRepository);
			List<EmailInfo> emailInfoList = notificationDao.findByEmailId(notification.getRecipientEmail());
			Integer size = emailInfoList.size();
			emailData.setSizeOfHalfAnHour(size);
			List<EmailInfo> emailInfoList1 = notificationDao.findByEmailId(notification.getRecipientEmail(), notification.getSubject());
			size = emailInfoList1.size();
			emailData.setSizeOfWithinADay(size);
			jewelleryShopService.sendEmail(emailData, notification);
		} catch (Exception e) {
			log.error("Failed to process data sending request: {}, exception: {}", message, e);
			throw new GHFException(String.format("%s%s%s", Constants.FAILED_PROCESS, Constants.FAILED_REQUEST_DATA, e.getMessage()));
		}
	}
}