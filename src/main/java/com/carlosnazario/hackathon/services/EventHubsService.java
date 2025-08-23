package com.carlosnazario.hackathon.services;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.carlosnazario.hackathon.models.Simulacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "eventhubs.enabled", havingValue = "true")
public class EventHubsService {

    private static final Logger logger = LoggerFactory.getLogger(EventHubsService.class);
    private final EventHubProducerClient producerClient; // Injeta o cliente diretamente
    private final ObjectMapper objectMapper;

    @Autowired
    public EventHubsService(EventHubProducerClient producerClient, ObjectMapper objectMapper) {
        this.producerClient = producerClient;
        this.objectMapper = objectMapper;
    }

    public void sendSimulationEvent(Simulacao simulacao) {
        try {
            String simulationJson = objectMapper.writeValueAsString(simulacao);

            EventDataBatch eventDataBatch = producerClient.createBatch();
            eventDataBatch.tryAdd(new EventData(simulationJson));

            producerClient.send(eventDataBatch);
            logger.info("Evento de simulação enviado com sucesso. ID da Simulação: {}", simulacao.getIdSimulacao());

        } catch (JsonProcessingException e) {
            logger.error("Erro ao serializar o objeto Simulacao para JSON", e);
        } catch (Exception e) {
            logger.error("Erro ao enviar evento para o Event Hubs", e);
        }
    }
}
