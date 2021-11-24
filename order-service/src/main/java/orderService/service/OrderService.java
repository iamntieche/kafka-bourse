package orderService.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import orderService.model.Order;
import orderService.model.OrderType;

@Component
public class OrderService {

	private Logger log = LoggerFactory.getLogger(OrderService.class);

	private static long orderId = 0;

	LinkedList<Order> buyOrders = new LinkedList<>(
			List.of(new Order(++orderId, 1, 1, 100, LocalDateTime.now(), OrderType.BUY, 1000),
					new Order(++orderId, 2, 1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
					new Order(++orderId, 3, 1, 100, LocalDateTime.now(), OrderType.BUY, 1030),
					new Order(++orderId, 4, 1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
					new Order(++orderId, 5, 1, 200, LocalDateTime.now(), OrderType.BUY, 1000),
					new Order(++orderId, 11, 1, 100, LocalDateTime.now(), OrderType.BUY, 1050)));

	LinkedList<Order> sellOrders = new LinkedList<>(
			List.of(new Order(++orderId, 6, 1, 200, LocalDateTime.now(), OrderType.SELL, 950),
					new Order(++orderId, 7, 1, 100, LocalDateTime.now(), OrderType.SELL, 1000),
					new Order(++orderId, 8, 1, 100, LocalDateTime.now(), OrderType.SELL, 1050),
					new Order(++orderId, 9, 1, 300, LocalDateTime.now(), OrderType.SELL, 1000),
					new Order(++orderId, 10, 1, 200, LocalDateTime.now(), OrderType.SELL, 1020)));

	@Bean
	public Supplier<Message<Order>> orderBuySupplier() {
		return () -> {
			if (buyOrders.peek() == null)
				return null;
			Message<Order> o = MessageBuilder.withPayload(buyOrders.peek())
					.setHeader(KafkaHeaders.MESSAGE_KEY, Objects.requireNonNull(buyOrders.poll()).getId()).build();
			log.info("Order: {} ", o.getPayload());
			return o;
		};
	}

	@Bean
	public Supplier<Message<Order>> orderSellSupplier() {
		return () -> {
			if (sellOrders.peek() == null)
				return null;

			Message<Order> o = MessageBuilder.withPayload(sellOrders.peek())
					.setHeader(KafkaHeaders.MESSAGE_KEY, Objects.requireNonNull(sellOrders.poll()).getId()).build();
			log.error("Order: {}", o.getPayload());
			return o;
		};
	}
}
