package com.jkk.aihome.aspect;

import com.jkk.aihome.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ReceiveMsgNotPCSendAspect {
	@Around("@annotation(com.jkk.aihome.aspect.ReceiveMsgNotPCSend)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		MqttMessage mqttMessage = (MqttMessage) args[1];
		if (!MessageUtil.judgeSendByPC(new String(mqttMessage.getPayload()))) {
			try {
				return pjp.proceed(args);
			}catch (Exception e) {
				log.error("订阅服务发生异常", e);
			}

		}

		return null;
	}
}
