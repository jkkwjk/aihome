package com.jkk.aihome.aspect;

import com.jkk.aihome.util.MessageUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ReceiveMsgNotPCSendAspect {
	@Around("@annotation(com.jkk.aihome.aspect.ReceiveMsgNotPCSend)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		MqttMessage mqttMessage = (MqttMessage) args[1];
		if (!MessageUtil.judgeSendByPC(new String(mqttMessage.getPayload()))) {
			return pjp.proceed(args);
		}

		return null;
	}
}
