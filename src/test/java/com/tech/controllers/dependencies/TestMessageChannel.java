/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

https://github.com/rstoyanchev/spring-websocket-portfolio/blob/master/src/test/java/org/springframework/samples/portfolio/web/standalone/TestMessageChannel.java

 */
package com.tech.controllers.dependencies;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.AbstractSubscribableChannel;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author KuroiTenshi
 */
public class TestMessageChannel extends AbstractSubscribableChannel{
    private final List<Message<?>> messages = new ArrayList<>();

	public List<Message<?>> getMessages() {
		return this.messages;
	}

	@Override
	protected boolean sendInternal(Message<?> message, long timeout) {
		this.messages.add(message);
		return true;
	}
}
