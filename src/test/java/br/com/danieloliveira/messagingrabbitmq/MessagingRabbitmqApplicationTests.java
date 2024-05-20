package br.com.danieloliveira.messagingrabbitmq;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import br.com.danieloliveira.messagingrabbitmq.config.RabbitmqConfig;
import br.com.danieloliveira.messagingrabbitmq.receiver.Receiver;
import br.com.danieloliveira.messagingrabbitmq.runner.Runner;

@SpringBootTest
class MessagingRabbitmqApplicationTests {
    @MockBean
    private Runner runner;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Receiver receiver;

    @Test
    public void test() throws Exception {
        try {
            rabbitTemplate.convertAndSend(RabbitmqConfig.queueName, "Hello from RabbitMQ!");
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        } catch (AmqpConnectException e) {
            // ignore - rabbit is not running
        }
    }
}
