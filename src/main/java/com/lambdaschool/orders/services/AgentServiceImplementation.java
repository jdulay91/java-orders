package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service(value = "agentService")
public class AgentServiceImplementation implements AgentService {

    @Autowired
    private AgentsRepository agentrepos;

    @Override
    public Agent findAgentById(long agentcode) {
        return agentrepos.findById(agentcode).orElseThrow(()-> new EntityNotFoundException("Agent with id:" + agentcode + " Has not been found"));
    }
}
